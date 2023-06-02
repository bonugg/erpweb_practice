package com.example.testproj.controller;

import com.example.testproj.Clazz.Approval.Vacation;
import com.example.testproj.repository.CalendarRepository;
import com.example.testproj.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class VacationController {
    @Autowired
    private HttpSession httpSession;
    private final VacationRepository vacationRepository;
    private final CalendarRepository calendarRepository;

    @GetMapping("/vacation")
    public String vacation(Model model) {
        return "Approval/vacationPage";
    }

    @PostMapping("/vacation/add")
    public String vacationadd(Vacation vacation) {
        vacation.setTITLE(vacation.getTITLE().concat(" " + vacation.getVACATIONTYPE()));
        vacationRepository.save(vacation);
        return "redirect:/";
    }

    @GetMapping("/vacationAcessView")
    public String vacationAcessView(@RequestParam("VNO") long VNO, Model model) {
        Object userVacation = vacationRepository.findByVNOView(VNO);
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("userList", userVacation); // Map에 데이터를 직접 담음
        model.addAttribute("templateData", templateData);
        return "Approval/vacationAcessViewPage";
    }
}
