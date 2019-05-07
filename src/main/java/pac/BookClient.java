/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac;

import com.google.gson.Gson;
import java.io.IOException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.util.Arrays;
import java.util.List;

public class BookClient {

    private static ClientConfig config;
    private static Client client;
    private static WebResource service;

    public static void main(String[] args) throws IOException {

        config = new DefaultClientConfig();
        client = Client.create(config);
        //skapar web resurs för webservice-programmet
        service = client.resource(
                UriBuilder.fromUri("http://localhost:8080/REST_books_withweb").build());
        BookClient bookClient = new BookClient();
        WebResource.Builder builder = bookClient.createBuilderXMLallBooks();
        String xmlString = builder.get(String.class);
        System.out.println("Books as XML:");
        System.out.println(xmlString);
        System.out.println("Book objects:");
        //hämta book objekt
        Book[] bookArray = builder.get(Book[].class);
        for (Book b : bookArray) {
            System.out.println(b);
        }
        bookClient.callJSON();
        bookClient.callPost();
        bookClient.callDelete();
    }

    private void callJSON() {
        Gson gson = new Gson();
        WebResource.Builder builder1 = createBuilderJSONallBooks("rest/BookService/bookj/1");
        String jsonBook = builder1.get(String.class);
        System.out.println("1 book as JSON:");
        System.out.println(jsonBook);
        System.out.println("1 book recreated from JSON:");
        Book book = gson.fromJson(jsonBook, Book.class);
        System.out.println("Book info, id: " + book.getId() + " title: "
                + book.getTitle() + " author: " + book.getAuthor() + "\n");
        //Getting all books
        WebResource.Builder builder2 = createBuilderJSONallBooks("rest/BookService/booksJSON");
        String jsonString2 = builder2.get(String.class);
        System.out.println("Books as JSON:");
        System.out.println(jsonString2);
        System.out.println("Books deserialized from JSON:");
        Book[] books = gson.fromJson(jsonString2, Book[].class);
        for (Book b : books) {
            System.out.println(b);
        }
        System.out.println("1 book turned into JSON again");
        //COnvert POJO to JSON
        Book myBook = new Book(13, "4 Hour WorkWeek", "Tim Ferriss");
        String convertedBook = gson.toJson(myBook);
        System.out.println(convertedBook);
 }
    
    private void callPost()
    {
    Book book = new Book(8, "Designing data intensive applications", "Marin Kleppman");
    ClientResponse response = service.path("rest/BookService/book/add")
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, book);
    System.out.println("Response from post: ");   
    System.out.println("Response " + response.getEntity(String.class));
    }
    
    private void callDelete()
    {
    ClientResponse response = service.path("rest/BookService/book/1/delete")
                .accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
    System.out.println("Response from delete: ");   
    System.out.println("Response " + response.getEntity(String.class));
    }

    private WebResource.Builder createBuilderXMLallBooks() {
        //skapar ny webresurs genom att lägga till sökväg till webresursen för webservice-programmet
        //den nya webresursen är för webservice-metoden
        WebResource resource = service.path("rest/BookService/books");
        //lägger till korrekt format av mediatyp, MIME TYP
        WebResource.Builder builder = resource.accept(MediaType.APPLICATION_XML);
        //anropar get-metod. Datatyp på svaret (response) specificeras
        return builder;
    }

    private WebResource.Builder createBuilderJSONallBooks(String extrapath) {
        WebResource resource = service.path(extrapath);//"rest/BookService/booksJSON"
        WebResource.Builder builder = resource.accept(MediaType.APPLICATION_JSON);
        return builder;
    }
}
