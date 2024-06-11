package hipravin.samples.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    ProblemDetail handleBookmarkNotFoundException(RuntimeException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Internal Error");
        problemDetail.setProperties(Map.of("custom-key", "Custom Value"));

//        problemDetail.setType(URI.create("https://api.bookmarks.com/errors/not-found"));

        //return value of type ProblemDetail is enriched with additional details such as instance implicitly by Spring
        return problemDetail;
    }

}