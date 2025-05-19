package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  // 全受講生の取得
  List<Student> search();
  
  // IDで受講生を検索
  Student searchStudent(String id);

  // 全受講生コース情報の取得
  List<StudentCourse> searchStudentCourseList();

  // 受講生IDに紐づくコース情報取得
  List<StudentCourse> searchStudentCourse(String studentId);

  // 受講生の新規登録
  void registerStudent(Student student);

  // コース情報の新規登録
  void registerStudentCourse(StudentCourse studentCourse);

  // 受講生情報の更新
  void updateStudent(Student student);

  // コース名の更新（1件）
  void updateStudentCourse(StudentCourse studentCourse);
}