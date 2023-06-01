package com.example.testproj.controller;

import com.example.testproj.User.Calendar;
import com.example.testproj.User.SessionUser;
import com.example.testproj.User.User;
import com.example.testproj.repository.CalendarRepository;
import com.example.testproj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/production")
public class CalendarController {
    @Autowired
    private HttpSession httpSession;
    private final CalendarRepository calendarRepository;

    @RequestMapping(value = "/monthPlan", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> monthPlan() {
        if (httpSession.getAttribute("user") != null) {

            SessionUser user1 = (SessionUser) httpSession.getAttribute("user");
            List<Calendar> calendarList = calendarRepository.findByDEPT(user1.getDEPT());
            List<Map<String, Object>> mapList = calendarList.stream().map(calendar -> {
                Map<String, Object> map = new HashMap<>();
                map.put("title", calendar.getTITLE());
                map.put("description", calendar.getDESCRIPTION());
                map.put("start", calendar.getStart());
                map.put("end", calendar.getEnd());
                map.put("classify", calendar.getCLASSIFY());
                map.put("vacationtype", calendar.getVACATIONTYPE());
                return map;
            }).collect(Collectors.toList());

            List<Map<String, Object>> list = mapList;

            JSONObject jsonObj = new JSONObject();
            JSONArray jsonArr = new JSONArray();
            HashMap<String, Object> hash = new HashMap<String, Object>();

            for (int i = 0; i < list.size(); i++) {
                hash.put("title", list.get(i).get("title")); //제목
                hash.put("description", list.get(i).get("description")); //설명
                hash.put("start", list.get(i).get("start")); //시작일자
                hash.put("end", list.get(i).get("end")); //종료일자
                hash.put("classify", list.get(i).get("classify")); //구분
                hash.put("vacationtype", list.get(i).get("vacationtype")); //휴가타입

                jsonObj = new JSONObject(hash); //중괄호 {key:value , key:value, key:value}
                jsonArr.add(jsonObj); // 대괄호 안에 넣어주기[{key:value , key:value, key:value},{key:value , key:value, key:value}]
            }
            return jsonArr;
        } else {
            JSONObject jsonObj = new JSONObject();
            JSONArray jsonArr = new JSONArray();
            HashMap<String, Object> hash = new HashMap<String, Object>();
            for (int i = 0; i < 1; i++) {
                hash.put("title", "none"); //제목
                jsonObj = new JSONObject(hash); //중괄호 {key:value , key:value, key:value}
                jsonArr.add(jsonObj); // 대괄호 안에 넣어주기[{key:value , key:value, key:value},{key:value , key:value, key:value}]
            }
            System.out.println(jsonArr);
            return jsonArr;
        }
    }
}
