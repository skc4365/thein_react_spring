package com.skc.order2.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skc.order.dto.OrderRequest;
import com.skc.order.service.MessageService2;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService2 {

	private final MessageService2 messageService2;

	public OrderService2(@Qualifier("smsMessageService2") MessageService2 messageService2) {
		this.messageService2 = messageService2;
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
		// 2. 메일 발송 : 발송 안됨
		// ----------------------------------


//		// ----------------------------------
//		// 3. SMS 발송 : @Qualifier("smsMessageService2") 선택하여 발송함
//		// ----------------------------------
		messageService2.send(request.getPhone(), "주문이 완료되었습니다.");

		log.info("===== 주문 서비스 종료 =====");

	}

}
