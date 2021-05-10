package com.springcloud.springcloudsleuth.issue1937;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;

@SpringBootTest
class Issue1937ApplicationTests {

	@Autowired
	JmsTemplate jmsTemplate;

	@Value("${artemis.queue.request}")
	String REQUEST_QUEUE;

	private final static String MESSAGE = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

	@Test
	void thisWorksBecauseJmsIsAlreadyInstrumented() {
		jmsTemplate.setDeliveryPersistent(true);
		for (int i = 0; i < 1000; ++i) {
			jmsTemplate.convertAndSend(REQUEST_QUEUE, i + " -------- " + MESSAGE);
		}
	}
}
