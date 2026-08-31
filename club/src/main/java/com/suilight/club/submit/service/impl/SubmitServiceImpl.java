package com.suilight.club.submit.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suilight.club.submit.entity.Submit;
import com.suilight.club.submit.mapper.SubmitMapper;
import com.suilight.club.submit.service.SubmitService;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class SubmitServiceImpl extends ServiceImpl<SubmitMapper, Submit> implements SubmitService {
    public Submit findById(Integer id) { return getById(id); }
    public List<Submit> findAll() { return list(); }
    public boolean create(Submit submit) { validate(submit); return save(submit); }
    public boolean update(Submit submit) { validate(submit); if (submit.getId() == null || getById(submit.getId()) == null) throw new IllegalArgumentException("报名记录不存在"); return updateById(submit); }
    public boolean delete(Integer id) { if (getById(id) == null) throw new IllegalArgumentException("报名记录不存在"); return removeById(id); }
    private void validate(Submit s) { if (s == null || blank(s.getTruename()) || blank(s.getNcunum())) throw new IllegalArgumentException("姓名和学号不能为空"); if (s.getTruename().length() > 20 || s.getNcunum().length() > 10 || len(s.getWebname()) > 20 || len(s.getQq()) > 15 || len(s.getAimpartment()) > 29) throw new IllegalArgumentException("报名字段长度超过数据库限制"); }
    private int len(String v) { return v == null ? 0 : v.length(); }
    private boolean blank(String v) { return v == null || v.isBlank(); }
}
