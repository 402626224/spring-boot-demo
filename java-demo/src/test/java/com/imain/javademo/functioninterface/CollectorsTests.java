package com.imain.javademo.functioninterface;

import com.imain.javademo.model.OutstandingClass;
import com.imain.javademo.model.Student;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * author Songrui.Liu
 * date 2019/12/1120:39
 */
public class CollectorsTests {

    private final Logger logger = LoggerFactory.getLogger(CollectorsTests.class);

    public List<Student> list() {
        List<Student> students1 = new ArrayList<>(3);
        students1.add(new Student("路飞", 30, 175));
        students1.add(new Student("红发", 40, 180));
        students1.add(new Student("白胡子", 50, 185));
        return students1;
    }


    public OutstandingClass getClass1() {
        return new OutstandingClass().setName("one class").setStudents(list());
    }

    public OutstandingClass getClass2() {
        List<Student> students = new ArrayList<>(list());
        students.remove(1);
        return new OutstandingClass().setName("two class").setStudents(students);
    }

    @Test
    public void testBiggestGroup() {
        Stream<OutstandingClass> classStream = Stream.of(getClass1(), getClass2());

        OutstandingClass biggestClass = classStream.collect(
                Collectors.maxBy(Comparator.comparing(outClass -> outClass.getStudents().size())))
                .orElseGet(OutstandingClass::new);
        logger.info("biggestClass:{}", biggestClass);

        Optional<OutstandingClass> biggest = Arrays.asList(getClass1(), getClass2()).stream().max(Comparator.comparing(outClass -> outClass.getStudents().size()));
        logger.info("biggest:{}", biggest);
    }

    /**
     * 转换成值
     * example ：maxBy或者minBy就是求最大值与最小值
     */
    @Test
    public void testAvg() {
        List<Student> list = list();
        Optional<Student> maxAgeStudent = list.stream().collect(Collectors.maxBy(Comparator.comparing(Student::getAge)));
        logger.info("maxAgeStudent:{}", maxAgeStudent);

        double avg = list.stream().collect(Collectors.averagingInt(Student::getAge));
        logger.info("avg:{}", avg);

        int sum = list.stream().collect(Collectors.summingInt(Student::getAge));
        logger.info("sum:{}", sum);
    }

    /**
     * 转换成块
     * example： 示例学生分为会 "大于40" 与 "不大于40" 的两个集合
     */
    @Test
    public void testPart() {
        Map<Boolean, List<Student>> listMap = list().stream().collect(
                Collectors.partitioningBy(s -> s.getAge() > 40)
        );
        logger.info("age 大于 40 的：{}", listMap.get(Boolean.TRUE));
        logger.info("age 不大于 40 的：{}", listMap.get(Boolean.FALSE));
    }

    /**
     * 数据分组 (Collectors.groupingBy与SQL 中的 group by 操作是一样的。)
     * example： 根据年龄做分组
     */
    @Test
    public void testGroupingBy() {
        List<Student> list = list();
        list.add(new Student("name", 50, 196));
        Map<Integer, List<Student>> listMap = list.stream().collect(
                Collectors.groupingBy(s -> s.getAge())
        );

        for (Map.Entry<Integer, List<Student>> entry : listMap.entrySet()) {
            logger.info("age : {} ,student:{}", entry.getKey(), entry.getValue());
        }

    }

    /**
     * 字符串拼接 (joining接收三个参数，第一个是分界符，第二个是前缀符，第三个是结束符。也可以不传入参数Collectors.joining()，这样就是直接拼接。)
     * example: 所有学生的名字拼接起来
     */
    @Test
    public void testJoin() {
        String names = list().stream().map(Student::getName).collect(Collectors.joining(",", "[", "]"));
        logger.info("names :{}", names);
    }

    @Test
    public void testEx(){
        OutstandingClass outstandingClass = getClass1();
        Optional<OutstandingClass> optional = Optional.ofNullable(outstandingClass);


        Object one = optional.map(c -> c.getStudents()).map(l -> l.get(0)).map(s -> s.getName()).orElseThrow(() -> new RuntimeException("name is null"));
        logger.info("one : {}", one);

        outstandingClass.getStudents().get(0).setName(null);
        optional.map(Optional::of).orElseThrow(()->new RuntimeException("outClass is null "))
                .map(c-> c.getStudents().get(0)).map(Optional::of).orElseThrow(()->new RuntimeException("student is null"))
                .map(s-> s.getName()).orElseThrow(()-> new RuntimeException("student name is null"));



    }

}
