package br.com.github.order.service.domain;

import br.com.github.order.service.domain.enums.OrderStatus;
import br.com.github.order.service.domain.enums.OrderType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "TB_ORDER", schema = "home_broker")
public class Order {

    @Id
    @SequenceGenerator(name = "order_seq", schema = "home_broker", sequenceName = "order_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_SHARE")
    private Share share;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENT")
    private Client client;

    @Column(name = "MIN_PRICE")
    private BigDecimal minPrice;

    @Column(name = "MAX_PRICE")
    private BigDecimal maxPrice;

    @Column(name = "AMOUNT")
    private Integer amount;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "ORDER_TYPE")
    @Enumerated(EnumType.STRING)
    private OrderType orderType;


}
