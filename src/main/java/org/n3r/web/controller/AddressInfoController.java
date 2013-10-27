package org.n3r.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    @RequestMapping(value = "/add_input", method = RequestMethod.GET)
    public String addInput(Model model, AddressInfo addressInfo) {
        return "add_input";
    }

    @RequestMapping(value = "/edit_input/{id}", method = RequestMethod.GET)
    public String editInput(Model model, @PathVariable String id) {
        model.addAttribute("addressInfo", addressInfoService.get(id));
        model.addAttribute("isEditInput", true);
        return "add_input";
    }

    @RequestMapping(value = "/add_save", method = RequestMethod.POST)
    public String addSave(Model model, @Valid AddressInfo addressInfo, BindingResult result) {
        if (result.hasErrors())
            return "add_input";
        addressInfoService.save(addressInfo);
        model.addAttribute("pages", addressInfoService.findAddressInfo(addressInfo));
        return "index";
    }

    @RequestMapping(value = "/edit_save", method = RequestMethod.POST)
    public String editSave(Model model, @Valid AddressInfo addressInfo, BindingResult result) {
        model.addAttribute("isEditInput", true);
        if (result.hasErrors())
            return "add_input";
        addressInfoService.update(addressInfo);
        model.addAttribute("pages", addressInfoService.findAddressInfo(addressInfo));
        return "index";
    }

    @RequestMapping(value = { "/index", "/search" }, method = { RequestMethod.GET, RequestMethod.POST })
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
