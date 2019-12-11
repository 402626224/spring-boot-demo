package com.imain.javademo.functioninterface;

import com.imain.javademo.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.function.*;

/**
 * author Songrui.Liu
 * date 2019/12/915:16
 */
public class FunctionInterfaceTests {

    private Logger logger = LoggerFactory.getLogger(FunctionInterfaceTests.class);

    /**
     * Predicate （判断真假）
     * example:  x 是否大于185
     */
    @Test
    public void testPredicate() {
        Predicate<Integer> predicate = x -> x > 185;
        logger.info("preducate:{}", predicate.test(175));
    }

    /**
     * Consumer (消费消息)
     * example: 输出一个值
     */
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

    /**
     * Function （将T映射为R，转换功能）
     * example: 获得user对象的name
     */
    @Test
    public void testFunction() {
        // User::getName例子中这种编写lambda表达式的方式称为方法引用
        Function<User, String> function = User::getName;
        String name = function.apply(new User().setName("UserName"));
        logger.info("user.name=[{}]", name);

        Function<User, User> contro = (user) -> {
            return user.setId(1).setName("contro");
        };
        User u1 = new User();
        u1 = contro.apply(u1);
        logger.info("user:=[{}]", u1.toString());
    }

    /**
     * Supplier 生产消息
     * example: 工厂方法
     */
    @Test
    public void testSupplier() {
        Supplier<Integer> supplier = () -> Integer.valueOf(BigDecimal.TEN.toString());
        logger.info("supplier.get:[{}]", supplier.get());
    }

    /**
     * UnaryOperator （一元操作）
     * example:     逻辑非
     */
    @Test
    public void testUnaryOperator() {
        UnaryOperator<Boolean> unaryOperator = uglily -> !uglily;
        Boolean result = unaryOperator.apply(true);
        logger.info("result:=[{}]", result);
    }

    /**
     * BinaryOperator (二元操作)
     * example： 求两个数的乘积
     */
    @Test
    public void testBinaryOperator() {
        BinaryOperator<Integer> binaryOperator = (x, y) -> x * y;
        Integer result = binaryOperator.apply(1, 2);
        logger.info("result:=[{}]", result);
    }
}
