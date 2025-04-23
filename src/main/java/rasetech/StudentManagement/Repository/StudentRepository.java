package rasetech.StudentManagement.Repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import rasetech.StudentManagement.Data.Student;
import rasetech.StudentManagement.Data.StudentsCourses;

/**
 * 受講生情報を扱うリポジトリ
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */

/**
 * ③　Serviceオブジェクトでドメイン処理とか終わったら、このRepositoryオブジェクト（Datebase）に対して検索処理を渡す。
 * Repositoryオブジェクトでは、DatebaseにアクセスしてSQLを発行して、その結果を呼び出すのだけを担ってる。
 *
 * 以下に書いてあるinterface=このオブジェクトに処理を入れれない。実装は何も書けない。
 */

@Mapper
public interface StudentRepository {

  /**
   * 全件検索します。
   * @return　全件検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> search();//searchでリファクタリングして、シグネチャーの変更で「String name」消した。

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();
}
