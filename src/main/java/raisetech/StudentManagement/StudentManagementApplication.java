package raisetech.StudentManagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@SpringBootApplication
// ✅ 新（小文字に変更）
@MapperScan("raisetech.StudentManagement.repository")
public class StudentManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }
}

/**
 * Controllerのオブジェクトと、Serviceのオブジェクトを作って、 このメインオブジェクトで使ってた機能を移したので、すごく見やすくなった！
 */