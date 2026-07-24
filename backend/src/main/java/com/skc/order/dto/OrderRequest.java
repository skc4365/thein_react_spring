package com.skc.order.dto;

import lombok.Data;

@Data
public class OrderRequest {
	private String productName;
	private int quantity;
	private String customerName;
	private String phone;
	private String email;
}
