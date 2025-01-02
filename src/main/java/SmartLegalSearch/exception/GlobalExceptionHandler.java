package SmartLegalSearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/*
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * @ControllerAdvice: 用來處理所有異常
 * @ResponseBody: 將返回結果轉為 JSON 格式
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
     * @ExceptionHandler: 用來指定當捕獲到的特定異常，如 BindException
     * BindException: 所有Spring Boot 中，有使用@註解標示的錯誤
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(BindException e) {
        // 建立 Map 儲存@註解的錯誤欄位和訊息
        Map<String, String> errors = new HashMap<>();
        // getBindingResult() 取得所有錯誤的物件，getAllErrors() 則是將不同錯誤轉成 List
        e.getBindingResult().getAllErrors().forEach(error -> {
           String fieldName = ((FieldError) error).getField();
           String errorMessage = error.getDefaultMessage();
           errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
