package org.n3r.web.controller;

import javax.annotation.Resource;

import org.n3r.web.entity.AddressInfo;
import org.n3r.web.service.AddressInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
    public String login() {
        return "login";
    }



}
