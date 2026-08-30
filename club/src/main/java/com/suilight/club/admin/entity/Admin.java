package com.suilight.club.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("admin")
public class Admin {
    @TableId
    private Long id;

    private String username;

    private String password;

    private String role;
    private Boolean status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}