package com.example.testproj.controller;

import com.example.testproj.Clazz.Approval.Business;
import com.example.testproj.Clazz.Approval.Meeting;
import com.example.testproj.repository.BusinessRepository;
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
public class BusinessController {
    @Autowired
    private HttpSession httpSession;
    private final BusinessRepository businessRepository;
    private final CalendarRepository calendarRepository;

    @GetMapping("/business")
    public String meeting() {
        return "businessPage";
    }

    @PostMapping("/business/add")
    public String businessadd(Business business) {
        businessRepository.save(business);
        return "redirect:/";
    }

    @GetMapping("/businessAcessView")
    public String vacationAcessView(@RequestParam("VNO") long VNO, Model model) {
        Object userBusiness = businessRepository.findByVNOView(VNO);
        Map<String, Object> businessData = new HashMap<>();
        businessData.put("userBusiness", userBusiness); // Map에 데이터를 직접 담음
        model.addAttribute("businessData", businessData);
        return "businessAcessViewPage";
    }
}
