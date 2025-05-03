package rasetech.StudentManagement.Repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
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

  /**
   * 全件検索します。
   *
   * @return　全件検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> search();//searchでリファクタリングして、シグネチャーの変更で「String name」消した。

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

  // 学生情報の保存
  @Insert(
      "INSERT INTO students(name,nickname, email, area, age, sex,remark,is_deleted)"
          + " VALUES(#{name}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses(student_id, course_name, course_start_at, course_end_at) "
      + "VALUES(#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentsCourses(StudentsCourses studentsCourses);
}