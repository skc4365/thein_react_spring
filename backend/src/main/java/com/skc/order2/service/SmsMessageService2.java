package com.skc.order2.service;

import org.springframework.stereotype.Service;

import com.skc.order.service.MessageService2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmsMessageService2 implements MessageService2 {

	@Override
	public void send(String receiver, String message) {

		log.info("========== SMS ==========");
		log.info("받는사람 : {}", receiver);
		log.info("내용 : {}", message);
		log.info("=========================");
	}

}
