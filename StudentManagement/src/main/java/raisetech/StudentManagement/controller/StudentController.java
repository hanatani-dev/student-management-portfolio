package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.domain.StudentSearchCondition;
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
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 条件指定による受講生検索です。
   */
  @Operation(summary = "条件付き検索", description = "名前・性別・地域・削除フラグで受講生を検索します。")
  @GetMapping("/searchStudent")
  public List<StudentDetail> searchStudentsByCondition(
      @Validated StudentSearchCondition condition) {
    return service.searchStudentsByConditions(condition);
  }

  // 新しく追加
  @Operation(
      summary = "テスト用の例外発生",
      description = "意図的にTestExceptionを発生させるテスト用エンドポイントです。",
      responses = {
          @ApiResponse(responseCode = "500", description = "テスト用例外が発生しました")
      }
  )
  @GetMapping("/testException")
  public void testException() throws TestException {
    throw new TestException("これはテスト用の例外です！");
  }

  //ControllerTestクラス用の例外メソッド
  @Operation(summary = "旧API用メッセージ", description = "古いURLにアクセスされた際のメッセージを返すエンドポイント")
  @GetMapping("/exception")
  public ResponseEntity<String> oldApiMessage() {
    return ResponseEntity
        .badRequest()
        .body("このAPIは現在利用できません。古いURLとなっています。");
  }

  /**
   * 受講生検索です。 IDに紐づく任意の情報を取得します。
   *
   * @param id 　受講生ID
   * @return　受講生
   */
  @Operation(summary = "個人検索", description = "指定したIDの受講生を検索します。")
  @GetMapping("/student/{id}")//単一検索できる！PostmanでID検索したみたいに！
  public StudentDetail getStudent(
      @PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id) {
    return service.searchStudent(id);
  }

  /**
   * 条件指定による受講生検索です。
   *
   * @param condition 検索条件（名前・性別など）
   * @return 該当する受講生詳細のリスト
   */
  @Operation(summary = "条件検索", description = "条件を指定して受講生を検索します。")
  @PostMapping("/students/search")
  public List<StudentDetail> searchByConditions(@RequestBody StudentSearchCondition condition) {
    return service.searchStudentsByConditions(condition);
  }

  /**
   * 受講生詳細の登録を行います。登録時だけ、IDは入力しない！（Studentクラス:IDは自動採番されるので）ってしたいので、@ValidationとOnRegister追加。
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */
  @Operation(summary = "受講生登録", description = "受講生を登録します。")
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
  @Operation(summary = "受講生更新", description = "受講生情報を更新します")
  @PutMapping("/updateStudent")//Put=全体更新　Patch=部分更新
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    // 学生リストに更新
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

}