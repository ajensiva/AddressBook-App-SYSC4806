package org.example;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/addressbooks")
public class AddressBookController {

    private final AddressBookRepository repo;

    public AddressBookController(AddressBookRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public AddressBook createAddressBook() {
        AddressBook ab = new AddressBook();
        return repo.save(ab);
    }

    @PostMapping("/{id}/buddies")
    public AddressBook addBuddy(@PathVariable Long id, @RequestBody BuddyInfo buddy) {
        AddressBook ab = repo.findById(id).orElseThrow();
        ab.addBuddy(buddy);
        return repo.save(ab);
    }

    @DeleteMapping("/{id}/buddies/{buddyId}")
    public AddressBook removeBuddy(@PathVariable Long id, @PathVariable Long buddyId) {
        AddressBook ab = repo.findById(id).orElseThrow();
        ab.getBuddies().removeIf(buddy -> buddy.getId().equals(buddyId));
        return repo.save(ab);
    }

    @GetMapping("/{id}")
    public AddressBook getAddressBook(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @GetMapping("/")
    public RedirectView redirectToGui() {
        AddressBook ab = StreamSupport.stream(repo.findAll().spliterator(), false)
                .findFirst()
                .orElseGet(this::createAddressBook);

        return new RedirectView("/gui/addressbooks/" + ab.getId());
    }
}

