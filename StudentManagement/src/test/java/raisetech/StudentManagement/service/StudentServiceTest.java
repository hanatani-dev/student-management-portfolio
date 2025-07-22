package raisetech.StudentManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.domain.StudentSearchCondition;
import raisetech.StudentManagement.repository.StudentRepository;

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
    sut.searchStudentList();

    //検証 ✅「呼ばれている」ことだけを確認するテストの意味
    //ここで重要なのは「中身が何か」じゃなくて、「呼び出されたこと」をチェックしてる点！
    //これはMock（偽物）相手のユニットテストではよくやるパターン。
    verify(repository, times(1)).search();//repositoryクラスのsearch1回呼び出してますよってこと。
    verify(repository, times(1)).searchStudentCourseList();//searchStudentCourseListも1回呼び出してますよ。
    verify(converter, times(1)).convertStudentDetails(studentList,
        studentCourseList);//convertクラスのconvertメソッドも1回呼び出してますよ。
    //後処理
    //ここでDBをもとに戻す。
  }

  @Test
  void 受講生詳細の検索_リポジトリの処理が適切に呼び出せていること() {
    // --- 準備 ---実際のアプリケーションでは IDが自動採番されてたり、1～100までの制限があったりする。
    //でも、テストの中ではそこまで厳密に再現しなくてOKなことが多い！
    String id = "999";

    // Mock用のStudentとStudentCourse作成
    Student student = new Student();
    student.setId(id); // getId()が動作するように

    // モックの戻り値設定
    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(id)).thenReturn(new ArrayList<>());

    StudentDetail expected = new StudentDetail(student, new ArrayList<>());

    // --- 実行 ---
    StudentDetail actual = sut.searchStudent(id);

    // --- 検証 ---
    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourse(id);

    // 戻り値が正しいか（identity比較OK）
    assertEquals(expected.getStudent().getId(), actual.getStudent().getId());
  }


  @Test
  void 受講生詳細登録_リポジトリの登録メソッドが正しく呼ばれ_引数のStudentDetailが返ること() {
    // --- 準備 ---

    // モックのStudent作成
    Student student = new Student();

    // モックのStudentCourse（1件だけ）
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    // モックのStudentDetail
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    // --- 実行 ---
    sut.registerStudent(studentDetail);

    // --- 検証 ---
    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(1)).registerStudentCourse(studentCourse);
  }

  @Test
  void 受講生詳細の登録_初期化処理が行われること() {
    String id = "999";
    Student student = new Student();
    student.setId(id);
    StudentCourse studentCourse = new StudentCourse();

    sut.initStudentsCourse(studentCourse, student.getId());

    assertEquals(id, studentCourse.getStudentId());
    assertEquals(LocalDateTime.now().getHour(), studentCourse.getCourseStartAt().getHour());
    assertEquals(LocalDateTime.now().plusYears(1).getYear(),
        studentCourse.getCourseEndAt().getYear());
  }

  @Test
  void 受講生情報更新_受講生とコースが正しく更新されること() {
    // --- 準備 ---

    // モックのStudent
    Student student = new Student();

    // モックのコース（今回は2件作ってみる）
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    // モックのStudentDetail
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    // --- 実行 ---
    sut.updateStudent(studentDetail);

    // --- 検証 ---
    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourse);
  }

  @Test
  void 受講生コース情報の更新でステータスが正しく反映されること() {
    Student student = new Student();
    StudentCourse course = new StudentCourse();
    course.setId("1");
    course.setStudentId("1");
    course.setCourseName("Javaコース");
    course.setCourseStartAt(LocalDateTime.now());
    course.setCourseEndAt(LocalDateTime.now().plusMonths(6));
    course.setStatusId(2); // ← 例えば 2 = 受講中 の場合
    course.setStatusName("受講中");

    List<StudentCourse> courseList = List.of(course);
    StudentDetail detail = new StudentDetail(student, courseList);

    sut.updateStudent(detail);

    verify(repository, times(1)).updateStudentCourse(course);
    assertEquals(2, course.getStatusId());
    assertEquals("受講中", course.getStatusName());
  }

  @Test
  void 条件指定検索_リポジトリとコンバーターが正しく呼び出されること() {
    // --- 準備 ---
    var condition = new StudentSearchCondition();
    condition.setName("スネ夫");
    condition.setSex("男性");
    condition.setArea("東京都");

    List<Student> mockStudentList = new ArrayList<>();
    List<StudentCourse> mockCourseList = new ArrayList<>();
    List<StudentDetail> expectedDetails = new ArrayList<>();

    when(repository.searchByConditions(condition)).thenReturn(mockStudentList);
    when(repository.searchStudentCourseList()).thenReturn(mockCourseList);
    when(converter.convertStudentDetails(mockStudentList, mockCourseList)).thenReturn(
        expectedDetails);

    // --- 実行 ---
    List<StudentDetail> actual = sut.searchStudentsByConditions(condition);

    // --- 検証 ---
    verify(repository).searchByConditions(condition);
    verify(repository).searchStudentCourseList();
    verify(converter).convertStudentDetails(mockStudentList, mockCourseList);
    assertEquals(expectedDetails, actual);
  }
}

