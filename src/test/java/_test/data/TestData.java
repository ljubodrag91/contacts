package _test.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import com.model.Contact;
import com.services.ContactService;

public abstract class TestData {
	@SuppressWarnings("unused")
	@Autowired
	protected ContactService contactService;
	@SuppressWarnings("unused")
	@Autowired
	protected ElasticsearchTemplate esTemplate;
	
	// Test Data
	protected List<Contact> friendsOfFriends;
	protected List<Contact> friends;
	protected Contact contact;
	protected Contact contactTwo;
	
	@SuppressWarnings("serial")
	protected void initTestData() {
		friendsOfFriends = new ArrayList<Contact>() {
			{
				add(new Contact("2", "Lara", "23456789",null));
				add(new Contact("3", "Jack", "987654321",null));
			}
		};
		friends = new ArrayList<Contact>() {
			{
				add(new Contact("2", "Lara", "23456789",friendsOfFriends));
				add(new Contact("3", "Jack", "987654321",friendsOfFriends));
			}
		};
		
		contact = new Contact("1", "Bob", "12345678", friends);
		contactTwo = new Contact("4", "Billy", "55555555", friends);
	}
	
	
}
