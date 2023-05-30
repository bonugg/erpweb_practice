package com.example.testproj.controller;

import com.example.testproj.User.Calendar;
import com.example.testproj.User.SessionUser;
import com.example.testproj.User.User;
import com.example.testproj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {
    @Autowired
    private HttpSession httpSession;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/")
    public String main(Model model) {
        if (httpSession.getAttribute("user") != null) {
            SessionUser user = (SessionUser) httpSession.getAttribute("user");
            model.addAttribute("user", user);
        }
        return "mainPage";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "loginPage";
    }

    @GetMapping("/sginin")
    public String signin() {
        return "signinPage";
    }

    @PostMapping("/sginin_member")
    public String signin_post(User user) {
        user.setPWD(passwordEncoder.encode(user.getPWD()));
        userRepository.save(user);
        return "redirect:/";
    }

    @ResponseBody
    @RequestMapping(value = "/CnoCheck", method = RequestMethod.POST) //아이디 중복체크
    public int CnoCheck(@RequestBody String CNO) throws Exception {
        System.out.println(CNO);
        int count = 0;
        count = userRepository.CnoCheck(Long.parseLong(CNO));
        return count;
    }

    @GetMapping("/vacation")
    public String vacation(Model model) {
        if (httpSession.getAttribute("user") != null) {
            SessionUser user = (SessionUser) httpSession.getAttribute("user");
            model.addAttribute("user", user);
        }
        return "vacationPage";
    }

}
