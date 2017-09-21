package com.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.main.SpringBootFrameworkApplication;

import static org.junit.Assert.assertNotNull;

import org.elasticsearch.client.Client;
import _test.data.TestData;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootFrameworkApplication.class)
public class ElasticsearchConfigTest extends TestData{

	@Autowired
	Client client;
	
	@Test
	public void testClient() {
		assertNotNull(client);
	}
	
	@Test
	public void testElasticsearchTemplate() {
		assertNotNull(esTemplate);
	}
}
