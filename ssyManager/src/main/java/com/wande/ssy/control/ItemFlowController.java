package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.ItemflowService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itemflow")
public class ItemFlowController {

    @Reference(interfaceClass=ItemflowService.class)
    private ItemflowService itemflowService;


}
