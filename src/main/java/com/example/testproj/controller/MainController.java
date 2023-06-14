package com.example.testproj.controller;

import com.example.testproj.Clazz.User.SessionUser;
import com.example.testproj.Clazz.User.User;
import com.example.testproj.Clazz.calendar.Calendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.testproj.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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
    public String main() {



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

    @ResponseBody
    @RequestMapping(value = "/onadd", method = RequestMethod.POST)
    public String onadd(HttpServletRequest request) throws Exception {
        Calendar calendar = new Calendar();
        SessionUser user1 = (SessionUser) httpSession.getAttribute("user");
        User user = userRepository.findByCNO(user1.getCNO()).get();
        calendar.setUser(user);
        calendar.setDEPT(user.getDEPT());
        Date today = new Date();
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Date inputDate = inputDateFormat.parse(String.valueOf(today));
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String outputDateString = outputDateFormat.format(inputDate);

        calendar.setCLASSIFY("출퇴근");
        calendar.setStart(outputDateString);
        calendar.setEnd("0");
        calendarRepository.save(calendar);

        return outputDateString;
    }

    @ResponseBody
    @RequestMapping(value = "/offadd", method = RequestMethod.POST)
    public String offadd(@RequestParam("start") String start) throws Exception {
        SessionUser user1 = (SessionUser) httpSession.getAttribute("user");
        Calendar calendar = calendarRepository.findByCLASSIFYANDSTARTy(user1.getNO(),start).get();
        Date today = new Date();
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Date inputDate = inputDateFormat.parse(String.valueOf(today));
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String outputDateString = outputDateFormat.format(inputDate);

        calendar.setEnd(outputDateString);

        // start와 end 시간을 Date 형식으로 변환
        SimpleDateFormat startEndDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date startDate = startEndDateFormat.parse(calendar.getStart());
        Date endDate = startEndDateFormat.parse(outputDateString);

        // start가 오전 9시 전이고 end가 오후 6시 이후인 경우
        java.util.Calendar startDateCalendar = java.util.Calendar.getInstance();
        startDateCalendar.setTime(startDate);
        java.util.Calendar endDateCalendar = java.util.Calendar.getInstance();
        endDateCalendar.setTime(endDate);

        if (startDateCalendar.get(java.util.Calendar.HOUR_OF_DAY) < 9 && endDateCalendar.get(java.util.Calendar.HOUR_OF_DAY) >= 18) {
            calendar.setTITLE("출석");
        }else if (startDateCalendar.get(java.util.Calendar.HOUR_OF_DAY) < 9 && endDateCalendar.get(java.util.Calendar.HOUR_OF_DAY) < 18) {
            calendar.setTITLE("조퇴");
        }else if (startDateCalendar.get(java.util.Calendar.HOUR_OF_DAY) >= 9 && endDateCalendar.get(java.util.Calendar.HOUR_OF_DAY) >= 18) {
            calendar.setTITLE("지각");
        }else {
            calendar.setTITLE("결석");
        }
        calendarRepository.save(calendar);

        return outputDateString;
    }

    @ResponseBody
    @RequestMapping(value = "/onaddcheck", method = RequestMethod.POST) //좋아요 체크 및 좋아요 수
    public String onaddcheck(@RequestParam("start") String start) throws Exception {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        String startdate = calendarRepository.findByCLASSIFYANDSTART(user.getNO(),start);
        return startdate;
    }

    @ResponseBody
    @RequestMapping(value = "/offaddcheck", method = RequestMethod.POST) //좋아요 체크 및 좋아요 수
    public String offaddcheck(@RequestParam("end") String end) throws Exception {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        String enddate = calendarRepository.findByCLASSIFYANDEND(user.getNO(),end);
        return enddate;
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
}
