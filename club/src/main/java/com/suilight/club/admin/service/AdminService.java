package com.suilight.club.admin.service;

import com.suilight.club.admin.entity.Admin;
import com.suilight.club.events.entity.Event;
import com.suilight.club.submit.entity.Submit;

import java.util.List;

public interface AdminService {

    Admin findById(Integer id);

    Admin findByUsername(String username);

    Admin authenticate(String username, String password);

    List<Admin> findAll();

    boolean create(Admin admin);

    boolean update(Admin admin);

    boolean delete(Integer id);

    Event findEventById(Integer id);

    List<Event> findEvents();

    boolean createEvent(Event event);

    boolean updateEvent(Event event);

    boolean deleteEvent(Integer id);

    Submit findSubmitById(Integer id);

    List<Submit> findSubmits();
}
