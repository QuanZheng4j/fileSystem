package com.example.filesystem.controller;

import com.example.filesystem.model.dto.ReasonDTO;
import com.example.filesystem.model.vo.SelectUser;
import com.example.filesystem.service.OperationService;
import com.example.filesystem.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ExcelController
 * @Description TODO
 * @Author zq
 * Date 2020/2/26 18:55
 * Version 1.0
 */
@RequestMapping("/excel")
@RestController
@Api(tags = "导出Excel @ 郑权", description = "ExcelController")
public class ExcelController {

    private static final Logger loggger = LoggerFactory.getLogger(ExcelController.class);

    @Resource
    private OperationService operationService;

    @PostMapping("/excelDownload")
    @ApiOperation("导出excel")
    public void excelDownload(HttpServletResponse response, @RequestBody SelectUser selectUser) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("审批记录导出");

        List<ReasonDTO> list = operationService.getAllOperation(selectUser.getTimeStart(),selectUser.getTimeEnd());

        int insertRows = 0;

        for (int i = 0 ; i < list.size() ; i++){
            Row titileRow = sheet.createRow(insertRows);
            titileRow.createCell(0).setCellValue("申请人:");
            titileRow.createCell(1).setCellValue(list.get(i).getName());
            titileRow.createCell(2).setCellValue("审批人:");
            titileRow.createCell(3).setCellValue(list.get(i).getApprovalName());
            titileRow.createCell(4).setCellValue("审批时间:");
            titileRow.createCell(5).setCellValue(list.get(i).getApprovalTime());
            CellRangeAddress rangeAddress = new CellRangeAddress(insertRows,insertRows,5,6);
            sheet.addMergedRegion(rangeAddress);
            titileRow.createCell(8).setCellValue("审批结果:");
            titileRow.createCell(9).setCellValue(list.get(i).getApprovalResult());
            insertRows++;

            Row titileRow1 = sheet.createRow(insertRows);
            titileRow1.createCell(0).setCellValue("文件名:");
            CellRangeAddress rangeAddress1 = new CellRangeAddress(insertRows,insertRows,1,2);
            sheet.addMergedRegion(rangeAddress1);
            titileRow1.createCell(1).setCellValue(list.get(i).getFileName());
            titileRow1.createCell(3).setCellValue("文件大小:");
            CellRangeAddress rangeAddress2 = new CellRangeAddress(insertRows,insertRows,4,6);
            sheet.addMergedRegion(rangeAddress2);
            titileRow1.createCell(4).setCellValue(list.get(i).getFileSize());
            insertRows++;

            Row titileRow2 = sheet.createRow(insertRows);
            titileRow2.createCell(0).setCellValue("申请原因:");
            CellRangeAddress rangeAddress3 = new CellRangeAddress(insertRows,insertRows,1,9);
            sheet.addMergedRegion(rangeAddress3);
            titileRow2.createCell(1).setCellValue(list.get(i).getReason());
            insertRows++;

            CellRangeAddress rangeAddress4 = new CellRangeAddress(insertRows,insertRows,0,9);
            sheet.addMergedRegion(rangeAddress4);

            insertRows++;
        }


        OutputStream outputStream = null;

        try {
            String fileName = "审批记录";
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xls","utf-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workbook.close();
            loggger.info("导出文件:" + DateUtil.dataToString(new Date()));
        } catch (Exception e) {
            loggger.error("导出文件错误:" + e.getMessage());
        }
    }

}
