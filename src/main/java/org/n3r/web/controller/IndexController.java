package org.n3r.web.controller;

import javax.annotation.Resource;

import org.n3r.web.entity.AddressInfo;
import org.n3r.web.service.AddressInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @Resource
    AddressInfoService addressInfoService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model, AddressInfo addressInfo) {
        model.addAttribute("pages", addressInfoService.findAllAddressInfo());
        return "index";
    }

    @RequestMapping(value = "add_input", method = RequestMethod.GET)
    public String add(Model model) {
        return "add_input";
    }

    @RequestMapping(value = "add_save", method = RequestMethod.POST)
    public String addSave(Model model, AddressInfo addressInfo) {
        addressInfoService.save(addressInfo);
        return "add_save";
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String search(Model model, AddressInfo addressInfo) {
        return "index";
    }

}
