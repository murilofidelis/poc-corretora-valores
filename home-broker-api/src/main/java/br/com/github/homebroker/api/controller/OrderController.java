package br.com.github.homebroker.api.controller;

import br.com.github.homebroker.api.domain.dto.OrderDto;
import br.com.github.homebroker.api.domain.dto.SendDto;
import br.com.github.homebroker.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping()
    public ResponseEntity<Void> send(@RequestBody List<OrderDto> orders) {
        service.send(orders);
        return ResponseEntity.ok().build();
    }

}
