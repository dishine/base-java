package com.shinedi.javabase.controller;

import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import com.shinedi.javabase.common.util.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @author D-S
 * Created on 2019/12/6 10:39 上午
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping(value = "")
public class ExcelController {

    @GetMapping("/excel/pkg")
    public void download(HttpServletResponse response, Object packageQueryDTO,@RequestHeader(value = "Authorization" ,required = false) String token) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        //从service中获取list<object>
        List<Object> packageExcelVOList = Lists.newArrayList();
        //开始时间 结束时间
        Date startdd = new Date();
        Date enddd = new Date();
        //String fileName = DateUtils.formatDateTimeYYMMDD(startdd) + "--" + DateUtils.formatDateTimeYYMMDD(enddd)+" Wiredyou Package Data";
        // 避免中文乱码的问题
        String fileName =
                URLEncoder.encode(DateUtils.formatDateTimeYYMMDD(startdd) + "--" + DateUtils.formatDateTimeYYMMDD(enddd)+" Wiredyou Package Data", "UTF-8");
        //标记返回结果中的文件名
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        //导出结构体 object.class
        EasyExcel.write(response.getOutputStream(), Object.class).sheet("sheet").doWrite(packageExcelVOList);
    }

}
