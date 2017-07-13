package com.ylz.activemq;

import javax.jms.Destination;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ylz.activemq.service.ProducerService;


public class MQStart {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring-config.xml" });
			context.start();
			Destination destination = (Destination) context.getBean("topicDestination");
			ProducerService producerService = (ProducerService)context.getBeanFactory().getBean("ProducerService");
			for(int i=1; i <= 10 ; i++){
				producerService.sendMessage(destination,"No "+i);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
