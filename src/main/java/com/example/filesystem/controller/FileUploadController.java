package com.example.filesystem.controller;

import com.example.filesystem.util.DateUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.concurrent.ThreadFactory;


/**
 * @ClassName FlieUploadController
 * @Description TODO
 * @Author zq
 * Date 2020/2/18 10:37
 * Version 1.0
 */

@CrossOrigin
@RequestMapping("/fileUpload")
@RestController
@Api(tags = "文件上传接口 @ 郑权", description = "FileUploadController")
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @PostMapping("/uploadWork")
    @ApiOperation("上传文件")
    public synchronized String uploadWork(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) {

        try {
            request.setCharacterEncoding("UTF-8");
            String user = request.getParameter("user");


            //获取桌面目录
//            File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
//            String desktopPath = desktopDir.getAbsolutePath();
            String fileSavaUrl = "D:/exportDir";
            String time = DateUtil.dataToUrl(new Date());
            user = time + "/" + user;

            //文件名
            String fileName = file.getOriginalFilename();

            //此处解决获取到的fileName包含路径信息问题
            int unixSep = fileName.lastIndexOf('/');

            int winSep = fileName.lastIndexOf('\\');

            int pos = (winSep > unixSep ? winSep : unixSep);
            if (pos != -1) {
                // Any sort of path separator found...
                fileName = fileName.substring(pos + 1);
            }
//            fileName = DateUtil.dataToFolder(new Date()) + fileName;
            String fold = fileSavaUrl + "/" + user + "/" + DateUtil.dataToFolder(new Date()) + "/" + fileName;

            File folder = new File(fold);
            if (!folder.getParentFile().exists()) {
                folder.getParentFile().mkdirs();
            }
            FileInputStream in = null;
            FileOutputStream out = null;
            FileChannel fileChannel = null;
            FileChannel fileChannelout = null;
            try {
                // FileUtils.copyInputStreamToFile(file.getInputStream(),folder);
                //  file.transferTo(folder);
                out = new FileOutputStream(folder);
                in = (FileInputStream) file.getInputStream();
                fileChannel = in.getChannel();
                fileChannelout = out.getChannel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                while (true) {
                    byteBuffer.clear();
                    int r = fileChannel.read(byteBuffer);
                    if (r == -1) {
                        break;
                    }
                    byteBuffer.flip();
                    while (byteBuffer.remaining() > 0) {
                        fileChannelout.write(byteBuffer);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("文件传输错误:" + DateUtil.dataToString(new Date()) + ":--->>>" + e.getMessage());
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (fileChannel != null) {
                    fileChannel.close();
                }
                if (fileChannelout != null) {
                    fileChannelout.close();
                }
            }

            if (file.isEmpty()) {
                logger.error(user + ":上传空文件 -->>" + DateUtil.dataToString(new Date()));
                return "EMPTY";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传文件错误:" + DateUtil.dataToString(new Date()) + ":--->>>" + e.getMessage());
            return "EXCEPTION";
        }
        return "SUCCESS";
    }

}
