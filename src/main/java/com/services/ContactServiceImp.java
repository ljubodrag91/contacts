package com.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.model.Contact;
import com.repositories.ContactRepository;

@Service
public class ContactServiceImp implements ContactService{

	
	private ContactRepository contactRepository;
	
	@Autowired
	public void setContactRepository(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}
	
    @Autowired
    private ElasticsearchOperations es;
    
	@Override
	public Contact save(Contact contact) {
		return contactRepository.save(contact);
	}

	@Override
	public Contact findById(String id) {
		return contactRepository.findOne(id);
	}

	@Override
	public Contact findByName(String name) {
		return contactRepository.findByName(name);
	}

	@Override
	public Contact findByPhone(String phone) {
		return contactRepository.findByPhone(phone);
	}

	@Override
	public List<Contact> getFriends(String userId) {
		String[] friendIds = contactRepository.findOne(userId).getFriends();
		List<Contact> friends = new ArrayList<>();
		for(int i = 0;i<friendIds.length;i++) {
			friends.add(contactRepository.findOne(friendIds[i]));
		}
		return friends;
	}

	@Override
	public void delete(Contact contact) {
		contactRepository.delete(contact);	
	}

	@Override
	public boolean update(String id, String content) {

		Contact contact = contactRepository.findOne(id);
		final UpdateQuery updateQuery;
		UpdateRequest updateRequest = null;
		int successful = 0;
		
		if(contact!=null) {
			
			try {
				String message = content;
				XContentParser parser = XContentFactory
						.xContent(XContentType.JSON)
						.createParser(message.getBytes());
				parser.close();
				
				updateRequest = new UpdateRequest();
			    updateRequest.index("users");
			    updateRequest.type("contacts");
			    updateRequest.id(id);
			    updateRequest.doc(XContentFactory.jsonBuilder()
			    		.copyCurrentStructure(parser)
			    		);
			}catch(IOException e) {
				return false;
			}
			try {
				updateQuery = new UpdateQueryBuilder().withId(id)
						.withClass(Contact.class).withUpdateRequest(updateRequest).build();
			}catch(NullPointerException e) {
				return false;
			}
			UpdateResponse response = es.update(updateQuery);
			successful = response.getShardInfo().getSuccessful();
		}else {
			return false;
		}
		if(successful > 0) {
			return true;
		}else {
			return false;
		}
	}
	@Override
	public List<Contact> getAllContacts() {
		return Lists.newArrayList(contactRepository.findAll());
	}
	@Override
	public void addFriend(String userId, String friendId) {
		Contact user = contactRepository.findOne(userId);
		Contact friend = contactRepository.findOne(friendId);
		List<String> temp = new ArrayList<>(Arrays.asList(user.getFriends()));
		temp.add(friend.getId());
		String[] array = new String[temp.size()];
		array = temp.toArray(array);

		user.setFriends(array);
		String json = new Gson().toJson(user);
		update(user.getId(), json);
	}
	@Override
	public void removeFriend(String userId, String friendId) {
		Contact user = contactRepository.findOne(userId);
		Contact friend = contactRepository.findOne(friendId);
		
		List<String> temp = new ArrayList<>(Arrays.asList(user.getFriends()));
		temp.remove(friend.getId());
		
		String[] array = new String[temp.size()];
		array = temp.toArray(array);

		user.setFriends(array);
		
		String json = new Gson().toJson(user);
		update(user.getId(), json);	
	}
}
