package br.com.jnetocurti.jaxrs.resource;

import javax.ejb.EJB;
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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.com.jnetocurti.ejb.BookServiceBean;
import br.com.jnetocurti.jaxrs.dto.BookDTO;
import br.com.jnetocurti.jpa.model.Book;

@Path(value = "library")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class Books {

	@Context
	private UriInfo uriInfo;
	
	@EJB
	private BookServiceBean serviceBean; 

	@GET
	@Path("/books")
	public Response read() {

		return Response.ok(serviceBean.listAllBooks()).build();
	}

	@GET
	@Path("/books/{id}")
	public Response read(@PathParam("id") Long id) {

		Book book = serviceBean.findBookById(id);

		if (book == null) {
			return Response.noContent().status(Status.NOT_FOUND).build();
		}

		return Response.ok(serviceBean.findBookById(id)).build();
	}

	@POST
	@Path("/books")
	public Response create(BookDTO bookDTO) {

		Book book = serviceBean.saveBook(BookDTO.tooEntity(bookDTO));

		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Long.toString(book.getId()));

		return Response.created(builder.build()).build();
	}

	@PUT
	@Path("/books/{id}")
	public Response update(@PathParam("id") Long id, BookDTO bookDTO) {

		Book book = BookDTO.tooEntity(bookDTO);
		book.setId(id);

		serviceBean.saveBook(book);

		return Response.noContent().build();
	}

	@DELETE
	@Path("/books/{id}")
	public Response delete(@PathParam("id") Long id) {

		serviceBean.removeBook(id);

		return Response.noContent().build();
	}

}
