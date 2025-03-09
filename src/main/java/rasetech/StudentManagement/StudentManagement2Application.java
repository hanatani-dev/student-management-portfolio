package rasetech.StudentManagement;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagement2Application {

	public static void main(String[] args) {
		SpringApplication.run(StudentManagement2Application.class, args);
	}

	@GetMapping("/hello")
	public String hello() {
		return "Hello, World!";
	}
	//GET POST
	//GETは取得する、リクエストの結果を受け取る
	//POSTは情報を与える、渡す
}
