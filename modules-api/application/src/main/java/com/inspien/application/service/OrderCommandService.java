package com.inspien.application.service;

import com.inspien.application.port.in.OrderCommandUseCase;
import com.inspien.application.port.in.OrderCreateCommand;
import com.inspien.application.port.in.OrderHeader;
import com.inspien.application.port.in.OrderItem;
import com.inspien.application.port.out.OrderOutPort;
import com.inspien.application.port.out.SftpOutPort;
import com.inspien.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class OrderCommandService implements OrderCommandUseCase {
    private final OrderOutPort repo;
    private final SftpOutPort sftpService;

    @Value("${applicantKey}")
    private String applicantKey;

    @Override
    public void processOrder(OrderCreateCommand command) {
        List<OrderHeader> headers = command.getHeaders();
        Map<String, OrderHeader> headersByUser = groupingHeaderByUser(headers);

        for (OrderItem item : command.getItems()) {
            if(!headersByUser.containsKey(item.getUserId())) {
                throw new IllegalArgumentException("Header에 존재하지 않는 userId: " + item.getUserId());
            }
            OrderHeader header = headersByUser.get(item.getUserId());

            Order order = Order.create(generateOrderId(), item.getUserId(), item.getItemId(), applicantKey,
                    header.getName(), header.getAddress(), item.getItemName(), item.getPrice(), header.getStatus());

            repo.save(order);

            String receiptContent = generateFileContent(order);
            try {
                File receiptFile = generateReceiptTxtFile(receiptContent);
                sftpService.sendBySftp(receiptFile.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private Map<String, OrderHeader> groupingHeaderByUser(List<OrderHeader> headers) {
        return headers.stream()
                .collect(Collectors.toMap(
                        OrderHeader::getUserId,
                        Function.identity()
                ));
    }

    private File generateReceiptTxtFile(String content) throws IOException {
        String fileName = "INSPIEN_[박진영]_" + "[" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "]" +".txt";
        Path path = Path.of(fileName);

        Files.writeString(path, content, StandardCharsets.UTF_8);

        return path.toFile();
    }

    private String generateFileContent(Order order) {
        return String.join("^",
                order.getOrderId(),
                order.getUserId(),
                order.getItemId(),
                order.getApplicantKey(),
                order.getName(),
                order.getAddress(),
                order.getItemName(),
                order.getPrice()
        ) + "\n";
    }

    private String generateOrderId() {
        String lastId = repo.findLastId();

        if (lastId == null) {
            return "A001";
        }

        char prefix = lastId.charAt(0);
        int number = Integer.parseInt(lastId.substring(1));

        number++;

        if (number > 999) {
            number = 1;
            prefix++;

            if (prefix > 'Z') {
                throw new IllegalStateException("주문 ID 최대 범위를 초과했습니다.");
            }
        }
        return String.format("%c%03d", prefix, number);
    }
}
