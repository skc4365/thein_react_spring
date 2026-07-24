package com.skc.order.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skc.order.dto.OrderRequest;
import com.skc.order1.service.OrderService1;
import com.skc.order2.service.OrderService2;
import com.skc.order3.service.OrderService3;
import com.skc.order4.service.OrderService4;
import com.skc.order5.service.OrderService5;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService1 orderService1;
	private final OrderService2 orderService2;
	private final OrderService3 orderService3;
	private final OrderService4 orderService4;
	private final OrderService5 orderService5;
	
	public OrderController(
			OrderService1 orderService1, 
			OrderService2 orderService2,
			OrderService3 orderService3,
			OrderService4 orderService4,
			OrderService5 orderService5 
			) 
	{
		this.orderService1 = orderService1;
		this.orderService2 = orderService2;
		this.orderService3 = orderService3;
		this.orderService4 = orderService4;
		this.orderService5 = orderService5;
	}

	@PostMapping("/ex1")
	public String order1(@RequestBody OrderRequest request) {

		log.info("Controller 요청 시작");

		orderService1.order(request);

		log.info("Controller 요청 종료");

		return "주문 완료-1";

	}

	@PostMapping("/ex2")
	public String order2(@RequestBody OrderRequest request) {

		log.info("Controller 요청 시작");

		orderService2.order(request);

		log.info("Controller 요청 종료");

		return "주문 완료-2";

	}

	@PostMapping("/ex3")
	public String order3(@RequestBody OrderRequest request) {

		log.info("Controller 요청 시작");

		orderService3.order(request);

		log.info("Controller 요청 종료");

		return "주문 완료-3";

	}

	@PostMapping("/ex4")
	public String order4(@RequestBody OrderRequest request) {

		log.info("Controller 요청 시작");

		orderService4.order(request);

		log.info("Controller 요청 종료");

		return "주문 완료-4";

	}

	@PostMapping("/ex5")
	public String order5(@RequestBody OrderRequest request) {

		log.info("Controller 요청 시작");

		orderService5.order(request);

		log.info("Controller 요청 종료");

		return "주문 완료-5";

	}

}
