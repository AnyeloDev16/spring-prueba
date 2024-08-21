package com.skydev.ejemplo2_springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skydev.ejemplo2_springdatajpa.entity.Audit;

@Repository
public interface IAuditRepository extends JpaRepository<Audit, Long>{

}
