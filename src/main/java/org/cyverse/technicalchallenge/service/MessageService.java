package org.cyverse.technicalchallenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nats.client.Connection;
import io.nats.client.Subscription;


/**
 * @author WentaoZhou
 * @Date 10/17/2020
 *
 */

@Service
public class MessageService {
	
	@Autowired
	NatsClientService natsService;
	
	@Autowired
	PublishService publishService;
	
	@Autowired
	SubscriberService subscriberService;
	
	private final static Logger logger = LoggerFactory.getLogger(MessageService.class);
	
	/**
	 * publish twitter timeline and subscriber
	 * @param
	 * @return
	 */
	public void twitterTimelineMsg() {
		// init a NATS connection
		Connection conn = natsService.initConnection("");
		
		// Subscribe to subject "twitter-timeline"
		Subscription timelineSubscription
			= natsService.subscribeSync(conn, "twitter-timeline");
		
		// publish messages
		publishService.publishTwitterTimeline(conn);
		// read messages from NATS server
		subscriberService.subscriberTwitterTimeline(timelineSubscription);
				
		try {
			// close NATS connection
			conn.close();
		} catch (InterruptedException e) {
			logger.error("Failed to close NATS connection", e);
		}
	}
}
