package com.kbe.storage;

import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SftpService {
    @Value("${remoteHost}")
    private String remoteHost;
    @Value("${sftp_username}")
    private String username;
    @Value("${sftp_password}")
    private String password;
    private int port = 22;

    public void getFile(String src, String destination) throws JSchException, SftpException {
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();
        channelSftp.get(src, destination);
        channelSftp.exit();
    }

    private ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        Session jschSession = jsch.getSession(username, remoteHost, port);
        jschSession.setPassword(password);
        JSch.setConfig("StrictHostKeyChecking", "no");
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");
    }
}
