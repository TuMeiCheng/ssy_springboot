package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.EqpsortService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eqpsort")
public class EqpSortController {

    @Reference(interfaceClass=EqpsortService.class)
    private EqpsortService eqpsortService;


}
