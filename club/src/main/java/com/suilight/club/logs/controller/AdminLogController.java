package com.suilight.club.logs.controller;

import com.suilight.club.admin.entity.Admin;
import com.suilight.club.admin.service.AdminService;
import com.suilight.club.logs.dto.LogQueryRequest;
import com.suilight.club.logs.service.LogService;
import com.suilight.club.logs.vo.LogVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/** 超级管理员查询操作日志。 */
@RestController
@RequestMapping("/api/admin/logs")
public class AdminLogController {

    private static final String ADMIN_ID = "adminId";

    private final AdminService adminService;
    private final LogService logService;

    public AdminLogController(AdminService adminService, LogService logService) {
        this.adminService = adminService;
        this.logService = logService;
    }

    @GetMapping
    public List<LogVO> findAll(@ModelAttribute LogQueryRequest request, HttpSession session) {
        Admin admin = currentSuperAdmin(session);
        return logService.findAll(admin, request.getUsername(), request.getStart(), request.getEnd())
                .stream()
                .map(LogVO::from)
                .toList();
    }

    private Admin currentSuperAdmin(HttpSession session) {
        Object value = session.getAttribute(ADMIN_ID);
        if (!(value instanceof Integer id)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "请先登录");
        }
        Admin admin = adminService.findById(id);
        if (admin == null) {
            session.invalidate();
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "管理员不存在");
        }
        if (!Boolean.TRUE.equals(admin.getSupe())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "只有超级管理员可以查看操作日志");
        }
        return admin;
    }
}
