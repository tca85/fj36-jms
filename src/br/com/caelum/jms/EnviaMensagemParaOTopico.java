package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Publica mensagens no tópico do HornetQ
 * 
 * - Execute as classes RegistraFinanceiroNoTopico e EnviaMensagemParaOTopico e envie algumas mensagens
 * - Teste se o subscriber é durável
 *   - termine a execução do RegistraFinanceiroNoTopico
 *   - envie algumas mensagens do terminal da classe EnviaMensagemParaOTopico
 *   - rode a RegistraFinanceiroNoTopico para verificar se recebeu as mensagens
 * 
 * @author tca85
 *
 */
public class EnviaMensagemParaOTopico {
	public static void main(String[] args) throws NamingException {
		InitialContext ic = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) ic.lookup("jms/RemoteConnectionFactory");
		
		Topic topico = (Topic) ic.lookup("jms/TOPICO.LIVRARIA");
		
		// usuário e senha para conectar no Jboss jms / jms2
		try(JMSContext context = factory.createContext("jms", "jms2")){
			JMSProducer producer = context.createProducer();
			
			// cria o cabeçalho da mensagem
			producer.setProperty("formato", "ebook");
			
			Scanner scanner = new Scanner(System.in);
			
			while (scanner.hasNext()) {
				String msg = scanner.nextLine();
				
				producer.send(topico, "[Thiago Alves]: " + msg);
			}
			scanner.close();
		}
	}
}