package com.suilight.club.admin.vo;

import com.suilight.club.admin.entity.Admin;
import lombok.Data;

@Data
public class AdminVO {
    private Integer id;
    private String username;
    private Boolean supe;

    public static AdminVO from(Admin admin) {
        AdminVO vo = new AdminVO();
        vo.setId(admin.getId());
        vo.setUsername(admin.getUsername());
        vo.setSupe(admin.getSupe());
        return vo;
    }
}
