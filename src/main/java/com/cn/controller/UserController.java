package com.cn.controller;


import com.cn.util.SolrUtil;
import com.cn.model.User;
import com.cn.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    private static Logger log= LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @RequestMapping("/insert")
    public String insertone(HttpServletRequest request, String name, int age, int sex){
        log.debug("-------------");
        String uid=UUID.randomUUID().toString();
        User user = new User(uid,name,age,sex, new Date());
//        userService.insertone(user);
//        log.debug(user.toString());

//        //        redis 测试
//        Jedis jedis = new Jedis("127.0.0.1",6379);
//        log.debug(jedis.ping());
//
//        jedis.set("age","23");
//        request.setAttribute("age",jedis.get("age"));

        SolrUtil solrUtil = new SolrUtil();
        try {
            solrUtil.addDoc(user);
        }catch (Exception e){
            System.out.println(e);
        }
        return  "redirect:listall";

    }

    @RequestMapping("/listall")
    public String listall(HttpServletRequest request, @RequestParam(value="pn",defaultValue="1")Integer pn,
                          Model model){
        int pageSize=10;
//        List <User> listuser = userService.findall();
        SolrUtil solrUtil = new SolrUtil();
        try {

            //总条数
            long totalNum=solrUtil.querySolrAll();
            //总页数
            long pageTotal=(totalNum-1)/pageSize+1;
            if (pn>pageTotal){
                return "redirect:listall?pn="+pageTotal;

            }else if (pn <= 0){
                return "redirect:listall?pn="+1;
            }
            List<User> solrlistuser = solrUtil.querySolr(pn);


            request.setAttribute("listuser",solrlistuser);
            request.setAttribute("currentPn",pn);
            request.setAttribute("pn",pn);
            //总共条数和总的页数
            request.setAttribute("listNum",totalNum);
            request.setAttribute("pageTotal",pageTotal);
            Integer startpage=null;
            Integer endpage = null;
            //分页判断
            if (pageTotal > 5){
                if (pn < 3){
                    startpage = 1;
                    endpage = 5;
                }else if (pn >= 3){
                    startpage = pn -2;
                    if ( pn+2 <= pageTotal){
                        endpage = pn +2;
                    }else {
                        endpage = Math.toIntExact(pageTotal);
                    }
                }
            }else  if (pageTotal <= 5){
                startpage = 1;
                endpage = Math.toIntExact(pageTotal);
            }
            //将分页完成后的起始参数 传递过去
            request.setAttribute("startpage",startpage);
            request.setAttribute("endpage",endpage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "index";
    }

    @RequestMapping("/deleteone")
    public String deleteuser(HttpServletRequest request){
        String id=request.getParameter("id");
        SolrUtil solrUtil = new SolrUtil();
        try {
            solrUtil.deleteDocumentById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/user/listall";
    }

    @RequestMapping("updateuser")
    public String alteruser(HttpServletRequest request){
        String name=request.getParameter("name_modal");
        String id = request.getParameter("id_modal");
        SolrUtil solrUtil = new SolrUtil();

        return  null;
    }

}
