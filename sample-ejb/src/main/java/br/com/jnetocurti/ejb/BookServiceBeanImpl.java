package br.com.jnetocurti.ejb;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.jnetocurti.jpa.model.Book;
import br.com.jnetocurti.jpa.repository.BookRepository;

@Stateless
public class BookServiceBeanImpl implements BookServiceBean {

	@Inject
	private BookRepository bookRepository;

	@Override
	public Book saveBook(Book book) {

		if (book.getId() == null) {
			bookRepository.save(book);
		} else {
			bookRepository.update(book);
		}

		return book;
	}

	@Override
	public void removeBook(Long bookId) {

		bookRepository.deleteById(bookId);
	}

	@Override
	public Book findBookById(Long bookId) {

		return bookRepository.findById(bookId);
	}

	@Override
	public Collection<Book> listAllBooks() {

		return bookRepository.findAll();
	}

}