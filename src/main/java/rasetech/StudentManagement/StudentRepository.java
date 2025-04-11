package rasetech.StudentManagement;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM student WHERE name = #{name}")
  Student searchByName(String name);

  @Insert("INSERT student values(#{name}, #{age}")
  void registerStudent(String name, int age);

  @Update("UPDATE studen SET age = #{age}WHERE name = #{name}")
  void updateStudent(String name, int age);

  @Delete("DLETE FROM student WHERE name = #{name}")
  void deleteStudent(String name);
}
