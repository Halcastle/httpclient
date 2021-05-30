package com.pwang6.httpclient.controller;

import com.alibaba.fastjson.JSONObject;
import com.pwang6.httpclilent.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/http")
public class HttpController {
    private Logger logger = LoggerFactory.getLogger(HttpController.class);

    @GetMapping(path = "/demo/{id}")
    public JSONObject getDemo(@PathVariable(name = "id") String id, @RequestParam(name = "name") String name){
        logger.info("get请求，url路径的id值为：{}",id);
        logger.info("get请求，url后参数的name值为：{}",name);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rtnCode","0000");
        jsonObject.put("rtnMsg","成功");
        return jsonObject;
    }

    @PostMapping(path = "/demo/post1")
    public JSONObject postDemo1(@RequestBody Person person){
        logger.info("Person:{}",person.toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rtnCode","0000");
        jsonObject.put("rtnMsg",person);
        return jsonObject;
    }

    @PostMapping(path = "/demo/post2")
    public JSONObject postDemo2(@RequestBody Map<String,String> person){
        logger.info("Person:{}",person.toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rtnCode","0000");
        jsonObject.put("rtnMsg",person);
        return jsonObject;
    }

    @GetMapping(path = "/demo/get2")
    public JSONObject getDemo2(@RequestHeader(name="myheader") String myheader,@CookieValue(name = "mycookie") String mycookie){
        logger.info("myheader:{}",myheader);
        logger.info("mycookie:{}",mycookie);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rtnCode","0000");
        jsonObject.put("rtnMsg",myheader+mycookie);
        return jsonObject;
    }

    @GetMapping(path = "/demo/get3")
    public JSONObject getDemo3(HttpServletRequest request){
        logger.info("myheader:{}",request.getHeader("myheader"));
        logger.info("cookies:{}",request.getCookies());
        for(Cookie cookie : request.getCookies()){
            if("mycookie".equals(cookie.getName())){
                logger.info("mycookie:{}",cookie.getName());
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rtnCode","0000");
        jsonObject.put("rtnMsg","成功");
        return jsonObject;
    }

    @GetMapping(path = "/demo/get")
    public JSONObject getDemo4(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rtnCode","0000");
        jsonObject.put("rtnMsg","访问成功");
        return jsonObject;
    }


}
