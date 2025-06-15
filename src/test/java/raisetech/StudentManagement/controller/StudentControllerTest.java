package raisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    when(service.searchStudentList()).thenReturn(List.of(new StudentDetail()));
    mockMvc.perform(get("/studentList"))

        .andExpect(status().isOk())
        .andExpect(content().json("[{\"student\":null,\"studentCourseList\":null}]"));
    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力した時に入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("テストです。");
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setArea("奈良県");
    student.setSex("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いた時に入力チェックに掛かること() {
    Student student = new Student();
    student.setId("テストです。");
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setArea("奈良県");
    student.setSex("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    //これは、student っていうオブジェクトに対して、バリデーションを実行してる。
    //つまり「@NotBlank」とか「@Pattern」で決めたルールに違反してないかをチェック！

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("数字のみ入力するようにしてください");
    //JUnitの実行ログ or IDE上のテスト結果。
    //「バリデーション設定したけど、ちゃんと動いてるか？」の確認用
  }

  @Test
  void 受講生一覧が正常に取得できること() throws Exception {
    when(service.searchStudentList()).thenReturn(List.of());
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk());
  }

  @Test
  void 個別の受講生が正常に取得できること() throws Exception {
    StudentDetail dummy = new StudentDetail();
    when(service.searchStudent("123")).thenReturn(dummy);

    mockMvc.perform(get("/student/123"))
        .andExpect(status().isOk());
  }

  @Test
  void 登録APIが正常に動作すること() throws Exception {
    StudentDetail dummy = new StudentDetail();
    when(service.registerStudent(any())).thenReturn(dummy);

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(dummy);

    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk());
  }

  @Test
  void 更新APIが正常に動作すること() throws Exception {
    doNothing().when(service).updateStudent(any());

    StudentDetail dummy = new StudentDetail();
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(dummy);

    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk());
  }

  @Test
  void testExceptionエンドポイントで500エラーが返ること() throws Exception {
    mockMvc.perform(get("/testException"))
        .andExpect(status().isInternalServerError());
  }
}
