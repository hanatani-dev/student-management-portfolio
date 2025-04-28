package rasetech.StudentManagement.Controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rasetech.StudentManagement.Data.Student;
import rasetech.StudentManagement.Data.StudentsCourses;
import rasetech.StudentManagement.Service.StudentService;

/**
 * ①　まず、Controllerオブジェクトには、リクエスト飛んでくる。ここで、レスポンス処理とかする。
 */
@RestController//メインオブジェクトで作業してたControllerを、このオブジェクトに分ける。
public class StudentController {

  private StudentService service;

  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/studentsList")
  public List<Student> getStudentList() {
    //リクエストの加工処理、入力チェックとか入ってくる。
    return service.searchStudentList();
    //これは Service層（ビジネスロジック層） のメソッドを呼んでる。コントローラーから呼び出すなら基本はService層を通すのがベスト！　直接Repositoryを呼ぶと、テスト・変更・拡張がしにくくなることがあるよ。
  }

  @GetMapping("/studentsCourseList")
  public List<StudentsCourses> getStudentsCoursesList() {
    return service.searchStudentsCoursesList();
  }
}
