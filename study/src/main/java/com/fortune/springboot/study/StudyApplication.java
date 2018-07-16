package com.fortune.springboot.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动类
 * @author fortune.wu
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class, ActiveMQAutoConfiguration.class})
@EnableCaching
public class StudyApplication {

	/**
	 * 启动
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(StudyApplication.class, args);
	}
}
