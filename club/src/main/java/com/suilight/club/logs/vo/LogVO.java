package com.suilight.club.logs.vo;

import com.suilight.club.logs.entity.Log;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogVO {
    private Integer id;
    private LocalDateTime date;
    private String username;
    private String operation;
    private Integer userId;

    public static LogVO from(Log log) {
        LogVO vo = new LogVO();
        vo.setId(log.getId());
        vo.setDate(log.getDate());
        vo.setUsername(log.getUsername());
        vo.setOperation(log.getOperation());
        vo.setUserId(log.getUserId());
        return vo;
    }
}
