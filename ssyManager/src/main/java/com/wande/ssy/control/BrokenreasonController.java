package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.BrokenreasonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/brokenreason")
@RestController
public class BrokenreasonController {

    @Reference(interfaceClass=BrokenreasonService.class)
    private BrokenreasonService brokenreasonService;

}
