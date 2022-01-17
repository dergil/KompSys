package com.kbe.storage.service;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumService {

    public static String createChecksum(final String filePath)
            throws FileNotFoundException,
            IOException,
            NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try {
            DigestInputStream dis = new DigestInputStream(new FileInputStream(filePath), md);
            while (dis.read() != -1) {
                md = dis.getMessageDigest();
            }
        } catch (Exception FileNotFoundException) {
            return "-1";
        }
        return convertToHex(md.digest());
    }


    private static String convertToHex(final byte[] messageDigest) {
        BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);
        while (hexText.length() < 32) {
            hexText = "0".concat(hexText);
        }
        return hexText;
    }

    public static boolean updateChecksum(final String filePath) {

        //todo:        storageService.transferFile( "./checksum/");


        return false;
    }
}

