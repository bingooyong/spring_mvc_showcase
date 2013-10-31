package org.n3r.web.controller.demo;

import java.util.Map;

import org.n3r.core.lang.RDate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo")
public class ThymeleafDemoConroller {

    @RequestMapping("index")
    public String index(Map<String, Object> result) {

        result.put("today", RDate.toDateStr());

        User user = new User("lv", "yong", "test");
        result.put("user", user);

        return "demo/index";
    }
}
