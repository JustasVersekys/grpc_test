package com.telesoftas.client.controller;

import com.telesoftas.grpc.service.Data;
import com.telesoftas.grpc.service.Response;
import com.telesoftas.grpc.service.SqlServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
public class SimpleController {

    private int grpcPort;
    private String grpcHostname;

    private SqlServiceGrpc.SqlServiceBlockingStub stub;
    private long startTime;

    public SimpleController(@Value("${grpc.port}") int grpcPort, @Value("${grpc.hostname}") String grpcHostname) {
        this.grpcPort = grpcPort;
        this.grpcHostname = grpcHostname;
    }

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(grpcHostname, grpcPort).usePlaintext().build();
        stub = SqlServiceGrpc.newBlockingStub(managedChannel);
    }

    @PostMapping(value = "/doreq")
    public void save(String s) {
        log.info("Request started");
        startTime = System.currentTimeMillis();
        Data data = Data.newBuilder().setData(s).build();
        Response response = stub.saveData(data);
        log.info("Request finished {}, time {}", response.getMessage(), System.currentTimeMillis() - startTime);
    }
}
