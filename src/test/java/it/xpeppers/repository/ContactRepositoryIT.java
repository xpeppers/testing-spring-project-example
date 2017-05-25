package it.xpeppers.repository;

import it.xpeppers.model.Contact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactRepositoryIT {

    @Autowired
    private ContactRepository repository;

    @Before
    public void setup() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void save_a_contact() throws Exception {
        Contact contact = new Contact();
        contact.setFirstName("First Name");
        contact.setLastName("Last Name");
        contact.setPhoneNumber("+39 329 654321");

        repository.save(contact);
        List<Contact> contacts = newArrayList(repository.findAll());

        assertThat(contacts, hasSize(1));
    }

    @Test
    public void find_a_contact_by_id() throws Exception {
        Contact contact = new Contact();
        contact.setFirstName("First Name");
        contact.setLastName("Last Name");
        contact.setPhoneNumber("+39 329 654321");

        Contact savedContact = repository.save(contact);
        Contact foundContact = repository.findOne(savedContact.getId());

        assertThat(foundContact, is(savedContact));
    }

    @Test
    public void delete_a_contact() throws Exception {
        Contact contact = new Contact();
        contact.setFirstName("First Name");
        contact.setLastName("Last Name");
        contact.setPhoneNumber("+39 329 654321");

        repository.save(contact);
        List<Contact> contacts = newArrayList(repository.findAll());

        assertThat(contacts, hasSize(1));

        repository.delete(contact);

        assertThat(newArrayList(repository.findAll()), is(empty()));
    }
}