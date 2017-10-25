package com.fortune.springboot.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动类
 * @author fortune.wu
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
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
