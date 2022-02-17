//package com.kompsys.domain.dto;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import java.io.Serializable;
//
//public class CalculateRequest implements Serializable {
//    @JsonProperty("price")
//    double price;
//    @JsonProperty("salesTax")
//    double salesTax;
//
//    public CalculateRequest() {
//    }
//
//    public CalculateRequest(double price, double salesTax) {
//        this.price = price;
//        this.salesTax = salesTax;
//    }
//
//    @Override
//    public String toString() {
//        return "CalculateRequest{" +
//                "price=" + price +
//                ", salesTax=" + salesTax +
//                '}';
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public double getSalesTax() {
//        return salesTax;
//    }
//
//    public void setSalesTax(double salesTax) {
//        this.salesTax = salesTax;
//    }
//}
