package com.imain.javademo.optionalservice;

import com.imain.javademo.model.Student;
import com.imain.javademo.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    /**
     * 这个示例中，两个 Optional  对象都包含非空值，两个方法都会返回对应的非空值。
     * 不过，orElse() 方法仍然创建了 User 对象。与之相反，orElseGet() 方法不创建 User 对象。
     */
    @Test
    public void givenPresentValue_whenCompare_thenOk() {
        User user = new User(1, "1234");
        logger.info("Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());
        logger.info("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(() -> createNewUser());
    }

    private User createNewUser() {
        logger.info("exec createNewUser");
        return new User();
    }


    @Test
    public void testMap() {
        Optional<User> optional = Optional.ofNullable(user);
        String name = optional.map(u -> u.getName()).orElse("null");
        logger.info("username:={}", name);
    }

    /**
     * Map 与 flatMap
     * 调用Stream.of的静态方法将两个list转换为Stream，再通过flatMap将两个流合并为一个。
     */
    @Test
    public void testMapAndFlatMap() {
        // map
        List<User> users = new ArrayList<>();
        users.add(new User(1, "路飞"));
        users.add(new User(2, "红发"));
        users.add(new User(3, "白胡子"));

        List<String> names = users.stream().map(user -> user.getName()).collect(Collectors.toList());
        logger.info("names:={}", names);


        // flatMap 调用Stream.of的静态方法将两个list转换为Stream，再通过flatMap将两个流合并为一个。
        List<Student> students = new ArrayList<>(3);
        students.add(new Student("路飞", 22, 175));
        students.add(new Student("红发", 40, 180));
        students.add(new Student("白胡子", 50, 185));

        List<Student> studentList = Stream.of(students,
                Arrays.asList(new Student("艾斯", 25, 183),
                        new Student("雷利", 48, 176)))
                .flatMap(students1 -> students1.stream()).collect(Collectors.toList());
        logger.info("studentList:={}", studentList);
    }


}
