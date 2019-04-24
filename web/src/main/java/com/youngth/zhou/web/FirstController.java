package com.youngth.zhou.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by YoungTH on 2018/10/26.
 */
@Controller
@RequestMapping("/first")
public class FirstController {
    private Logger logger = LoggerFactory.getLogger(FirstController.class);
    private String PREFIX="/first/";



    @RequestMapping("")
    public String index(){
        return PREFIX+"first.html";
    }

    @RequestMapping("/test2")
    public String test2(){
        return PREFIX+"test2.html";
    }

    @RequestMapping("/test3")
    public String test3(){
        return PREFIX+"test3.html";
    }

}
