package org.n3r.web.controller;

import javax.annotation.Resource;

import org.n3r.eql.EqlPage;
import org.n3r.web.entity.AddressInfo;
import org.n3r.web.service.AddressInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/pageDemo")
public class PageDemoController extends BaseController {

    @Resource
    AddressInfoService addressInfoService;

    @RequestMapping(value = "simple", method = { RequestMethod.GET, RequestMethod.POST })
    public String simple(Model model, AddressInfo addressInfo, EqlPage eqlPage) {
        model.addAttribute("pages", addressInfoService.findPage(addressInfo, eqlPage));
        return "demo/example8";
    }

    @ModelAttribute("eqlPage")
    public EqlPage getEqlPage(EqlPage eqlPage) {
        if (eqlPage.getPageRows() == 0)
            eqlPage.setPageRows(10);
        return eqlPage;
    }

    @RequestMapping(value = "simple2", method = { RequestMethod.GET, RequestMethod.POST })
    public String simple2(Model model, AddressInfo addressInfo, EqlPage eqlPage) {
        model.addAttribute("pages", addressInfoService.findPage(addressInfo, eqlPage));
        return "demo/example10";
    }

    @RequestMapping(value = "ajaxIndex", method = { RequestMethod.GET, RequestMethod.POST })
    public String ajax(Model model, AddressInfo addressInfo, EqlPage eqlPage) {
        eqlPage.setPageRows(10);
        return "demo/example9";
    }

    @RequestMapping(value = "ajaxQuery", method = { RequestMethod.GET, RequestMethod.POST })
    public String ajaxQuery(Model model, AddressInfo addressInfo, EqlPage eqlPage) {
        model.addAttribute("pages", addressInfoService.findPage(addressInfo, eqlPage));
        return "demo/ajaxpagetable";
    }
}
