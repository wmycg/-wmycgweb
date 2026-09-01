package com.suilight.club.submit.dto;

import com.suilight.club.submit.entity.Submit;
import lombok.Data;

@Data
public class SubmitCreateRequest {
    private String webname;
    private String truename;
    private String ncunum;
    private String QQ;
    private String aimpartment;

    public Submit toEntity() {
        Submit submit = new Submit();
        submit.setWebname(webname);
        submit.setTruename(truename);
        submit.setNcunum(ncunum);
        submit.setQQ(QQ);
        submit.setAimpartment(aimpartment);
        return submit;
    }
}
