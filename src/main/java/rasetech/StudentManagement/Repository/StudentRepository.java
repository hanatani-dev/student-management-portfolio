package rasetech.StudentManagement.Repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import rasetech.StudentManagement.Data.Student;
import rasetech.StudentManagement.Data.StudentsCourses;

/**
 * 受講生情報を扱うリポジトリ 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */

/**
 * ③　Serviceオブジェクトでドメイン処理とか終わったら、このRepositoryオブジェクト（Datebase）に対して検索処理を渡す。
 * Repositoryオブジェクトでは、DatebaseにアクセスしてSQLを発行して、その結果を呼び出すのだけを担ってる。
 * <p>
 * 以下に書いてあるinterface=このオブジェクトに処理を入れれない。実装は何も書けない。
 */

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

  // 学生情報の保存
  @Insert("INSERT INTO students(name, nickname, email, area, age, sex, remark, is_deleted) "
      + "VALUES(#{name}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  // コース情報の保存
  @Insert("INSERT INTO students_courses(student_id, course_name, start_data_at, end_data_at) "
      + "VALUES(#{studentId}, #{courseName}, #{startDataAt}, #{endDataAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentsCourse(StudentsCourses course);

  //学生情報の更新
  @Update(
      "UPDATE students SET(name=#{name}, nickname=#{nickname},"
          + " email=#{email}, area=#{area}, age=#{age}, sex=#{sex}, remark=#{remark}, is_deleted=#{isDeleted}) WHERE id = #{id}")
  void updateStudent(Student student);

  // コース情報の更新
  @Update("UPDATE student_courses SET(course_name = #{courseName})) WHERE id = #{id}")
  void updateStudentsCourses(StudentsCourses studentsCourses);
}
