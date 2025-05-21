package raisetech.StudentManagement.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exceptionHandler.TestException;
import raisetech.StudentManagement.service.StudentService;

/**
 * 以下、運用する人に見せるのか、チームメンバーに見せるのか、で、 受講生の検索や登録、更新などを行うREST　APIとして受け付けるControllerです。
 */
@Validated//class全体に対して検証を入れる。
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
  public List<StudentDetail> getStudentList() throws TestException {
    throw new TestException(
        "現在のこのAPIは利用できません。URLは「studentList」ではなく「students」を利用してください。");
  }

  /**
   * 受講生検索です。 IDに紐づく任意の情報を取得します。
   *
   * @param id 　受講生ID
   * @return　受講生
   */
  @GetMapping("/student/{id}")//単一検索できる！PostmanでID検索したみたいに！
  public StudentDetail getStudent(
      @PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id) {
    return service.searchStudent(id);
  }

  /**
   * 受講生詳細の登録を行います。登録時だけ、IDは入力しない！（Studentクラス:IDは自動採番されるので）ってしたいので、@ValidationとOnRegister追加。
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @Validated(raisetech.StudentManagement.validaion.OnRegisterStudent.class) @RequestBody StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生詳細の更新を行います。キャンセルフラグの更新もここで行います（論理削除）
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */
  @PutMapping("/updateStudent")//Put=全体更新　Patch=部分更新
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    // 学生リストに更新
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  @ExceptionHandler(TestException.class)//例外をハンドリングする＝コントロールする
  public ResponseEntity<String> handleTestException(
      TestException ex) {//①・・・一覧検索で書いた例外処理、実行したらここに飛んでくる。
    //ログ出力できるようにする文章は、returnの前の行に書くこと多いが、会社に準ずる。
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    //   ↑　　　　　　　　　　　　　　　　　　　　　　　↑　　　　　　　　　　　　　　↑
    //Postmanの一覧検索画面には、　　　　　　　400のBAD_REQUEST出て、　　　一覧検索に書いたコメントが出力される。
  }
}