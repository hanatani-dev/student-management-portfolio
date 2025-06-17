package raisetech.StudentManagement;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

public class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void setUp() {
    sut = new StudentConverter();
  }

  @Test
  void convertStudentDetailsで全項目が正しくマッピングされること() {
    // Arrange
    Student student = new Student();
    student.setId("1");
    student.setName("山田花子");
    student.setKanaName("ヤマダハナコ");
    student.setNickname("はなちゃん");
    student.setEmail("hana@example.com");
    student.setArea("東京都渋谷区");
    student.setAge(25);
    student.setSex("女性");
    student.setRemark("備考です");
    student.setDeleted(false);

    LocalDateTime start = LocalDateTime.of(2024, 4, 1, 0, 0);
    LocalDateTime end = LocalDateTime.of(2025, 4, 1, 0, 0);

    StudentCourse course = new StudentCourse();
    course.setId("101");
    course.setStudentId("1");
    course.setCourseName("Javaプログラミング入門");
    course.setCourseStartAt(start);
    course.setCourseEndAt(end);

    // Act
    List<StudentDetail> result = sut.convertStudentDetails(
        List.of(student),
        List.of(course)
    );

    // Assert
    assertThat(result).hasSize(1);
    StudentDetail detail = result.get(0);
    Student resultStudent = detail.getStudent();
    List<StudentCourse> resultCourses = detail.getStudentCourseList();

    assertThat(resultStudent.getId()).isEqualTo("1");
    assertThat(resultStudent.getName()).isEqualTo("山田花子");
    assertThat(resultStudent.getKanaName()).isEqualTo("ヤマダハナコ");
    assertThat(resultStudent.getNickname()).isEqualTo("はなちゃん");
    assertThat(resultStudent.getEmail()).isEqualTo("hana@example.com");
    assertThat(resultStudent.getArea()).isEqualTo("東京都渋谷区");
    assertThat(resultStudent.getAge()).isEqualTo(25);
    assertThat(resultStudent.getSex()).isEqualTo("女性");
    assertThat(resultStudent.getRemark()).isEqualTo("備考です");
    assertThat(resultStudent.isDeleted()).isFalse();

    assertThat(resultCourses).hasSize(1);
    StudentCourse resultCourse = resultCourses.get(0);
    assertThat(resultCourse.getId()).isEqualTo("101");
    assertThat(resultCourse.getStudentId()).isEqualTo("1");
    assertThat(resultCourse.getCourseName()).isEqualTo("Javaプログラミング入門");
    assertThat(resultCourse.getCourseStartAt()).isEqualTo(start);
    assertThat(resultCourse.getCourseEndAt()).isEqualTo(end);
  }
}
