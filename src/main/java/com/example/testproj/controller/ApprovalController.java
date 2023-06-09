package com.example.testproj.controller;

import com.example.testproj.Clazz.Approval.Business;
import com.example.testproj.Clazz.Approval.Meeting;
import com.example.testproj.Clazz.Approval.Vacation;
import com.example.testproj.Clazz.User.User;
import com.example.testproj.Clazz.User.SessionUser;
import com.example.testproj.Clazz.calendar.Calendar;
import com.example.testproj.repository.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ApprovalController {
    @Autowired
    private HttpSession httpSession;
    private final UserRepository userRepository;
    private final VacationRepository vacationRepository;
    private final MeetingRepository meetingRepository;
    private final BusinessRepository businessRepository;
    private final CalendarRepository calendarRepository;

    @GetMapping("/managerAcess")
    public String managerAccess(){
        return "Approval/managerAcessPage";
    }

    @RequestMapping(value = "/managerAcessAjax", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map<String, Object>> vacationList(
            @PageableDefault(page = 0, size = 3, sort = "VNO", direction = Sort.Direction.DESC) Pageable pageable,
            Model model){
        SessionUser user1 = (SessionUser) httpSession.getAttribute("user");
        Page<Object[]> userVacationList =  vacationRepository.findByDeptList(user1.getDEPT(), pageable);

        List<Map<String, Object>> mapList =  userVacationList.getContent().stream().map(content -> {
            Vacation vacations = (Vacation) content[0];
            User users = (User) content[1];
            Map<String, Object> map = new HashMap<>();
            map.put("VNO", vacations.getVNO());
            map.put("TITLE", vacations.getTITLE());
            map.put("VACATIONTYPE", vacations.getVACATIONTYPE());
            map.put("start", vacations.getStart());
            map.put("end", vacations.getEnd());
            map.put("DESCRIPTION", vacations.getDESCRIPTION());
            map.put("Accessva", vacations.getAccessva());
            map.put("CLASSIFY", vacations.getCLASSIFY());
            map.put("CANCLEREASON", vacations.getCANCLEREASON());
            map.put("APPROVER", vacations.getAPPROVER());
            map.put("CNO", users.getCNO());
            return map;
        }).collect(Collectors.toList());

        return new PageImpl<>(mapList, pageable, userVacationList.getTotalElements());
    }

    @RequestMapping(value = "/managerMeetingAcessAjax", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map<String, Object>> meetingList(
            @PageableDefault(page = 0, size = 3, sort = "VNO", direction = Sort.Direction.DESC) Pageable pageable,
            Model model){
        SessionUser user1 = (SessionUser) httpSession.getAttribute("user");
        Page<Object[]> userMeetingList =  meetingRepository.findByDeptList(user1.getDEPT(), pageable);

        List<Map<String, Object>> mapList =  userMeetingList.getContent().stream().map(content -> {
            Meeting meeting = (Meeting) content[0];
            User users = (User) content[1];
            Map<String, Object> map = new HashMap<>();
            map.put("VNO", meeting.getVNO());
            map.put("TITLE", meeting.getTITLE());
            map.put("start", meeting.getStart());
            map.put("end", meeting.getEnd());
            map.put("DESCRIPTION", meeting.getDESCRIPTION());
            map.put("Accessva", meeting.getAccessva());
            map.put("CLASSIFY", meeting.getCLASSIFY());
            map.put("CANCLEREASON", meeting.getCANCLEREASON());
            map.put("APPROVER", meeting.getAPPROVER());
            map.put("CNO", users.getCNO());
            map.put("NAME", users.getNAME());
            return map;
        }).collect(Collectors.toList());

        return new PageImpl<>(mapList, pageable, userMeetingList.getTotalElements());
    }

    @RequestMapping(value = "/managerBusinessAcessAjax", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map<String, Object>> BusinessList(
            @PageableDefault(page = 0, size = 3, sort = "VNO", direction = Sort.Direction.DESC) Pageable pageable,
            Model model){
        SessionUser user1 = (SessionUser) httpSession.getAttribute("user");
        Page<Object[]> userBusinessList =  businessRepository.findByDeptList(user1.getDEPT(), pageable);

        List<Map<String, Object>> mapList =  userBusinessList.getContent().stream().map(content -> {
            Business business = (Business) content[0];
            User users = (User) content[1];
            Map<String, Object> map = new HashMap<>();
            map.put("VNO", business.getVNO());
            map.put("TITLE", business.getTITLE());
            map.put("start", business.getStart());
            map.put("end", business.getEnd());
            map.put("DESCRIPTION", business.getDESCRIPTION());
            map.put("Accessva", business.getAccessva());
            map.put("CLASSIFY", business.getCLASSIFY());
            map.put("CANCLEREASON", business.getCANCLEREASON());
            map.put("APPROVER", business.getAPPROVER());
            map.put("CNO", users.getCNO());
            return map;
        }).collect(Collectors.toList());

        return new PageImpl<>(mapList, pageable, userBusinessList.getTotalElements());
    }

    @GetMapping("/Access")
    public String Access(@RequestParam("VNO") long VNO, @RequestParam("DEPT") String DEPT, @RequestParam("CLASSIFY") String CLASSIFY, Model model) {
        if (httpSession.getAttribute("user") != null) {
            SessionUser user = (SessionUser) httpSession.getAttribute("user");
            if (CLASSIFY.equals("휴가")) {
                Vacation vacation = vacationRepository.findByVNO(VNO);
                vacation.setCANCLEREASON(null);
                vacation.setAccessva("승인");
                vacation.setAPPROVER(user.getNAME());
                Optional<Calendar> optional = calendarRepository.findByVNO(vacation.getVNO());
                if (optional.isPresent()) { //데이터가 있으면 실행
                } else {
                    vacationRepository.save(vacation);
                    Calendar calendar = new Calendar(vacation.getTITLE(), vacation.getDESCRIPTION(),
                            vacation.getStart(), vacation.getEnd(), DEPT,
                            vacation.getCLASSIFY(), vacation.getVACATIONTYPE(), vacation.getVNO(),
                            vacation.getUser());
                    calendarRepository.save(calendar);
                }
            } else if (CLASSIFY.equals("회의")) {
                Meeting meeting = meetingRepository.findByVNO(VNO);
                meeting.setCANCLEREASON(null);
                meeting.setAccessva("승인");
                meeting.setAPPROVER(user.getNAME());
                Optional<Calendar> optional = calendarRepository.findByMNO(meeting.getVNO());
                if (optional.isPresent()) { //데이터가 있으면 실행
                } else {
                    meetingRepository.save(meeting);
                    Calendar calendar = new Calendar(meeting.getTITLE(), meeting.getDESCRIPTION(),
                            meeting.getStart(), meeting.getEnd(), DEPT,
                            meeting.getCLASSIFY(), meeting.getVNO(),
                            meeting.getUser());
                    calendarRepository.save(calendar);
                }
            } else {
                Business business = businessRepository.findByVNO(VNO);
                business.setCANCLEREASON(null);
                business.setAccessva("승인");
                business.setAPPROVER(user.getNAME());
                Optional<Calendar> optional = calendarRepository.findByBNO(business.getVNO());
                if (optional.isPresent()) { //데이터가 있으면 실행
                } else {
                    businessRepository.save(business);
                    Calendar calendar = new Calendar(business.getTITLE(), business.getDESCRIPTION(),
                            business.getStart(), business.getEnd(), DEPT,
                            business.getCLASSIFY(), business.getVNO(),
                            business.getUser());
                    calendarRepository.save(calendar);
                }
            }
        }
        return "redirect:/managerAcess";
    }

    @GetMapping("/Cancle")
    public String Cancle(@RequestParam("VNO") long VNO, @RequestParam("CLASSIFY") String CLASSIFY, @RequestParam("CANCLEREASON") String CANCLEREASON) {
        if (httpSession.getAttribute("user") != null) {
            SessionUser user = (SessionUser) httpSession.getAttribute("user");
            if (CLASSIFY.equals("휴가")) {
                Vacation vacation = vacationRepository.findByVNO(VNO);
                vacation.setCANCLEREASON(CANCLEREASON);
                vacation.setAccessva("반려");
                vacation.setAPPROVER(user.getNAME());
                vacationRepository.save(vacation);
                Optional<Calendar> optional = calendarRepository.findByVNO(vacation.getVNO());
                if (optional.isPresent()) { //데이터가 있으면 실행
                    Calendar calendar = optional.get();
                    calendarRepository.deleteById(calendar.getCALNO());
                }
            } else if (CLASSIFY.equals("회의")) {
                Meeting meeting = meetingRepository.findByVNO(VNO);
                meeting.setCANCLEREASON(CANCLEREASON);
                meeting.setAccessva("반려");
                meeting.setAPPROVER(user.getNAME());
                meetingRepository.save(meeting);
                Optional<Calendar> optional = calendarRepository.findByMNO(meeting.getVNO());
                if (optional.isPresent()) { //데이터가 있으면 실행
                    Calendar calendar = optional.get();
                    calendarRepository.deleteById(calendar.getCALNO());
                }
            } else {
                Business business = businessRepository.findByVNO(VNO);
                business.setCANCLEREASON(CANCLEREASON);
                business.setAccessva("반려");
                business.setAPPROVER(user.getNAME());
                businessRepository.save(business);
                Optional<Calendar> optional = calendarRepository.findByBNO(business.getVNO());
                if (optional.isPresent()) { //데이터가 있으면 실행
                    Calendar calendar = optional.get();
                    calendarRepository.deleteById(calendar.getCALNO());
                }
            }
        }
        return "redirect:/managerAcess";
    }
}
