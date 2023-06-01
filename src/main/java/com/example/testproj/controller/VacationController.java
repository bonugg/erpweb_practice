package com.example.testproj.controller;

import com.example.testproj.User.Calendar;
import com.example.testproj.User.SessionUser;
import com.example.testproj.User.User;
import com.example.testproj.User.UserVacation;
import com.example.testproj.repository.CalendarRepository;
import com.example.testproj.repository.UserRepository;
import com.example.testproj.repository.UserVacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class VacationController {
    @Autowired
    private HttpSession httpSession;
    private final UserVacationRepository userVacationRepository;
    private final CalendarRepository calendarRepository;

    @GetMapping("/vacation")
    public String vacation(Model model) {
        if (httpSession.getAttribute("user") != null) {
            SessionUser suser = (SessionUser) httpSession.getAttribute("user");
            User user = new User(suser);
            model.addAttribute("user", user);
        }
        return "vacationPage";
    }

    @PostMapping("/vacation/add")
    public String vacationadd(UserVacation userVacation){
        userVacation.setTITLE(userVacation.getTITLE().concat(" "+userVacation.getVACATIONTYPE()));
        userVacationRepository.save(userVacation);
        return "redirect:/";
    }

    @GetMapping("/vacationAcess")
    public String vacationAccess(Model model) {
        if (httpSession.getAttribute("user") != null) {
            SessionUser user = (SessionUser) httpSession.getAttribute("user");
            model.addAttribute("user", user);
            List<Object[]> userVacationList = userVacationRepository.findByDeptList(user.getDEPT());
            List<Object> firstList = new ArrayList<>();
            List<Object> secondList = new ArrayList<>();

            userVacationList.forEach(v -> {
                firstList.add(v[0]);
                secondList.add(v[1]);
            });

            model.addAttribute("userVacation", firstList);
            model.addAttribute("user", secondList);
        }
        return "vacationAcessPage";
    }

    @GetMapping("/vacationAcessView")
    public String vacationAcessView(@RequestParam("VNO") long VNO, Model model) {
        if (httpSession.getAttribute("user") != null) {
            SessionUser user = (SessionUser) httpSession.getAttribute("user");
            model.addAttribute("user", user);
            Object userVacation = userVacationRepository.findByVNOView(VNO);
            Object[] arr = (Object[]) userVacation;

            model.addAttribute("userVacation", arr[0]);
            model.addAttribute("user", arr[1]);
        }
        return "vacationAcessViewPage";
    }

    @GetMapping("/Access")
    public String Access(@RequestParam("VNO") long VNO ,@RequestParam("DEPT") String DEPT ,Model model) {
        if (httpSession.getAttribute("user") != null) {
            SessionUser suser = (SessionUser) httpSession.getAttribute("user");
            model.addAttribute("user", suser);
            List<UserVacation> userVacationList = userVacationRepository.findAll();
            model.addAttribute("userVacation", userVacationList);
            UserVacation userVacation = userVacationRepository.findByVNO(VNO);
            userVacation.setAccessva("승인");
            userVacationRepository.save(userVacation);
            Calendar calendar = new Calendar(userVacation.getTITLE(), userVacation.getDESCRIPTION(),
                    userVacation.getStart(),userVacation.getEnd(),DEPT,
                    userVacation.getCLASSIFY(),userVacation.getVACATIONTYPE(),userVacation.getVNO(),
                    userVacation.getUser());
            calendarRepository.save(calendar);
        }
        return "redirect:/vacationAcess";
    }
    @GetMapping("/Cancle")
    public String Cancle(@RequestParam("VNO") long VNO ,@RequestParam("DEPT") String DEPT , Model model) {
        if (httpSession.getAttribute("user") != null) {
            SessionUser user = (SessionUser) httpSession.getAttribute("user");
            model.addAttribute("user", user);
        }
        List<UserVacation> userVacationList = userVacationRepository.findAll();
        model.addAttribute("userVacation", userVacationList);
        UserVacation userVacation = userVacationRepository.findByVNO(VNO);
        userVacation.setAccessva("반려");
        userVacationRepository.save(userVacation);
        Optional<Calendar> optional = calendarRepository.findByVNO(userVacation.getVNO());
        if (optional.isPresent()) { //데이터가 있으면 실행
            Calendar calendar = optional.get();
            calendarRepository.deleteById(calendar.getCALNO());
            return "redirect:/vacationAcess";
        } else {
            return "redirect:/vacationAcess"; // 적절한 예외 처리 페이지로 리다이렉트
        }
    }
}
