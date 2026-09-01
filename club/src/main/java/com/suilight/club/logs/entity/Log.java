package com.suilight.club.logs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/** 管理员活动操作日志。 */
@Data
@TableName("logs")
public class Log {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private LocalDateTime date;
    private String username;
    @TableField("losimformation")
    private String operation;
    @TableField("userid")
    private Integer userId;
}
