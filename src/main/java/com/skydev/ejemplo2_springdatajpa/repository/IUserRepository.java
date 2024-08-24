package com.skydev.ejemplo2_springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import com.skydev.ejemplo2_springdatajpa.entity.User;


@Repository
public interface IUserRepository extends JpaRepository<User, Long>, RevisionRepository<User, Long, Integer>{

}
