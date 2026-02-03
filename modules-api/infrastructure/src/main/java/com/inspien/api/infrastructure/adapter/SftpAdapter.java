package com.inspien.api.infrastructure.adapter;

import com.inspien.api.application.port.out.SftpOutPort;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Properties;

@Component
public class SftpAdapter implements SftpOutPort {
    @Value("${sftp.host}")
    private String host;

    @Value("${sftp.port}")
    private int port;

    @Value("${sftp.username}")
    private String username;

    @Value("${sftp.password}")
    private String password;

    @Value("${sftp.remote-dir}")
    private String remoteDir;

    @Override
    public void sendBySftp(Path localFile) {
        Session session = null;
        ChannelSftp channel = null;

        try {
            JSch jsch = new JSch();

            session = jsch.getSession(username, host, port);
            session.setPassword(password);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();

            channel.cd(remoteDir);

            channel.put(
                    localFile.toString(),
                    localFile.getFileName().toString()
            );

        } catch (Exception e) {
            throw new RuntimeException("SFTP 전송 실패", e);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
}
