package com.skc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skc.entity.Hello;
import com.skc.repository.HelloRepository;

@Service
public class HelloService {

	private final HelloRepository repository;

	public HelloService(HelloRepository repository) {
		this.repository = repository;
	}

	public List<Hello> findAll() {
		return repository.findAll();
	}

	public Hello save(Hello hello) {
		return repository.save(hello);
	}

	public Hello update(Long id, Hello hello) {
		Hello entity = repository.findById(id).orElseThrow();

		entity.setMessage(hello.getMessage());

		return repository.save(entity);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

}
