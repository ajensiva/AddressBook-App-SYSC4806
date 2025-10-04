package org.example;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )

    private List<BuddyInfo> buddies = new ArrayList<>();
    private String name;

    public AddressBook() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public List<BuddyInfo> getBuddies() { return buddies; }

    public void addBuddy(BuddyInfo b) {
        buddies.add(b);
    }
    public void removeBuddy(BuddyInfo b) {
        buddies.remove(b);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}