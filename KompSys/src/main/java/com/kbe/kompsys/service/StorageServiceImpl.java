package com.kbe.kompsys.service;

import com.jcraft.jsch.*;
import com.kbe.kompsys.service.interfaces.StorageService;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {
    private String remoteHost = "sftp";
    private String username = "foo";
    private String password = "Mypassword123";
    private int port = 22;

    @Override
    public void transferFile (String localFile, String remoteDir) throws JSchException, SftpException {
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();
        System.out.println(localFile);
        channelSftp.put(localFile, remoteDir + "file.txt");
        channelSftp.exit();
    }

    private ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
//        jsch.setKnownHosts("/home/archdoc/.ssh/known_hosts");
        Session jschSession = jsch.getSession(username, remoteHost, port);
        jschSession.setPassword(password);
        JSch.setConfig("StrictHostKeyChecking", "no");
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");
    }
}
