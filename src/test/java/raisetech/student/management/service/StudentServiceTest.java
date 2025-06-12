package raisetech.student.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
//Testメソッドのつど。
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細一覧検索＿リポジトリとコンバーターの処理が適切に呼び出せていること() {//メソッドや変数名には、日本語使える！
    // Mock化　Mockito Stub
    //事前準備
    //Testするときに使う、sut＝system under test 正常に動作していることを表す。被試験対象

    List<Student> studentList = new ArrayList<>();
    //　このコードは、「中身が空っぽのリストを返す」モック設定って意味。
    //つまり、サービスメソッド（sut.searchStudentList()）が内部で repository.search() を呼んだとき、空のListが返ってくるようにしてるってこと。

    //　🤔 じゃあなぜ「空」でテストしてるの？
    //これは「最低限、処理が正しく呼ばれてるか」を検証するための雛形テストだから！
    //💬 実際のデータで結果を比較するテストじゃなくて、「指定のメソッドがちゃんと呼ばれたかどうか」を見てるだけ！

    List<StudentCourse> studentCourseList = new ArrayList<>();

    when(repository.search()).thenReturn(studentList);//Mockitoは、返り値を疑似的に操作できる。
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    //実行・・・ Testするときに使う、sut＝system under test 正常に動作していることを表す。被試験対象
    List<StudentDetail> actual = sut.searchStudentList();

    //検証 ✅「呼ばれている」ことだけを確認するテストの意味
    //ここで重要なのは「中身が何か」じゃなくて、「呼び出されたこと」をチェックしてる点！
    //これはMock（偽物）相手のユニットテストではよくやるパターン。
    verify(repository, times(1)).search();//repositoryクラスのsearch1回呼び出してますよってこと。
    verify(repository, times(1)).search();//searchStudentCourseListも1回呼び出してますよ。
    verify(converter, times(1)).convertStudentDetails(studentList,
        studentCourseList);//convertクラスのconvertメソッドも1回呼び出してますよ。
    //後処理
    //ここでDBをもとに戻す。
  }

  @Test
  void 受講生詳細検索_リポジトリが正しく呼ばれ_正しい受講生詳細が返ること() {
    // --- 準備 ---実際のアプリケーションでは IDが自動採番されてたり、1～100までの制限があったりする。
    //でも、テストの中ではそこまで厳密に再現しなくてOKなことが多い！
    String studentId = "123";

    // Mock用のStudentとStudentCourse作成
    Student mockStudent = new Student();
    mockStudent.setId(studentId); // getId()が動作するように

    List<StudentCourse> mockCourseList = List.of(new StudentCourse());

    // モックの戻り値設定
    when(repository.searchStudent(studentId)).thenReturn(mockStudent);
    when(repository.searchStudentCourse(studentId)).thenReturn(mockCourseList);

    // --- 実行 ---
    StudentDetail result = sut.searchStudent(studentId);

    // --- 検証 ---
    verify(repository, times(1)).searchStudent(studentId);
    verify(repository, times(1)).searchStudentCourse(studentId);

    // 戻り値が正しいか（identity比較OK）
    assertEquals(mockStudent, result.getStudent());
    assertEquals(mockCourseList, result.getStudentCourseList());

    //🔍 解説ポイント
    //mockStudent.setId(...) をちゃんとやらないと、2つ目の searchStudentCourse(student.getId()) が null で動かなくなる可能性がある
    //StudentDetail の中身（student と courseList）がちゃんとMockの値と一致してるかを assertEquals でチェック
    //verify(...) でメソッドが1回だけちゃんと呼ばれてるか確認
  }

  @Test
  void 受講生詳細登録_リポジトリの登録メソッドが正しく呼ばれ_引数のStudentDetailが返ること() {
    // --- 準備 ---

    // モックのStudent作成
    Student student = new Student();
    student.setId("123");

    // モックのStudentCourse（1件だけ）
    StudentCourse course = new StudentCourse();
    List<StudentCourse> courseList = List.of(course);

    // モックのStudentDetail
    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourseList(courseList);

    // --- 実行 ---
    StudentDetail result = sut.registerStudent(detail);

    // --- 検証 ---
    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(course);
    assertEquals(detail, result); // 引数と同じオブジェクトが返る
  }
}