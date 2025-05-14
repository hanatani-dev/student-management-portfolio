package rasetech.Domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import rasetech.StudentManagement.Data.Student;
import rasetech.StudentManagement.Data.StudentsCourses;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentsCourses;
  /**
   * StudentsCoursesオブジェクトは、Studentオブジェクトに対して、複数コース受講してることあるはず。List使う！
   */


}
