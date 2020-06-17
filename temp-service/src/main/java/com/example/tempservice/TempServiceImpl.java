package com.example.tempservice;

import com.example.grpc.weather.TempServiceGrpc;
import com.example.grpc.weather.WeatherOuterClass;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class TempServiceImpl extends TempServiceGrpc.TempServiceImplBase {

    @Override
    public void getCurrent(WeatherOuterClass.Coordinates request, StreamObserver<WeatherOuterClass.Temp> responseObserver) {
        System.out.println("getCurrent: " + request.toString());
        responseObserver.onNext(WeatherOuterClass.Temp.newBuilder().setResult("temp result").build());
        responseObserver.onCompleted();
    }

}
