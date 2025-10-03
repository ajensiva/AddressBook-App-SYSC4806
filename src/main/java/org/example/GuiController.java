package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GuiController {

    private final AddressBookRepository repo;

    public GuiController(AddressBookRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/gui/addressbooks/{id}")
    public String viewAddressBook(@PathVariable Long id, Model model) {
        AddressBook ab = repo.findById(id).orElseThrow();
        model.addAttribute("addressBook", ab);
        return "addressbook";
    }
}
