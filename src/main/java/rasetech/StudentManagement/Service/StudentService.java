package rasetech.StudentManagement.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rasetech.StudentManagement.Data.Student;
import rasetech.StudentManagement.Data.StudentsCourses;
import rasetech.StudentManagement.Repository.StudentRepository;

/**
 * 単一責任の原則。メインオブジェクトの、リクエスト処理してるControllerと、Serviceの業務処理がごっちゃにならないように、オブジェクト分ける。
 * 一度Controllerと同じオブジェクトで業務処理のコード書いてしまえば、後できれいに整理するとかができない！
 */

/**
 * ②　Controllerオブジェクトにリクエストがとんできたら、Serviceオブジェクトに来る。 Serviceオブジェクトの中で、ドメイン処理や業務処理を行う。
 * <p>
 * ④　Repositoryオブジェクトから返ってきたSQLの結果を、またこのServiceオブジェクトで受け取って、
 * Serviceオブジェクトでまた処理が必要であれば、行われてから、Controllerオブジェクトに処理されたものが返る。
 */
@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    //ここで何かしらの処理を行う・・・検索処理

    List<Student> allStudents = repository.search();

    List<Student> studentsIn30s = allStudents.stream()
        .filter(student -> student.getAge() >= 30 && student.getAge() < 40)
        .toList();
    //絞り込みをする。年齢が30代の人のみを抽出する。
    //抽出したリストをcontrollerオブジェクトに返す。

    return studentsIn30s;//これは Repository層（データアクセス層） のメソッドを直接呼んでる。 生SQLまたはJPAクエリなどで、データベースからデータをそのまま取得する。
  }

  public List<StudentsCourses> searchStudentsCoursesList() {
    // 全件取得
    List<StudentsCourses> allCourses = repository.searchStudentsCourses();

    List<StudentsCourses> javaCourses = allCourses.stream()
        .filter(course -> course.getCourseName() != null &&
            course.getCourseName().toLowerCase().contains("java"))
        .toList();
    // 絞り込み検索で「Javaコース」のコース情報のみを抽出する。
    //　抽出したリストをcontrollerオブジェクトに返す。
    return javaCourses;
  }
}
