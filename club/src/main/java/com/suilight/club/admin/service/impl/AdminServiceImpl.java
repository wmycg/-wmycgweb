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

/** 管理员业务门面：账号、活动和报名信息均从这里提供给管理端。 */
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
        Admin admin = findByUsername(username);
        return admin != null && password != null && password.equals(admin.getPassword()) ? admin : null;
    }

    @Override
    public List<Admin> findAll() {
        return list();
    }

    @Override
    public boolean create(Admin admin) {
        validateAdmin(admin);
        if (findByUsername(admin.getUsername()) != null) {
            throw new IllegalArgumentException("管理员用户名已存在");
        }
        return save(admin);
    }

    @Override
    public boolean update(Admin admin) {
        validateAdmin(admin);
        if (admin.getId() == null || findById(admin.getId()) == null) {
            throw new IllegalArgumentException("管理员不存在");
        }
        return updateById(admin);
    }

    @Override
    public boolean delete(Integer id) {
        if (findById(id) == null) {
            throw new IllegalArgumentException("管理员不存在");
        }
        return removeById(id);
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
    public Submit findSubmitById(Integer id) {
        return submitService.findById(id);
    }

    @Override
    public List<Submit> findSubmits() {
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

    private boolean blank(String value) {
        return value == null || value.isBlank();
    }
}
