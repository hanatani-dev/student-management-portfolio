package rasetech.StudentManagement.Data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {//MySQLの、Student＿ManagementのDatebaseの中につくったTABLE「students」に関連するようにここ作っていく。
  private String id;
  private String name;//ここは、後で変数名かわる！　application.propertiesで、trueとした文に解説した通り、
  //Mysqlにて設定した箇所＝アンダーバーの前は小文字でいいが、アンダーバーの後ろは大文字というルール、ここにも使われる。
  private String nickname;
  private String email;
  private String area;
  private int age;
  private String sex;
  private String remark;//備考欄
  private boolean isDeleted;//論理削除＝授業終了／解約後にsqlで検索にでてこないようにする。　物理削除＝データ削除・復元不可能
}