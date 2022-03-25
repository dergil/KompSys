package com.kbe.kompsys.service.interfaces;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public interface SftpService {
    void putFile(String localFile, String remoteDir) throws JSchException, SftpException;
}
