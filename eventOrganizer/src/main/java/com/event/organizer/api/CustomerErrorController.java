package eventOrganizer.src.main.java.com.event.organizer.api;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    String error (HttpServletRequest request) {
        return "<h1>Error occurred</h1>";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
