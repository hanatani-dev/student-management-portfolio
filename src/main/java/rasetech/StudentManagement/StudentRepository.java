package rasetech.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();//searchでリファクタリングして、シグネチャーの変更で「String name」消した。

  //@Insert、@Update、@Deleteも、第10回では使わないので、ノイズが起きないように消しておく。
}
