package com.example.humidityservice;

import com.example.grpc.weather.HumidityServiceGrpc;
import com.example.grpc.weather.WeatherOuterClass;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HumidityServiceImpl extends HumidityServiceGrpc.HumidityServiceImplBase {

    @Override
    public void getCurrent(WeatherOuterClass.Coordinates request, StreamObserver<WeatherOuterClass.Humidity> responseObserver) {
        System.out.println(request.toString());
        responseObserver.onNext(WeatherOuterClass.Humidity.newBuilder().setResult("humidity result").build());
        responseObserver.onCompleted();
    }

}
