package raisetech.student.management;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "受講生管理システム"))
@SpringBootApplication
// ✅ 新（小文字に変更）
@MapperScan("raisetech.student.management.repository")
public class StudentManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }
}

/**
 * Controllerのオブジェクトと、Serviceのオブジェクトを作って、 このメインオブジェクトで使ってた機能を移したので、すごく見やすくなった！
 */