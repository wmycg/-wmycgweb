package com.suilight.club.admin.controller;

import com.suilight.club.admin.entity.Admin;
import com.suilight.club.admin.service.AdminService;
import com.suilight.club.events.entity.Event;
import com.suilight.club.events.service.EventService;
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

    public AdminEventController(EventService eventService, AdminService adminService) {
        this.eventService = eventService;
        this.adminService = adminService;
    }

    @PostMapping
    public boolean create(@RequestBody Event event, HttpSession session) {
        currentAdmin(session);
        return eventService.create(event);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody Event event, HttpSession session) {
        currentAdmin(session);
        event.setId(id);
        return eventService.update(event);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id, HttpSession session) {
        currentAdmin(session);
        return eventService.delete(id);
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
