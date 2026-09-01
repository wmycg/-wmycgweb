package com.suilight.club.logs.service;

import com.suilight.club.admin.entity.Admin;
import com.suilight.club.logs.entity.Log;

import java.util.List;

public interface LogService {
    boolean record(Admin admin, String operation);
    List<Log> findAll(Admin operator);
}
