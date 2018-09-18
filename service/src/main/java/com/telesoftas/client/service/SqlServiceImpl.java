package com.telesoftas.client.service;

import com.telesoftas.client.model.TstTable;
import com.telesoftas.client.repo.TstTableRepo;
import com.telesoftas.grpc.service.Data;
import com.telesoftas.grpc.service.Response;
import com.telesoftas.grpc.service.SqlServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@GRpcService
public class SqlServiceImpl extends SqlServiceGrpc.SqlServiceImplBase {
    private TstTableRepo tstTableRepo;
    private static final String SAVED = "SAVED";
    private static final String FAILED = "FAILED";

    @Autowired
    public SqlServiceImpl(TstTableRepo tstTableRepo) {
        this.tstTableRepo = tstTableRepo;
    }

    @Override
    public void saveData(Data request, StreamObserver<Response> responseObserver) {
        log.info("Request received {}", request);
        Response response;
        TstTable table = tstTableRepo.save(TstTable.builder().data(request.getData()).build());
        if (table.getId() != null) {
            response = Response.newBuilder().setSuccess(Boolean.TRUE).setMessage(SAVED).build();
        } else {
            response = Response.newBuilder().setSuccess(Boolean.FALSE).setMessage(FAILED).build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        log.info("Request saved {}", request);
    }
}
