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
import static it.xpeppers.model.ContactTest.aContact;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactRepositoryIT {

    @Autowired
    private ContactRepository repository;

    @Before
    public void setup() {
        repository.deleteAll();
    }

    @Test
    public void saves_a_contact() {
        Contact contact = aContact("First Name", "Last Name", "+39 329 654321");

        repository.save(contact);

        List<Contact> contacts = newArrayList(repository.findAll());
        assertThat(contacts, hasSize(1));
    }

    @Test
    public void finds_a_contact_by_id() {
        Contact contact = aContact("First Name", "Last Name", "+39 329 654321");
        Contact savedContact = repository.save(contact);

        Contact foundContact = repository.findOne(savedContact.getId());

        assertThat(foundContact, is(savedContact));
    }

    @Test
    public void deletes_a_contact() {
        Contact contact = aContact("First Name", "Last Name", "+39 329 654321");
        repository.save(contact);

        List<Contact> contacts = newArrayList(repository.findAll());
        assertThat(contacts, hasSize(1));

        repository.delete(contact);

        assertThat(newArrayList(repository.findAll()), is(empty()));
    }
}
