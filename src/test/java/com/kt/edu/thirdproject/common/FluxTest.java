package com.kt.edu.thirdproject.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FluxTest {

    @Test
    public void flux_just_consumer() {
        List<String> names = new ArrayList<String>();
        Flux<String> flux = Flux.just("자바","스칼라","파이썬").log(); //데이터를 때려 넣을때는 just를 사용
        flux.subscribe(s -> {
            System.out.println("시퀀스 수신 : " + s);
            names.add(s);
        });
        assertEquals(names, Arrays.asList("자바","스칼라","파이썬"));
    }

    @Test
    public void ArrayTest() {
        List<String> names = new ArrayList<>();
        names.add("자바");
        names.add("스칼라");
        names.add("파이썬");

        for (String s : names){
            System.out.println("시퀀스 수신 : " + s);
        };

        Iterator iter = names.iterator();

        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

    @Test
    public void StreamTest() {
        List<String> names = new ArrayList<>();
        names.add("자바");
        names.add("스칼라");
        names.add("파이썬");

        List<String> javaFilteredList = names.stream()
                .filter(s -> s.equals("자바"))
                .collect(Collectors.toList());

        System.out.println(javaFilteredList);
    }


    @Data
    @AllArgsConstructor
    public class Member {
        private String name;
        private Integer age;
        private Boolean married;
    }

    @Test
    public void StreamTest2() {
        List<Member> members = new ArrayList<>();
        members.add(new Member("김", 1, false));
        members.add(new Member("이", 3, true));
        members.add(new Member("박", 5, true));
        members.add(new Member("최", 7, false));


        List<Member> membersFilteredList = members.stream()
                .filter(s-> s.getAge() > 4)
                .collect(Collectors.toList());

        System.out.println("4살 이상 : " + membersFilteredList);

        List<Integer> ageList = members.stream()
                .map(s -> s.getAge())
                .collect(Collectors.toList());
        System.out.println("ageList : " + ageList);

        List<String> nameList = members.stream()
                .map(Member::getName)
                .collect(Collectors.toList());
        System.out.println("nameList : " + nameList);
    }

    @Test
    public void flux_fromArray() {
        List<String> names = new ArrayList<>();
        names.add("11");
        names.add("22");
        names.add("33");


        //

        Flux<String> flux = Flux.fromIterable(names);
        flux.subscribe(names::add);// 람다식을 더 줄인 것

        System.out.println(names);

        //assertEquals(names, Arrays.asList("자바", "스칼라", "파이썬")); // 비교
    }

    @Test
    public void defaultEmpty(){
        Flux.empty().defaultIfEmpty (10).subscribe(System.out::println);
    }

    @Test
    public void zip(){
        var flux1 = Flux.range(1, 5);
        var flux2 = Flux.range(1, 10).map(it -> it * 10);
        var flux3 = Flux.range(1, 5).map(it -> it * 100);

        Flux.zip(flux1, flux2, flux3)
                .subscribe(
                        item -> System.out.println("[Subscriber] onNext : " + item),
                        e -> System.out.println("[Subscriber] onError : " + e.getMessage()),
                        () -> System.out.println("[Subscriber] onComplete")
                );
    }
}