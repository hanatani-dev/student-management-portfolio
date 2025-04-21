package rasetech.StudentManagement;

public class Student {//MySQLの、Student＿ManagementのDatebaseの中につくったTABLE「students」に関連するようにここ作っていく。
  private String fullName;//ここは、後で変数名かわる！　application.propertiesで、trueとした文に解説した通り、
  //Mysqlにて設定した箇所＝アンダーバーの前は小文字でいいが、アンダーバーの後ろは大文字というルール、ここにも使われる。
  private int age;

  public String getFullName() {//name
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
