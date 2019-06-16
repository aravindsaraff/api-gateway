package com.asaraff.gateway.services;


import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class GatewayServiceImpl implements GatewayService {
    public Observable<String> getHello(String name) {

        return Observable.just("Hello ".concat(name));
    }

    public Mono<String> getHelloReactive(String name) {
        return Mono.just("hello ".concat(name));
    }
}
