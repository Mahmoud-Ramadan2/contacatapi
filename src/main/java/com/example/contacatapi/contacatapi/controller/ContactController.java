package com.example.contacatapi.contacatapi.controller;


import com.example.contacatapi.contacatapi.DTO.ContactRequest;
import com.example.contacatapi.contacatapi.DTO.ContactResponse;
import com.example.contacatapi.contacatapi.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    // Add a new contact
    @PostMapping("/{userId}")
    public ResponseEntity<ContactResponse> addContact(@PathVariable Long userId, @Valid @RequestBody ContactRequest contactRequest) {
        ContactResponse contactResponse = contactService.addContact(userId, contactRequest);
        return new ResponseEntity<>(contactResponse, HttpStatus.CREATED);
    }

    // List all contacts for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<ContactResponse>> listContacts(@PathVariable Long userId) {
        List<ContactResponse> contactList = contactService.listContacts(userId);
        return new ResponseEntity<>(contactList, HttpStatus.OK);
    }

    // Retrieve a contact by ID
    @GetMapping("/{userId}/{contactId}")
    public ResponseEntity<ContactResponse> getContactById(@PathVariable Long userId, @PathVariable Long contactId) {
        ContactResponse contactResponse = contactService.getContactById(userId, contactId);
        return new ResponseEntity<>(contactResponse, HttpStatus.OK);
    }

    // Delete a contact
    @DeleteMapping("/{userId}/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long userId, @PathVariable Long contactId) {
        contactService.deleteContact(userId, contactId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

