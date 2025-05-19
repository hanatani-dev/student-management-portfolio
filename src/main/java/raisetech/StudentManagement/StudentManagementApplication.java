package raisetech.StudentManagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("raisetech.StudentManagement.Repository")
public class StudentManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }
}

/**
 * Controllerのオブジェクトと、Serviceのオブジェクトを作って、 このメインオブジェクトで使ってた機能を移したので、すごく見やすくなった！
 */