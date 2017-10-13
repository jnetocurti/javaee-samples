package br.com.jnetocurti.jaxrs.endpoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.jnetocurti.jaxrs.domain.Book;

@Path(value = "library")
public class Books {

	private static int next = 1;

	private static List<Book> books = new ArrayList<>();

	@GET
	@Path("/books")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Collection<Book> getBooks() {
		return books;
	}

	@GET
	@Path("/books/{id}")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Book getBook(@PathParam("id") int id) {

		return books.get(books.indexOf(Book.builder().withId(id).build()));
	}

	@POST
	@Path("/books")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Book addBook(Book book) {

		book.setId(next++);

		books.add(book);

		return book;
	}

	@PUT
	@Path("/books/{id}")
	@Consumes(value = MediaType.APPLICATION_JSON)
	public void updateBook(@PathParam("id") int id, Book req) {

		Book book = books.get(books.indexOf(Book.builder().withId(id).build()));

		book.setTitle(req.getTitle());
		book.setAuthor(req.getAuthor());
	}

	@DELETE
	@Path("/books/{id}")
	public void removeBook(@PathParam("id") int id) {

		Book book = books.get(books.indexOf(Book.builder().withId(id).build()));

		books.remove(book);
	}

}
