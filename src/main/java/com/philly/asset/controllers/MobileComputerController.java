package com.philly.asset.controllers;

import com.philly.asset.models.MobileComputer;
import com.philly.asset.repositories.MobileComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MobileComputerController {
    @Autowired
    MobileComputerRepository mcRepository;

    @RequestMapping("/mc")
    public String product(Model model) {
        model.addAttribute("mcs", mcRepository.findAll());
        return "mc/mc";
    }

    @RequestMapping("/create")
    public String create(Model model) {
        return "mc/create";
    }

    @RequestMapping("/save")
    public String save(@RequestParam String hostName, @RequestParam String assetNo, @RequestParam String modelName) {
        MobileComputer mc = new MobileComputer(hostName,assetNo,modelName);
        mcRepository.save(mc);

        return "redirect:/mc/show/" + mc.getId();
    }

    @RequestMapping("/show/{id}")
    public String show(@PathVariable String id, Model model) {
        model.addAttribute("mc", mcRepository.findById(id).get());
        return "mc/show";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam String id) {
        MobileComputer mc = mcRepository.findById(id).get();
        mcRepository.delete(mc);

        return "redirect:/mc/mc";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("mc", mcRepository.findById(id).get());
        return "mc/edit";
    }

    @RequestMapping("/update")
    public String update(@RequestParam String id, @RequestParam String hostName, @RequestParam String assetNo, @RequestParam String modelName) {
        MobileComputer mc = mcRepository.findById(id).get();
        mc.setAssetNo(assetNo);
        mc.setHostName(hostName);
        mc.setModelName(modelName);
        mcRepository.save(mc);

        return "redirect:/mc/show/" + mc.getId();
    }
}