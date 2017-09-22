package com.config;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.main.SpringBootFrameworkApplication;
import org.elasticsearch.client.Client;
import _test.data.TestData;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootFrameworkApplication.class)
public class ElasticsearchConfigTest extends TestData{

	@Autowired
	protected Client client;
	
	@Test
	public void testClient() {
		assertThat(client, is(notNullValue()));
	}
	
	@Test
	public void testElasticsearchTemplate() {
		assertThat(esTemplate, is(notNullValue()));
	}
}