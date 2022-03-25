package com.kbe.storage.service.interfaces;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public interface SftpService {
  void getFile(String src, String destination) throws JSchException, SftpException;
}
