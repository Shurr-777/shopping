package com.example.shopping;
import com.example.shopping.entity.Cart;
import com.example.shopping.entity.data.Category;
import com.example.shopping.entity.data.Page;
import com.example.shopping.repository.CategoryRepository;
import com.example.shopping.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class Common {
    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @ModelAttribute
    public void sharedData(Model model, HttpSession session, Principal principal) {

        if (principal != null) {
            model.addAttribute("principalName", principal.getName());
            model.addAttribute("principal", principal);
        }

        List<Page> pages = pageRepository.findAllByOrderBySequenceNumberAsc();

        List<Category> categories = categoryRepository.findAllByOrderBySequenceNumberAsc();

        boolean cartActive = false;

        if (session.getAttribute("cart") != null) {
            HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");

            int size = 0;
            double total = 0;

            for (Cart value: cart.values()) {
                size += value.getQuantity();
                total += value.getQuantity() * Double.parseDouble(value.getPrice());
            }

            model.addAttribute("size", size);
            model.addAttribute("total", total);

            cartActive = true;
        }

        model.addAttribute("pages", pages);
        model.addAttribute("categories", categories);
        model.addAttribute("cartActive", cartActive);

    }
}
