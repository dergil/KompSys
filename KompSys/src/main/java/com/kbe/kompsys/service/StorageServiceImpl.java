package com.kbe.kompsys.service;

import com.jcraft.jsch.*;
import com.kbe.kompsys.service.interfaces.StorageService;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {
    private String remoteHost = "localhost";
    private String username = "archdoc";
    private String privateKey = "/home/archdoc/.ssh/id_rsa";

    @Override
    public void transferFile (String localFile, String remoteDir) throws JSchException, SftpException {
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();

        channelSftp.put(localFile, remoteDir + "jschFile.txt");

        channelSftp.exit();
    }

    private ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts("/home/archdoc/.ssh/known_hosts");
        Session jschSession = jsch.getSession(username, remoteHost);
        jsch.addIdentity(privateKey);
        JSch.setConfig("StrictHostKeyChecking", "no");
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");
    }
}
