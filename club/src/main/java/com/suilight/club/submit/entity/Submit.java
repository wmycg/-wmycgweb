package com.suilight.club.submit.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@TableName("submits")
public class Submit {
    @TableId(type = IdType.AUTO) private Integer id;
    private LocalDateTime date;
    private String webname;
    private String truename;
    private String ncunum;
    /** 数据库列名为 QQ，必须显式映射，避免被推导为 q_q。 */
    @TableField("QQ")
    private String QQ;
    private String aimpartment;
}
