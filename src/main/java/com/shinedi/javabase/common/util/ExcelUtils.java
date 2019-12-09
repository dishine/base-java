package com.shinedi.javabase.common.util;

import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ExcelUtils {

    /**
     * 导出 数据
     * @param list
     * @param response
     * @param clazz
     * @return
     */
    public static  void export(List<? extends BaseRowModel> list, HttpServletResponse response, Class<? extends BaseRowModel> clazz) {

        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
        try {
            String fileName = new String(
                    (new SimpleDateFormat("yyyy-MM-dd").format(new Date())).getBytes(), "UTF-8");
            Sheet sheet2 = new Sheet(2, 3,clazz, "sheet", null);
            writer.write(list, sheet2);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
            //response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.finish();
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 导出Excel方法
     * @param excelDto 导出对应的list<object></>
     */
    public void exportExcel(List<Object> excelDto) {
        //todo 标题
        List<String> row1 = Lists.newArrayList("","","");
        List<List<String>> rows = new ArrayList<>();
        rows.add(row1);
        for (Object w  : excelDto) {
            //todo 从object中获得 标题中相对应
            List<String> res = Lists.newArrayList("","");
            rows.add(res);
        }
        //todo 导出Excel的位置及名称
        BigExcelWriter writer = ExcelUtil.getBigWriter("/Users/shinedi/Documents/wind/bill/11-25.xls");
        // 一次性写出内容，使用默认样式
        writer.write(rows);
        //todo 关闭writer，释放内存
        writer.close();
    }



}
