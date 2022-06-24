package com.example.mssqlapp;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class MssqlappApplication {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(MssqlappApplication.class, args).close();
	}


    @Bean
    Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int poolSize = Runtime.getRuntime().availableProcessors();
        // int poolSize = 1;
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize);
        // executor.setQueueCapacity(10000);
        executor.setThreadNamePrefix("FcmPoCreator - ");
        executor.initialize();
        return executor;
    }
}