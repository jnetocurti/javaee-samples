package br.com.jnetocurti.jaxrs.domain;

import javax.annotation.Generated;

public class Book {

	private int id;

	private String title;

	private String author;

	public Book() {
	}

	@Generated("SparkTools")
	private Book(Builder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.author = builder.author;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * Creates builder to build {@link Book}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Book}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private int id;
		private String title;
		private String author;

		private Builder() {
		}

		public Builder withId(int id) {
			this.id = id;
			return this;
		}

		public Builder withTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder withAuthor(String author) {
			this.author = author;
			return this;
		}

		public Book build() {
			return new Book(this);
		}
	}

}
