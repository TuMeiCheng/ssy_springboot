package com.wande.ssy.config;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.wall.WallFilter;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.source.ClassPathSourceFactory;

/**
 * @author chox su
 * @date 2017/11/29 10:16
 */
@Configuration
public class ActiveRecordPluginConfig {

	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.url}")
	private String url;

	@Bean
	public ActiveRecordPlugin initActiveRecordPlugin() {

		DruidPlugin druidPlugin = new DruidPlugin(url, username, password);
		// 加强数据库安全
		WallFilter wallFilter = new WallFilter();
		wallFilter.setDbType("mysql");
		druidPlugin.addFilter(wallFilter);
		// 添加 StatFilter 才会有统计数据
		// druidPlugin.addFilter(new StatFilter());
		// 必须手动调用start
		druidPlugin.start();

		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
		JfinalORM.mapping(arp);
		arp.setShowSql(false);

		arp.getEngine().setSourceFactory(new ClassPathSourceFactory());
//		arp.addSqlTemplate("/sql/all_sqls.sql");
		// 必须手动调用start
		arp.start();
		return arp;
	}

}