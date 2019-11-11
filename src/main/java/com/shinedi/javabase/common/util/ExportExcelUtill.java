package com.shinedi.javabase.common.util;

import com.alibaba.excel.EasyExcel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author D-S
 * Created on 2019/11/11 10:16 上午
 */

public class ExportExcelUtill {
    public void download(HttpServletResponse response, @RequestParam long start, @RequestParam long end) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系

        //fixme 需要导出的结构体
        //todo 需要添加注解
        // @ExcelProperty(value = "表格中的名称", index = 0)
        // @ColumnWidth(25)
        List<Objects> ExcelVOList = new ArrayList<>();

        Date startdd = new Date(start);
        Date enddd = new Date(end);

        String fileName =
                URLEncoder.encode("PACKAGE导出 start=" + DateUtils.formatDateTime(startdd) + " end=" + DateUtils.formatDateTime(enddd), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");


        EasyExcel.write(response.getOutputStream(), Objects.class).sheet("sheet").doWrite(ExcelVOList);
    }

}
