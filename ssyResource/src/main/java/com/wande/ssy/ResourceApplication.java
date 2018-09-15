package com.wande.ssy;

import com.alibaba.fastjson.JSON;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.*;
import com.wande.ssy.entity.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

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
		//模糊查询
		Kv cond2 = Kv.by("'a' = 'a'","");
		cond2.set("provideWay  = ", 1);     //添加条件
		String name_par = "区广场";
		cond2.set("name", " like '%"+ name_par +"%'"); //模糊查询
		SqlPara sp = Db.getSqlPara("find", Kv.by("cond", cond2));  //获取
		System.out.println(sp.getSql());
		Page<Record> page = Db.paginate(1,10,sp);
		for (Record record : page.getList()) {
			System.out.println(record.getStr("name")+"...............");
		}

		//分页查询
		Page<Record> ls = Db.paginate(1, 8, "select * ", "from eqp_area where 1=1 and areaType =  2  and name like '%广场%'");
		for (Record record : ls.getList()) {
			System.out.println(record.getStr("name"));
		}


		Log l = new Log().findById(17);
		System.out.println(JSON.toJSONString(l.toRecord().getColumns()));
		Record r1 = Db.findFirst("select * from eqp_log where logId = "+17);
		System.out.println(r1);

		User u = new User();
		u.setPhone("13452900610");
		u.setName("张大大");
		u.setCreateBy(32L);
		u.setSkey(22222);
		u.setPassword("SDKGW424252");
		u.setStatus(1);
		u.setCreateBy(323L);
		u.setCreateTime(new Date().getTime());
		System.out.println(u.getName()+"大傻逼");
		System.out.println(u.toRecord().toString()+"eeeeeeeeeee");

		u.save();

		User user = new User();
		Record uuu = new Record().set("name","习近平").set("phone","110").set("pwd","2343").set("skey",2512).set("status",2)
				.set("createTime",new Date().getTime()).set("createBy",33);
		System.out.println(uuu.toString()+"uuuuuuuu");
		Db.save("eqp_user",u.toRecord());
		user = user.findFirst("SELECT * FROM eqp_user where phone = "+u.getPhone());

		System.out.println(Db.find("SELECT * FROM eqp_user where phone = ?", u.getPhone()).get(0));
		//System.out.println(user.findFirst("SELECT * FROM eqp_user where phone = ?", u.getPhone()).getName());
		if (user != null) {
			System.out.println("该手机已经注册");
			System.out.println(user.toString());
		}else {
			//否则保存到数据库
			System.out.println("可以注册");
		}
		return Ret.ok("data", JSON.toJSON(r1.getColumns())).set("msg", "成功").set("data1",JSON.toJSONString(l.toRecord().getColumns())).set("data2", l);
    }

	@RequestMapping("/abc/tt")
	public Object tt(@RequestParam("name") String  nn) {
		System.out.println("来到tt"+nn);
		Log l = new Log().findById(17);
		System.out.println(JSON.toJSONString(l.toRecord().getColumns()));
		Record r1 = Db.findFirst("select * from eqp_log where logId = "+17);
		System.out.println(r1);
		return Ret.ok("data", JSON.toJSON(r1.getColumns()))
				.set("msg", "成功")
				.set("data1",JSON.toJSONString(l.toRecord().getColumns()))
				.set("data2", l);
	}

	@RequestMapping("/addUser")
	public Object addUser(@Valid User user){
		System.out.println("添加用户");
		System.out.println(user.toString());
		System.out.println(user.get("name")+"可惜");
		System.out.println(user.getName());
		return "252525fsdf";
	}
	@RequestMapping("/addArea")
	public Object addArea(@Valid Area area){
		System.out.println("添加场地");
		System.out.println(area.toString());
		System.out.println(area.get("name")+"");
		System.out.println(area.getName());
		return area.toJson();
	}


	@RequestMapping("/addEqp")
	public Object addeqp(@Valid Eqp eqp){
		System.out.println("添加器材");
		System.out.println(eqp.toString());
		System.out.println(eqp.get("name")+"");
		System.out.println(eqp.getName());
		eqp.setImgurl("www.sdgs.png");
		System.out.println(eqp.getImgurl());
		eqp.setModifyTime(12345678);
		System.out.println(eqp.getModifyTime());
		return eqp.toJson();
	}

	@RequestMapping("/test")
	public Object addeqp(Integer uin){
		Admin admin = new Admin();
		Admin aa = admin.findById(uin);
		System.out.println("我是aa:"+aa.toString());

		if (admin ==null) {
			System.out.println("查询不到");
		}

		return admin;
	}



}



