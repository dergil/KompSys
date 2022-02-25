//package com.kbe.storage.service;
//
//
//import com.kbe.storage.service.interfaces.CsvImportService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.*;
//import java.math.BigInteger;
//import java.security.DigestInputStream;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Objects;
//import java.util.Scanner;
//
//@Service
//public class ChecksumService {
//
//  final String CHECKSUM_PATH = "S:\\___Studium\\5.Semester\\Storage\\src\\main\\resources\\checksum.txt";
//  final String CARCSV_PATH = "S:\\___Studium\\5.Semester\\Storage\\src\\main\\resources\\cars.csv";
//  private boolean checksumStatusIsUpToDate;
//
//  @Autowired
//  private final CsvImportService csvImportService;
//
//  public ChecksumService(CsvImportService csvImportService) {
//    this.csvImportService = csvImportService;
//  }
//
//  public String updateStorage() throws IOException, NoSuchAlgorithmException {
//    String storageStatus;
//
//    setChecksumStatus();
//    if (!checksumStatusIsUpToDate) {
//      updateDatabaseByCsv();
//      updateChecksum("xxxx");
//
//
//    }
//
//    return "";
//  }
//
//  private void setChecksumStatus() throws IOException, NoSuchAlgorithmException {
//    String savedChecksum = getCurrentChecksumFromFile();
//    String currentChecksum = createChecksum(CHECKSUM_PATH);
//
//    checksumStatusIsUpToDate = Objects.equals(savedChecksum, currentChecksum);
//
//  }
//
//
//  private String getCurrentChecksumFromFile() throws IOException {
//    String filepath = "src/test/resources/checksum.txt";
//
//    BufferedReader reader = new BufferedReader(new FileReader(filepath));
//    String hash = reader.readLine();
//    reader.close();
//    return hash;
//  }
//
//  private static String createChecksum(final String filePath) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
//
//    MessageDigest md = MessageDigest.getInstance("SHA-256");
//    try {
//      DigestInputStream dis = new DigestInputStream(new FileInputStream(filePath), md);
//      while (dis.read() != -1) {
//        md = dis.getMessageDigest();
//      }
//    } catch (Exception FileNotFoundException) {
//      return "-1";
//    }
//    return convertToHex(md.digest());
//  }
//
//  private static String convertToHex(final byte[] messageDigest) {
//    BigInteger bigint = new BigInteger(1, messageDigest);
//    String hexText = bigint.toString(16);
//    while (hexText.length() < 32) {
//      hexText = "0".concat(hexText);
//    }
//    return hexText;
//  }
//
//  public static boolean updateChecksum(final String filePath) {
//
//    //todo:        storageService.transferFile( "./checksum/");
//
//
//    return false;
//  }
//
//
//  public String testChecksum(String filepath) throws IOException, NoSuchAlgorithmException {
//    String filePath = "S:\\___Studium\\5.Semester\\Storage\\src\\main\\resources\\cars.csv";
//    String checksum = ChecksumService.createChecksum(filePath);
//    String filePath2 = "S:\\___Studium\\5.Semester\\Storage\\src\\main\\resources\\cars2.csv";
//    String checksum2 = ChecksumService.createChecksum(filePath2);
//    System.out.println(checksum);
//    System.out.println(checksum2);
//    return checksum;
//  }
//
//  public boolean updateDatabaseByCsv() throws FileNotFoundException {
//
//    List<List<String>> carlist = csvImportService.importCsv("cars.csv");
//
//    return false;
//  }
//}

