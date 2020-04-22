package com.example.filesystem;

import com.example.filesystem.controller.SocketController;
import com.example.filesystem.model.dto.Approval;
import com.example.filesystem.service.UserService;
import com.example.filesystem.util.DateUtil;
import com.example.filesystem.util.StringUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadFactory;


/**
 * @ClassName MyApplicationRunner
 * @Description TODO
 * @Author zq
 * Date 2020/2/16 15:29
 * Version 1.0
 */
@Component
public class ApplicationRunnerService implements CommandLineRunner {

    private static final Logger loggger = LoggerFactory.getLogger(ApplicationRunnerService.class);

    @Resource
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        serverCreate();
    }

    /**
     * 创建服务
     */
    public void serverCreate() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            InetAddress addr = InetAddress.getLocalHost();
            serverSocketChannel.socket().bind(new InetSocketAddress(addr.getHostAddress(), 12000));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().setReuseAddress(true);
            List<Approval> list = userService.selectAllApproval();
            loggger.info("服务端启动监听:" + addr.getHostAddress() + "--->>>" + DateUtil.dataToString(new Date()));
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    socketChannel.configureBlocking(false);
                    socketChannel.socket().setSoTimeout(10);
                    for (Approval approval : list) {
                        if (socketChannel.getRemoteAddress().toString().contains(approval.getUserName())) {
                            SocketController.appro.put(approval.getUserName(), socketChannel);
                            ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("开启审批端:" + approval.getUserName() + "接收线程").build();
                            Thread thread = threadFactory.newThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        reciveMsg(socketChannel, approval.getUserName());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            thread.start();
                            String name = thread.getName();
                            if (name.equals("开启审批端:1接收线程")) {
                            } else {
                                loggger.info("正常启动--->>" + name + "--->>>" + DateUtil.dataToString(new Date()));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            loggger.error("服务端启动项异常信息:" + e.getMessage() + "--->>>Time:" + DateUtil.dataToString(new Date()));
        }
    }

    /**
     * 接收数据
     */
    public void reciveMsg(SocketChannel socketChannel, String ip) {
        try {
            if (socketChannel != null) {
                while (true) {
                    Thread.sleep(1);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    socketChannel.read(byteBuffer);
                    byteBuffer.flip();
                    Charset charset = Charset.forName("UTF-8");
                    String str = charset.newDecoder().decode(byteBuffer.asReadOnlyBuffer()).toString();
                    // System.out.println("接收到返回值:" + str);
                    if (str.toUpperCase().contains("SUCCESS")) {
                        str = StringUtil.subString(str, "||", "SUCCESS||");
                        if (SocketController.approvalResultMap.containsKey(str)) {
                            SocketController.approvalResultMap.get(str).setResult("SUCCESS");
                        } else {
                        }
                    } else if (str.toUpperCase().contains("FAIL")) {
                        str = StringUtil.subString(str, "||", "FAIL||");
                        if (SocketController.approvalResultMap.containsKey(str)) {
                            SocketController.approvalResultMap.get(str).setResult("FAIL");
                        } else {
                        }
                    }
                }
            }
        } catch (IOException io) {
            if (io.getMessage().contains("远程主机强迫关闭了一个现有的连接")) {
                String error = ip + "断开连接";
                if (error.equals("1断开连接")) {
                } else {
                    loggger.error("审批端:" + ip + "断开连接 --->>>" + DateUtil.dataToString(new Date()));
                    for (String key : SocketController.approvalResultMap.keySet()) {
                        if (SocketController.approvalResultMap.get(key).getApprovalIp().equals(ip)) {
                            SocketController.approvalResultMap.get(key).setResult("CLOSED");
                        }
                    }
                }
            }
        } catch (Exception e) {
            loggger.error("接收数据线程异常信息:" + e.getMessage() + "-->>Time:" + DateUtil.dataToString(new Date()));
        }
    }
}
