package com.model;

import java.util.Arrays;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
@Document(indexName = "users" , type = "contacts")
public class Contact {
	@Id
	private String id;
	private String name;
	private String phone;
	private String[] friends;
	
	public Contact() {
		super();
	}

	public Contact(String id, String name, String phone, String[] friends) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.friends = friends;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String[] getFriends() {
		return friends;
	}

	public void setFriends(String[] friends) {
		this.friends = friends;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", name=" + name + ", phone=" + phone + ", friends=" + Arrays.toString(friends) + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Contact) {
			if(((Contact) obj).getId().equals(this.getId()) && 
					((Contact) obj).getName().equals(this.getName()) && 
						((Contact) obj).getPhone().equals(this.getPhone()) &&
							(Arrays.equals(((Contact) obj).getFriends(), this.getFriends())))
				return true;
			else
				return false;
		}else
			return false;
	}
}