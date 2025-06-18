package raisetech.StudentManagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.repository.StudentRepository;

@MybatisTest
@ActiveProfiles("test")  // テスト用プロファイル読み込みたい場合
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)//大元のMysqlでなく、H2DBに強制的につなげる！
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {//授業で作成
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void IDで受講生が検索できること() {
    Student student = sut.searchStudent("1");
    assertThat(student).isNotNull();
    assertThat(student.getName()).isEqualTo("山田太郎");
  }

  @Test
  void 存在しないIDでは受講生が取得できないこと() {
    Student student = sut.searchStudent("999");
    assertThat(student).isNull();
  }

  @Test
  void nullのIDで検索した場合はnullが返ること() {
    Student student = sut.searchStudent(null);
    assertThat(student).isNull();
  }

  @Test
  void 全ての受講生コース情報が取得できること() {
    List<StudentCourse> courses = sut.searchStudentCourseList();
    assertThat(courses.size()).isEqualTo(10);
  }

  @Test
  void 受講生IDに紐づくコース情報が取得できること() {
    List<StudentCourse> courses = sut.searchStudentCourse("1");
    assertThat(courses.size()).isEqualTo(3);// data.sqlに合わせて
  }

  @Test
  void 受講生の登録が行えること() { //授業で作成
    Student student = new Student();
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setArea("奈良県");
    student.setAge(36);
    student.setSex("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);

    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 必須項目が抜けている受講生は登録できないこと() {
    Student student = new Student();
    student.setEmail("invalid@example.com"); // 名前など未設定

    assertThatThrownBy(() -> sut.registerStudent(student))
        .isInstanceOf(Exception.class); // 実装に合わせて調整
  }


  @Test
  void コース登録が行えること() {
    StudentCourse course = new StudentCourse();
    course.setStudentId("1");
    course.setCourseName("Pythonコース");
    course.setCourseStartAt(LocalDateTime.of(2025, 1, 1, 10, 0));
    course.setCourseEndAt(LocalDateTime.of(2025, 5, 1, 15, 0));

    sut.registerStudentCourse(course);

    List<StudentCourse> courses = sut.searchStudentCourse("1");
    assertThat(courses.size()).isEqualTo(4);// 元の3件＋1
  }

  @Test
  void 受講生情報が更新できること() {
    Student student = sut.searchStudent("1");
    student.setEmail("updated@example.com");
    student.setKanaName("ヤマダタロウUPDATED");

    sut.updateStudent(student);

    Student updated = sut.searchStudent("1");
    assertThat(updated.getEmail()).isEqualTo("updated@example.com");
    assertThat(updated.getKanaName()).isEqualTo("ヤマダタロウUPDATED");
  }

  @Test
  void 受講生のコース名が更新できること() {
    List<StudentCourse> courses = sut.searchStudentCourse("1");
    StudentCourse course = courses.get(0);
    String oldName = course.getCourseName();

    course.setCourseName("変更されたコース");
    sut.updateStudentCourse(course);

    List<StudentCourse> updated = sut.searchStudentCourse("1");
    assertThat(updated.get(0).getCourseName())
        .isNotEqualTo(oldName)
        .isEqualTo("変更されたコース");
  }


}
