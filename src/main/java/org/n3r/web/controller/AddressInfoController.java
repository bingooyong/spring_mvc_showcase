package org.n3r.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.n3r.web.entity.AddressInfo;
import org.n3r.web.service.AddressInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AddressInfoController extends BaseController {

    @Resource
    AddressInfoService addressInfoService;

    @RequestMapping(value = { "add_input", "edit_input/{id}" }, method = RequestMethod.GET)
    public String add(Model model, AddressInfo addressInfo, @PathVariable String id) {
        if (StringUtils.isNotEmpty(id))
            addressInfo = addressInfoService.get(id);
        return "add_input";
    }

    @RequestMapping(value = "add_save", method = RequestMethod.POST)
    public String addSave(Model model, @Valid AddressInfo addressInfo, BindingResult result) {
        if (result.hasErrors())
            return "add_input";
        addressInfoService.save(addressInfo);
        model.addAttribute("pages", addressInfoService.findAddressInfo(addressInfo));
        return "index";
    }

    @RequestMapping(value = { "index", "search" }, method = { RequestMethod.GET, RequestMethod.POST })
    public String search(Model model, AddressInfo addressInfo) {
        model.addAttribute("pages", addressInfoService.findAddressInfo(addressInfo));
        return "index";
    }

    @RequestMapping("/del")
    public @ResponseBody
    java.util.Map del(String[] ids) {
        addressInfoService.delete(ids);
        return ajax(Status.success, "删除成功!");
    }
}
