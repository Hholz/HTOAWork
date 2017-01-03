package com.ht.serviceImpl.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ht.service.task.TaskJopService;

@Component
public class TaskJopServiceImpl implements TaskJopService {
//    @Scheduled(cron="0/5 * *  * * ?") 
	@Override
	public void testJop() {
//    System.out.println("每5s执行一次==========================");
	}

}
