package com.github.baymin.mongodb;

import com.github.baymin.mongodb.dao.StudentDaoTypeTwo;
import com.github.baymin.mongodb.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class StudentDaoTypeTwoTest {

    @Autowired
    private StudentDaoTypeTwo studentDaoTypeTwo;

    @Test
    void addOneStudent() {
        // 插入10行
        for (Integer count = 0; count < 10; count++) {
            Student student = new Student()
                    .setStudentId("study_" + count) //如果自己不去设置id则系统会分配给一个id
                    .setStudentName("Echo" + count)
                    .setStudentAge(count)
                    .setStudentScore(98.5 - count)
                    .setStudentBirthday(new Date());
            studentDaoTypeTwo.addOneStudent(student);
        }
    }

    @Test
    void deleteOneStudentByStudentId() {
        // 删除id为study_0的学生
        studentDaoTypeTwo.deleteOneStudentByStudentId("study_0");
    }

    @Test
    void updateOneStudent() {
        // 修改id为study_1的Student年龄为21
        Student student = studentDaoTypeTwo.getOneStudentByStudentId("study_1");
        student.setStudentAge(21);
        studentDaoTypeTwo.updateOneStudent(student);

    }

    @Test
    void getOneStudentByStudentId() {
        System.out.println(studentDaoTypeTwo.getOneStudentByStudentId("study_1"));
    }

    @Test
    void getAllStudent() {
        List<Student> studentList = studentDaoTypeTwo.getAllStudent();
        studentList.forEach(System.out::println);
    }

}
