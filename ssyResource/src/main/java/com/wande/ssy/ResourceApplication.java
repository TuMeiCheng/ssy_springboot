package com.wande.ssy;

import com.wande.ssy.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wande.ssy.entity.Log;

@RestController
@SpringBootApplication
@ImportResource({"classpath:dubbo-service-provider.xml" })
public class ResourceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ResourceApplication.class, args);
	}
	

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ResourceApplication.class);
    }
    
    @RequestMapping("/")
    public Object test() {
		Log l = new Log().findById(17);
		System.out.println(JSON.toJSONString(l.toRecord().getColumns()));
		Record r1 = Db.findFirst("select * from eqp_log where logId = "+17);
		System.out.println(r1);
		return Ret.ok("data", JSON.toJSON(r1.getColumns())).set("msg", "成功").set("data1",JSON.toJSONString(l.toRecord().getColumns())).set("data2", l);
    }

	@RequestMapping("/abc/tt")
	public Object tt(@RequestParam("name") String  nn) {
		System.out.println("来到tt"+nn);
		Log l = new Log().findById(17);
		System.out.println(JSON.toJSONString(l.toRecord().getColumns()));
		Record r1 = Db.findFirst("select * from eqp_log where logId = "+17);
		System.out.println(r1);
		return Ret.ok("data", JSON.toJSON(r1.getColumns())).set("msg", "成功").set("data1",JSON.toJSONString(l.toRecord().getColumns())).set("data2", l);
	}

}
