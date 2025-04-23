package rasetech.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  //@Insert、@Update、@Deleteも、第10回では使わないので、ノイズが起きないように消しておく。

  @Select("SELECT * FROM student")
  List<Student> searchStudent();//searchでリファクタリングして、シグネチャーの変更で「String name」消した。

  @Select("SELECT * FROM courses")
  List<StudentsCourses> searchStudentsCourses();
}
