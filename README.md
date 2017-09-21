#spring-boot-elasticseearch-REST</br>

Libraries: </br>
  
  Spring Boot - 1.5.7 </br>
  Spring Data Elasticsearch - Server version 2.4 </br>
  Swagger2  </br>
  Spring REST </br>
  
Endpoints: </br>
  GET 	/contact/search                     - get all contacts </br>
  GET		/contact/search/{id}                - get contact by contact id </br>
  GET 	/contact/{id}/friends               - get all friends by contact id </br>

  POST	/contact/{userId}/remove/{friendId} - remove friend </br>
  POST	/contact/{userId}/add/{friendId}    - add friend  </br>
  POST	/contact/register                   - register new contact </br>


  PUT		/contact/update/{id}                - update contact </br>
  
  DELETE  /contact/delete/{id}              - delete contact </br>
