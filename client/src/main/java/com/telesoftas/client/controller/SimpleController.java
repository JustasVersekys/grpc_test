package com.telesoftas.client.controller;

import com.telesoftas.grpc.service.Data;
import com.telesoftas.grpc.service.Response;
import com.telesoftas.grpc.service.SqlServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class SimpleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleController.class);

    private SqlServiceGrpc.SqlServiceBlockingStub stub;
    private long startTime;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();
        stub = SqlServiceGrpc.newBlockingStub(managedChannel);
    }

    @PostMapping(value = "/doreq")
    public void save(String s) {
        LOGGER.info("Request started");
        startTime = System.currentTimeMillis();
        Data data = Data.newBuilder().setData(s).build();
        Response response = stub.saveData(data);
        LOGGER.info("Request finished {}, time {}", response.getMessage(), System.currentTimeMillis() - startTime);
    }
}
