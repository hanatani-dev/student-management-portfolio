package rasetech.StudentManagement.Controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import rasetech.Domain.StudentDetail;
import rasetech.StudentManagement.Controller.Converter.StudentConverter;
import rasetech.StudentManagement.Data.Student;
import rasetech.StudentManagement.Data.StudentsCourses;
import rasetech.StudentManagement.Service.StudentService;

/**
 * ①　まず、Controllerオブジェクトには、リクエスト飛んでくる。ここで、レスポンス処理とかする。
 */
@Controller//メインオブジェクトで作業してたControllerを、このオブジェクトに分ける。
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentsCourseList();

    // ✅ isDeleted==false な受講生だけフィルター
    List<Student> activeStudents = students.stream()
        .filter(student -> !student.isDeleted())  // isDeleted が false（未キャンセル）だけ表示
        .toList();

    model.addAttribute("studentList",
        converter.convertStudentDetails(activeStudents, studentsCourses));
    return "studentList";
  }

  /**
   * ２．１．でServiseオブジェクトから返されたら、StudentDetailからの情報を、画面に出力する。
   *
   * @return 更新画面にいくっていうとこ、Java授業内で一番難しい！
   */
  @GetMapping("/student/{id}")
  public String getStudent(@PathVariable String id, Model model) {
    StudentDetail studentDetail = service.searchStudent(id);

    // ★ログ出力
    System.out.println("Courses size: " + (studentDetail.getStudentsCourses() != null
        ? studentDetail.getStudentsCourses().size() : "null"));

    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }

  /**
   * ↑のUpdateStudentで、UpdateStudent.htmlに紐づけてる。検索してきた結果をUpdateStudent.htmlに当てはめてる。
   * 動きとしては、RegisterStudent.htmlと同じ。更新ボタン押したとき動かす先がUpdateStudent.htmlに行くだけ。。
   */


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

  /**
   *
   */
  @PostMapping("/updateStudent")
  public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "updateStudent"; // バリデーションエラーがある場合、フォームに戻る
    }
    // 学生リストに更新
    service.updateStudent(studentDetail);
    return "redirect:/studentList";
  }
}