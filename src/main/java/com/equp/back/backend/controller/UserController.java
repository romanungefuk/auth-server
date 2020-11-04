package com.equp.back.backend.controller;

import com.equp.back.backend.model.Experience;
import com.equp.back.backend.model.JWT;
import com.equp.back.backend.model.TestResult;
import com.equp.back.backend.model.User;
import com.equp.back.backend.service.ExperienceService;
import com.equp.back.backend.service.TestresultService;
import com.equp.back.backend.service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private final TestresultService testresultService;
    private final JavaMailSender emailSender;
    JWT token = new JWT(); // временный токен


    @Autowired
    public UserController(UserService userService, ExperienceService experienceService, TestresultService testresultService, JavaMailSender emailSender) {
        this.userService = userService;
        this.experienceService = experienceService;
        this.testresultService = testresultService;
        this.emailSender = emailSender;
    }


    /**Регистрация нового пользователя
     *
     * @param username
     * @param email
     * @param password
     * @return
     */
    @PostMapping(value = "/api/v1/signup")
    public ResponseEntity<?> signup(@RequestParam (name = "username")String username,
                                    @RequestParam (name = "email")String email,
                                    @RequestParam (name = "password")String password) {


        JSONObject responseObject = new JSONObject();


        if (userService.findByEmail(email)!=null) {
            responseObject.put("codeResponse", 412);
            responseObject.put("message", "Пользователь с email: " + email + " уже существует");
            System.out.print(responseObject.toString());
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.PRECONDITION_FAILED);
            }
        else{
            User user = new User(username, email, password);
            userService.create(user);
            log.info(responseObject.toString());

            Experience tempExperience = new Experience(user.getId());
            experienceService.create(tempExperience);
            log.info("опыт для " + user.getName() + " создан");

            TestResult testresult = new TestResult(user.getId());
            testresultService.create(testresult);
            log.info("результат теста для" + user.getName() + " создан");

            JWT token = new JWT();

            responseObject.put("id", user.getId());
            responseObject.put("token", token.getToken());
            responseObject.put("message", "пользователь с email " + email + " создан");
            responseObject.put("codeResponse", 201);
            responseObject.put("user", user);
            responseObject.put("experience", experienceService.findByUserId(user.getId()));
            responseObject.put("testResult", testresultService.findByUserId(user.getId()));
            }
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.CREATED);
    }


    /**Авторизация действующего пользователя
     *
     * @param email
     * @param password
     * @return
     */
    @GetMapping(value = "/api/v1/auth")
    public ResponseEntity<?> auth(@RequestParam (name = "email")String email,
                                  @RequestParam (name = "password")String password){

        JSONObject responseObject = new JSONObject();

        User user = userService.findByEmail(email);
        if (user == null) {

            responseObject.put("codeResponse",404);
            responseObject.put("message","Пользователь с такими email не найден");
            log.info(responseObject.toString());

            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);
        }else if (!user.getPassword().equals(password)){

            responseObject.put("codeResponse",401);
            responseObject.put("message","Не верный пароль");
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.UNAUTHORIZED);
        }else {

            responseObject.put("id",user.getId());
            responseObject.put("token", token.getToken());
            responseObject.put("message","Пользователь найден");
            responseObject.put("codeResponse",201);
            responseObject.put("user", user);
            responseObject.put("experience", experienceService.findByUserId(user.getId()));
            responseObject.put("testResult", testresultService.findByUserId(user.getId()));
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.FOUND);
        }

    }

    /**Обновление пароля с помощью отправки по email
     *
     * @param email
     * @return
     * @throws MessagingException
     */
    @PostMapping(value = "/api/v1/update-by-mail")
    public ResponseEntity<?> updateByEmail(@RequestParam (name = "email")String email) throws MessagingException {
        JSONObject responseObject = new JSONObject();

        User user = userService.findByEmail(email);
        if (user == null) {
            responseObject.put("codeResponse", 404);
            responseObject.put("message", "Пользователь с такими email не найден");
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);
        }else {

            responseObject.put("codeResponse",202);
            responseObject.put("message","На email: "+email+" отправлена информация о изменении пароля");


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
//                    "<a href=\"http://localhost:8080/password_change?email="+user.getEmail()+"&name="+user.getName()+"&id="+user.getId()+"\">change password</a>"+
                    "<a href=\"http://www.eq-up.ru:8080/password_change?email="+user.getEmail()+"&name="+user.getName()+"&id="+user.getId()+"\">change password</a>"+
                    "</body>"+
                    "</html>";

            message.setContent(htmlMsg, "text/html");
            helper.setTo(user.getEmail());
            helper.setSubject("Change your EQup password");
            this.emailSender.send(message);


        }
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.ACCEPTED);

    }

    /**удаление пользователя
     *
     * @param id
     * @param token
     * @return
     */
    @DeleteMapping(value = "/api/v1/delete-user")
    public ResponseEntity<?> delete(@RequestParam (value = "id", defaultValue = "-1")Long id,
                                           @RequestParam (value = "token", defaultValue = "-1") String token){
        JSONObject responseObject = new JSONObject();

        User user = userService.findById(id);
//        System.err.println("user = "+user == null);
        System.err.println("token = "+token.equals(this.token.getToken()));
        if (userService.findById(id)==null || !token.equals(this.token.getToken())){
            responseObject.put("codeResponse", 404);
            responseObject.put("message", "Запись о пользователе не найдена или не корректный токен");
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);

        }else{
            userService.delete(user.getId());
            experienceService.delete(user.getId());
            responseObject.put("codeResponse",202);
            responseObject.put("message","Пользователь удален");
        }
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.ACCEPTED);

    }


    /**Обновление записи пользователя
     *
     * @param id
     * @param token
     * @return
     */
    @PostMapping(value = "/api/v1/user-update")
    public ResponseEntity<?> experienceUpdate(@RequestParam (value = "id", defaultValue = "-1")Long id,
                                              @RequestParam (value = "token", defaultValue = "-1") String token,
                                              @RequestParam (value = "experienceMindfulness", defaultValue = "-1")double experienceMindfulness,
                                              @RequestParam (value = "experienceAttitudes", defaultValue = "-1")double experienceAttitudes,
                                              @RequestParam (value = "experienceSelfRegulation", defaultValue = "-1")double experienceSelfRegulation,
                                              @RequestParam (value = "experienceEmpathy", defaultValue = "-1")double experienceEmpathy,
                                              @RequestParam (value = "testResultMindfulness", defaultValue = "-1")double testResultMindfulness,
                                              @RequestParam (value = "testResultAttitudes", defaultValue = "-1")double testResultAttitudes,
                                              @RequestParam (value = "testResultSelfRegulation", defaultValue = "-1")double testResultSelfRegulation,
                                              @RequestParam (value = "testResultEmpathy", defaultValue = "-1")double testResultEmpathy){
    {



        JSONObject responseObject = new JSONObject();
        Experience experience = experienceService.findByUserId(id);
        TestResult testResult = testresultService.findByUserId(id);
        User user = new User();

        if (experience == null || user == null || testResult == null || !token.equals(this.token.getToken())){
                responseObject.put("codeResponse", 404);
                responseObject.put("message", "Запись о пользователе не найдена или не корректный токен");
                log.info(responseObject.toString());
                return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);

        }else

        user.setEmail(userService.findById(id).getEmail());
        user.setName(userService.findById(id).getName());
            {

                if (experienceMindfulness > experience.getMindfulness()){
                    experience.setMindfulness(experienceMindfulness);
                }
                if (experienceAttitudes > experience.getAttitudes()){
                    experience.setAttitudes(experienceAttitudes);
                }
                if (experienceSelfRegulation > experience.getSelfRegulation()){
                    experience.setSelfRegulation(experienceSelfRegulation);
                }
                if (experienceEmpathy > experience.getEmpathy()){
                    experience.setEmpathy(experienceEmpathy);
                }


                if (testResultMindfulness > testResult.getMindfulness()){
                    testResult.setMindfulness(testResultMindfulness);
                }
                if (testResultAttitudes > testResult.getAttitudes()){
                    testResult.setAttitudes(testResultAttitudes);
                }
                if (testResultSelfRegulation > testResult.getSelfRegulation()){
                    testResult.setSelfRegulation(testResultSelfRegulation);
                }
                if (testResultEmpathy > testResult.getEmpathy()){
                    testResult.setEmpathy(testResultEmpathy);
                }

                experienceService.update(experience);
                log.info(experience.toString());
                testresultService.update(testResult);
                log.info(testResult.toString());
                log.info(user.toString());
                responseObject.put("id", id);
                responseObject.put("token", token);
                responseObject.put("message","данные пользователя обновлены");
                responseObject.put("codeResponse",302);
                responseObject.put("user", user);
                responseObject.put("experience", experience);
                responseObject.put("testResult", testResult);
                log.info(responseObject.toString());
            }
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.ACCEPTED);
        }

    }
}