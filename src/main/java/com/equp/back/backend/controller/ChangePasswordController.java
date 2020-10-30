package com.equp.back.backend.controller;

import com.equp.back.backend.model.User;
import com.equp.back.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Controller
@Slf4j
public class ChangePasswordController {

    private final UserService userService;
    private final JavaMailSender emailSender;


    @Autowired
    public ChangePasswordController(UserService userService, JavaMailSender emailSender) {
        this.userService = userService;
        this.emailSender = emailSender;
    }


    @GetMapping("/api/v1/password_change")
    public String changePassword(Model model, @RequestParam(value = "email", required = false) String email,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "id", required = false) Long id){
        log.info("Пользователь "+id+" с email: "+email+" зашел на страницу сброса пароля");
        model.addAttribute("email", email);
        model.addAttribute("name", name);
        model.addAttribute("id", id);
        model.addAttribute("message","");
        return "change_password_page";
    }
    @PostMapping("/api/v1/password_change")
    public String changePasswordOperation(Model model, @RequestParam(value = "password", required = false) String password,
                                          @RequestParam(value = "password_repeat", required = false) String passwordRepeat,
                                          @RequestParam(value = "id", required = false) Long id,
                                          @RequestParam(value = "email", required = false) String email,
                                          @RequestParam(value = "name", required = false) String name) throws MessagingException {

        User user = userService.findById(id);

        if (password.equals(passwordRepeat)){

            userService.update(user,password);

            MimeMessage message = emailSender.createMimeMessage();
            boolean multipart = true;
            MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
            String htmlMsg = "<!DOCTYPE html>"+
                    "<html lang=\"ru\">"+
                    "<head>"+
                    "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />"+
                    "<title>Title</title>"+
                    "</head>"+
                    "<body>"+
                    "<p>Password was changed successfully</p>"+
                    "</body>"+
                    "</html>";

            message.setContent(htmlMsg, "text/html");
            helper.setTo(user.getEmail());
            helper.setSubject("Password was changed successfully");
            this.emailSender.send(message);

            return "password_changed_successfully";
        }else if (!password.equals(passwordRepeat)){
            model.addAttribute("email", email);
            model.addAttribute("name", name);
            model.addAttribute("id", id);
            model.addAttribute("message","Пароли не сходятся");
        }

        return "change_password_page";

    }
}