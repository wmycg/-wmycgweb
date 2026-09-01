package com.suilight.club.events.controller;

import com.suilight.club.events.entity.Event;
import com.suilight.club.events.service.EventService;
import com.suilight.club.events.vo.EventVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventVO> findAll() {
        return eventService.findAll().stream().map(EventVO::from).toList();
    }

    @GetMapping("/{id}")
    public EventVO findById(@PathVariable Integer id) {
        Event event = eventService.findById(id);
        if (event == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "活动不存在");
        }
        return EventVO.from(event);
    }
}
