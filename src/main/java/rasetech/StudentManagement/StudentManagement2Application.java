package rasetech.StudentManagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("rasetech.StudentManagement.Repository")
public class StudentManagement2Application {

  public static void main(String[] args) {
    SpringApplication.run(StudentManagement2Application.class, args);
  }
}

/**
 * Controllerのオブジェクトと、Serviceのオブジェクトを作って、 このメインオブジェクトで使ってた機能を移したので、すごく見やすくなった！
 */