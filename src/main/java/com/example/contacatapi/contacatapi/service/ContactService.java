package com.example.contacatapi.contacatapi.service;

import com.example.contacatapi.contacatapi.DTO.ContactRequest;
import com.example.contacatapi.contacatapi.DTO.ContactResponse;
import com.example.contacatapi.contacatapi.exception.ContactNotFoundException;
import com.example.contacatapi.contacatapi.exception.UserNotFoundException;
import com.example.contacatapi.contacatapi.model.Contact;
import com.example.contacatapi.contacatapi.model.User;
import com.example.contacatapi.contacatapi.repository.ContactRepository;
import com.example.contacatapi.contacatapi.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    // list all contacts using pagination and sorting
    public Page<ContactResponse> listContacts(Long userId, int page, int size, String sortBy) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user with ID: " + userId + " does`t exist"));
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Contact> contactPage  = contactRepository.findAllByUser(user, pageable);
        return contactPage
                .map(c -> new ContactResponse(
                        c.getId(), c.getFirstName(), c.getLastName(), c.getPhoneNumber(), c.getEmail(), c.getBirthDate())
                );

    }


    //    public List<ContactResponse> listContacts(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException("user with ID: " + userId + " does`t exist"));
//        List<Contact> contacts = contactRepository.findByUser(user);
//
//        return contacts
//                .stream()
//                .map(c -> new ContactResponse(
//                        c.getId(), c.getFirstName(), c.getLastName(), c.getPhoneNumber(), c.getEmail(), c.getBirthDate())
//                )
//                .collect(Collectors.toList());
//    }
//
//    public ContactResponse getContactById(Long userId, Long contactId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException("user with ID: " + userId + " does`t exist"));
//        Contact contact = contactRepository.findByIdAndUser(contactId, user)
//                .orElseThrow(() -> new ContactNotFoundException("Contact with ID: " + contactId + " does`t exist"));
//        return mapToResponse(contact);
//
//    }
    public ContactResponse getContactById(Long userId, Long contactId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user with ID: " + userId + " does`t exist"));
        Contact contact = contactRepository.findByIdAndUser(contactId, user)
                .orElseThrow(() -> new ContactNotFoundException("Contact with ID: " + contactId + " does`t exist"));
        return mapToResponse(contact);

    }

    @Transactional
    public ContactResponse addContact(Long userId, ContactRequest contactRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user with ID: " + userId + " does`t exist"));
        Contact contact = new Contact();
        contact.setFirstName(contactRequest.getFirstName());
        contact.setLastName(contactRequest.getLastName());
        contact.setEmail(contactRequest.getEmail());
        contact.setPhoneNumber(contactRequest.getPhone());
        contact.setBirthDate(contactRequest.getBirthDate());
        contact.setUser(user);
        Contact savedContact = contactRepository.save(contact);

        return mapToResponse(savedContact);

    }

    @Transactional
    public void deleteContact(Long userId, Long contactId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user with ID: " + userId + " does`t exist"));
        Contact contact = contactRepository.findByIdAndUser(contactId, user)
                .orElseThrow(() -> new ContactNotFoundException("Contact with ID: " + contactId + " does`t exist"));
        contactRepository.delete(contact);
    }

    private ContactResponse mapToResponse(Contact contact) {
        return new ContactResponse(
                contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getPhoneNumber(),
                contact.getEmail(),
                contact.getBirthDate()
        );

    }


}