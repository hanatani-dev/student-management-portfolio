package rasetech.StudentManagement.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    List<StudentsCourses> studentsCourses = service.searchStudentsCoursesList();

    model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourses));
    return "studentList";
  }

  @GetMapping("/studentsCourseList")
  public List<StudentsCourses> getStudentsCoursesList() {
    return service.searchStudentsCoursesList();
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }

    // 受け取ったStudentオブジェクトをそのまま使って保存
    Student student = studentDetail.getStudent();
    service.saveStudent(student);

    return "redirect:/studentList";
  }
}