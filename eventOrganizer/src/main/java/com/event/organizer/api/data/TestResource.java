package eventOrganizer.src.main.java.com.event.organizer.api.data;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/test")
public class TestResource {

    @ApiOperation(value = "Returns a hardcoded test")
    @GetMapping
    public String test() {
        return "Testing";
    }

    @ApiOperation(value = "Returns a hardcoded test")
    @PostMapping("/post")
    public String testAdd(@RequestBody final String test) {
        return test;
    }

    @ApiOperation(value = "Returns a hardcoded test")
    @PutMapping("/put")
    public String testPut(@RequestBody final String test) {
        return test;
    }
}
