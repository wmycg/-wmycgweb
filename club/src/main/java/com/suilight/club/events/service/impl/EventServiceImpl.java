package com.suilight.club.events.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.suilight.club.events.entity.Event;
import com.suilight.club.events.mapper.EventMapper;
import com.suilight.club.events.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {

    @Override
    public Event findById(Integer id) {
        return getById(id);
    }

    @Override
    public List<Event> findAll() {
        return list();
    }

    @Override
    public boolean create(Event event) {
        validate(event);
        return save(event);
    }

    @Override
    public boolean update(Event event) {
        validate(event);
        if (event.getId() == null || getById(event.getId()) == null) {
            throw new IllegalArgumentException("活动不存在");
        }
        return updateById(event);
    }

    @Override
    public boolean delete(Integer id) {
        if (getById(id) == null) {
            throw new IllegalArgumentException("活动不存在");
        }
        return removeById(id);
    }

    private void validate(Event event) {
        if (event == null || blank(event.getDate()) || blank(event.getTitle())
                || blank(event.getPlace()) || blank(event.getBrief())) {
            throw new IllegalArgumentException("活动日期、标题、地点和简介不能为空");
        }
        if (event.getDate().length() > 20 || event.getTitle().length() > 20
                || event.getPlace().length() > 20 || event.getBrief().length() > 300) {
            throw new IllegalArgumentException("活动字段长度超过数据库限制");
        }
    }

    private boolean blank(String value) {
        return value == null || value.isBlank();
    }
}
