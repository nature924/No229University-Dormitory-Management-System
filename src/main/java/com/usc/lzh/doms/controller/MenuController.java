package com.usc.lzh.doms.controller;

import com.usc.lzh.doms.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping
public class MenuController {

    @Resource
    private MenuService menuService;


}
