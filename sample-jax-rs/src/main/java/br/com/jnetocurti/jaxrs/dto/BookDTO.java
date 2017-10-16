package br.com.jnetocurti.jaxrs.dto;

import org.modelmapper.ModelMapper;

import br.com.jnetocurti.jpa.model.Book;

public class BookDTO {

	private Long id;

	private String title;

	private String author;

	public BookDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public static Book tooEntity(BookDTO dto) {

		ModelMapper mapper = new ModelMapper();

		return mapper.map(dto, Book.class);
	}

}
