package com.equp.back.backend.controller;

import com.equp.back.backend.model.Experience;
import com.equp.back.backend.model.TestResult;
import com.equp.back.backend.model.User;
import com.equp.back.backend.repository.RoleRepository;
import com.equp.back.backend.security.jwt.JwtTokenProvider;
import com.equp.back.backend.service.ExperienceService;
import com.equp.back.backend.service.TestresultService;
import com.equp.back.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;


@RestController
@RequestMapping(value = "/api/v1/user")
@Slf4j
public class UserController {


    private final UserService userService;
    private final ExperienceService experienceService;
    private final TestresultService testresultService;
    private final JavaMailSender emailSender;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @Autowired
    public UserController(UserService userService, ExperienceService experienceService, TestresultService testresultService, JavaMailSender emailSender, RoleRepository roleRepository, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.experienceService = experienceService;
        this.testresultService = testresultService;
        this.emailSender = emailSender;
        this.roleRepository = roleRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    /**
     * Регистрация нового пользователя
     *
     * @param username
     * @param email
     * @param password
     * @return
     */





    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestParam(name = "username") String username,
                                    @RequestParam(name = "email") String email,
                                    @RequestParam(name = "password") String password) {


        JSONObject responseObject = new JSONObject();


        if (userService.findByEmail(email) != null) {
            responseObject.put("codeResponse", 412);
            responseObject.put("message", "Пользователь с email: " + email + " уже существует");
            System.out.print(responseObject.toString());
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.PRECONDITION_FAILED);
        } else {
            String encryptedPassword = jwtTokenProvider.passwordEncoder().encode(password);
            log.info("is password valid " + new BCryptPasswordEncoder().matches(password, encryptedPassword));
            log.info("correctPassword " + encryptedPassword);
            User user = new User(username, email, password);
            user.setFirstName(username);
            user.setLastName(username);
            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
            Date date = new Date(System.currentTimeMillis());
            user.setCreated(date);
            user.setUpdated(date);
            userService.create(user);
            log.info(responseObject.toString());

            Experience tempExperience = new Experience(user.getId());
            experienceService.create(tempExperience);
            log.info("опыт для " + user.getName() + " создан");

            TestResult testresult = new TestResult(user.getId());
            testresultService.create(testresult);
            log.info("результат теста для" + user.getName() + " создан");

            String token = jwtTokenProvider.createToken(email, user.getRoles());

            responseObject.put("id", user.getId());
            responseObject.put("token", token);
            responseObject.put("message", "пользователь с email " + email + " создан");
            responseObject.put("codeResponse", 201);
            responseObject.put("user", user);
            responseObject.put("experience", experienceService.findByUserId(user.getId()));
            responseObject.put("testResult", testresultService.findByUserId(user.getId()));
        }
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.CREATED);
    }

    /**
     * Обновление пароля в приложении
     *
     * @param id
     * @return
     */

    @PostMapping(value = "/update-by-app")
    public ResponseEntity<?> userUpdate(@RequestParam(value = "id", defaultValue = "-1") Long id,
                                        @RequestParam(value = "newPassword", defaultValue = "-1") String newPassword) {

        JSONObject responseObject = new JSONObject();
        Experience experience = experienceService.findByUserId(id);
        TestResult testResult = testresultService.findByUserId(id);
        User user = userService.findById(id);

        if (experience == null || user == null || testResult == null) {
            responseObject.put("codeResponse", 404);
            responseObject.put("message", "Запись о пользователе не найдена или не корректный запрос");
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);

        } else
            user.setEmail(userService.findById(id).getEmail());
        user.setName(userService.findById(id).getName());
        String newToken = jwtTokenProvider.createToken(userService.findById(id).getEmail(), user.getRoles());
        String encryptedPassword = jwtTokenProvider.passwordEncoder().encode(newPassword);
        userService.update(userService.findById(id), encryptedPassword);
        {

            responseObject.put("id", id);
            responseObject.put("token", newToken);
            responseObject.put("message", "Пароль обновлен");
            responseObject.put("codeResponse", 302);
            responseObject.put("user", user);
            responseObject.put("experience", experience);
            responseObject.put("testResult", testResult);
            log.info(responseObject.toString());

            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.ACCEPTED);

        }
    }

    /**
     * Обновление имени пользователя в приложении
     *
     * @param id      идентификатор пользователя
     * @param newName новое имя пользователя
     * @return возвращает пользователя, его опыт и его результаты тестов
     */
    @PostMapping(value = "/update-by-app-name")
    public ResponseEntity<?> userNameUpdate(@RequestParam(value = "id", defaultValue = "-1") Long id,
                                            @RequestParam(value = "newName", defaultValue = "-1") String newName) {
        JSONObject responseObject = new JSONObject();
        Experience experience = experienceService.findByUserId(id);
        TestResult testResult = testresultService.findByUserId(id);
        User user = userService.findById(id);
        if (experience == null || user == null || testResult == null) {
            responseObject.put("codeResponse", 404);
            responseObject.put("message", "Запись о пользователе не найдена или не корректный запрос");
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);

        } else {
            String newToken = jwtTokenProvider.createToken(userService.findById(id).getEmail(), user.getRoles());
            userService.updateName(user, newName);
            responseObject.put("id", id);
            responseObject.put("token", newToken);
            responseObject.put("message", "Имя поьзователя обновлено");
            responseObject.put("codeResponse", 302);
            responseObject.put("user", user);
            responseObject.put("experience", experience);
            responseObject.put("testResult", testResult);
            System.out.println(responseObject);
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.ACCEPTED);
        }
    }


    /**
     * Обновление пароля с помощью отправки по email
     *
     * @param email
     * @return
     * @throws MessagingException
     */
    @PostMapping(value = "/update-by-mail")
    public ResponseEntity<?> updateByEmail(@RequestParam(name = "email") String email) throws MessagingException {
        JSONObject responseObject = new JSONObject();

        User user = userService.findByEmail(email);
        if (user == null) {
            responseObject.put("codeResponse", 404);
            responseObject.put("message", "Пользователь с такими email не найден");
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);
        } else {

            responseObject.put("codeResponse", 202);
            responseObject.put("message", "На email: " + email + " отправлена информация о изменении пароля");


            MimeMessage message = emailSender.createMimeMessage();
            boolean multipart = true;
            MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
            String htmlMsg = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.=" +
                    "w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
                    "<html style=3D\"width:100%;font-family:arial, 'helvetica neue', helvetica, s=" +
                    "ans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0=" +
                    ";Margin:0\">" +
                    "<head>" +
                    "<meta http-equiv=3D\"Content-Security-Policy\" content=3D\"script-src 'non=" +
                    "e'; connect-src 'none'; object-src 'none'; form-action 'none';\">" +
                    "    <meta charset=3D\"UTF-8\">\n" +
                    "    <meta content=3D\"width=3Ddevice-width, initial-scale=3D1\" name=3D\"viewp=" +
                    "ort\">" +
                    "    <meta name=3D\"x-apple-disable-message-reformatting\">\n" +
                    "    <meta http-equiv=3D\"X-UA-Compatible\" content=3D\"IE=3Dedge\">\n" +
                    "    <meta content=3D\"telephone=3Dno\" name=3D\"format-detection\">" +
                    "<style type=\"text/css\">"+

                    "body {"+
                    "font-family: Circle, sans-serif;"+
                    "font-size: 80%"+
                    "}"+

                    "h2{"+
                    "color: #4572CC;"+
                    "font-size: 150%"+

                    "}"+

                    "</style>"+

                    "<title>Смена пароля от приложения EQup</title>" +
                    "</head>" +
                    "<body>" +
                    "<h2>Восстановление пароля для EQup</h2>"+
                    "<h2> </h2>"+

                    "<h4>Здравствуйте, " + user.getFirstName() + "!</h4> \n" +
                    "</br>\n"+
                    "<h4>Вы получили это письмо потому, что Вы (либо кто-то, выдающий себя за Вас) " +
                    "</br>\n"+
                    "при попытке входа в учетную запись EQup отправил запрос на изменение пароля. " +
                    "</h4>" +
                    "</br>\n"+
                    "<h4>Если Вы этого не делали, то не обращайте внимания на это письмо. " +
                    "</br>\n"+
                    "В случае повторного получения подобных писем - обратитесь в нашу поддерку " +
                    "</br>\n"+
                    "по адресу support@eq-up.ru" +
                    "</h4>" +

                    "<h4><a href=\"https://www.eq-up.ru:8443/password_change?email=" + user.getEmail() +
                    "&name=" + user.getName() + "&id=" + user.getId() + "\">Сменить пароль</a></h4>" +
                    "</br>" +
                    "</br>" +
                    "<h4>-------------------------</h4>" +
                    "</br>" +
                    "<h4>Ваша команда EQup</h4>" +
                    "</body>" +
                    "</html>";
            message.setContent(htmlMsg, "text/html; charset=utf-8");


            helper.setText(htmlMsg);
            helper.setFrom("no-reply@eq-up.ru");
            helper.setTo(user.getEmail());
            helper.setSubject("Изменение пароля от EQup");
            this.emailSender.send(message);


        }

        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.ACCEPTED);

    }

    /**
     * удаление пользователя
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam(value = "id", defaultValue = "-1") Long id) {
        JSONObject responseObject = new JSONObject();
        User user = userService.findById(id);
        if (user == null) {
            responseObject.put("codeResponse", 404);
            responseObject.put("message", "Запись о пользователе не найдена");
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);

        } else {
            userService.delete(user.getId());
            experienceService.delete(user.getId());
            responseObject.put("codeResponse", 202);
            responseObject.put("message", "Пользователь удален");
        }
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.ACCEPTED);

    }


    /**
     * Обновление записи пользователя
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/user-update")
    public ResponseEntity<?> userUpdate(@RequestParam(value = "id", defaultValue = "-1") Long id,
                                        @RequestParam(value = "experienceStartLocation", defaultValue = "-1") double experienceStartLocation,
                                        @RequestParam(value = "experienceMindfulness", defaultValue = "-1") double experienceMindfulness,
                                        @RequestParam(value = "experienceAttitudes", defaultValue = "-1") double experienceAttitudes,
                                        @RequestParam(value = "experienceSelfRegulation", defaultValue = "-1") double experienceSelfRegulation,
                                        @RequestParam(value = "experienceEmpathy", defaultValue = "-1") double experienceEmpathy,
                                        @RequestParam(value = "testResultStartLocation", defaultValue = "-1") double testResultStartLocation,
                                        @RequestParam(value = "testResultMindfulness", defaultValue = "-1") double testResultMindfulness,
                                        @RequestParam(value = "testResultAttitudes", defaultValue = "-1") double testResultAttitudes,
                                        @RequestParam(value = "testResultSelfRegulation", defaultValue = "-1") double testResultSelfRegulation,
                                        @RequestParam(value = "testResultEmpathy", defaultValue = "-1") double testResultEmpathy) {

        JSONObject responseObject = new JSONObject();
        Experience experience = experienceService.findByUserId(id);
        TestResult testResult = testresultService.findByUserId(id);
        User user = userService.findById(id);

        if (experience == null || user == null || testResult == null) {
            responseObject.put("codeResponse", 404);
            responseObject.put("message", "Запись о пользователе не найдена");
            log.info(responseObject.toString());
            return new ResponseEntity<>(responseObject.toMap(), HttpStatus.NOT_FOUND);

        } else

            user.setEmail(userService.findById(id).getEmail());
        user.setName(userService.findById(id).getName());
        {

            if (experienceStartLocation > experience.getStartLocation()) {
                experience.setStartLocation(experienceStartLocation);
            }
            if (experienceMindfulness > experience.getMindfulness()) {
                experience.setMindfulness(experienceMindfulness);
            }
            if (experienceAttitudes > experience.getAttitudes()) {
                experience.setAttitudes(experienceAttitudes);
            }
            if (experienceSelfRegulation > experience.getSelfRegulation()) {
                experience.setSelfRegulation(experienceSelfRegulation);
            }
            if (experienceEmpathy > experience.getEmpathy()) {
                experience.setEmpathy(experienceEmpathy);
            }


            if (testResultStartLocation > experience.getStartLocation()) {
                testResult.setStartLocation(testResultStartLocation);
            }
            if (testResultMindfulness > testResult.getMindfulness()) {
                testResult.setMindfulness(testResultMindfulness);
            }
            if (testResultAttitudes > testResult.getAttitudes()) {
                testResult.setAttitudes(testResultAttitudes);
            }
            if (testResultSelfRegulation > testResult.getSelfRegulation()) {
                testResult.setSelfRegulation(testResultSelfRegulation);
            }
            if (testResultEmpathy > testResult.getEmpathy()) {
                testResult.setEmpathy(testResultEmpathy);
            }
            String newToken = jwtTokenProvider.createToken(userService.findById(id).getEmail(), user.getRoles());
            experienceService.update(experience);
            log.info(experience.toString());
            testresultService.update(testResult);
            log.info(testResult.toString());
            log.info(user.toString());
            responseObject.put("id", id);
            responseObject.put("token", newToken);
            responseObject.put("message", "данные пользователя обновлены");
            responseObject.put("codeResponse", 302);
            responseObject.put("user", user);
            responseObject.put("experience", experience);
            responseObject.put("testResult", testResult);
            log.info(responseObject.toString());
        }
        return new ResponseEntity<>(responseObject.toMap(), HttpStatus.ACCEPTED);

    }

    @PutMapping(value = "/")
    public ResponseEntity<?> inits() {

        int count = 10000000;

        for (int i = 0; i < count; i++) {

            String username = "name"+i;
            String email = username+"@mail.ru";
            String password = "12345";

            JSONObject responseObject = new JSONObject();


            if (userService.findByEmail(email) != null) {
                responseObject.put("codeResponse", 412);
                responseObject.put("message", "Пользователь с email: " + email + " уже существует");
                System.out.print(responseObject.toString());
                log.info(responseObject.toString());
                return new ResponseEntity<>(responseObject.toMap(), HttpStatus.PRECONDITION_FAILED);
            } else {
                String encryptedPassword = jwtTokenProvider.passwordEncoder().encode(password);
                log.info("is password valid " + new BCryptPasswordEncoder().matches(password, encryptedPassword));
                log.info("correctPassword " + encryptedPassword);
                User user = new User(username, email, password);
                user.setFirstName(username);
                user.setLastName(username);
                user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
                Date date = new Date(System.currentTimeMillis());
                user.setCreated(date);
                user.setUpdated(date);
                userService.create(user);
                log.info(responseObject.toString());

                Experience tempExperience = new Experience(user.getId());
                experienceService.create(tempExperience);
                log.info("опыт для " + user.getName() + " создан");

                TestResult testresult = new TestResult(user.getId());
                testresultService.create(testresult);
                log.info("результат теста для" + user.getName() + " создан");

                String token = jwtTokenProvider.createToken(email, user.getRoles());

                responseObject.put("id", user.getId());
                responseObject.put("token", token);
                responseObject.put("message", "пользователь с email " + email + " создан");
                responseObject.put("codeResponse", 201);
                responseObject.put("user", user);
                responseObject.put("experience", experienceService.findByUserId(user.getId()));
                responseObject.put("testResult", testresultService.findByUserId(user.getId()));
                System.out.println(username + " create");
            }
        }
        return new ResponseEntity<>(count, HttpStatus.CREATED);
    }


}