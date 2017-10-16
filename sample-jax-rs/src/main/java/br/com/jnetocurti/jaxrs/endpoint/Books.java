package br.com.jnetocurti.jaxrs.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.com.jnetocurti.jaxrs.dto.BookDTO;

@Path(value = "library")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class Books {

	@Context
	private UriInfo uriInfo;

	private static int next = 1;

	private static List<BookDTO> books = new ArrayList<>();

	@GET
	@Path("/books")
	public Response read() {

		return Response.ok(books).build();
	}

	@GET
	@Path("/books/{id}")
	public Response read(@PathParam("id") int id) {

		BookDTO book = books.get(books.indexOf(BookDTO.builder().withId(id)
				.build()));

		return Response.ok(book).build();
	}

	@POST
	@Path("/books")
	public Response create(BookDTO book) {

		int id = next++;

		book.setId(id);
		books.add(book);

		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Integer.toString(id));

		return Response.created(builder.build()).build();
	}

	@PUT
	@Path("/books/{id}")
	public Response update(@PathParam("id") int id, BookDTO req) {

		BookDTO book = books.get(books.indexOf(BookDTO.builder().withId(id)
				.build()));

		book.setTitle(req.getTitle());
		book.setAuthor(req.getAuthor());

		return Response.noContent().build();
	}

	@DELETE
	@Path("/books/{id}")
	public Response delete(@PathParam("id") int id) {

		BookDTO book = books.get(books.indexOf(BookDTO.builder().withId(id)
				.build()));

		books.remove(book);

		return Response.noContent().build();
	}

}
