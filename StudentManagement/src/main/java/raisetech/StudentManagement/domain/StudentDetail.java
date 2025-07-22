package raisetech.StudentManagement.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@Schema(description = "受講生詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  @NotNull(message = "受講生情報（student）は必須です。")
  @Valid
  private Student student;

  @Valid
  private List<StudentCourse> studentCourseList;
  /**
   * StudentsCoursesオブジェクトは、Studentオブジェクトに対して、複数コース受講してることあるはず。List使う！
   */
}