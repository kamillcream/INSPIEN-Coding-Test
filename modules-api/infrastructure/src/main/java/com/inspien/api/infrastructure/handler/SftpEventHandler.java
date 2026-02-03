package com.inspien.api.infrastructure.handler;

import com.inspien.api.application.port.out.SftpOutPort;
import com.inspien.api.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
@RequiredArgsConstructor
@Slf4j
public class SftpEventHandler {
    private final SftpOutPort sftpAdapter;

    @Retryable(value = IOException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreated(Order order) throws IOException{
        String receiptContent = generateFileContent(order);
        try {
            File receiptFile = generateReceiptTxtFile(receiptContent);
            sftpAdapter.sendBySftp(receiptFile.toPath());
            log.info("[ORDER_RECEIPT_SEND] orderId={} step=SUCCESS", order.getOrderId());
        } catch (IOException e) {
            log.error(
                    "[ORDER_RECEIPT_SEND] orderId={} step=FAIL reason={}",
                    order.getOrderId(),
                    e.getMessage(),
                    e
            );
            throw e;
        }

    }

    private File generateReceiptTxtFile(String content) throws IOException {
        String fileName = "INSPIEN_[박진영]_" + "[" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "]" +".txt";
        Path path = Path.of(fileName);

        Files.writeString(
                path,
                content,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );

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
