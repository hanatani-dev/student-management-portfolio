package rasetech.StudentManagement;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagement2Application {

	@Autowired
	private StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StudentManagement2Application.class, args);
	}

	@GetMapping("/student")
	public List<Student> getStudentList() {//Listに赤線エラー出たが、青文字のインポート押したらエラー消えた。
		return repository.search();//@PostMapping（登録）、@PatchMapping（更新）、@DeleteMapping（削除）は第10回では使わないため、消す。
	}
}
