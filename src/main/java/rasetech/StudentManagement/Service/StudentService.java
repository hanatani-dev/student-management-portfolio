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

  /**
   * １．Serviseとしては、画面に入ってきたID情報に基づく特定・単一の受講生情報と、受講生IDに紐づく複数持っているであろうコース情報を取得する（getId)
   * Repositoryオブジェクトは取得した情報に応じてStudentID変更する。、取得データはStudentDetailにそれぞれ設定して、
   * controllerオブジェクトに返す＝return
   *
   * @param id
   * @return
   */
  public StudentDetail searchStudent(String id) {
    Student student = repository.searchStudent(id);
    List<StudentsCourses> studentsCourses = repository.searchStudentsCourse(student.getId());
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentsCourses);
    return studentDetail;
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.searchStudentsCoursesList();
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

  /**
   * 各コース名、一つだけ名前変えるとき、Serviseではリストで入ってくる。
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());

    // コース情報更新
    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
      repository.updateStudentsCourses(studentsCourse);
    }
  }
}
