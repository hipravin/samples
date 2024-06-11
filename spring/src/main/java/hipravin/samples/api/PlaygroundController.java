package hipravin.samples.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/playground/")
public class PlaygroundController {

    @GetMapping("/runtime-exception")
    ResponseEntity<?> brokenService() {
        throw new RuntimeException("Something went wrong as expected");
    }

    @GetMapping("/problem-detail")
    ResponseEntity<?> explicitProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.OK, "Ok but problem detail");
        problemDetail.setTitle("Problem Detail title");
        problemDetail.setType(URI.create("tag:example@example.org,2021-09-17:OutOfLuck"));
        return ResponseEntity.ok(problemDetail);
    }
}
