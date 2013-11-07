package org.n3r.web.controller;

import javax.annotation.Resource;

import org.n3r.eql.EqlPage;
import org.n3r.web.entity.AddressInfo;
import org.n3r.web.service.AddressInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/pageDemo")
public class PageDemoController extends BaseController {

    @Resource
    AddressInfoService addressInfoService;

    @RequestMapping(value = "simple", method = { RequestMethod.GET, RequestMethod.POST })
    public String example8(Model model, AddressInfo addressInfo, EqlPage eqlPage) {
        eqlPage.setPageRows(10);
        model.addAttribute("pages", addressInfoService.findPage(addressInfo, eqlPage));
        return "demo/example8";
    }
}
