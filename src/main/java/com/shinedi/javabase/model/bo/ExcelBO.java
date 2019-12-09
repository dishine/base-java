package com.shinedi.javabase.model.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * @author D-S
 * Created on 2019/12/6 10:48 上午
 * 导出文件的格式 标题
 */
@Data
public class ExcelBO {

    //标识导出标题名
    @ExcelProperty(value = "Create Time", index = 0)
    //标识表格的宽度
    @ColumnWidth(25)
    private String createTime;

    @ExcelProperty(value = "Update Time", index = 1)
    @ColumnWidth(25)
    private String updateTime;

    @ExcelProperty(value = "No.", index = 2)
    @ColumnWidth(20)
    private String packageId;


    @ExcelProperty(value = "Order",index = 3)
    @ColumnWidth(20)
    private String orderName;

    @ExcelProperty(value = "Sender",index = 4)
    @ColumnWidth(20)
    private String senderName;


    @ExcelProperty(value = "Receiver", index = 5)
    @ColumnWidth(20)
    private String receiverName;


    @ExcelProperty(value = "MobilePhone", index = 6)
    @ColumnWidth(20)
    private String receiverTel;
}
