package com.skc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skc.aop.LogExecutionTime;
import com.skc.entity.Hello;
import com.skc.service.HelloService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

	private final HelloService service;

	public HelloController(HelloService service) {
		this.service = service;
	}

	@Operation(summary = "회원조회", description = "회원목록을 조회합니다.")
	@GetMapping
	public List<Hello> list() {
		return service.findAll();
	}

	@PostMapping
	public Hello save(@RequestBody Hello hello) {
		return service.save(hello);
	}

	@PutMapping("/{id}")
	public Hello update(@PathVariable Long id, @RequestBody Hello hello) {

		return service.update(id, hello);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	
//	====== AOP ======
	@LogExecutionTime
    @GetMapping("/slow")
    public String slowMethod() throws InterruptedException {
        Thread.sleep(500); // 느린 작업 시뮬레이션
        return "완료!";
    }

    @GetMapping("/error")
    public String errorTest() {
        throw new RuntimeException("일부러 발생시킨 예외임당");
    }
	
	

}
