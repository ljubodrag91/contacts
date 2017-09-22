package com.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(indexName = "users" , type = "contacts")
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @AllArgsConstructor
public class Contact {
	@Id
	private String id;
	private String name;
	private String phone;
	private String[] friends;
}