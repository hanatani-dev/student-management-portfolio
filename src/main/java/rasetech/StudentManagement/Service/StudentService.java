package rasetech.StudentManagement.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rasetech.Domain.StudentDetail;
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
    return repository.search();
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.searchStudentsCourses();
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());

    // コース情報登録
    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
      studentsCourse.setStudentId(studentDetail.getStudent().getId());
      studentsCourse.setCourseStartAt(LocalDateTime.now());
      studentsCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));
      repository.registerStudentsCourses(studentsCourse);
    }
  }
}
