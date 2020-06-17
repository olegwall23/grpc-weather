package com.example.weatherservice;

import com.example.grpc.weather.*;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@GrpcService
public class WeatherServiceImpl extends WeatherServiceGrpc.WeatherServiceImplBase {

    private WindServiceGrpc.WindServiceFutureStub windServiceFutureStub =
            WindServiceGrpc.newFutureStub(NettyChannelBuilder.forAddress("127.0.0.1", 9091).usePlaintext().build());
    private TempServiceGrpc.TempServiceFutureStub tempServiceFutureStub =
            TempServiceGrpc.newFutureStub(ManagedChannelBuilder.forAddress("127.0.0.1", 9092).usePlaintext().build());
    private HumidityServiceGrpc.HumidityServiceFutureStub humidityServiceFutureStub =
            HumidityServiceGrpc.newFutureStub(ManagedChannelBuilder.forAddress("127.0.0.1", 9093).usePlaintext().build());;

    @Override
    public void getCurrent(WeatherOuterClass.Coordinates request, StreamObserver<WeatherOuterClass.Weather> responseObserver) {

        System.out.println("getCurrent: " + windServiceFutureStub);

        ListenableFuture<List<Object>> futures = Futures.allAsList(
                windServiceFutureStub.getCurrent(null),
                tempServiceFutureStub.getCurrent(null),
                humidityServiceFutureStub.getCurrent(null)
        );

        futures.addListener(() -> {}, command -> {
            System.out.println("futures Execute: " + futures.isDone());
            try {
                WeatherOuterClass.Weather.Builder weatherBuilder = WeatherOuterClass.Weather.newBuilder();
                futures.get().forEach(obj -> {
                    if (obj instanceof WeatherOuterClass.Wind) {
                        weatherBuilder.setWind(((WeatherOuterClass.Wind)obj));
                    } else if (obj instanceof WeatherOuterClass.Temp) {
                        weatherBuilder.setTemp(((WeatherOuterClass.Temp)obj));
                    } else if (obj instanceof WeatherOuterClass.Humidity) {
                        weatherBuilder.setHumidity(((WeatherOuterClass.Humidity)obj));
                    }
                });
                System.out.println("weather: " + weatherBuilder.build().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

}
