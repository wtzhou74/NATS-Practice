package org.cyverse.technicalchallenge.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Subscription;

public class NatsClientServiceTest {

	NatsClientService natsClientService = new NatsClientService();
	
	@Test
	@DisplayName("twetter APIs test")
    public void givenMessageExchange_MessagesReceived() throws Exception {

        Connection conn = natsClientService.initConnection("");

        Subscription sub1Subscription = natsClientService.subscribeSync(conn, "SUB1");
        Subscription sub2Subscription = natsClientService.subscribeSync(conn, "SUB2");
        natsClientService.publishMessage(conn, "SUB1", "SUB2", "hello there");

        Message message = sub1Subscription.nextMessage(Duration.ofMillis(2000));
        assertNotNull("No message!", message);
        System.out.println(message.getSubject());
        assertEquals("hello there", new String(message.getData()));

        natsClientService.publishMessage(conn, 
        		message.getReplyTo(), message.getSubject(), "hello back");

        message = sub2Subscription.nextMessage(Duration.ofMillis(2000));
        assertNotNull("No message!", message);
        assertEquals("hello back", new String(message.getData()));
    }
}
