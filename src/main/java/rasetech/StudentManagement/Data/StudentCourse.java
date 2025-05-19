package rasetech.StudentManagement.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

  @NotBlank(message = "コースIDは必須です。")
  @Size(max = 10, message = "コースIDは10文字以内で入力してください。")
  private String id;

  @NotBlank(message = "受講生IDは必須です。")
  private String studentId;

  @NotBlank(message = "コース名は必須です。")
  @Size(max = 50, message = "コース名は50文字以内で入力してください。")
  private String courseName;

  @NotNull(message = "コース開始日は必須です。")
  private LocalDateTime courseStartAt;

  @NotNull(message = "コース終了日は必須です。")
  private LocalDateTime courseEndAt;
}
