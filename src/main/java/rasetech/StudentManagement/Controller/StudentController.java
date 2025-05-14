package rasetech.StudentManagement.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rasetech.Domain.StudentDetail;
import rasetech.StudentManagement.Controller.Converter.StudentConverter;
import rasetech.StudentManagement.Data.Student;
import rasetech.StudentManagement.Data.StudentsCourses;
import rasetech.StudentManagement.Service.StudentService;

/**
 * ①　まず、Controllerオブジェクトには、リクエスト飛んでくる。ここで、レスポンス処理とかする。
 */
@RestController//メインオブジェクトで作業してたControllerを、このオブジェクトに分ける。
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public List getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentsCourseList();

    // ✅ isDeleted==false な受講生だけフィルター
    List<Student> activeStudents = students.stream()
        .filter(student -> !student.isDeleted())  // isDeleted が false（未キャンセル）だけ表示
        .toList();

    return converter.convertStudentDetails(activeStudents, studentsCourses);
  }

  @GetMapping("/student/{id}")//単一検索できる！PostmanでID検索したみたいに！
  public StudentDetail getStudent(@PathVariable String id) {
    return service.searchStudent(id);
  }


  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    // 学生リストにリダイレクト
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(
        responseStudentDetail);//Postmanでの受講生登録時、自動登録されたIDも登録画面に出るようにした。DBに見に行くのは手間なので。
  }

  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    // 学生リストに更新
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}