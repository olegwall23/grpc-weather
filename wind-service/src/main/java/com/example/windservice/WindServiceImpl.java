package com.example.windservice;

import com.example.grpc.weather.WeatherOuterClass;
import com.example.grpc.weather.WindServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class WindServiceImpl extends WindServiceGrpc.WindServiceImplBase {

    public void getCurrent(WeatherOuterClass.Coordinates request, StreamObserver<WeatherOuterClass.Wind> responseObserver) {
        System.out.println("getCurrent");
        responseObserver.onNext(WeatherOuterClass.Wind.newBuilder().setResult("wind result").build());
        responseObserver.onCompleted();
    }

}
