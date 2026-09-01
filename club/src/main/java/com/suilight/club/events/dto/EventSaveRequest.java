package com.suilight.club.events.dto;

import com.suilight.club.events.entity.Event;
import lombok.Data;

@Data
public class EventSaveRequest {
    private String date;
    private String title;
    private String place;
    private String brief;

    public Event toEntity(Integer id) {
        Event event = new Event();
        event.setId(id);
        event.setDate(date);
        event.setTitle(title);
        event.setPlace(place);
        event.setBrief(brief);
        return event;
    }
}
