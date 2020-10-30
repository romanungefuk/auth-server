package com.equp.back.backend.controller;

import com.equp.back.backend.model.Experience;
import com.equp.back.backend.model.User;
import com.equp.back.backend.service.ExperienceService;
import com.equp.back.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@RestController
@Slf4j
public class UserController {

    private final UserService userService;
    private final ExperienceService experienceService;
    private final JavaMailSender emailSender;


    @Autowired
    public UserController(UserService userService, ExperienceService experienceService, JavaMailSender emailSender) {
        this.userService = userService;
        this.experienceService = experienceService;
        this.emailSender = emailSender;
    }

    @PostMapping(value = "/api/v1/signup")
    public ResponseEntity<?> signup(@RequestParam (name = "username")String username,
                                    @RequestParam (name = "email")String email,
                                    @RequestParam (name = "password")String password) {


        JSONObject responseObject = new JSONObject();

            boolean b = userService.findByEmail(email)!=null;
            if (b) {

                responseObject.put("status", 412);
                responseObject.put("message", "Пользователь с email: " + email + " уже существует");
                responseObject.put("id", -1);
                System.out.print(responseObject.toString());
                log.info(responseObject.toString());
                return new ResponseEntity<>(responseObject.toMap(), HttpStatus.PRECONDITION_FAILED);
            }


        User user = new User(username,email,password);
        userService.create(user);
        responseObject.put("status",201);
        responseObject.put("message", "пользователь с email "+email+" создан");
        responseObject.put("id",user.getId());
        System.out.print(responseObject.toString());
        Experience ttt = new Experience(user.getId());
        log.info("опыт создан");
        experienceService.create(ttt);
        log.info(responseObject.toString());
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.CREATED);

    }

    @GetMapping(value = "/api/v1/auth")
    public ResponseEntity<?> read(@RequestParam (name = "email")String email,
                                    @RequestParam (name = "password")String password){

        JSONObject responseObject = new JSONObject();

        User user = userService.findByEmail(email);
        if (user == null) {

            responseObject.put("status",404);
            responseObject.put("message","Пользователь с такими email не найден");
            responseObject.put("id", -1);
            responseObject.put("name", "null");
            responseObject.put("email", "null");
            responseObject.put("experience", new Experience());
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);
        }else if (!user.getPassword().equals(password)){
            responseObject.put("status",401);
            responseObject.put("message","Не верный пароль");
            responseObject.put("id", -1);
            responseObject.put("name", user.getName());
            responseObject.put("email", user.getEmail());
            responseObject.put("experience", experienceService.findByUserId(user.getId()));
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.UNAUTHORIZED);
        }else {
            responseObject.put("status",302);
            responseObject.put("message","Пользователь найден");
            responseObject.put("id", user.getId());
            responseObject.put("name", user.getName());
            responseObject.put("email", user.getEmail());
            responseObject.put("experience", experienceService.findByUserId(user.getId()));
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.FOUND);
        }

    }

    @PostMapping(value = "/api/v1/update")
    public ResponseEntity<?> delete(@RequestParam (name = "email")String email) throws MessagingException {
        JSONObject responseObject = new JSONObject();

        User user = userService.findByEmail(email);
        if (user == null) {
            responseObject.put("status", 404);
            responseObject.put("message", "Пользователь с такими email не найден");
            responseObject.put("id", -1);
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);
        }else {

            responseObject.put("status",202);
            responseObject.put("message","На email: "+email+" отправлена информация о изменении пароля");
            responseObject.put("id", -1L);


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
                    "<h3>To change your password, follow the link:</h3>"+
//                    "<a href=\"http://localhost:8080/api/v1/password_change?email="+user.getEmail()+"&name="+user.getName()+"&id="+user.getId()+"\">change password</a>"+
                    "<a href=\"http://www.eq-up.ru:8080/api/v1/password_change?email="+user.getEmail()+"&name="+user.getName()+"&id="+user.getId()+"\">change password</a>"+
                    "</body>"+
                    "</html>";

            message.setContent(htmlMsg, "text/html");
            helper.setTo(user.getEmail());
            helper.setSubject("Change your EQup password");
            this.emailSender.send(message);


        }
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.ACCEPTED);

    }

    @PostMapping(value = "/api/v1/delete")
    public ResponseEntity<?> delete(@RequestParam (name = "email")String email,
                                  @RequestParam (name = "password")String password){
        JSONObject responseObject = new JSONObject();
        User user = userService.findByEmail(email);
        if (user == null) {
            responseObject.put("status", 404);
            responseObject.put("message", "Пользователь с такими email не найден");
            responseObject.put("id", -1);
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);
        }else if (!user.getPassword().equals(password)) {
            responseObject.put("status", 401);
            responseObject.put("message", "Не верный пароль");
            responseObject.put("id", -1);

            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.UNAUTHORIZED);
        }else{
            userService.delete(user.getId());
            experienceService.delete(user.getId());
            responseObject.put("status",202);
            responseObject.put("message","Пользователь удален");
            responseObject.put("id", -1L);
        }
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.ACCEPTED);

    }

    @PostMapping(value = "/api/v1/experience-update")
    public ResponseEntity<?> experienceUpdate(@RequestParam (name = "id")Long id,
                                              @RequestParam (name = "all_experience")double allExperience,
                                              @RequestParam (name = "mindfulness")double mindfulness,
                                              @RequestParam (name = "attitudes")double attitudes,
                                              @RequestParam (name = "selfregulation")double selfregulation,
                                              @RequestParam (name = "empathy")double empathy){
        JSONObject responseObject = new JSONObject();
        Experience experience = experienceService.findByUserId(id);
        if (experience == null){
                responseObject.put("status", 404);
                responseObject.put("message", "Запись о пользователе с такими id ("+id+") не найдена");
                responseObject.put("id", -1);
                log.info(responseObject.toString());
                return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);
        }else{
        experience.setAllExperience(allExperience);
        experience.setMindfulness(mindfulness);
        experience.setAttitudes(attitudes);
        experience.setSelfRegulation(selfregulation);
        experience.setEmpathy(empathy);
        experienceService.update(experience);
            responseObject.put("status",302);
            responseObject.put("id", id);
            responseObject.put("experience", experience);
            log.info(responseObject.toString());
        }
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.ACCEPTED);
    }

}

