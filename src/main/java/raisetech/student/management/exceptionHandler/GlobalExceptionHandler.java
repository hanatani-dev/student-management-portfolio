package raisetech.student.management.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice//@ControllerAdvice をつけると、アプリ全体の例外を拾える共通ハンドラーになる！
public class GlobalExceptionHandler {

  @ExceptionHandler(TestException.class)
  public ResponseEntity<String> handleTestException(TestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
// 他の共通例外（例：NullPointerExceptionとか）もここでまとめて処理できる！
