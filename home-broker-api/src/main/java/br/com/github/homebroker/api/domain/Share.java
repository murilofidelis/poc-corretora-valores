package br.com.github.homebroker.api.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@ToString
@Table(name = "TB_SHARE", schema = "home_broker")
public class Share {

    @Id
    @SequenceGenerator(name = "share_seq", schema = "home_broker", sequenceName = "share_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "share_seq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "COD")
    private String cod;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "LAST_PRINCE", scale = 2)
    private BigDecimal lastPrice;

}
