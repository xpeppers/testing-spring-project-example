package it.xpeppers.controller;

import it.xpeppers.model.Contact;
import it.xpeppers.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactRepository repository;

    @Autowired
    public ContactController(ContactRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<Contact> all() {
        return repository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Contact withId(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Contact contact) {
        Contact savedContact = repository.save(contact);
        return ResponseEntity
                .created(uriFor(savedContact))
                .build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @Valid @RequestBody Contact update) {
        Contact contact = repository.findOne(id);

        contact.update(update);

        repository.save(contact);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Contact contact = repository.findOne(id);

        repository.delete(contact);

        return ResponseEntity
                .noContent()
                .build();
    }

    private URI uriFor(Contact savedContact) {
        return URI.create("/contacts/" + savedContact.getId());
    }

}
