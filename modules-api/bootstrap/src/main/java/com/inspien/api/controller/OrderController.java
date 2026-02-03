package com.inspien.api.controller;

import com.inspien.api.dto.OrderRequestDto;
import com.inspien.api.application.port.in.OrderCommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderCommandUseCase orderCommandService;
    @PostMapping(
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrder(@Validated @RequestBody OrderRequestDto requestDto) {
        orderCommandService.processOrder(requestDto.toCommand());
        return ResponseEntity.ok("성공");
    }
}
