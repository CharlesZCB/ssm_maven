package com.cn.controller;


import com.cn.model.SolrUtil;
import com.cn.model.User;
import com.cn.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private static Logger log= LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @RequestMapping("/insert")
    public String insertone(HttpServletRequest request, String name, int age, int sex){
        log.debug("-------------");
        User user = new User(name,age,sex);
        userService.insertone(user);
        log.debug(user.toString());

        //        redis 测试
        Jedis jedis = new Jedis("127.0.0.1",6379);
        log.debug(jedis.ping());

        jedis.set("age","23");
        request.setAttribute("age",jedis.get("age"));

        SolrUtil solrUtil = new SolrUtil();
        try {
            solrUtil.querySolr();
        }catch (Exception e){
            System.out.println(e);
        }
        return  "redirect:listall";

    }

    @RequestMapping("/listall")
    public String listall(HttpServletRequest request, @RequestParam(value="pn",defaultValue="1")Integer pn,
                          Model model){
        PageHelper.startPage(pn,6);
        List <User> listuser = userService.findall();
        PageInfo<User> pageInfo=new PageInfo<User>(listuser,6);
        model.addAttribute("pageInfo", pageInfo);
//        SolrUtil solrUtil = new SolrUtil();
//        List<User> listuser = null;
//        try {
//            listuser = solrUtil.querySolr();
//            PageInfo<User> pageInfo=new PageInfo<User>(listuser,6);
//            model.addAttribute("pageInfo", pageInfo);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //request.setAttribute("listuser", listuser);
        return  "index";
    }

    @RequestMapping("/deleteone")
    public String deleteuser(HttpServletRequest request){

        int id=Integer.parseInt(request.getParameter("id"));
        userService.deleteById(id);
        return "redirect:/user/listall";
    }

}
