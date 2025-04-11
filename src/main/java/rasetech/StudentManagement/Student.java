package rasetech.StudentManagement;

public class Student {//MySQLの、Student＿ManagementのDatebaseの中につくったTABLE「students」に関連するようにここ作っていく。
  private String name;
  private int age;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
