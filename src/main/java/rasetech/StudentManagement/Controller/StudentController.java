package rasetech.StudentManagement.Controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }


  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent"; // バリデーションエラーがある場合、フォームに戻る
    }
    // 学生リストにリダイレクト
    service.registerStudent(studentDetail);
    return "redirect:/studentList";
  }

  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    // 学生リストに更新
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("The update process was successful.");
  }
}