package com.skc.order3.service;

import org.springframework.stereotype.Service;

import com.skc.order.service.MessageService3;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailMessageService3 implements MessageService3 {
	
	@Override
	public void send(String receiver, String message) {
		
      log.info("========== EMAIL ==========");
      log.info("받는사람 : {}", receiver);
      log.info("내용 : {}", message);
      log.info("===========================");

	}

}
