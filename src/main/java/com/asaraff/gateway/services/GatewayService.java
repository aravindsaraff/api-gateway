package com.asaraff.gateway.services;

import io.reactivex.Observable;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface GatewayService {

    Observable<String> getHello(String name);

    Mono<String> getHelloReactive(String name);
}
