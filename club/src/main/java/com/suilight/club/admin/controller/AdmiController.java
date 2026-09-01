package com.suilight.club.admin.controller;

import com.suilight.club.admin.dto.AdminCreateRequest;
import com.suilight.club.admin.dto.AdminLoginRequest;
import com.suilight.club.admin.dto.UpdatePasswordRequest;
import com.suilight.club.admin.entity.Admin;
import com.suilight.club.admin.service.AdminService;
import com.suilight.club.admin.vo.AdminVO;
import com.suilight.club.logs.service.LogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdmiController {

    private static final String ADMIN_ID = "adminId";

    private final AdminService adminService;
    private final LogService logService;

    public AdmiController(AdminService adminService, LogService logService) {
        this.adminService = adminService;
        this.logService = logService;
    }

    @PostMapping("/login")
    public AdminVO login(@RequestBody AdminLoginRequest request, HttpSession session) {
        Admin admin = adminService.authenticate(request.getUsername(), request.getPassword());
        if (admin == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误");
        }
        session.setAttribute(ADMIN_ID, admin.getId());
        logService.record(admin, "管理员登录");
        return AdminVO.from(admin);
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        Object id = session.getAttribute(ADMIN_ID);
        if (id instanceof Integer adminId) {
            Admin admin = adminService.findById(adminId);
            if (admin != null) {
                logService.record(admin, "管理员退出登录");
            }
        }
        session.invalidate();
    }

    @GetMapping("/me")
    public AdminVO me(HttpSession session) {
        return AdminVO.from(currentAdmin(session));
    }

    @PostMapping("/password")
    public boolean updatePassword(@RequestBody UpdatePasswordRequest request, HttpSession session) {
        Admin admin = currentAdmin(session);
        boolean success = adminService.updatePassword(admin, request.getOldPassword(), request.getNewPassword());
        if (success) {
            logService.record(admin, "修改管理员密码");
        }
        return success;
    }

    @GetMapping("/admins")
    public List<AdminVO> findAll(HttpSession session) {
        Admin admin = currentAdmin(session);
        requireSuperAdmin(admin);
        List<AdminVO> admins = adminService.findAll(admin).stream().map(AdminVO::from).toList();
        logService.record(admin, "查看管理员列表");
        return admins;
    }

    @PostMapping("/admins")
    public boolean create(@RequestBody AdminCreateRequest request, HttpSession session) {
        Admin newAdmin = new Admin();
        newAdmin.setUsername(request.getUsername());
        newAdmin.setPassword(request.getPassword());
        newAdmin.setSupe(request.getSupe());
        Admin admin = currentAdmin(session);
        requireSuperAdmin(admin);
        boolean success = adminService.create(admin, newAdmin);
        if (success) {
            logService.record(admin, "新增管理员（ID:" + newAdmin.getId() + "）");
        }
        return success;
    }

    @DeleteMapping("/admins/{id}")
    public boolean delete(@PathVariable Integer id, HttpSession session) {
        Admin admin = currentAdmin(session);
        requireSuperAdmin(admin);
        boolean success = adminService.delete(admin, id);
        if (success) {
            logService.record(admin, "删除管理员（ID:" + id + "）");
        }
        return success;
    }

    private Admin currentAdmin(HttpSession session) {
        Object id = session.getAttribute(ADMIN_ID);
        if (!(id instanceof Integer adminId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "请先登录");
        }
        Admin admin = adminService.findById(adminId);
        if (admin == null) {
            session.invalidate();
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "管理员不存在");
        }
        return admin;
    }

    private void requireSuperAdmin(Admin admin) {
        if (!Boolean.TRUE.equals(admin.getSupe())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "只有超级管理员可以执行此操作");
        }
    }
}
