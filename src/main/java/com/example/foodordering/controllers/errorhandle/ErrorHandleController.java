package com.example.foodordering.controllers.errorhandle;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@Hidden
public class ErrorHandleController implements ErrorController {

    @RequestMapping("/error")
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ResponseBody
    public String handleError(HttpServletRequest request) {
        return "Resource not found";
    }

}
