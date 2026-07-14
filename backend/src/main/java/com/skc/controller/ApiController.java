package com.skc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:5173") // React-vite 포트 허용
public class ApiController {

	@GetMapping("/hello")
	public Map<String, String> sayHello() {
		Map<String, String> response = new HashMap<>();
		response.put("message", "API Spring - Boot에서 보낸 데이터입니다!");
		return response;
	}

}
