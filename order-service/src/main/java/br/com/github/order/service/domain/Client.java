package br.com.github.order.service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_CLIENT", schema = "home_broker")
public class Client {

    @Id
    @SequenceGenerator(name = "client_seq", schema = "home_broker", sequenceName = "client_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "IDENTIFICATION_DOCUMENT")
    private String identificationDocument;

    @Column(name = "FULL_NAME")
    private String fullName;
}
