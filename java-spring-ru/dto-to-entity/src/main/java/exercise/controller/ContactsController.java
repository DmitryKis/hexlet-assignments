package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import exercise.model.Contact;
import exercise.repository.ContactRepository;
import exercise.dto.ContactDTO;
import exercise.dto.ContactCreateDTO;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    // BEGIN
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO create(@RequestBody ContactCreateDTO body){
        var contact = toContact(toContactDTO(body));
        contactRepository.save(contact);
        return toContactDTO(contact);
    }

    private ContactDTO toContactDTO(ContactCreateDTO contactCreateDTO){
        var dto = new ContactDTO();
        dto.setFirstName(contactCreateDTO.getFirstName());
        dto.setLastName(contactCreateDTO.getLastName());
        dto.setPhone(contactCreateDTO.getPhone());
        return dto;
    }

    private Contact toContact(ContactDTO dto){
        var instance = new Contact();
        instance.setPhone(dto.getPhone());
        instance.setFirstName(dto.getFirstName());
        instance.setLastName(dto.getLastName());
        return instance;
    }

    private ContactDTO toContactDTO(Contact contact){
        var dto = new ContactDTO();
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setPhone(contact.getPhone());
        dto.setId(contact.getId());
        dto.setUpdatedAt(contact.getUpdatedAt());
        dto.setCreatedAt(contact.getCreatedAt());
        return dto;
    }
    // END
}
