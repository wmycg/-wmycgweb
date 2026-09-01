package com.suilight.club.submit.service;
import com.suilight.club.submit.entity.Submit;
import java.util.List;
public interface SubmitService {
    Submit findById(Integer id);
    List<Submit> findAll();
    boolean create(Submit submit);
}
