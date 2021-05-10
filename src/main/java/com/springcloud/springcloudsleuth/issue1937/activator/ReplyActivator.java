package com.springcloud.springcloudsleuth.issue1937.activator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ReplyActivator {

    private final static String ZIPKIN_TAG = "REQUEST_ID";

    @Autowired
    @Qualifier("myJmsTemplate")
    JmsTemplate jmsTemplate;

    @Value("${artemis.queue.reply}")
    String REPLY_QUEUE;

    @Autowired
    private Tracer tracer;

    public void request(Message<String> message) throws JmsException {

        Span currentSpan = null;
        try {
            currentSpan = tracer.currentSpan();
            String requestId = UUID.randomUUID().toString();

            if (currentSpan != null) {
                currentSpan.tag(ZIPKIN_TAG, requestId);
                currentSpan.event("Sending to Queue");
            }

            jmsTemplate.convertAndSend(REPLY_QUEUE, message.getPayload(), m -> {
                m.setStringProperty("MY_PROPERTY", "MY_VALUE");
                return m;
            });

        } finally {
            if (currentSpan != null) {
                currentSpan.end();
            }
        }
    }
}
