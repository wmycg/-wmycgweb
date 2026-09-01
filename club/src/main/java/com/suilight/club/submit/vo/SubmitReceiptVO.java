package com.suilight.club.submit.vo;

import com.suilight.club.submit.entity.Submit;
import lombok.Data;

import java.time.LocalDateTime;

/** 游客提交报名后的回执，不返回个人报名内容。 */
@Data
public class SubmitReceiptVO {
    private Integer id;
    private LocalDateTime date;

    public static SubmitReceiptVO from(Submit submit) {
        SubmitReceiptVO vo = new SubmitReceiptVO();
        vo.setId(submit.getId());
        vo.setDate(submit.getDate());
        return vo;
    }
}
