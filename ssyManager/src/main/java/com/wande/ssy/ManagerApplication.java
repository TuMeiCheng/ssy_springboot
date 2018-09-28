package com.wande.ssy;


import com.alibaba.dubbo.config.annotation.Reference;
import com.wande.ssy.dubbo.provider.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ImportResource({"classpath:dubbo-service-manager.xml" })
public class ManagerApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ManagerApplication.class);
    }


    @Reference(interfaceClass=UserService.class)
    private UserService userService;

    @RequestMapping("/")
    public Object getInfo(){

        return "索拉卡赶集网";
    }
	
}
