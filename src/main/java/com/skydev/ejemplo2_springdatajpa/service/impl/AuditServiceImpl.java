package com.skydev.ejemplo2_springdatajpa.service.impl;

import org.springframework.stereotype.Service;

import com.skydev.ejemplo2_springdatajpa.entity.Audit;
import com.skydev.ejemplo2_springdatajpa.repository.IAuditRepository;
import com.skydev.ejemplo2_springdatajpa.service.IAuditService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements IAuditService{

    private final IAuditRepository auditRepository;

    @Override
    public Audit save(Audit audit) {
        return auditRepository.save(audit);
    }

}
