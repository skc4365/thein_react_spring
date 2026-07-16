package com.skc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skc.entity.Hello;

@Repository
public interface HelloRepository extends JpaRepository<Hello, Long> {

}
