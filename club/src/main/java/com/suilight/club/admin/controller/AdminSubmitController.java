package com.suilight.club.admin.controller;

import com.suilight.club.admin.entity.Admin;
import com.suilight.club.admin.service.AdminService;
import com.suilight.club.logs.service.LogService;
import com.suilight.club.submit.entity.Submit;
import com.suilight.club.submit.vo.SubmitVO;
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
    private final LogService logService;

    public AdminSubmitController(AdminService adminService, LogService logService) {
        this.adminService = adminService;
        this.logService = logService;
    }

    @GetMapping
    public List<SubmitVO> findAll(HttpSession session) {
        Admin admin = currentAdmin(session);
        List<Submit> submits = adminService.findSubmits(admin);
        logService.record(admin, "查看报名信息列表");
        return submits.stream().map(SubmitVO::from).toList();
    }

    @GetMapping("/{id}")
    public SubmitVO findById(@PathVariable Integer id, HttpSession session) {
        Admin admin = currentAdmin(session);
        Submit submit = adminService.findSubmitById(admin, id);
        if (submit == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "报名记录不存在");
        }
        logService.record(admin, "查看报名信息（ID:" + id + "）");
        return SubmitVO.from(submit);
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
