package com.shinedi.javabase.dao.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @Author: D-S
 * @Date: 2020/2/24 11:33 上午
 */
@Data
public class Notice {

    private Long id;

    private Date startTime;

    private Date  endTime;

    private String title;

    private String  content;

    private Date createTime;

    private Date updateTime;

}
