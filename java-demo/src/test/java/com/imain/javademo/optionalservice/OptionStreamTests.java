package com.imain.javademo.optionalservice;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * author Songrui.Liu
 * date 2019/12/1210:53
 */
public class OptionStreamTests {


    @Test
    public void test() throws  Exception{

        List list =  Arrays.asList("a","b","c");

        Optional<String> str = list.stream().filter(s -> Objects.equals(s, "a")).flatMap(a->a).map(s->s).findFirst();
        str.orElseThrow(() -> new RuntimeException("not exists"));


    }


}
