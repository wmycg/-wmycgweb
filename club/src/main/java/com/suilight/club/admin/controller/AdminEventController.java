package com.suilight.club.admin.controller;

import com.suilight.club.admin.entity.Admin;
import com.suilight.club.admin.service.AdminService;
import com.suilight.club.events.dto.EventSaveRequest;
import com.suilight.club.events.service.EventService;
import com.suilight.club.events.vo.EventVO;
import com.suilight.club.logs.service.LogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/** 管理员活动增删改接口。 */
@RestController
@RequestMapping("/api/admin/events")
public class AdminEventController {

    private final EventService eventService;
    private final AdminService adminService;
    private final LogService logService;

    public AdminEventController(EventService eventService, AdminService adminService, LogService logService) {
        this.eventService = eventService;
        this.adminService = adminService;
        this.logService = logService;
    }

    @PostMapping
    public EventVO create(@RequestBody EventSaveRequest request, HttpSession session) {
        Admin admin = currentAdmin(session);
        var event = request.toEntity(null);
        boolean success = eventService.create(event);
        if (success) {
            logService.record(admin, "新增活动" + (event.getId() == null ? "" : "（ID:" + event.getId() + "）"));
        }
        return EventVO.from(event);
    }

    @PutMapping("/{id}")
    public EventVO update(@PathVariable Integer id, @RequestBody EventSaveRequest request, HttpSession session) {
        Admin admin = currentAdmin(session);
        var event = request.toEntity(id);
        boolean success = eventService.update(event);
        if (success) {
            logService.record(admin, "修改活动（ID:" + id + "）");
        }
        return EventVO.from(event);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id, HttpSession session) {
        Admin admin = currentAdmin(session);
        boolean success = eventService.delete(id);
        if (success) {
            logService.record(admin, "删除活动（ID:" + id + "）");
        }
        return success;
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
