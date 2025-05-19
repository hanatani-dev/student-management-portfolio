package raisetech.StudentManagement.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import raisetech.StudentManagement.validaion.OnRegisterStudent;
import raisetech.StudentManagement.validaion.OnRpdateStudent;

@Getter
@Setter
public class Student {//MySQLの、Student＿ManagementのDatebaseの中につくったTABLE「students」に関連するようにここ作っていく。
  @Null(groups = OnRegisterStudent.class, message = "新規登録時はID不要です。")
  @NotNull(groups = OnRpdateStudent.class, message = "更新時はIDが必須です。")
  @Size(max = 100, groups = OnRpdateStudent.class, message = "IDは100文字以内で入力してください。")
  //@Size に groups = OnRpdateStudent.class を追加	登録時に @Size が実行されないようにするため
  private String id;

  @NotBlank(message = "名前は必須です。")
  @Size(max = 100, message = "名前は100文字以内で入力してください。")
  private String name;//ここは、後で変数名かわる！　application.propertiesで、trueとした文に解説した通り、
  //Mysqlにて設定した箇所＝アンダーバーの前は小文字でいいが、アンダーバーの後ろは大文字というルール、ここにも使われる。

  @Size(max = 20, message = "ニックネームは20文字以内で入力してください。")
  private String nickname;

  @NotBlank(message = "メールアドレスは必須です。")
  @Email(message = "正しいメールアドレス形式で入力してください。")
  private String email;

  @Size(max = 20, message = "地域名は100文字以内で入力してください。")
  private String area;

  @Min(value = 0, message = "年齢は0以上で入力してください。")
  @Max(value = 150, message = "年齢は150以下で入力してください。")
  private int age;

  @Pattern(regexp = "^(男性|女性|その他)?$", message = "性別は「男生」「女生」「その他」のいずれかで入力してください。")
  private String sex;

  @Size(max = 300, message = "備考は300文字以内で入力してください。")
  private String remark;//備考欄

  // isDeleted は boolean なのでバリデーション不要（nullにならない）
  private boolean isDeleted;//論理削除＝授業終了／解約後にsqlで検索にでてこないようにする。　物理削除＝データ削除・復元不可能
}