USE ProyectoJPA;
CREATE TABLE audit
(
    audit_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    entity_type     VARCHAR(50) NOT NULL,
    entity_id       BIGINT NOT NULL,
    action          VARCHAR(50) NOT NULL,
    previous_data   TEXT,
    date_change     DATETIME NOT NULL
);