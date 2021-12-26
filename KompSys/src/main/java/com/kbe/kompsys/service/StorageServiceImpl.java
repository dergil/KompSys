//package com.kbe.kompsys.service;
//
//import com.jcraft.jsch.*;
//import com.kbe.kompsys.service.interfaces.StorageService;
//import org.springframework.stereotype.Service;
//
//@Service
//public class StorageServiceImpl implements StorageService {
//    private String remoteHost = "localhost";
//    private String username = "username";
//    private String password = "password";
//    private int port = 22;
//
//    @Override
//    public void transferFile (String localFile, String remoteDir) throws JSchException, SftpException {
//        ChannelSftp channelSftp = setupJsch();
//        channelSftp.connect();
//
//        channelSftp.put(localFile, remoteDir + "jschFile.txt");
//
//        channelSftp.exit();
//    }
//
//    private ChannelSftp setupJsch() throws JSchException {
//        JSch jsch = new JSch();
//        jsch.setKnownHosts("/home/USERNAME/.ssh/known_hosts");
//        Session jschSession = jsch.getSession(username, remoteHost, port);
//        jschSession.setPassword(password);
//        JSch.setConfig("StrictHostKeyChecking", "no");
//        jschSession.connect();
//        return (ChannelSftp) jschSession.openChannel("sftp");
//    }
//}
