package br.com.jnetocurti.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(name = "BookMessageDrivenBean", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/booksQueue") })
public class BookMessageDrivenBean implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookMessageDrivenBean.class);

	@Override
	public void onMessage(Message message) {

		if (message instanceof TextMessage) {

			TextMessage textMessage = (TextMessage) message;

			try {

				LOGGER.info(String.format("A mensagem \" %s \" foi processada!",
						textMessage.getText()));

			} catch (JMSException e) {
				throw new RuntimeException("That was unexpected!", e);
			}
		}
	}
}