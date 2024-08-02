package com.knowit.commentService.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowit.commentService.entity.EmailRequest;


@Service

public class KafkaProducer {
	
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	public boolean sendMail(EmailRequest emailRequest) throws JsonProcessingException
	{
		String email = objectMapper.writeValueAsString(emailRequest);
		this.kafkaTemplate.send(Constant.sendMail,email);
		System.out.println("emailRequest Produce :"+email);
		return true;
	}
}
