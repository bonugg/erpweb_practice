package com.example.testproj.handler;

import com.example.testproj.Clazz.User.SessionUser;
import com.example.testproj.Clazz.User.User;
import com.example.testproj.repository.CalendarRepository;
import com.example.testproj.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
public class TeamLeaderInterceptor extends HandlerInterceptorAdapter {
    private final UserRepository userRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute("user") != null) {
            SessionUser user = (SessionUser) httpSession.getAttribute("user");
            if (modelAndView != null) {
                // Model에 사용자 객체 추가
                modelAndView.addObject("user", user);
                if (user.getPOSITION().equals("팀장")) {
                    modelAndView.addObject("isTeamleader", true);
                } else {
                    modelAndView.addObject("isTeammember", true);
                }
                LocalDate currentDate = LocalDate.now(); // 현재 날짜 가져오기
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 출력 형식 지정
                String formattedDate = currentDate.format(format); // 현재 날짜를 지정된 형식으로 변환

                SimpleDateFormat startEndDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

                List<User> userList = userRepository.findByDEPT(user.getDEPT());
                List<Integer> useroncheck = new ArrayList<>();
                List<Integer> useroffcheck = new ArrayList<>();
                for (int i = 0; i < userList.size(); i++) {
                    useroncheck.add(i, 0);
                    for (int j = 0; j < userList.get(i).getCalendarList().size(); j++) {
                        if (userList.get(i).getCalendarList().get(j).getCLASSIFY().equals("출퇴근")
                                && userList.get(i).getCalendarList().get(j).getStart().contains(formattedDate)) {
                            Date startDate = startEndDateFormat.parse(userList.get(i).getCalendarList().get(j).getStart());
                            java.util.Calendar startDateCalendar = java.util.Calendar.getInstance();
                            startDateCalendar.setTime(startDate);
                            if (startDateCalendar.get(java.util.Calendar.HOUR_OF_DAY) < 9) {
                                useroncheck.set(i, 1);
                            }else if (startDateCalendar.get(java.util.Calendar.HOUR_OF_DAY) >= 9) {
                                useroncheck.set(i, 2);
                            }
                        }
                    }
                }
                for (int i = 0; i < userList.size(); i++) {
                    useroffcheck.add(i, 0);
                    for (int j = 0; j < userList.get(i).getCalendarList().size(); j++) {
                        if (userList.get(i).getCalendarList().get(j).getCLASSIFY().equals("출퇴근")
                                && userList.get(i).getCalendarList().get(j).getEnd().contains(formattedDate)) {
                            Date startDate = startEndDateFormat.parse(userList.get(i).getCalendarList().get(j).getEnd());
                            java.util.Calendar endDateCalendar = java.util.Calendar.getInstance();
                            endDateCalendar.setTime(startDate);
                            if (endDateCalendar.get(java.util.Calendar.HOUR_OF_DAY) >= 18) {
                                useroffcheck.set(i, 1);
                            }else if (endDateCalendar.get(java.util.Calendar.HOUR_OF_DAY) < 18) {
                                useroffcheck.set(i, 2);
                            }
                        }
                    }
                }

                List<Map<String, Object>> combinedList = new ArrayList<>();
                for (int i = 0; i < userList.size(); i++) {
                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("user", userList.get(i));
                    dataMap.put("oncheck", useroncheck.get(i));
                    dataMap.put("offcheck", useroffcheck.get(i));
                    combinedList.add(dataMap);
                }
                modelAndView.addObject("userMap", combinedList);
            }
        }
    }
}