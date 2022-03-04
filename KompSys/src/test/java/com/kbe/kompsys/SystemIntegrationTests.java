package com.kbe.kompsys;

import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SystemIntegrationTests {
    @ClassRule
    public static GenericContainer gatewayServer
            = new GenericContainer("gateway:0.0.1-SNAPSHOT")
            .withExposedPorts(8082);
//            .withCommand("/bin/sh", "-c", "java","-jar","/app.jar");

    @ClassRule
    public static GenericContainer calculatorServer
            = new GenericContainer("calculator:0.0.1-SNAPSHOT")
            .withExposedPorts(8080);
//            .withCommand("/bin/sh", "-c", "java","-jar","/app.jar");

    @Test
    public void givenSimpleWebServerContainer_whenGetRequest_thenReturnsResponse()
            throws Exception {
        String address = "http://"
                + gatewayServer.getContainerIpAddress()
                + ":" + gatewayServer.getMappedPort(8082)
                + "/calculate?pricePreTax=99000&salesTax=0.19";
        String response = simpleGetRequest(address);
        System.out.println(response);

//        assertEquals(response, "Hello World!");
    }

    private String simpleGetRequest(String address) throws Exception {
        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        return content.toString();
    }
}
