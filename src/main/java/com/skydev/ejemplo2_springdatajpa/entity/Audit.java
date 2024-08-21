package com.skydev.ejemplo2_springdatajpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Long id;
    
    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "entity_id")
    private Long entityId;

    private String action;

    @Column(name = "previous_data")
    private String previousData;

    @Column(name = "date_change")
    private LocalDateTime dateChange;

    public Audit(String entityType, Long entityId, String action, String previousData, LocalDateTime dateChange) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.action = action;
        this.previousData = previousData;
        this.dateChange = dateChange;
    }

}
