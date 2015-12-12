package br.com.caelum.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Tratamento das mensagens enviadas pelos emissores
 * 
 * @author tca85
 *
 */
public class TratadorDeMensagem implements MessageListener{

	@Override
	public void onMessage(Message msg) {
		TextMessage textMessage = (TextMessage) msg;
		
		try {
			System.out.println("Tratador recebendo a mensagem: " + textMessage.getText());
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}