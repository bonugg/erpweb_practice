package com.example.testproj.controller;

import com.example.testproj.Clazz.Approval.Business;
import com.example.testproj.Clazz.User.SessionUser;
import com.example.testproj.Clazz.User.User;
import com.example.testproj.Clazz.calendar.Calendar;
import com.example.testproj.Clazz.Approval.Meeting;
import com.example.testproj.Clazz.Approval.Vacation;
import com.example.testproj.repository.*;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {
    @Autowired
    private HttpSession httpSession;
    private final UserRepository userRepository;
    private final VacationRepository vacationRepository;
    private final MeetingRepository meetingRepository;
    private final BusinessRepository businessRepository;
    private final CalendarRepository calendarRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/")
    public String main(Model model) {
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

    @GetMapping("/userAcess")
    public String userAcess(Model model) {
        if (httpSession.getAttribute("user") != null) {
            SessionUser user = (SessionUser) httpSession.getAttribute("user");

            List<Object[]> userVacationList = vacationRepository.findByNOList(user.getNO());
            Map<String, Object> vacationListtData = new HashMap<>();
            vacationListtData.put("vacationList", userVacationList); // Map에 데이터를 직접 담음
            model.addAttribute("vacationListtData", vacationListtData);

            List<Object[]> businesstList = businessRepository.findByNOList(user.getNO());
            Map<String, Object> businesstListData = new HashMap<>();
            businesstListData.put("businesstList", businesstList); // Map에 데이터를 직접 담음
            model.addAttribute("businesstListData", businesstListData);

            List<Object[]> meetingtList = meetingRepository.findByNOList(user.getNO());
            Map<String, Object> meetingtListData = new HashMap<>();
            meetingtListData.put("meetingList", meetingtList); // Map에 데이터를 직접 담음
            model.addAttribute("meetingtListData", meetingtListData);
        }
        return "Approval/userAcessPage";
    }

    @GetMapping("/managerAcess")
    public String managerAccess(Model model) {
        if (httpSession.getAttribute("user") != null) {
            SessionUser user = (SessionUser) httpSession.getAttribute("user");

            List<Object[]> userVacationList = vacationRepository.findByDeptList(user.getDEPT());
            Map<String, Object> vacationListtData = new HashMap<>();
            vacationListtData.put("vacationList", userVacationList); // Map에 데이터를 직접 담음
            model.addAttribute("vacationListtData", vacationListtData);

            List<Object[]> businesstList = businessRepository.findByDeptList(user.getDEPT());
            Map<String, Object> businesstListData = new HashMap<>();
            businesstListData.put("businesstList", businesstList); // Map에 데이터를 직접 담음
            model.addAttribute("businesstListData", businesstListData);

            List<Object[]> meetingtList = meetingRepository.findByDeptList(user.getDEPT());
            Map<String, Object> meetingtListData = new HashMap<>();
            meetingtListData.put("meetingList", meetingtList); // Map에 데이터를 직접 담음
            model.addAttribute("meetingtListData", meetingtListData);
        }
        return "Approval/managerAcessPage";
    }

    @GetMapping("/Access")
    public String Access(@RequestParam("VNO") long VNO, @RequestParam("DEPT") String DEPT, @RequestParam("CLASSIFY") String CLASSIFY, Model model) {
        if (CLASSIFY.equals("휴가")) {
            Vacation vacation = vacationRepository.findByVNO(VNO);
            vacation.setCANCLEREASON(null);
            vacation.setAccessva("승인");
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
        return "redirect:/managerAcess";
    }

    @GetMapping("/Cancle")
    public String Cancle(@RequestParam("VNO") long VNO, @RequestParam("CLASSIFY") String CLASSIFY, @RequestParam("CANCLEREASON") String CANCLEREASON) {
        if (CLASSIFY.equals("휴가")) {
            Vacation vacation = vacationRepository.findByVNO(VNO);
            vacation.setCANCLEREASON(CANCLEREASON);
            vacation.setAccessva("반려");
            vacationRepository.save(vacation);
            Optional<Calendar> optional = calendarRepository.findByVNO(vacation.getVNO());
            if (optional.isPresent()) { //데이터가 있으면 실행
                Calendar calendar = optional.get();
                calendarRepository.deleteById(calendar.getCALNO());
                return "redirect:/managerAcess";
            } else {
                return "redirect:/managerAcess"; //예외 처리 페이지로 리다이렉트
            }
        } else if (CLASSIFY.equals("회의")) {
            Meeting meeting = meetingRepository.findByVNO(VNO);
            meeting.setCANCLEREASON(CANCLEREASON);
            meeting.setAccessva("반려");
            meetingRepository.save(meeting);
            Optional<Calendar> optional = calendarRepository.findByMNO(meeting.getVNO());
            if (optional.isPresent()) { //데이터가 있으면 실행
                Calendar calendar = optional.get();
                calendarRepository.deleteById(calendar.getCALNO());
                return "redirect:/managerAcess";
            } else {
                return "redirect:/managerAcess"; //예외 처리 페이지로 리다이렉트
            }
        }else{
            Business business = businessRepository.findByVNO(VNO);
            business.setCANCLEREASON(CANCLEREASON);
            business.setAccessva("반려");
            businessRepository.save(business);
            Optional<Calendar> optional = calendarRepository.findByBNO(business.getVNO());
            if (optional.isPresent()) { //데이터가 있으면 실행
                Calendar calendar = optional.get();
                calendarRepository.deleteById(calendar.getCALNO());
                return "redirect:/managerAcess";
            } else {
                return "redirect:/managerAcess"; //예외 처리 페이지로 리다이렉트
            }
        }
    }
}
