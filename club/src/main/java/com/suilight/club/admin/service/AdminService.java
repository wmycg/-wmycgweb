package com.suilight.club.admin.service;

import com.suilight.club.admin.entity.Admin;
import com.suilight.club.events.entity.Event;
import com.suilight.club.submit.entity.Submit;

import java.util.List;

public interface AdminService {

    Admin findById(Integer id);

    Admin findByUsername(String username);

    Admin authenticate(String username, String password);

    List<Admin> findAll(Admin operator);

    boolean create(Admin operator, Admin newAdmin);

    boolean updatePassword(Admin operator, String oldPassword, String newPassword);

    boolean delete(Admin operator, Integer targetAdminId);

    Event findEventById(Integer id);

    List<Event> findEvents();

    boolean createEvent(Event event);

    boolean updateEvent(Event event);

    boolean deleteEvent(Integer id);

    Submit findSubmitById(Admin operator, Integer id);

    List<Submit> findSubmits(Admin operator);

}
