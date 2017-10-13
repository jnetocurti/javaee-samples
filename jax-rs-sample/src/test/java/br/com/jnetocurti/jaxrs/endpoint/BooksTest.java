package br.com.jnetocurti.jaxrs.endpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.jnetocurti.jaxrs.dto.BookDTO;

@RunWith(Arquillian.class)
public class BooksTest {

	private static final String BOOKS_PATH = "library/books";

	@Deployment
	public static WebArchive createDeployment() {

		return ShrinkWrap.create(WebArchive.class).addClasses(
				SampleApplication.class, Books.class, BookDTO.class);
	}

	@Test
	@RunAsClient
	@InSequence(1)
	public void createTest(@ArquillianResteasyResource WebTarget webTarget) {

		BookDTO book = BookDTO.builder().withTitle("Titulo")
				.withAuthor("Autor").build();

		Response response = webTarget.path(BOOKS_PATH).request()
				.post(Entity.json(book));

		assertEquals(201, response.getStatus());
	}

	@Test
	@RunAsClient
	@InSequence(2)
	public void readAllExpectedOneTest(
			@ArquillianResteasyResource WebTarget webTarget) {

		Response response = webTarget.path(BOOKS_PATH).request().get();

		final List<BookDTO> books = response
				.readEntity(new GenericType<List<BookDTO>>() {
				});

		assertNotNull(books);
		assertEquals(1, books.size());
		assertEquals(1, books.get(0).getId());
		assertEquals("Titulo", books.get(0).getTitle());
		assertEquals("Autor", books.get(0).getAuthor());
		assertEquals(200, response.getStatus());
	}

	@Test
	@RunAsClient
	@InSequence(3)
	public void updateTest(@ArquillianResteasyResource WebTarget webTarget) {

		BookDTO book = BookDTO.builder().withTitle("Titulo altered")
				.withAuthor("Autor altered").build();

		Response response = webTarget.path(BOOKS_PATH)
				.path(Integer.toString(1)).request().put(Entity.json(book));

		assertEquals(204, response.getStatus());
	}

	@Test
	@RunAsClient
	@InSequence(4)
	public void readOneTest(@ArquillianResteasyResource WebTarget webTarget) {

		Response response = webTarget.path(BOOKS_PATH)
				.path(Integer.toString(1)).request().get();

		BookDTO book = response.readEntity(BookDTO.class);

		assertNotNull(book);
		assertEquals(1, book.getId());
		assertEquals("Titulo altered", book.getTitle());
		assertEquals("Autor altered", book.getAuthor());
		assertEquals(200, response.getStatus());
	}

	@Test
	@RunAsClient
	@InSequence(5)
	public void deleteTest(@ArquillianResteasyResource WebTarget webTarget) {

		Response response = webTarget.path(BOOKS_PATH)
				.path(Integer.toString(1)).request().delete();

		assertEquals(204, response.getStatus());
	}

	@Test
	@RunAsClient
	@InSequence(6)
	public void readAllExpectedZeroTest(
			@ArquillianResteasyResource WebTarget webTarget) {

		Response response = webTarget.path(BOOKS_PATH).request().get();

		final List<BookDTO> books = response
				.readEntity(new GenericType<List<BookDTO>>() {
				});

		assertNotNull(books);
		assertEquals(0, books.size());
	}

}
