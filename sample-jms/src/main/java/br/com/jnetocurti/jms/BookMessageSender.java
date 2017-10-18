package br.com.jnetocurti.jms;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;

public class BookMessageSender {

	@Inject
	private JMSContext jmsContext;

	@Resource(lookup = "java:/jms/queue/booksQueue")
	private Destination booksQueue;

	public void send(String bookTitle) {

		jmsContext.createProducer().send(booksQueue, bookTitle);
	}

}
