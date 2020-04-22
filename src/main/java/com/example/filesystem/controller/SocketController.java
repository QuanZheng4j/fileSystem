package com.example.filesystem.controller;

import com.example.filesystem.annotations.ResponseResult;
import com.example.filesystem.model.ApprovalResult;
import com.example.filesystem.model.vo.CancelUser;
import com.example.filesystem.model.vo.OperationUser;
import com.example.filesystem.model.po.Operation;
import com.example.filesystem.service.OperationService;
import com.example.filesystem.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.*;

/**
 * @ClassName SocketController
 * @Description TODO
 * @Author zq
 * Date 2020/1/16 11:51
 * Version 1.0
 */
@ResponseResult
@RequestMapping("/socket")
@RestController
@Api(tags = "Socket相关接口 @ 郑权", description = "SocketController")
public class SocketController {
    private static final Logger logger = LoggerFactory.getLogger(SocketController.class);
    @Resource
    private OperationService operationService;

//    @Resource
//    private UserService userService;

    public static ConcurrentHashMap<String, SocketChannel> appro = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, ApprovalResult> approvalResultMap = new ConcurrentHashMap<>();

//    public void serverCreate() {
//        try {
//            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//            InetAddress addr = InetAddress.getLocalHost();
//            serverSocketChannel.socket().bind(new InetSocketAddress(addr.getHostAddress(), 12000));
//            serverSocketChannel.configureBlocking(false);
//            serverSocketChannel.socket().setReuseAddress(true);
//            List<Approval> list = userService.selectAllApproval();
//            System.out.println("服务端启动监听:" + addr.getHostAddress());
//            while (true) {
//                SocketChannel socketChannel = serverSocketChannel.accept();
//                if (socketChannel != null) {
//                    socketChannel.configureBlocking(false);
//                    socketChannel.socket().setSoTimeout(10);
//                    for (Approval approval : list) {
//                        if (socketChannel.getRemoteAddress().toString().contains(approval.getUserName())) {
//                            appro.put(approval.getUserName(), socketChannel);
//                            ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("开启审批端:" + approval.getUserName() + "接收线程").build();
//                            Thread thread = threadFactory.newThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        reciveMsg(socketChannel, approval.getUserName());
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//                            thread.start();
//                            String name = thread.getName();
//                            if (name.equals("开启审批端:1接收线程")) {
//                            } else {
//                                System.out.println("正常启动--->>" + name);
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @PostMapping("/requestSendFile")
    @ResponseBody
    @ApiOperation("申请发送文件")
    public String requestSendFile(@RequestBody OperationUser operationUser) {
        //|one|申请人:zq|two|IP地址:192.168.1.70|three|文件名:file|four|文件大小:100|five|申请原因:不知道|six|
        String str = "|one|申请人:" + operationUser.getName() + "|two|部门:" + operationUser.getDepartment() + "|three|文件名:" + operationUser.getFileName() + "|four|文件大小:" + operationUser.getFileSize() + "|five|申请原因:" + operationUser.getReason() + "|six|";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.clear();
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        try {
            SocketChannel socketChannel = appro.get(operationUser.getApprovalIP());
            if (socketChannel != null) {
                socketChannel.write(byteBuffer);
                ApprovalResult approvalResult = new ApprovalResult();
                approvalResult.setApprovalIp(operationUser.getApprovalIP());
                approvalResult.setResult("");
                approvalResultMap.put(operationUser.getName(), approvalResult);
                //  String result = reciveMsg(socketChannel, approvalIP);
                String result = getReceiveMsg(operationUser.getName());
                if (!result.equals("EXCEPTION") && !result.equals("CANCEL")) {
                    //插入审批结果
                    Operation operation = new Operation();
                    operation.setUserId(operationUser.getId());
                    operation.setFileName(operationUser.getFileName());
                    operation.setFileSize(operationUser.getFileSize());
                    operation.setReason(operationUser.getReason());
                    operation.setApprovalName(operationUser.getApprovalName());
                    if (result.equals("SUCCESS")) {
                        operation.setApprovalResult("同意");
                    } else if (result.equals("FAIL")) {
                        operation.setApprovalResult("拒绝");
                    }
                    operation.setApprovalTime(new Date());
                    operationService.addOperation(operation);
                }
                //这里要加一个result判断，拒绝or主动取消
                logger.info(operationUser.getApprovalName() + "'" +result + "'" + operationUser.getName() +"的上传文件请求--->>>" + DateUtil.dataToString(new Date()));
                return result;
            }
            return "NULL";
        } catch (Exception e) {
            logger.error(operationUser.getName() + "调用申请发送文件异常:" + e.getMessage() + "-->>Time:" +DateUtil.dataToString(new Date()));
            return "EXCEPTION";
        }
    }


    @PostMapping("/cancel")
    @ResponseBody
    @ApiOperation("取消审核")
    public String cancel(@RequestBody CancelUser cancelUser) {
        String Name = cancelUser.getName();
        String approvalIP = cancelUser.getApprovalIP();
        String str = "$|delete me:" + Name + "|$";
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        byteBuffer.clear();
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();

        SocketChannel socketChannel = appro.get(approvalIP);
        if (socketChannel != null) {
            try {
                socketChannel.write(byteBuffer);
                for (String key : approvalResultMap.keySet()) {
                    if (approvalResultMap.get(key).getApprovalIp().equals(approvalIP)) {
                        approvalResultMap.get(key).setResult("CANCEL");
                        logger.info(Name + "取消了发送文件请求" + DateUtil.dataToString(new Date()));
                    }
                }
            } catch (Exception e) {
                logger.info(Name + "取消发送文件异常:" + e.getMessage() + "--->>>Time:" + DateUtil.dataToString(new Date()));
                return "EXCEPTION";
            }
        } else {
            logger.error(Name + "取消发送文件请求失败" + DateUtil.dataToString(new Date()));
            return "FAIL";
        }
        return "SUCCESS";
    }

//    /**
//     * 接收审批结果方法.
//     */
//    public void reciveMsg(SocketChannel socketChannel, String ip) {
//        try {
//            if (socketChannel != null) {
//                while (true) {
//                    Thread.sleep(1);
//                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//                    socketChannel.read(byteBuffer);
//                    byteBuffer.flip();
//                    Charset charset = Charset.forName("UTF-8");
//                    String str = charset.newDecoder().decode(byteBuffer.asReadOnlyBuffer()).toString();
//                    // System.out.println("接收到返回值:" + str);
//                    if (str.toUpperCase().contains("SUCCESS")) {
//                        str = StringUtil.subString(str, "||", "SUCCESS||");
//                        if (approvalResultMap.containsKey(str)) {
//                            approvalResultMap.get(str).setResult("SUCCESS");
//                        } else {
//                        }
//                    } else if (str.toUpperCase().contains("FAIL")) {
//                        str = StringUtil.subString(str, "||", "FAIL||");
//                        if (approvalResultMap.containsKey(str)) {
//                            approvalResultMap.get(str).setResult("FAIL");
//                        } else {
//                        }
//                    }
//                }
//            }
//        } catch (IOException io) {
//            if (io.getMessage().contains("远程主机强迫关闭了一个现有的连接")) {
//                String error = ip + "断开连接";
//                if (error.equals("1断开连接")) {
//                } else {
//                    System.out.println("审批端:" + ip + "断开连接");
//                    for (String key : approvalResultMap.keySet()) {
//                        if (approvalResultMap.get(key).getApprovalIp().equals(ip)) {
//                            approvalResultMap.get(key).setResult("CLOSED");
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 获取申请返回信息.
     */
    public String getReceiveMsg(String key) {
        while (true) {
            try {
                ApprovalResult value = approvalResultMap.get(key);
                if (value != null && value.getResult() != "" && !value.getResult().equals("")) {
                    approvalResultMap.remove(key);
                    return value.getResult();
                }
                Thread.sleep(2000);
            } catch (Exception e) {
                logger.error(key + "获取审批返回信息异常:" + e.getMessage() + "-->>Time:" + DateUtil.dataToString(new Date()));
                return "EXCEPTION";
            }
        }
    }

    /**
     * 关闭申请连接方法
     */
    public void closeClient(SocketChannel socketChannel) throws Exception {
        if (socketChannel != null) {
            String str = "|关闭连接|";
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.clear();
            byteBuffer.put(str.getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            Thread.sleep(100);
            socketChannel.close();
        }
    }

    /**
     * .判断审批人IP是否在线
     */
    public void tellConnect(String ip) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                //判断是否掉线
                boolean clientIsConnect = isConnect(ip);
            }
        }, 1, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
    }

    /**
     * ping IP 方法.
     */
    public boolean isConnect(String ip) {
        boolean bool = false;
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("ping " + ip + " -n 1");
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                if (line.indexOf("请求超时") >= 0) {
                    bool = false;
                    return bool;
                }
            }
            is.close();
            isr.close();
            br.close();
            if (null != sb && !sb.toString().equals("")) {
                if (sb.toString().indexOf("TTL") > 0) {
                    bool = true;
                } else {
                    bool = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

}
