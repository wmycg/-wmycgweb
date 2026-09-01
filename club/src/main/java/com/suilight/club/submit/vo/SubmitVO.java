package com.suilight.club.submit.vo;

import com.suilight.club.submit.entity.Submit;
import lombok.Data;

import java.time.LocalDateTime;

/** 仅管理员接口返回的报名详情。 */
@Data
public class SubmitVO {
    private Integer id;
    private LocalDateTime date;
    private String webname;
    private String truename;
    private String ncunum;
    private String qq;
    private String aimpartment;

    public static SubmitVO from(Submit submit) {
        SubmitVO vo = new SubmitVO();
        vo.setId(submit.getId());
        vo.setDate(submit.getDate());
        vo.setWebname(submit.getWebname());
        vo.setTruename(submit.getTruename());
        vo.setNcunum(submit.getNcunum());
        vo.setQq(submit.getQQ());
        vo.setAimpartment(submit.getAimpartment());
        return vo;
    }
}
