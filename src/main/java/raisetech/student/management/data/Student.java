package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
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
import raisetech.student.management.validaion.OnRegisterStudent;
import raisetech.student.management.validaion.OnRpdateStudent;

@Schema(description = "受講生")
@Getter
@Setter
public class Student {//MySQLの、Student＿ManagementのDatebaseの中につくったTABLE「students」に関連するようにここ作っていく。

  @Schema(description = "受講生ID（自動採番）", example = "1")
  @Null(groups = OnRegisterStudent.class, message = "新規登録時はID不要です。")
  @NotNull(groups = OnRpdateStudent.class, message = "更新時はIDが必須です。")
  @Pattern(regexp = "^\\d+$", message = "IDは数字を入力してください。")
  private String id;

  @Schema(description = "氏名", example = "山田 花子")
  @NotBlank(message = "名前は必須です。")
  @Size(max = 100, message = "名前は100文字以内で入力してください。")
  private String name;//ここは、後で変数名かわる！　application.propertiesで、trueとした文に解説した通り、
  //Mysqlにて設定した箇所＝アンダーバーの前は小文字でいいが、アンダーバーの後ろは大文字というルール、ここにも使われる。

  @Schema(description = "フリガナ", example = "ヤマダ ハナコ")
  @NotBlank(message = "フリガナは必須です。")
  @Size(max = 100, message = "フリガナは100文字以内で入力してください。")
  private String kanaName; // ★ここを追加！

  @Schema(description = "ニックネーム", example = "はなちゃん")
  @Size(max = 20, message = "ニックネームは20文字以内で入力してください。")
  private String nickname;

  @Schema(description = "メールアドレス", example = "hana@example.com")
  @NotBlank(message = "メールアドレスは必須です。")
  @Email(message = "正しいメールアドレス形式で入力してください。")
  private String email;

  @Schema(description = "住んでいる地域", example = "東京都渋谷区")
  @Size(max = 20, message = "地域名は100文字以内で入力してください。")
  private String area;

  @Schema(description = "年齢", example = "25")
  @Min(value = 0, message = "年齢は0以上で入力してください。")
  @Max(value = 150, message = "年齢は150以下で入力してください。")
  private int age;

  @Schema(description = "性別（男性／女性／その他）", example = "女性")
  @Pattern(regexp = "^(男性|女性|その他)?$", message = "性別は「男性」「女性」「その他」のいずれかで入力してください。")
  private String sex;

  @Schema(description = "受講生の備考", example = "試験日は12/1")
  @Size(max = 300, message = "備考は300文字以内で入力してください。")
  private String remark;//備考欄

  // isDeleted は boolean なのでバリデーション不要（nullにならない）
  private boolean isDeleted;//論理削除＝授業終了／解約後にsqlで検索にでてこないようにする。　物理削除＝データ削除・復元不可能

  //@AssertTrue:相関チェック・・・複数項目に関連付けて条件付けられる。「例：身長体重が入力されており、BMI値がとんでもない値にならないこと」
}