package org.cyverse.technicalchallenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Duration;

import org.cyverse.technicalchallenge.service.NatsClientService;
import org.junit.jupiter.api.Test;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Subscription;

public class NatsClientTest {

	NatsClientService natsClientService = new NatsClientService();
	
	@Test
    public void givenMessageExchange_MessagesReceived() throws Exception {

        Connection conn = natsClientService.initConnection("");

        Subscription fooSubscription = natsClientService.subscribeSync(conn, "foo.bar");
        Subscription barSubscription = natsClientService.subscribeSync(conn, "bar.foo");
        natsClientService.publishMessage(conn, "foo.bar", "bar.foo", "hello there");

        Message message = fooSubscription.nextMessage(Duration.ofMillis(2000));
        assertNotNull("No message!", message);
        System.out.println(message.getSubject());
        assertEquals("hello there", new String(message.getData()));

        natsClientService.publishMessage(conn, 
        		message.getReplyTo(), message.getSubject(), "hello back");

        message = barSubscription.nextMessage(Duration.ofMillis(2000));
        assertNotNull("No message!", message);
        assertEquals("hello bacc", new String(message.getData()));
    }
}
