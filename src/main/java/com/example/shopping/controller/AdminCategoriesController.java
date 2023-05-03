package com.example.shopping.controller;
import com.example.shopping.entity.data.Category;
import com.example.shopping.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
public class AdminCategoriesController {

        @Autowired
        private CategoryRepository categoryRepository;

        @GetMapping("/admin/categories")
        public String index(Model model) {

            List<Category> categories = categoryRepository.findAllByOrderBySequenceNumberAsc();

            model.addAttribute("categories", categories);

            return "admin/categories/index";
        }


        @GetMapping("/admin/categories/add")
        public String add(Category category) {
            return "admin/categories/add";
        }

        @PostMapping("/admin/categories/add")
        public String add(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

            if (bindingResult.hasErrors()) {
                return "admin/categories/add";
            }

            redirectAttributes.addFlashAttribute("message", "Category added");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");

            String identificator = category.getName().toLowerCase().replace(" ", "-");

            Category categoryExists = categoryRepository.findByName(category.getName());

            if (categoryExists != null) {
                redirectAttributes.addFlashAttribute("message", "Category exists, choose another");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("categoryInfo", category);
            } else {
                category.setIdentificator(identificator);
                category.setSequenceNumber(100);

                categoryRepository.save(category);
            }
            return "redirect:/admin/categories";
        }

        @GetMapping("/admin/categories/edit/{id}")
        public String edit(@PathVariable int id, Model model) {
 
            Category category = categoryRepository.findById(id).get();

            model.addAttribute("category", category);

            System.out.println("CATEGORY " + category);

            return "admin/categories/edit";
        }

        @PostMapping("/admin/categories/edit")
        public String edit(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

            Category categoryCurrent = categoryRepository.getOne(category.getId());

            if (bindingResult.hasErrors()) {
                model.addAttribute("categoryName", categoryCurrent.getName());
                return "admin/categories/edit";
            }


            redirectAttributes.addFlashAttribute("message", "Category edited");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");

            String identificator = category.getName().toLowerCase().replace(" ", "-");

            Category categoryExists = categoryRepository.findByName(category.getName());

            if (categoryExists != null) {
                redirectAttributes.addFlashAttribute("message", "Category exists, choose another");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute("categoryInfo", category);
            } else {
                category.setIdentificator(identificator);

                categoryRepository.save(category);
            }
            return "redirect:/admin/categories";
        }

        @GetMapping("/admin/categories/delete/{id}")
        public String edit(@PathVariable int id, RedirectAttributes redirectAttributes) {

            categoryRepository.deleteById(id);

            redirectAttributes.addFlashAttribute("message", "Category deleted");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");

            return "redirect:/admin/categories";
        }

        @PostMapping("/admin/categories/reorder")
        public @ResponseBody
        String reorder(@RequestParam("id[]") int[] id) {
            int count = 1;
            Category category;

            for (int categoryId : id) {
                category = categoryRepository.getOne(categoryId);
                category.setSequenceNumber(count);
                categoryRepository.save(category);
                count++;
            }

            return "ok";
        }
}
