package uz.pdp.spring_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.pdp.spring_boot.criteria.GenericCriteria;
import uz.pdp.spring_boot.dto.auth.UserCreateDto;
import uz.pdp.spring_boot.services.auth.UserService;

@Controller
public class UserController extends AbstractController<UserService> {

    @Autowired
    public UserController(UserService service) {
        super(service);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @RequestMapping(value = "/user/admin/", method = RequestMethod.GET)
    public String adminPage() {
        return "user/admin";
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @RequestMapping(value = "/admin/create/", method = RequestMethod.GET)

    public String createPage(Model model) {
        model.addAttribute("authRole");
        return "admin/create";
    }

    @RequestMapping(value = "/admin/create/", method = RequestMethod.POST)
    public String create(@ModelAttribute UserCreateDto dto) {
        service.create(dto);
        return "redirect:/";
    }

    @RequestMapping(value = "/admin/list/", method = RequestMethod.GET)
    public String listPage(Model model) {
        model.addAttribute("users", service.getAll(new GenericCriteria()));
        return "admin/list";
    }
}

