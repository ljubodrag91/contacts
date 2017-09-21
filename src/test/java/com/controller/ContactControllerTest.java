package com.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.main.SpringBootFrameworkApplication;
import com.model.Contact;

import _test.data.TestData;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootFrameworkApplication.class)
public class ContactControllerTest extends TestData{

	@Autowired
	protected WebApplicationContext webApplicationContext;
	
	protected MockMvc mvc;
	
	protected void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Before
    public void before() {
	  	esTemplate.deleteIndex(Contact.class);
        esTemplate.createIndex(Contact.class);
        esTemplate.putMapping(Contact.class);
        esTemplate.refresh(Contact.class);
        setUp();
        super.initTestData();
    }
	
	@Test
	public void testGetAllContacts() throws Exception {
		contactService.save(contact);
		contactService.save(contactTwo);
		String uri = "/contact/search";
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		assertEquals(200, status);
		assertTrue(content.trim().length() > 0);
	}
	@Test
	public void testGetContact() throws Exception {
		contactService.save(contact);
		String uri = "/contact/search/{id}";
		String id = contact.getId();
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.get(uri,id)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		assertEquals(200, status);
		assertTrue(content.trim().length() > 0);
	}
	@Test
	public void testGetFriends() throws Exception {
		contactService.save(contact);
		String uri = "/contact/search/{id}/friends";
		String id = contact.getId();
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.get(uri,id)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		assertEquals(200, status);
		assertTrue(content.trim().length() > 0);
	}
	@Test
	public void testRegister() throws Exception {
		String json = new Gson().toJson(contact);
		String uri = "/contact/register";
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
				.andReturn();
		int status = result.getResponse().getStatus();
		
		assertEquals(201, status);
	}
	@Test
	public void testAddFriend() throws Exception {
		String uri = "/contact/{userId}/add/{friendId}";
		contactService.save(contact);
		contactService.save(contactTwo);
		String userId = contact.getId();
		String friendId = contact.getId();
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.post(uri,userId,friendId)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = result.getResponse().getStatus();
		
		assertEquals(200, status);
	}
	@Test
	public void testRemoveFriend() throws Exception {
		String uri = "/contact/{userId}/remove/{friendId}";
		contactService.save(contact);
		contactService.save(contactTwo);
		String userId = contact.getId();
		String friendId = contact.getId();
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.post(uri,userId,friendId)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = result.getResponse().getStatus();
		
		assertEquals(200, status);
	}
	@Test
	public void testUpdate_OK() throws Exception {
		contactService.save(contact);
		contact.setPhone(String.valueOf(Integer.MAX_VALUE));
		String uri = "/contact/update/{id}";
		String id = contact.getId();
		String json = new Gson().toJson(contact);
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.put(uri,id).contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
				.andReturn();
		int status = result.getResponse().getStatus();
		
		assertEquals(200, status);
	}
	@Test
	public void testDelete_OK() throws Exception {
		contactService.save(contact);
		String uri = "/contact/delete/{id}";
		String id = contact.getId();
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.delete(uri,id))
				.andReturn();
		int status = result.getResponse().getStatus();
		
		assertEquals(200, status);
	}
	
}
