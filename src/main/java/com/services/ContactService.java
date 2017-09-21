package com.services;

import java.util.List;

import com.model.Contact;

public interface ContactService {

    Contact save(Contact contact);

	Contact findById(String id);

	Contact findByName(String name);
	
	Contact findByPhone(String phone);
	
	List<Contact> getFriends(String id);
	
	void addFriend(String userId,String friendId);
	
	void removeFriend(String userId,String friendId);
	
	void delete(Contact contact);
	
	boolean update(String id,String contact);
	
	List<Contact> getAllContacts();
}
