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
import rasetech.StudentManagement.Service.StudentService;

/**
 * 以下、運用する人に見せるのか、チームメンバーに見せるのか、で、 受講生の検索や登録、更新などを行うREST　APIとして受け付けるControllerです。
 */
@RestController//メインオブジェクトで作業してたControllerを、このオブジェクトに分ける。
public class StudentController {

  /**
   * 　受講生サービス
   */
  private StudentService service;


  @Autowired
  public StudentController(StudentService service) {
    this.service = service;

  }

  /**
   * 受講生詳細の一覧検索です。 全件検索を行うので、条件指定は行わないものになります。
   *
   * @return 受講生詳細一覧（全件）
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生検索です。 IDに紐づく任意の情報を取得します。
   *
   * @param id 　受講生ID
   * @return　受講生
   */
  @GetMapping("/student/{id}")//単一検索できる！PostmanでID検索したみたいに！
  public StudentDetail getStudent(@PathVariable String id) {
    return service.searchStudent(id);
  }

  /**
   * 受講生詳細の登録を行います。
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */
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