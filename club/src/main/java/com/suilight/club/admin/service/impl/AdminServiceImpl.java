package com.suilight.club.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.suilight.club.admin.entity.Admin;
import com.suilight.club.admin.mapper.AdminMapper;
import com.suilight.club.admin.service.AdminService;
import com.suilight.club.events.entity.Event;
import com.suilight.club.events.service.EventService;
import com.suilight.club.submit.entity.Submit;
import com.suilight.club.submit.service.SubmitService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    private final EventService eventService;
    private final SubmitService submitService;

    public AdminServiceImpl(EventService eventService, SubmitService submitService) {
        this.eventService = eventService;
        this.submitService = submitService;
    }

    @Override
    public Admin findById(Integer id) {
        return getById(id);
    }

    @Override
    public Admin findByUsername(String username) {
        return getOne(new LambdaQueryWrapper<Admin>().eq(Admin::getUsername, username));
    }

    @Override
    public Admin authenticate(String username, String password) {
        if (blank(username) || blank(password)) {
            return null;
        }
        Admin admin = findByUsername(username);
        return admin != null && password.equals(admin.getPassword()) ? admin : null;
    }

    @Override
    public List<Admin> findAll(Admin operator) {
        requireSuperAdmin(operator);
        return list();
    }

    @Override
    public boolean create(Admin operator, Admin newAdmin) {
        requireSuperAdmin(operator);
        validateAdmin(newAdmin);
        if (findByUsername(newAdmin.getUsername()) != null) {
            throw new IllegalArgumentException("管理员用户名已存在");
        }
        return save(newAdmin);
    }

    @Override
    public boolean updatePassword(Admin operator, String oldPassword, String newPassword) {
        requireCurrentAdmin(operator);
        if (oldPassword == null || !oldPassword.equals(operator.getPassword())) {
            throw new IllegalArgumentException("旧密码错误");
        }
        validatePassword(newPassword);
        operator.setPassword(newPassword);
        return updateById(operator);
    }

    @Override
    public boolean delete(Admin operator, Integer targetAdminId) {
        requireSuperAdmin(operator);
        if (operator.getId().equals(targetAdminId)) {
            throw new IllegalStateException("不能删除当前登录管理员");
        }
        if (findById(targetAdminId) == null) {
            throw new IllegalArgumentException("管理员不存在");
        }
        return removeById(targetAdminId);
    }

    @Override
    public Event findEventById(Integer id) {
        return eventService.findById(id);
    }

    @Override
    public List<Event> findEvents() {
        return eventService.findAll();
    }

    @Override
    public boolean createEvent(Event event) {
        return eventService.create(event);
    }

    @Override
    public boolean updateEvent(Event event) {
        return eventService.update(event);
    }

    @Override
    public boolean deleteEvent(Integer id) {
        return eventService.delete(id);
    }

    @Override
    public Submit findSubmitById(Admin operator, Integer id) {
        requireCurrentAdmin(operator);
        return submitService.findById(id);
    }

    @Override
    public List<Submit> findSubmits(Admin operator) {
        requireCurrentAdmin(operator);
        return submitService.findAll();
    }


    private void validateAdmin(Admin admin) {
        if (admin == null || blank(admin.getUsername()) || blank(admin.getPassword())) {
            throw new IllegalArgumentException("管理员用户名和密码不能为空");
        }
        if (admin.getUsername().length() > 20 || admin.getPassword().length() > 20) {
            throw new IllegalArgumentException("管理员用户名或密码超过数据库限制");
        }
    }

    private void validatePassword(String password) {
        if (blank(password)) {
            throw new IllegalArgumentException("新密码不能为空");
        }
        if (password.length() > 20) {
            throw new IllegalArgumentException("新密码超过数据库限制");
        }
    }

    private void requireCurrentAdmin(Admin operator) {
        if (operator == null || operator.getId() == null || findById(operator.getId()) == null) {
            throw new IllegalStateException("当前管理员不存在");
        }
    }

    private void requireSuperAdmin(Admin operator) {
        if (operator == null || operator.getId() == null) {
            throw new IllegalStateException("当前管理员不存在");
        }
        if (!Boolean.TRUE.equals(operator.getSupe())) {
            throw new IllegalStateException("只有超级管理员可以执行此操作");
        }
    }

    private boolean blank(String value) {
        return value == null || value.isBlank();
    }
}
