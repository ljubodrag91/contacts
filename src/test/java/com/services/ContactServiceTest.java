package com.services;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.google.gson.Gson;
import com.main.SpringBootFrameworkApplication;
import com.model.Contact;
import _test.data.TestData;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootFrameworkApplication.class)
public class ContactServiceTest extends TestData{

	@Before
    public void before() {
	  	esTemplate.deleteIndex(Contact.class);
        esTemplate.createIndex(Contact.class);
        esTemplate.putMapping(Contact.class);
        esTemplate.refresh(Contact.class);
        super.initTestData();
    }	
	@Test
	public void save() {
		Contact testContact = contactService.save(contact);
		
		assertThat(testContact.getId(), notNullValue());
		assertThat(contact.getName(), equalTo(testContact.getName()));
		assertThat(contact.getPhone(), equalTo(testContact.getPhone()));
        assertThat(contact.getFriends(), equalTo(testContact.getFriends()));
	}
	@Test
	public void findById() {
    	contactService.save(contact);
        Contact testContact = contactService.findById(contact.getId());	

		assertThat(testContact.getId(), notNullValue());
		assertThat(contact.getName(), equalTo(testContact.getName()));
		assertThat(contact.getPhone(), equalTo(testContact.getPhone()));
        assertThat(contact.getFriends(), equalTo(testContact.getFriends()));
	}
	@Test
	public void findByName() {
		contactService.save(contact);
        Contact testContact = contactService.findByName(contact.getName());

		assertThat(testContact.getId(), notNullValue());
		assertThat(contact.getName(), equalTo(testContact.getName()));
		assertThat(contact.getPhone(), equalTo(testContact.getPhone()));
        assertThat(contact.getFriends(), equalTo(testContact.getFriends()));
	}
	@Test
	public void findByPhone() {
		contactService.save(contact);
        Contact testContact = contactService.findByPhone(contact.getPhone());

		assertThat(testContact.getId(), notNullValue());
		assertThat(contact.getName(), equalTo(testContact.getName()));
		assertThat(contact.getPhone(), equalTo(testContact.getPhone()));
        assertThat(contact.getFriends(), equalTo(testContact.getFriends()));
	}
	@Test
	public void getFriends() {
		contactService.save(contact);
		contactService.save(contactTwo);
		List<Contact> friends = contactService.getFriends(contact.getId());
        List<Contact> retrievedFriends = contactService.getFriends(contact.getId());
        assertThat(friends, equalTo(retrievedFriends));
	}
	@Test
	public void delete() {
		contactService.save(contact);
    	contactService.delete(contact);
    	
    	Contact deletedContact = contactService.findById(contact.getId());
    	assertThat(deletedContact, nullValue());
	}
	@Test
	public void update() {
		contactService.save(contact);
		contact.setName(String.valueOf(Long.MAX_VALUE));
		String json = new Gson().toJson(contact);
		boolean successfull = contactService.update(contact.getId(), json);
		Contact updatedContact = contactService.findById(contact.getId());
		
		assertThat(successfull, equalTo(true));
		assertThat(updatedContact, equalTo(contact));
	}
	@Test
	public void findAll() {
		contactService.save(contact);
		contactService.save(contactTwo);
		
		assertThat(contactService.getAllContacts(), hasSize(2));
	}
	@Test
	public void removeFriend() {
		contactService.save(contact);
		contactService.save(contactTwo);
		contactService.addFriend(contact.getId(), contactTwo.getId());
		contactService.removeFriend(contact.getId(), contactTwo.getId());
		
		assertThat(contactService.findById(contact.getId()).getFriends(), equalTo(contact.getFriends()));
	}

	@Test
	public void addFriend() {
		contactService.save(contact);
		contactService.save(contactTwo);
		contactService.addFriend(contact.getId(), contactTwo.getId());
		
		assertThat(Arrays.asList(contactService.findById(contact.getId()).getFriends()), hasItems(contactTwo.getId()));
	}
}