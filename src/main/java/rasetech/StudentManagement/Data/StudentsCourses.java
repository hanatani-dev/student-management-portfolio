package rasetech.StudentManagement.Data;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentsCourses {

  private String id;
  private String studentId;
  private String courseName;
  private LocalDate startDataAt;
  private LocalDate endDataAt;

}
