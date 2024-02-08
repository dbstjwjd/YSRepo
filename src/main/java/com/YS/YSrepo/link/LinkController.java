package com.YS.YSrepo.link;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/link")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Link> linkList = linkService.getList();
        model.addAttribute("list", linkList);
        return "link/list";
    }

    @PostMapping("/save")
    @PreAuthorize("isAuthenticated()")
    public String create(String name, String address) {
        linkService.saveLink(name, address);
        return "redirect:/link/list";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(@PathVariable("id") Integer id, String name, String address) {
        Link link = linkService.getLink(id);
        linkService.modifyLink(link, name, address);
        return "redirect:/link/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Link link = linkService.getLink(id);
        linkService.deleteLink(link);
        return "redirect:/link/list";
    }
}
