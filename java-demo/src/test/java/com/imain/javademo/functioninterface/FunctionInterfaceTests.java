package com.imain.javademo.functioninterface;

import com.imain.javademo.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * author Songrui.Liu
 * date 2019/12/915:16
 */
public class FunctionInterfaceTests {

    private Logger logger = LoggerFactory.getLogger(FunctionInterfaceTests.class);

    @Test
    public void testPredicate() {
        Predicate<Integer> predicate = x -> x > 185;
        logger.info("preducate:{}", predicate.test(175));
    }


    @Test
    public void testConsumer() {
        // 静态函数
        Consumer<String> consoleConsumer = System.out::println;
        consoleConsumer.accept("console print");
        // 已有函数
        Consumer<String> logsConsumer = x -> logPrint(x);
        logsConsumer.accept("logs print");
        // 单次声明
        Consumer<String> lcon = (x) -> {
            logger.info("logs:[{}]", x);
        };
        lcon.accept("hello");
    }

    private void logPrint(String logs) {
        logger.info("logs:[{}]", logs);
    }

    @Test
    public void testFunction() {
        Function<User, String> function = User::getName;
        String name = function.apply(new User().setName("UserName"));
        logger.info("user.name=[{}]", name);
    }

    @Test
    public void testSupplier() {
        Supplier<Integer> supplier = () -> Integer.valueOf(BigDecimal.TEN.toString());
        logger.info("supplier.get:[{}]", supplier.get());
    }
}
