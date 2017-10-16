package br.com.jnetocurti.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import br.com.jnetocurti.jpa.model.Book;

@Remote
public interface BookServiceBean {

	Book saveBook(Book book);

	void removeBook(Long bookId);

	Book findBookById(Long bookId);

	Collection<Book> listAllBooks();

}
