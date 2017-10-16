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
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.jnetocurti.jaxrs.dto.BookDTO;
import br.com.jnetocurti.jaxrs.resource.Books;

@RunWith(Arquillian.class)
public class BooksTest {

	private static final String BOOKS_PATH = "library/books";
	
	@Deployment
	public static WebArchive createDeployment() {

		WebArchive archive = ShrinkWrap
				.create(WebArchive.class)
				.addPackages(true, Books.class.getPackage(), BookDTO.class.getPackage());

		JavaArchive javaArchive = Maven.resolver()
				.loadPomFromFile("pom.xml").resolve("br.com.jnetocurti:sample-ejb:1.0")
				.withoutTransitivity().asSingle(JavaArchive.class);

		archive.addAsLibraries(javaArchive);

		javaArchive = Maven.resolver()
				.loadPomFromFile("pom.xml").resolve("br.com.jnetocurti:sample-jpa:1.0")
				.withoutTransitivity().asSingle(JavaArchive.class);

		javaArchive.delete("/META-INF/persistence.xml");
		javaArchive.add(new ClassLoaderAsset("META-INF/persistence-test.xml"), "/META-INF/persistence.xml");

		archive.addAsLibraries(javaArchive);
		
		javaArchive = Maven.resolver()
				.loadPomFromFile("pom.xml").resolve("org.modelmapper:modelmapper:1.1.1")
				.withoutTransitivity().asSingle(JavaArchive.class);

		archive.addAsLibraries(javaArchive);

		return archive;
	}

	@Test
	@RunAsClient
	@InSequence(1)
	public void createTest(@ArquillianResteasyResource WebTarget webTarget) {

		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle("Titulo");
		bookDTO.setAuthor("Autor");

		Response response = webTarget.path(BOOKS_PATH).request()
				.post(Entity.json(bookDTO));
		
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
		assertEquals("Titulo", books.get(0).getTitle());
		assertEquals("Autor", books.get(0).getAuthor());
		assertEquals(200, response.getStatus());
	}

	@Test
	@RunAsClient
	@InSequence(3)
	public void updateTest(@ArquillianResteasyResource WebTarget webTarget) {

		BookDTO book = new BookDTO();
		book.setTitle("Titulo altered");
		book.setAuthor("Autor altered");

		Response response = webTarget.path(BOOKS_PATH)
				.path(getBookId(webTarget).toString()).request()
				.put(Entity.json(book));

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
		assertEquals("Titulo altered", book.getTitle());
		assertEquals("Autor altered", book.getAuthor());
		assertEquals(200, response.getStatus());
	}

	@Test
	@RunAsClient
	@InSequence(5)
	public void deleteTest(@ArquillianResteasyResource WebTarget webTarget) {

		Response response = webTarget.path(BOOKS_PATH)
				.path(getBookId(webTarget).toString()).request().delete();

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
	
	private Long getBookId(WebTarget webTarget) {

		Response response = webTarget.path(BOOKS_PATH).request().get();

		final List<BookDTO> books = response
				.readEntity(new GenericType<List<BookDTO>>() {
				});
		
		return books.get(0).getId();
	}

}
