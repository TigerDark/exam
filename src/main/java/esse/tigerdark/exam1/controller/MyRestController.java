package esse.tigerdark.exam1.controller;

import esse.tigerdark.exam1.service.RestService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/connect")
public class MyRestController {

    private final RestService restService;

    public MyRestController(RestService restService) {
        this.restService = restService;
    }

    @GetMapping
    public ResponseEntity<String> getConnection(HttpServletRequest request) {
        return ResponseEntity.ok(restService.getConnection(request));
    }
}
