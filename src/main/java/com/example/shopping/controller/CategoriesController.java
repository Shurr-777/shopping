package com.example.shopping.controller;
import com.example.shopping.entity.data.Category;
import com.example.shopping.entity.data.Product;
import com.example.shopping.repository.CategoryRepository;
import com.example.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoriesController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{identificator}")
    public String category(@PathVariable String identificator, Model model, @RequestParam(value="page", required = false) Integer p) {

        int perPage = 6;
        int page = (p != null) ? p : 0;

        Pageable pageable = PageRequest.of(page, perPage);
        long count = 0;

        if (identificator.equals("all")) {
            Page<Product> products = productRepository.findAll(pageable);

            count = productRepository.count();

            model.addAttribute("products", products);
        } else {

            Category category = categoryRepository.findByIdentificator(identificator);

            if (category == null) {
                return "redirect:/";
            }

            int categoryId = category.getId();
            String categoryName = category.getName();
            List<Product> products = productRepository.findAllByCategoryId(Integer.toString(categoryId), pageable);

            count = productRepository.countByCategoryId(Integer.toString(categoryId));

            model.addAttribute("products", products);
            model.addAttribute("categoryName", categoryName);
        }

        double pageCount = Math.ceil((double)count/ (double)perPage);

        model.addAttribute("pageCount", (int)pageCount);
        model.addAttribute("perPage", perPage);
        model.addAttribute("count", count);
        model.addAttribute("page", page);

        return "products";
    }

}
