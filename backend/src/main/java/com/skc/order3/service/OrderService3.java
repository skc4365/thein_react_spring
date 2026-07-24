package com.skc.order3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skc.order.dto.OrderRequest;
import com.skc.order.service.MessageService3;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService3 {

	private final List<MessageService3> listService3;

	public OrderService3(List<MessageService3> listService3) {
		this.listService3 = listService3;
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
		// 2. 메일 발송, 3. SMS 발송 : 순차적으로 둘 다 발송.
		// ----------------------------------
		listService3.forEach(
				service3 -> 
				service3.send(("" + request.getEmail() + request.getPhone() + ""), "주문이 완료되었습니다."));

		log.info("===== 주문 서비스 종료 =====");

	}

}
