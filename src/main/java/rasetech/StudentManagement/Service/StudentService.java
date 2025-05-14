package rasetech.StudentManagement.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rasetech.Domain.StudentDetail;
import rasetech.StudentManagement.Controller.Converter.StudentConverter;
import rasetech.StudentManagement.Data.Student;
import rasetech.StudentManagement.Data.StudentsCourses;
import rasetech.StudentManagement.Repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生一覧検索です。全件検索を行うので、条件指定は行いません。
   *
   * @return　受講生一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentsCourses> studentsCoursesList = repository.searchStudentsCoursesList();//controllerの全件検索をServiseで行う。
    return converter.convertStudentDetails(studentList, studentsCoursesList);
  }

  /**
   * 受講生検索です。IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。・・・処理の詳細も書く。
   *
   * @param id 　受講生ID
   * @return　受講生情報
   */
  public StudentDetail searchStudent(String id) {
    Student student = repository.searchStudent(id);
    List<StudentsCourses> studentsCourses = repository.searchStudentsCourse(student.getId());
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentsCourses);
    return studentDetail;
  }
  

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());

    // コース情報登録
    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
      studentsCourse.setStudentId(studentDetail.getStudent().getId());
      studentsCourse.setCourseStartAt(LocalDateTime.now());
      studentsCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));
      repository.registerStudentsCourses(studentsCourse);
    }
    return studentDetail;
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
