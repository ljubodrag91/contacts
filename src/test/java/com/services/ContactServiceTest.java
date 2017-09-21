package com.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

        assertNotNull(testContact.getId());
        assertEquals(testContact.getName(), contact.getName());
        assertEquals(testContact.getPhone(), contact.getPhone());
        assertEquals(testContact.getFriends(), contact.getFriends());
	}

	@Test
	public void findById() {
    	contactService.save(contact);
        Contact testContact = contactService.findById(contact.getId());	

        assertNotNull(testContact.getId());
        assertEquals(testContact.getName(), contact.getName());
        assertEquals(testContact.getPhone(), contact.getPhone());
        assertEquals(testContact.getFriends(), contact.getFriends());
	}

	@Test
	public void findByName() {
		contactService.save(contact);
        Contact testContact = contactService.findByName(contact.getName());

        assertNotNull(testContact.getId());
        assertEquals(testContact.getName(), contact.getName());
        assertEquals(testContact.getPhone(), contact.getPhone());
        assertEquals(testContact.getFriends(), contact.getFriends());
	}

	@Test
	public void findByPhone() {
		contactService.save(contact);
        Contact testContact = contactService.findByPhone(contact.getPhone());

        assertNotNull(testContact.getId());
        assertEquals(testContact.getName(), contact.getName());
        assertEquals(testContact.getPhone(), contact.getPhone());
        assertEquals(testContact.getFriends(), contact.getFriends());
	}

	@Test
	public void getFriends() {
		contactService.save(contact);
        List<Contact> testContact = contactService.getFriends(contact.getId());

        for (int i = 0;i < testContact.size();i++) {
        	assertEquals(testContact.get(i), contact.getFriends().get(i));
		}
	}

	@Test
	public void addFriend() {
		contactService.save(contact);
		contactService.save(contactTwo);
		
		contactService.addFriend(contact.getId(), contactTwo.getId());
		
		assertTrue(contact.getFriends().size() < contactService.findById(contact.getId()).getFriends().size());
		assertTrue(contactService.findById(contact.getId()).getFriends().contains(contactTwo));
	}

	@Test
	public void delete() {
		contactService.save(contact);
    	contactService.delete(contact);
    	Contact testContact = contactService.findById(contact.getId());
    	
        assertNull(testContact);	
	}
	
	@Test
	public void update() {
		contactService.save(contact);
		contact.setName(String.valueOf(Long.MAX_VALUE));
		String json = new Gson().toJson(contact);
		boolean successfull = contactService.update(contact.getId(), json);
		Contact updated = contactService.findById(contact.getId());
		
		assertTrue(successfull);
		assertEquals(contact, updated);	
	}
	
	@Test
	public void findAll() {
		contactService.save(contact);
		contactService.save(contactTwo);
		
		assertTrue(contactService.getAllContacts().size() == 2);	
	}
	
	@Test
	public void removeFriend() {
		contactService.save(contact);
		contactService.save(contactTwo);
		contactService.addFriend(contact.getId(), contactTwo.getId());
		contactService.removeFriend(contact.getId(), contactTwo.getId());
		
		assertTrue(!contactService.findById(contact.getId()).getFriends().contains(contactTwo));
	}
}
