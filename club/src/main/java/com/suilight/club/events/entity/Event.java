package com.suilight.club.events.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("events")
public class Event {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("date")
    private String date;
    private String title;
    private String place;
    private String brief;
}
