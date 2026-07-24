package com.skc.order5.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skc.order.dto.OrderRequest;
import com.skc.order.service.MessageService5;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService5 {

    private final MessageService5 emailService5;

    private final MessageService5 smsService5;
    
    public OrderService5(
            @Qualifier("emailMessageService5") MessageService5 emailService5,
            @Qualifier("smsMessageService5") MessageService5 smsService5) {

        this.emailService5 = emailService5;
        this.smsService5 = smsService5;
    }

    @Transactional
    public void order(OrderRequest request) {

        //----------------------------------
        // 1. 주문 생성
        //----------------------------------
        log.info("===== 주문 생성 =====");
        log.info("상품 : {}", request.getProductName());
        log.info("수량 : {}", request.getQuantity());
        log.info("주문자 : {}", request.getCustomerName());

        // repository.save(order);
        log.info("주문 DB 저장 완료");

        //----------------------------------
        // 2. 메일 발송
        //----------------------------------
        emailService5.send(
                request.getEmail(),
                "주문이 완료되었습니다."
        );

        //----------------------------------
        // 3. SMS 발송
        //----------------------------------
        smsService5.send(
                request.getPhone(),
                "주문이 완료되었습니다."
        );

        log.info("===== 주문 서비스 종료 =====");

    }

}
