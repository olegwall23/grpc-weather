package com.example.weatherservice;

import com.example.grpc.weather.WeatherOuterClass;
import com.example.grpc.weather.WeatherServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class WeatherServiceApplicationTests {

    @Test
    void contextLoads() throws InterruptedException {

        ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        WeatherServiceGrpc.WeatherServiceStub stub = WeatherServiceGrpc.newStub(channel);

        stub.withDeadline(Deadline.after(10, TimeUnit.SECONDS)).getCurrent(WeatherOuterClass.Coordinates.newBuilder().build(),
                new StreamObserver<com.example.grpc.weather.WeatherOuterClass.Weather>(){

                    @Override
                    public void onNext(WeatherOuterClass.Weather weather) {
                        System.out.println("onNext: " + weather.toString());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("onError" + throwable.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }
                });


        Thread.sleep(15000);
    }

}
