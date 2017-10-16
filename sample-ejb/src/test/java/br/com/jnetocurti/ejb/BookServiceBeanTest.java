package br.com.jnetocurti.ejb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.jnetocurti.jpa.model.Book;

@RunWith(Arquillian.class)
@Transactional(value = TransactionMode.ROLLBACK)
public class BookServiceBeanTest {

	@EJB
	private BookServiceBean serviceBean;
	
	private Long bookId = null;
	
	@Deployment
	public static Archive<?> createDeployment() {
		
		return ShrinkWrap
				.create(WebArchive.class)
				.addPackages(true, BookServiceBean.class.getPackage())
				.addAsLibraries(getJPASampleDependency());
	}

	private static JavaArchive getJPASampleDependency() {
		
		JavaArchive jpa = Maven.resolver()
				.loadPomFromFile("pom.xml")
				.resolve("br.com.jnetocurti:sample-jpa:1.0")
				.withoutTransitivity().asSingle(JavaArchive.class);

		jpa.delete("/META-INF/persistence.xml");
		jpa.add(new ClassLoaderAsset("META-INF/persistence-test.xml"), "/META-INF/persistence.xml");
		
		return jpa;
	}
	
	@Before
	public void setup() {
		this.bookId = serviceBean.saveBook(createBook("Title", "Author")).getId();
	}

	@Test
	public void saveBookTest() {

		Book book = serviceBean.saveBook(createBook("Title save test",
				"Author save test"));

		assertNotNull(book);
		assertNotNull(book.getId());
		assertEquals("Title save test", book.getTitle());
		assertEquals("Author save test", book.getAuthor());
	}
	
	@Test
	public void updateBookTest() {

		Book book = new Book();
		book.setId(bookId);
		book.setTitle("Title update test");
		book.setAuthor("Author update test");

		Book altered = serviceBean.findBookById(bookId);

		assertNotNull(altered);
		assertEquals("Title update test", book.getTitle());
		assertEquals("Author update test", book.getAuthor());
	}

	@Test
	public void removeBookTest() {

		List<Book> books = (List<Book>) serviceBean.listAllBooks();
		assertNotNull(books);
		assertEquals(1, books.size());

		serviceBean.removeBook(bookId);

		books = (List<Book>) serviceBean.listAllBooks();
		assertEquals(0, books.size());
	}

	@Test
	public void findBookByIdTest() {

		Book book = serviceBean.findBookById(bookId);

		assertNotNull(book);
		assertEquals("Title", book.getTitle());
		assertEquals("Author", book.getAuthor());
	}

	@Test
	public void listAllBooksTest() {

		List<Book> books = (List<Book>) serviceBean.listAllBooks();

		assertNotNull(books);
		assertEquals(1, books.size());
		assertEquals("Title", books.get(0).getTitle());
		assertEquals("Author", books.get(0).getAuthor());
	}
	
	private Book createBook(String title, String author) {
		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		return book;
	}

}
