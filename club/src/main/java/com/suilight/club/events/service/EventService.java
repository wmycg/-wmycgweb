package com.suilight.club.events.service;

import com.suilight.club.events.entity.Event;
import java.util.List;

public interface EventService {
    Event findById(Integer id);
    List<Event> findAll();
    boolean create(Event event);
    boolean update(Event event);
    boolean delete(Integer id);
}
