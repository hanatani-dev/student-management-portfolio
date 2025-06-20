package raisetech.StudentManagement.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 検索条件をまとめたDTOクラス（受講生検索用）
 */
@Getter
@Setter
public class StudentSearchCondition {

  private String name;   // 名前（部分一致）
  private String sex;    // 性別（完全一致）
  private String area;   // 地域（部分一致）
  private Boolean deleted; // 論理削除フラグ（true: 削除済、false: 有効）

  // 必要なら age なども追加できる！
}
