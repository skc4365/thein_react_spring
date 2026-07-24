package com.skc.order4.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.skc.order.dto.OrderRequest;
import com.skc.order.service.MessageService4;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService4 {

	private final Map<String, MessageService4> serviceMap4;

	public OrderService4(Map<String, MessageService4> serviceMap4) {
		this.serviceMap4 = serviceMap4;
	}

	@Transactional
	public void order(OrderRequest request) {

		// ----------------------------------
		// 1. 주문 생성
		// ----------------------------------
		log.info("===== 주문 생성 =====");
		log.info("상품 : {}", request.getProductName());
		log.info("수량 : {}", request.getQuantity());
		log.info("주문자 : {}", request.getCustomerName());

		// repository.save(order);
		log.info("주문 DB 저장 완료");

		// ----------------------------------
		// 2. 메일 발송 : Map 발송됨
		// ----------------------------------
		MessageService4 email = serviceMap4.get("emailMessageService4");
		email.send(request.getEmail(), "주문 완료");

//		// ----------------------------------
//		// 3. SMS 발송 : Map 발송됨
//		// ----------------------------------
		MessageService4 sms = serviceMap4.get("smsMessageService4");
		sms.send(request.getPhone(), "주문 완료");

		
		log.info("===== 주문 서비스 종료 =====");

	}

}