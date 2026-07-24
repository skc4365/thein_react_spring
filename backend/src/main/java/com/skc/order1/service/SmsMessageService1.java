package com.skc.order1.service;

import org.springframework.stereotype.Service;

import com.skc.order.service.MessageService1;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmsMessageService1 implements MessageService1 {

	@Override
	public void send(String receiver, String message) {

		log.info("========== SMS ==========");
		log.info("받는사람 : {}", receiver);
		log.info("내용 : {}", message);
		log.info("=========================");
	}

}
