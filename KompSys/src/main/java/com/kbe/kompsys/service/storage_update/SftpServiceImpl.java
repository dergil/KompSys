package com.kbe.kompsys.service.storage_update;

import com.jcraft.jsch.*;
import com.kbe.kompsys.service.interfaces.SftpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SftpServiceImpl implements SftpService {
    @Value("${remoteHost}")
    private String remoteHost;
    @Value("${sftp_username}")
    private String username;
    @Value("${sftp_password}")
    private String password;
    private int port = 22;

    @Override
    public void putFile(String localFile, String remoteDir) throws JSchException, SftpException {
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();
//        leading slash needed
        channelSftp.put(localFile, remoteDir + "/file.txt");
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
