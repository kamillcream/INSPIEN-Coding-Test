package com.inspien.application.port.out;

import java.nio.file.Path;

public interface SftpOutPort {
    void sendBySftp(Path localFile);
}
