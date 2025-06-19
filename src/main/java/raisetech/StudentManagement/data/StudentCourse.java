package raisetech.StudentManagement.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
@JsonPropertyOrder({"id", "studentId", "courseName", "courseStartAt", "courseEndAt", "statusId",
    "statusName"})
public class StudentCourse {

  @Schema(description = "コースID（自動採番）", example = "1")
  @NotBlank(message = "コースIDは必須です。")
  @Size(max = 10, message = "コースIDは10文字以内で入力してください。")
  private String id;

  @Schema(description = "受講生ID (受講生情報にて自動採番されている)", example = "1")
  @NotBlank(message = "受講生IDは必須です。")
  private String studentId;

  @Schema(description = "コース名", example = "Javaプログラミング入門")
  @NotBlank(message = "コース名は必須です。")
  @Size(max = 50, message = "コース名は50文字以内で入力してください。")
  private String courseName;

  @Schema(description = "コース開始日", example = "2024-04-01T00:00:00")
  @NotNull(message = "コース開始日は必須です。")
  @JsonProperty("courseStartAt")
  private LocalDateTime courseStartAt;

  @Schema(description = "コース終了日", example = "2025-04-01T00:00:00")
  @NotNull(message = "コース終了日は必須です。")
  @JsonProperty("courseEndAt")
  private LocalDateTime courseEndAt;

  @Schema(description = "ステータスID", example = "1")
  @NotNull(message = "ステータスIDは必須です。")
  private Integer statusId;

  @Schema(description = "ステータス名（仮申込／本申込 など）", example = "仮申込")
  @JsonProperty("statusName")
  private String statusName;

  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }
}
