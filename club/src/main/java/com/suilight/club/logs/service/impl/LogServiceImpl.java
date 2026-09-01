package com.suilight.club.logs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.suilight.club.admin.entity.Admin;
import com.suilight.club.logs.entity.Log;
import com.suilight.club.logs.mapper.LogMapper;
import com.suilight.club.logs.service.LogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {
    @Override
    public boolean record(Admin admin, String operation) {
        if (admin == null || admin.getId() == null || operation == null || operation.isBlank()) {
            throw new IllegalArgumentException("日志操作者和操作内容不能为空");
        }
        if (operation.length() > 100) {
            throw new IllegalArgumentException("日志操作内容超过数据库限制");
        }
        Log log = new Log();
        log.setDate(LocalDateTime.now());
        log.setUsername(admin.getUsername());
        log.setOperation(operation);
        log.setUserId(admin.getId());
        return save(log);
    }

    @Override
    public List<Log> findAll(Admin operator) {
        if (operator == null || operator.getId() == null || !Boolean.TRUE.equals(operator.getSupe())) {
            throw new IllegalStateException("只有超级管理员可以查看操作日志");
        }
        return list(new LambdaQueryWrapper<Log>().orderByDesc(Log::getDate).orderByDesc(Log::getId));
    }
}
