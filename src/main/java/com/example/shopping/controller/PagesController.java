package com.example.shopping.controller;
import com.example.shopping.entity.data.Page;
import com.example.shopping.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/")
public class PagesController {
    @Autowired
    private PageRepository pageRepository;

    @GetMapping("/")
    public String home(Model model) {

        Page page = pageRepository.findByIdentificator("home");
        model.addAttribute("page", page);

        return "page";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/{identificator}")
    public String page(@PathVariable String identificator, Model model) {

        Page page = pageRepository.findByIdentificator(identificator);

        if (page == null) {
            return "redirect:/";
        }

        model.addAttribute("page", page);

        return "page";
    }
}
