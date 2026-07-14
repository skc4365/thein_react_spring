package com.skc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class MainController {

	@GetMapping("/main-hello")
	public Map<String, String> sayMainHello() {
		Map<String, String> response = new HashMap<>();
		response.put("message", "http://localhost:8090/main/main-hello에서 보낸 데이터");
		return response;
	}
}
