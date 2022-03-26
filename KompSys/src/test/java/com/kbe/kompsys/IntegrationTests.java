package com.kbe.kompsys;

import com.github.dergil.kompsys.dto.car.CarView;
import com.github.dergil.kompsys.dto.car.ReadCarRequest;
import com.kbe.kompsys.util.CarMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.junit.RabbitAvailable;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness.InvocationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;


// start RabbitMQ beforehand
@SpringBootTest(args={"--remoteHost=sftp", "--sftp_username=foo", "--sftp_password=Mypassword123",
        "--spring.sql.init.platform=", "--spring.datasource.driver-class-name=", "--spring.jpa.database-platform="})
@SpringJUnitConfig
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RabbitAvailable
public class IntegrationTests {
//    https://github.com/spring-projects/spring-amqp/blob/a3aedc67072bdfc01850dec7f87195cd8bf86381/spring-rabbit-test/src/test/java/org/springframework/amqp/rabbit/test/examples/ExampleRabbitListenerSpyAndCaptureTest.java

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    @Autowired
    private RabbitListenerTestHarness harness;

    @Autowired
    private CarMapper carMapper;

    @Test
    public void successfulGet() {
        long id = 3;
        ReadCarRequest readCarRequest = new ReadCarRequest(id);
        CarView response = (CarView) this.rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                "car",
                readCarRequest);
        Assertions.assertEquals(id, carMapper.toCar(response).getId());
    }

    @Test
    void unsuccessfulGet() throws InterruptedException {
        ReadCarRequest readCarRequest = new ReadCarRequest(130);
        this.rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                "car",
                readCarRequest);

        InvocationData invocationData = this.harness.getNextInvocationDataFor("car_listener", 10, TimeUnit.SECONDS);
        assertThat(invocationData).isNotNull();
        assertThat(invocationData.getThrowable().toString()).contains("NotFoundException");
    }
}
