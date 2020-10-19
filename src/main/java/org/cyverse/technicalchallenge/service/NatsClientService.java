package org.cyverse.technicalchallenge.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Subscription;

/**
 * NATS client APIs (mainly for NATS client API unit tests)
 * 
 * @author WentaoZhou
 * @Date 10/17/2020
 *
 */
@Service
public class NatsClientService {

    private final Map<String, Subscription> subscriptions = new HashMap<>();

    private final static Logger logger = LoggerFactory.getLogger(NatsClientService.class);

    /**
     * init NATS connection
     * @param serverURI
     * @return
     */
    public Connection initConnection(String serverURI) {
  	try {
	    	if ((serverURI != null) && (!serverURI.isEmpty())) {
	            return Nats.connect(serverURI);
	        } else {
	            return Nats.connect(); // default uri: "jnats://localhost:4222";
	        }
		} catch (IOException | InterruptedException e) {
			logger.error("Error connecting to NATs! ", e);
        return null;
		}
  }

    /** 
     * close NATS connection
     * @param natsConn
     */
    public void closeConnection(Connection natsConn) {
        // Close connection        
		try {
			natsConn.close();
		} catch (InterruptedException ie) {
			logger.error("Error disconnect NATs! ", ie);
		}
    }

    

    /**
     * publish message
     * 
     * @param natsConn
     * @param topic
     * @param replyTo
     * @param message
     * @throws IOException
     */
    public void publishMessage(Connection natsConn, 
    		String topic, String replyTo, String message) throws IOException {
    	natsConn.publish(topic, replyTo, message.getBytes());
    }

    /* create a synchronous subscription to the specified subject.
    * <p>See {@link #createDispatcher(MessageHandler) createDispatcher} for
    * information about creating an asynchronous subscription with callbacks.
    *
	* @param natsConn
	* @param topic
	* @return
	*/
    public Subscription subscribeSync(Connection natsConn, String topic) {
        return natsConn.subscribe(topic);
    }

    /**
     * @param topic
     */
    public void unsubscribe(String topic) {
        Subscription subscription = subscriptions.get(topic);

		if (subscription != null) {
		    subscription.unsubscribe();
		} else {
			logger.error("{} not found. Unable to unsubscribe.", topic);
		}
    }

}
