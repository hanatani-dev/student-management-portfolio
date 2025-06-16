package raisetech.StudentManagement;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

public class StudentConverterTest {

  private final StudentConverter converter = new StudentConverter();

  @Test
  void convertStudentDetailsで正しくマッピングされること() {
    // Arrange
    Student student = new Student();
    student.setId("1");
    student.setName("テスト太郎");

    StudentCourse course = new StudentCourse();
    course.setId("101");
    course.setStudentId("1");
    course.setCourseName("Javaコース");

    // Act
    List<StudentDetail> result = converter.convertStudentDetails(
        List.of(student),
        List.of(course)
    );

    // Assert
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getStudent().getId()).isEqualTo("1");
    assertThat(result.get(0).getStudentCourseList()).hasSize(1);
    assertThat(result.get(0).getStudentCourseList().get(0).getCourseName()).isEqualTo("Javaコース");
  }
}
