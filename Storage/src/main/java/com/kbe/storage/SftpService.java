package com.kbe.storage;

import com.jcraft.jsch.*;
import org.springframework.stereotype.Service;

@Service
public class SftpService {
    private String remoteHost = "sftp";
    private String username = "foo";
    private String password = "Mypassword123";
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
