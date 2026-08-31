package com.suilight.club.admin.controller;

import com.suilight.club.admin.entity.Admin;
import com.suilight.club.admin.service.AdminService;
import com.suilight.club.submit.entity.Submit;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/** 管理员查看新生报名信息。 */
@RestController
@RequestMapping("/api/admin/submits")
public class AdminSubmitController {

    private final AdminService adminService;

    public AdminSubmitController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<Submit> findAll(HttpSession session) {
        return adminService.findSubmits(currentAdmin(session));
    }

    @GetMapping("/{id}")
    public Submit findById(@PathVariable Integer id, HttpSession session) {
        return adminService.findSubmitById(currentAdmin(session), id);
    }

    private Admin currentAdmin(HttpSession session) {
        Object value = session.getAttribute("adminId");
        if (!(value instanceof Integer id)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "请先登录");
        }
        Admin admin = adminService.findById(id);
        if (admin == null) {
            session.invalidate();
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "管理员不存在");
        }
        return admin;
    }
}
