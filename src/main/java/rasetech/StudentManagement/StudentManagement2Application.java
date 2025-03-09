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

	private String name = "Enami Kouji";
	private String age = "37";

	public static void main(String[] args) {
		SpringApplication.run(StudentManagement2Application.class, args);
	}

	@GetMapping("/studentInfo")
	public String getStudentInfo() {
		return name + " " + age + "sai";
	}

	//GET POST
	//GETは取得する、リクエストの結果を受け取る
	//POSTは情報を与える、渡す
	@PostMapping("/studentInfo")
	public void setStudentInfo(String name, String age){//setName=名前登録する
		this.name = name;
		this.age = age;
	}
	@PostMapping("/studentName")
	public void updateStudentName(String name){
		this.name = name;
	}
}
