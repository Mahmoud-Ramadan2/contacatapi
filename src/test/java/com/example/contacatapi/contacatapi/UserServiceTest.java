package com.example.contacatapi.contacatapi;

import com.example.contacatapi.contacatapi.DTO.ContactRequest;
import com.example.contacatapi.contacatapi.DTO.ContactResponse;
import com.example.contacatapi.contacatapi.exception.UserNotFoundException;
import com.example.contacatapi.contacatapi.model.Contact;
import com.example.contacatapi.contacatapi.model.User;
import com.example.contacatapi.contacatapi.repository.ContactRepository;
import com.example.contacatapi.contacatapi.repository.UserRepository;
import com.example.contacatapi.contacatapi.service.ContactService;
import com.example.contacatapi.contacatapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private UserService userService;
    @InjectMocks
    private ContactService contactService;


    @Test
    void testGetUserByEmail() {
        //Arrange
        String testEmail = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(testEmail);
        when(userRepository.getUserByEmail(testEmail))
                .thenReturn(Optional.of(mockUser));
//Act
        User result = userService.getUserByEmail(testEmail);

        // assert & verify
        assertNotNull(result);
        assertEquals(testEmail, result.getEmail());
        verify(userRepository, times(1)).getUserByEmail(testEmail);
    }

    @Test
    public void testAddContactSuccessfully() {
        //Arrange
        Long userId = 1L;
        User mockUser = new User();
mockUser.setId(userId);
        ContactRequest contactRequest = new ContactRequest();
        contactRequest.setFirstName("Hany");
        contactRequest.setLastName("Mohamed");
        contactRequest.setEmail("Hany@temp.com");
        contactRequest.setPhone("011234575");
        contactRequest.setBirthDate(LocalDate.of(1990,1,2));

        Contact mockContact = new Contact();
        mockContact.setId(1L);
        mockContact.setFirstName(contactRequest.getFirstName());
        mockContact.setLastName(contactRequest.getLastName());
        mockContact.setEmail(contactRequest.getEmail());
        mockContact.setPhoneNumber(contactRequest.getPhone());
        mockContact.setBirthDate(contactRequest.getBirthDate());
        mockContact.setUser(mockUser);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(contactRepository.save(any(Contact.class))).thenReturn(mockContact);

        //Act
        ContactResponse result = contactService.addContact(userId, contactRequest);

        // assert & verify
        assertNotNull(result);
        assertEquals(contactRequest.getFirstName(), result.getFirstName());
        assertEquals(contactRequest.getLastName(), result.getLastName());
        assertEquals(contactRequest.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findById(userId);
        verify(contactRepository, times(1)).save(any(Contact.class));







    }



}
