package com.skc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.skc.entity.Hello;

public interface HelloRepository extends JpaRepository<Hello, Long> {

}
