package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @GetMapping("/gui/addressbooks")
    public String viewAllAddressBooks(Model model) {
        List<AddressBook> books = (List<AddressBook>) repo.findAll();
        model.addAttribute("addressBooks", books);
        return "addressbook";
    }

    @PostMapping("/gui/addressbooks")
    public String createAddressBook(@RequestParam String name) {
        AddressBook ab = new AddressBook();
        ab.setName(name);
        repo.save(ab);
        return "redirect:/gui/addressbooks/" + ab.getId();
    }

    @PostMapping("/gui/addressbooks/{id}/buddies")
    public String addBuddy(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String number,
            @RequestParam(required = false) String address) {

        AddressBook ab = repo.findById(id).orElseThrow();
        BuddyInfo buddy = new BuddyInfo(name, number, address);
        ab.addBuddy(buddy);
        repo.save(ab);

        return "redirect:/gui/addressbooks";
    }
}
