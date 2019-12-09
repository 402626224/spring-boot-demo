package com.imain.javademo.optionalservice;

import com.imain.javademo.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * author Songrui.Liu
 * date 2019/12/910:03
 */
public class OptionalServiceTests {

    private Logger logger = LoggerFactory.getLogger(OptionalServiceTests.class);

    private User user = new User().setId(1).setName("tom");

    @Test
    public void testIfPresent() {
        Optional<User> optional = Optional.ofNullable(user);
        optional.ifPresent(System.out::print);
    }

    @Test
    public void testOrElse() {
        Optional<User> optional = Optional.ofNullable(null);
        User user = optional.orElse(new User().setId(0).setName("default"));
        logger.info("user:={}", user);
    }

    @Test
    public void testMap() {
        Optional<User> optional = Optional.ofNullable(user);
        String name = optional.map(u -> u.getName()).orElse("");
        logger.info("username:={}", name);
    }


}
