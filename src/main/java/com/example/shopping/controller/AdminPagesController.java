package com.example.shopping.controller;
import com.example.shopping.entity.data.Page;
import com.example.shopping.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
public class AdminPagesController {
    @Autowired
    private PageRepository pageRepository;

    @GetMapping("/admin/pages")
    public String index(Model model) {

        List<Page> pages = pageRepository.findAllByOrderBySequenceNumberAsc();

        model.addAttribute("pages", pages);

        return "admin/pages/index";
    }

    @GetMapping("/admin/pages/add")
    public String add(Page page) {
        return "admin/pages/add";
    }

    @PostMapping("/admin/pages/add")
    public String add(@Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/pages/add";
        }

        redirectAttributes.addFlashAttribute("message", "Page added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String identificator = page.getIdentificator() == "" ? page.getTitle().toLowerCase().replace(" ", "-") : page.getIdentificator().toLowerCase().replace(" ", "-");

        Page slugExists = pageRepository.findByIdentificator(page.getIdentificator());

        if (slugExists != null) {
            redirectAttributes.addFlashAttribute("message", "Slug exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", page);
            return "redirect:/admin/pages/add";
        } else {
            page.setIdentificator(identificator);
            page.setSequenceNumber(100);

            pageRepository.save(page);
        }
        return "redirect:/admin/pages";
    }

    @GetMapping("/admin/pages/edit/{id}")
    public String edit(@PathVariable int id, Model model) {

        Page page = pageRepository.findById(id).get();

        model.addAttribute("page", page);

        return "admin/pages/edit";
    }

    @PostMapping("/admin/pages/edit")
    public String edit(@Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        Page pageCurrent = pageRepository.findById(page.getId()).get();

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", pageCurrent.getTitle());
            return "admin/pages/edit";
        }

        redirectAttributes.addFlashAttribute("message", "Page edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String identificator = page.getIdentificator() == "" ? page.getTitle().toLowerCase().replace(" ", "-") : page.getIdentificator().toLowerCase().replace(" ", "-");

        Page slugExists = pageRepository.findByIdentificatorAndIdNot(identificator, page.getId());

        if (slugExists != null) {
            redirectAttributes.addFlashAttribute("message", "Slug exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", page);
            return "redirect:/admin/pages/edit/" + page.getId();
        } else {
            page.setIdentificator(identificator);

            pageRepository.save(page);
        }
        return "redirect:/admin/pages";
    }

    @GetMapping("/admin/pages/delete/{id}")
    public String edit(@PathVariable int id, RedirectAttributes redirectAttributes) {

        pageRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Page deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/admin/pages";
    }

    @PostMapping("/admin/pages/reorder")
    public @ResponseBody String reorder(@RequestParam("id[]") int[] id) {
        int count = 1;
        Page page;

        for (int pageId : id) {
            page = pageRepository.findById(pageId).get();
            page.setSequenceNumber(count);
            pageRepository.save(page);
            count++;
        }

        return "ok";
    }

}
