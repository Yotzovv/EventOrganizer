package com.event.organizer.api.filter.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
