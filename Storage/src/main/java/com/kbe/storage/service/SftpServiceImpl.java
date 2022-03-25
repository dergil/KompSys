package com.kbe.storage.service;

import com.jcraft.jsch.*;
import com.kbe.storage.service.interfaces.SftpService;
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
  public void getFile(String src, String destination) throws JSchException, SftpException {
    ChannelSftp channelSftp = setupJsch();
    channelSftp.connect();
    try {
      channelSftp.get(src, destination);
    } catch (Exception ignored) {

    }

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
