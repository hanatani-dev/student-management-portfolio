package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索です。全件検索を行うので、条件指定は行いません。
   *
   * @return　受講生詳細一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();//controllerの全件検索をServiseで行う。
    return converter.convertStudentDetails(studentList, studentCourseList);

    //🔧 テストを書く準備（ざっくりと）
    //フレームワーク: JUnit 5 (Jupiter) + Mockito
    //テスト対象: StudentService
    //モック対象: StudentRepository, StudentConverter
  }

  /**
   * 受講生詳細検索です。IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。・・・処理の詳細も書く。
   *
   * @param id 　受講生ID
   * @return　受講生情報
   */
  public StudentDetail searchStudent(String id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourse = repository.searchStudentCourse(student.getId());
    return new StudentDetail(student, studentCourse);
    //✅ このメソッドの流れ：
    //1. repository.searchStudent(id) を呼び出して Student を取得
    //2. student.getId() を使って、repository.searchStudentCourse(...) を呼び出す
    //3. 最後に new StudentDetail(student, studentCourse) を返す

    //🧪 つまり、テストで確認すべきこと
    //リポジトリが 期待通りに呼ばれているか
    //Student と StudentCourse の値から、StudentDetail が正しく作られているか
  }

  /**
   * 受講生詳細の登録を行います。受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定します。
   *
   * @param studentDetail 　受講生詳細
   * @return　登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    //準備
    Student student = studentDetail.getStudent();
    repository.registerStudent(student);

    // やりたいことをやる
    studentDetail.getStudentCourseList().forEach(studentCourse -> {
      initStudentsCourse(studentCourse, student);
      repository.registerStudentCourse(studentCourse);
    });
    return studentDetail;

    //✔ ポイント整理
    //1. Student を取り出して、リポジトリで登録する（repository.registerStudent(...)）
    //2. StudentCourse のリストをループして、初期化処理（initStudentsCourse()）＋登録（registerStudentCourse()）
    //3. 最後に StudentDetail を返す

    //🧪 テストで確認すべきこと
    //repository.registerStudent() が1回呼ばれていること
    //studentCourse の数だけ registerStudentCourse() が呼ばれること
    //戻り値が 引数と同じ studentDetail であること

    //※ initStudentsCourse() の中身（日時とか）は「単体テストで細かく確認しなくてもOK！」って考えるのが一般的。Mockじゃない private メソッドだから。
    //なぜ private にするのか？ １．他から勝手に呼ばれないようにしたい（安全性）　２．役割が限定されてるから、テスト対象じゃない　３．registerStudent() の中に含まれている動作と考えるべき
    //🧪 テストではどう扱うの？ private メソッドは直接テストしない
    //    そのかわり、それを使ってる public メソッドを通じて、間接的に正しく動いてるかを確認するのが基本の考え方。
    //    たとえば… initStudentsCourse(...) では courseStartAt, courseEndAt をセットしてる
    //    だから registerStudent() のテストで、「返されたコースにちゃんと値が入ってるか？」を見ることで、間接的に initStudentsCourse() の動作を検証できる！

    //initStudentsCourse(...) → 中でだけ使う、準備のための下ごしらえ ①他のクラスには関係ない　②役割：コースに日付とか ID をセットするだけ　③単独では意味がない処理（呼ばれても何も返さない）
    //⇕
    //registerStudent(...) → 外から呼び出す、サービスのメイン機能　①Controller から呼ばれる　②他のクラスが使う　③「この子が責任持ってやる仕事！」
    //
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param studentCourse 　受講生コース情報
   * @param student       　受講生
   */
  private void initStudentsCourse(StudentCourse studentCourse, Student student) {
    LocalDateTime now = LocalDateTime.now();

    studentCourse.setStudentId(student.getId());
    studentCourse.setCourseStartAt(now);
    studentCourse.setCourseEndAt(now.plusYears(1));
  }

  /**
   * 受講生詳細の更新を行います。 受講生と受講生コース情報をそれぞれ更新します。
   *
   * @param studentDetail 　受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());

    // コース情報更新
    studentDetail.getStudentCourseList()
        .forEach(studentCourse -> repository.updateStudentCourse(studentCourse));

    
  }
}
