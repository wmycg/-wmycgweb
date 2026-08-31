package com.suilight.club.admin.controller;

import com.suilight.club.admin.dto.AdminCreateRequest;
import com.suilight.club.admin.dto.AdminLoginRequest;
import com.suilight.club.admin.dto.UpdatePasswordRequest;
import com.suilight.club.admin.entity.Admin;
import com.suilight.club.admin.service.AdminService;
import com.suilight.club.admin.vo.AdminVO;
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

    public AdmiController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public AdminVO login(@RequestBody AdminLoginRequest request, HttpSession session) {
        Admin admin = adminService.authenticate(request.getUsername(), request.getPassword());
        if (admin == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误");
        }
        session.setAttribute(ADMIN_ID, admin.getId());
        return AdminVO.from(admin);
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @PostMapping("/password")
    public boolean updatePassword(@RequestBody UpdatePasswordRequest request, HttpSession session) {
        return adminService.updatePassword(currentAdmin(session), request.getOldPassword(), request.getNewPassword());
    }

    @GetMapping("/admins")
    public List<AdminVO> findAll(HttpSession session) {
        return adminService.findAll(currentAdmin(session)).stream().map(AdminVO::from).toList();
    }

    @PostMapping("/admins")
    public boolean create(@RequestBody AdminCreateRequest request, HttpSession session) {
        Admin newAdmin = new Admin();
        newAdmin.setUsername(request.getUsername());
        newAdmin.setPassword(request.getPassword());
        newAdmin.setSupe(request.getSupe());
        return adminService.create(currentAdmin(session), newAdmin);
    }

    @DeleteMapping("/admins/{id}")
    public boolean delete(@PathVariable Integer id, HttpSession session) {
        return adminService.delete(currentAdmin(session), id);
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
}
