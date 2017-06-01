package it.xpeppers.repository;

import it.xpeppers.model.Contact;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = ContactRepositoryIT.Initializer.class)
public class ContactRepositoryIT {

    @ClassRule
    public static final PostgreSQLContainer POSTGRES = new PostgreSQLContainer();

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

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            EnvironmentTestUtils.addEnvironment("testcontainers", configurableApplicationContext.getEnvironment(),
                    "spring.datasource.url=" + POSTGRES.getJdbcUrl(),
                    "spring.datasource.username=" + POSTGRES.getUsername(),
                    "spring.datasource.password=" + POSTGRES.getPassword(),
                    "spring.jpa.generate-ddl=true"
            );
        }
    }
}