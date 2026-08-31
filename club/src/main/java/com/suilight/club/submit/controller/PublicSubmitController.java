package com.suilight.club.submit.controller;

import com.suilight.club.submit.entity.Submit;
import com.suilight.club.submit.service.SubmitService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 新生公开提交报名表单。 */
@RestController
@RequestMapping("/api/submits")
public class PublicSubmitController {

    private final SubmitService submitService;

    public PublicSubmitController(SubmitService submitService) {
        this.submitService = submitService;
    }

    @PostMapping
    public boolean create(@RequestBody Submit submit) {
        return submitService.create(submit);
    }
}
