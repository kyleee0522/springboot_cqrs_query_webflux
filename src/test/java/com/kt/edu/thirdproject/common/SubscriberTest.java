package com.kt.edu.thirdproject.common;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;


public class SubscriberTest {

    @Test
    public void SubscriberTest1() {
        Flux.range(1, 5) // 1부터 5까지 세 개의 이벤트를 발생시키는 Publisher
                .subscribe(new Subscriber<>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        subscription.request(3);
                        //subscription.request(Long.MAX_VALUE);
                        System.out.println("[Subscriber] onSubscribe");
                    }

                    @Override
                    public void onNext(Integer item) {
                        System.out.println("[Subscriber] onNext : " + item);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("[Subscriber] onError : " + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("[Subscriber] onComplete");
                    }
                });
    }
}
