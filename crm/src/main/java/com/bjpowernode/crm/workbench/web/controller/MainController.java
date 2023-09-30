package com.bjpowernode.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version: java version 1.8
 * @Author: Zerroi
 * @description: 工作区页面跳转
 * @date: 2023-09-13 19:37
 */

@Controller
public class MainController {
    @RequestMapping("/workbench/main/index.do")
    public String index() {
        return "workbench/main/index";
    }
}
