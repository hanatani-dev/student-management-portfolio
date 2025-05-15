package rasetech.Domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rasetech.StudentManagement.Data.Student;
import rasetech.StudentManagement.Data.StudentCourse;

@Getter
@Setter
@NoArgsConstructor//引数全く使わないコンストラクタ
@AllArgsConstructor//全項目＝studentとstudentsCourses、両方設計するコンストラクタ
public class StudentDetail {

  private Student student;
  private List<StudentCourse> studentCourseList;
  /**
   * StudentsCoursesオブジェクトは、Studentオブジェクトに対して、複数コース受講してることあるはず。List使う！
   */


}
