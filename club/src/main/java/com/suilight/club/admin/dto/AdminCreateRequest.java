package com.suilight.club.admin.dto;

import lombok.Data;

@Data
public class AdminCreateRequest {
    private String username;
    private String password;
    private Boolean supe;
}
