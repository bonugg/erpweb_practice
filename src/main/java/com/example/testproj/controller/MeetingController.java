package com.example.testproj.controller;

import com.example.testproj.Clazz.Approval.Meeting;
import com.example.testproj.repository.CalendarRepository;
import com.example.testproj.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MeetingController {
    @Autowired
    private HttpSession httpSession;
    private final MeetingRepository meetingRepository;
    private final CalendarRepository calendarRepository;

    @GetMapping("/meeting")
    public String meeting() {
        return "meetingPage";
    }

    @PostMapping("/meeting/add")
    public String meetingadd(Meeting meeting) {
        meetingRepository.save(meeting);
        return "redirect:/";
    }

    @GetMapping("/meetingAcessView")
    public String vacationAcessView(@RequestParam("VNO") long VNO, Model model) {
        Object userVacation = meetingRepository.findByVNOView(VNO);
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("userList", userVacation); // Map에 데이터를 직접 담음
        model.addAttribute("templateData", templateData);
        return "meetingAcessViewPage";
    }
}
