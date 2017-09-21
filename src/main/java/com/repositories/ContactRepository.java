package com.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import com.model.Contact;

@Repository
public interface ContactRepository extends ElasticsearchRepository<Contact, String>{
	
	public Contact findByName(String name);
	
	public Contact findByPhone(String phone);
	
}
