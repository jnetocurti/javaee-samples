package br.com.jnetocurti.jaxrs.endpoint;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.jnetocurti.jaxrs.domain.Book;

@RunWith(Arquillian.class)
public class BooksTest {

	@Deployment
	public static WebArchive createDeployment() {

		return ShrinkWrap.create(WebArchive.class).addClasses(
				SampleApplication.class, Books.class, Book.class);
	}

	@Test
	@RunAsClient
	public void getBooksTest(@ArquillianResteasyResource WebTarget webTarget) {

		Response response = webTarget
				.path("library/books")
				.request()
				.get();

		Assert.assertEquals(200, response.getStatus());
	}

}
