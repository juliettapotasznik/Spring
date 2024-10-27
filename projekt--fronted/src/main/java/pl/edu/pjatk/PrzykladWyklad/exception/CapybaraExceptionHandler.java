package pl.edu.pjatk.PrzykladWyklad.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CapybaraExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CapybaraNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(RuntimeException exception, WebRequest request)
    {
        return ResponseEntity.notFound().build();
    }
@ExceptionHandler({CapybaraAlreadyExistException.class, CapybaraWrongAgeException.class})
protected ResponseEntity<String> handleBadReequest(RuntimeException excepetion, WebRequest request)
{
    return ResponseEntity.badRequest().body(excepetion.getMessage());
}
@ExceptionHandler(ArithmeticException.class)
protected ResponseEntity<String> handleDivideByZero(RuntimeException exception,WebRequest request)
{
    return ResponseEntity.badRequest().body("Sorry, no division by zero!");
}
}



