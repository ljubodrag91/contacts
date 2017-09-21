package _test.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import com.model.Contact;
import com.services.ContactService;

public abstract class TestData {
	@Autowired
	protected ContactService contactService;
	@Autowired
	protected ElasticsearchTemplate esTemplate;
	
	// Test Data
	protected String[] friends;
	protected String[] friendsTwo;
	protected Contact contact;
	protected Contact contactTwo;
	
	protected void initTestData() {
		friends = new String[]{"4"};
		friendsTwo = new String[]{"1"};
		contact = new Contact("1", "Bob", "12345678", friends);
		contactTwo = new Contact("4", "Billy", "55555555", friends);
	}
	
	
}
