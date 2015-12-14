package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Roteamento baseado no conteúdo (propriedade formato com valor ebook)
 * 
 * Execute as classes RegistraFinanceiroNoTopico, RegistraGeradorNoTopico e EnviaMensagemParOTopico
 * 
 * 
 * @author tca85
 *
 */
public class RegistraGeradorNoTopico {
	
	public static void main(String[] args) throws NamingException {
		InitialContext ic = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) ic.lookup("jms/RemoteConnectionFactory");
		
		Topic topico = (Topic) ic.lookup("jms/TOPICO.LIVRARIA");
		
		// usuário + senha para conectar no Jboss
		try(JMSContext context = factory.createContext("jms", "jms2")){
			context.setClientID("GeradorEbook");
			
			// dessa forma vai receber somente as mensagens que tiverem a propriedade formato, com valor ebook
			JMSConsumer consumer = context.createDurableConsumer(topico, "AssinaturaEbook", "formato='ebook'", false);
			
			consumer.setMessageListener(new TratadorDeMensagem());
			
			context.start();
			
			Scanner teclado = new Scanner(System.in);
			
			System.out.println("Gerador esperando as mensagens do tópico");
			System.out.println("Pressione enter para fechar a conexao");
			
			teclado.nextLine();
			teclado.close();
			context.stop();
		}
	}
}