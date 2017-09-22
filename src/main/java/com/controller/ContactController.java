package com.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.model.Contact;
import com.services.ContactService;

@RestController
@RequestMapping(value = "/contact")
public class ContactController {
	
    private ContactService contactService;
    
    @Autowired
    public ContactController(ContactService contactService) {
    	this.contactService = contactService;
    }
    
    @GetMapping(value = "/search",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contact> getAllContacts(){
		return contactService.getAllContacts();
    }
    
    @GetMapping(value = "/search/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Contact getContact(@PathVariable(value="id") String id){
		return contactService.findById(id);
    }
    
    @GetMapping(value = "/search/{id}/friends",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contact> getFriends(@PathVariable(value="id") String id){
		return contactService.getFriends(id);
    }
    
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody String json){
		Contact contact = new Gson().fromJson(json, Contact.class);
    	contactService.save(contact);
    	String location = "Location:\r\n 	/contact/search/"+contact.getId()+"";
    	return new ResponseEntity<String>(location,HttpStatus.CREATED);
    }
    @PostMapping(value = "/{userId}/add/{friendId}")
    public ResponseEntity<String> addFriend(@PathVariable(value="userId") String userId,@PathVariable(value="friendId") String friendId){
    	contactService.addFriend(userId, friendId);
    	return new ResponseEntity<String>(HttpStatus.OK);	
    }
    @PostMapping(value = "/{userId}/remove/{friendId}")
    public ResponseEntity<String> removeFriend(@PathVariable(value="userId") String userId,@PathVariable(value="friendId") String friendId){
    	contactService.removeFriend(userId, friendId);
    	return new ResponseEntity<String>(HttpStatus.OK);	
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<String> update(@PathVariable(value="id") String id,@RequestBody String json){
    	contactService.update(id, json);
		return new ResponseEntity<String>(HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable(value="id") String id){
    	contactService.delete(contactService.findById(id));
    	return new ResponseEntity<String>(HttpStatus.OK);
    }
}