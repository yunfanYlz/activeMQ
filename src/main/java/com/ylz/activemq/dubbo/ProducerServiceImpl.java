package com.ylz.activemq.dubbo;

import javax.jms.Destination;  
import javax.jms.JMSException;  
import javax.jms.Message;  
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;  
import org.springframework.jms.core.MessageCreator;  
import org.springframework.stereotype.Service;

import com.ylz.activemq.service.ProducerService;  
   
@Service(value="ProducerService")
public class ProducerServiceImpl implements ProducerService {  
   
	@Autowired
	@Qualifier("jmsTemplate")
    private JmsTemplate jmsTemplate;  
      
    public void sendMessage(Destination destination, final String message) {  
        System.out.println("---------------生产者发送消息-----------------");  
        System.out.println("---------------生产者发了一个消息：" + message);  
        jmsTemplate.send(destination, new MessageCreator() {  
            public Message createMessage(Session session) throws JMSException {  
                return session.createTextMessage(message);  
            }  
        });  
    }   
  
} 
