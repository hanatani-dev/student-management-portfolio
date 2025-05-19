package raisetech.StudentManagement.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import raisetech.StudentManagement.StudentManagementApplication;

public class ServletInitializer extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    // 修正後バージョン（新しいクラス名に合わせて）
    return application.sources(StudentManagementApplication.class);
  }

}
