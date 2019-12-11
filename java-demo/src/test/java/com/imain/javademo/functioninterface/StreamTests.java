package com.imain.javademo.functioninterface;

import com.imain.javademo.model.Student;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * author Songrui.Liu
 * date 2019/12/1117:22
 */
public class StreamTests {

    private Logger logger = LoggerFactory.getLogger(StreamTests.class);

    public List<Student> getStudent() {
        List<Student> students = new ArrayList<>(3);
        students.add(new Student("路飞", 22, 175));
        students.add(new Student("红发", 40, 180));
        students.add(new Student("白胡子", 50, 185));
        return students;
    }

    @Test
    public void testMinAndMax() {
        List<Student> list = getStudent();
        Optional<Student> max = list.stream().max(Comparator.comparing(s -> s.getAge()));
        Optional<Student> min = list.stream().min(Comparator.comparing(s -> s.getAge()));

        logger.info("max:={};min:={}", max, min);
    }

    @Test
    public void testFilterAndCount() {
        List<Student> list = getStudent();
        long count = list.stream().filter(s -> s.getAge() < 45).count();
        logger.info("年龄小于45岁的人数是：{}", count);
    }

    /**
     * 我们看得reduce接收了一个初始值为0的累加器，依次取出值与累加器相加，最后累加器的值就是最终的结果。
     */
    @Test
    public void testReduce() {
        Integer reduce = Stream.of(1, 2, 3, 4).reduce(0, (acc, x) -> acc + x);
        logger.info("reduce:{}", reduce);
    }

}
