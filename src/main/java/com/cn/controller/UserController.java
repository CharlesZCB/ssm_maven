package com.cn.controller;


import com.cn.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
    private static Logger log= LoggerFactory.getLogger(UserController.class);
    @RequestMapping("/insert")
    public String insertone(HttpServletRequest request, Model model){
        int userId = Integer.parseInt(request.getParameter("id"));
        System.out.println("userId:"+userId);
        User user=null;
        if (userId==1) {
            user = new User();
            user.setAge(11);
            user.setName("黄花");
            user.setSex(1);
        }

        log.debug(user.toString());
        model.addAttribute("user", user);
        return "index";

    }

}
