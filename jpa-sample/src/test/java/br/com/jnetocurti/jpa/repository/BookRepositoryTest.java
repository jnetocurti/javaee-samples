package br.com.jnetocurti.jpa.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.jnetocurti.jpa.model.Book;

@RunWith(Arquillian.class)
@Transactional(value = TransactionMode.ROLLBACK)
public class BookRepositoryTest {

	@Inject
	private BookRepository repository;
	
	private Long bookId = null;

	@Deployment
	public static Archive<?> createDeployment() {

		return ShrinkWrap
				.create(WebArchive.class)
				.addPackages(true, Book.class.getPackage(), BookRepository.class.getPackage())
				.addAsResource("META-INF/beans.xml", "META-INF/beans.xml")
				.addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml");
	}
	
	@Before
	public void setup() {
		this.bookId = repository.save(createBook("Title", "Author")).getId();
	}

	@Test
	public void saveTest() {

		Book book = repository.save(createBook("Title save test", "Author save test"));

		assertNotNull(book);
		assertNotNull(book.getId());
		assertEquals("Title save test", book.getTitle());
		assertEquals("Author save test", book.getAuthor());
	}	

	@Test
	public void findAll() {
		
		List<Book> books = (List<Book>) repository.findAll();

		assertNotNull(books);
		assertEquals(1, books.size());
		assertEquals("Title", books.get(0).getTitle());
		assertEquals("Author", books.get(0).getAuthor());
	}
	
	@Test
	public void updateTest() {
		
		Book book = createBook("Title update test", "Author update test");
		book.setId(this.bookId);
		repository.update(book);
		
		List<Book> books = (List<Book>) repository.findAll();

		assertNotNull(books);
		assertEquals(1, books.size());
		assertEquals("Title update test", books.get(0).getTitle());
		assertEquals("Author update test", books.get(0).getAuthor());
	}

	@Test
	public void deleteTest() {

		List<Book> books = (List<Book>) repository.findAll();
		assertNotNull(books);
		assertEquals(1, books.size());

		repository.delete(repository.findById(bookId));

		books = (List<Book>) repository.findAll();

		assertNotNull(books);
		assertEquals(0, books.size());
	}

	@Test
	public void deleteByIdTest() {

		List<Book> books = (List<Book>) repository.findAll();
		assertNotNull(books);
		assertEquals(1, books.size());

		repository.deleteById(bookId);

		books = (List<Book>) repository.findAll();

		assertNotNull(books);
		assertEquals(0, books.size());
	}

	@Test
	public void findByIdTest() {

		Book book = repository.findById(bookId);

		assertNotNull(book);
		assertEquals("Title", book.getTitle());
		assertEquals("Author", book.getAuthor());
	}

	private Book createBook(String title, String author) {
		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		return book;
	}

}
