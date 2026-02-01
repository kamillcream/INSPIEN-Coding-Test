package com.inspien.application.service;

import com.inspien.application.port.in.OrderCommandUseCase;
import com.inspien.application.port.in.OrderCreateCommand;
import com.inspien.application.port.in.OrderHeader;
import com.inspien.application.port.in.OrderItem;
import com.inspien.application.port.out.OrderOutPort;
import com.inspien.application.port.out.SftpOutPort;
import com.inspien.domain.Order;
import lombok.RequiredArgsConstructor;
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

    @Override
    public void processOrder(OrderCreateCommand command) {
        List<OrderHeader> headers = command.getHeaders();
        Map<String, OrderHeader> headersByUser = groupingHeaderByUser(headers);

        for (OrderItem item : command.getItems()) {
            if(!headersByUser.containsKey(item.getUserId())) { // 예외. Item에 있는 UserId가 Header에는 없는 경우.
                throw new RuntimeException();
            }
            OrderHeader header = headersByUser.get(item.getUserId());

            Order order = Order.create("A114", item.getUserId(), item.getItemId(), "1",
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
}
