package com.skc.order1.service;

import org.springframework.stereotype.Service;

import com.skc.order.dto.OrderRequest;
import com.skc.order.service.MessageService1;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService1 {

	private final MessageService1 messageService1;

	public OrderService1(MessageService1 messageService1) {
		this.messageService1 = messageService1;
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
		// 2. 메일 발송 : @Primary 우선선택
		// ----------------------------------
		messageService1.send(request.getEmail(), "주문이 완료되었습니다.");

//		// ----------------------------------
//		// 3. SMS 발송 : 발송 안됨
//		// ----------------------------------


		log.info("===== 주문 서비스 종료 =====");

	}

}
