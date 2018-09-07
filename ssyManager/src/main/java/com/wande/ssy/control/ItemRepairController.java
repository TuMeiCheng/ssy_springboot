package com.wande.ssy.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.ItemrepairService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itemrepair")
public class ItemRepairController {

    @Reference(interfaceClass=ItemrepairService.class)
    private ItemrepairService itemrepairService;
}
