package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressBookController.class)
public class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressBookRepository repo;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createAddressBookTest() throws Exception {
        AddressBook mockAb = new AddressBook();

        when(repo.save(any(AddressBook.class)))
                .thenReturn(mockAb);
        mockAb.setId(1L);

        this.mockMvc.perform(post("/api/addressbooks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void addBuddyTest() throws Exception {
        long addressBookId = 1L;
        BuddyInfo buddy = new BuddyInfo("Alice", "555-1234", "Ottawa, ON");

        AddressBook ab = new AddressBook();
        ab.setId(addressBookId);

        when(repo.findById(addressBookId)).thenReturn(Optional.of(ab));

        when(repo.save(any(AddressBook.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/api/addressbooks/{id}/buddies", addressBookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buddy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(addressBookId))
                .andExpect(jsonPath("$.buddies[0].name").value("Alice"))
                .andExpect(jsonPath("$.buddies[0].number").value("555-1234"));
    }

    @Test
    public void removeBuddyTest() throws Exception {
        long addressBookId = 1L;
        long buddyId = 100L;

        BuddyInfo buddy = new BuddyInfo("Alice", "555-1234", "Ottawa, ON");
        buddy.setId(buddyId);

        AddressBook ab = new AddressBook();
        ab.setId(addressBookId);
        ab.addBuddy(buddy);

        when(repo.findById(addressBookId)).thenReturn(Optional.of(ab));

        when(repo.save(any(AddressBook.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(delete("/api/addressbooks/{id}/buddies/{buddyId}", addressBookId, buddyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(addressBookId))
                .andExpect(jsonPath("$.buddies").isEmpty());
    }
}

