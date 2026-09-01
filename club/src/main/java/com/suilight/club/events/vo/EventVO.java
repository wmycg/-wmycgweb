package com.suilight.club.events.vo;

import com.suilight.club.events.entity.Event;
import lombok.Data;

@Data
public class EventVO {
    private Integer id;
    private String date;
    private String title;
    private String place;
    private String brief;

    public static EventVO from(Event event) {
        EventVO vo = new EventVO();
        vo.setId(event.getId());
        vo.setDate(event.getDate());
        vo.setTitle(event.getTitle());
        vo.setPlace(event.getPlace());
        vo.setBrief(event.getBrief());
        return vo;
    }
}
