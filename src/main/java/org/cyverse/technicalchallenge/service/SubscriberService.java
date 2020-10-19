package org.cyverse.technicalchallenge.service;

import java.time.Duration;

import org.apache.commons.lang.SerializationUtils;
import org.cyverse.technicalchallenge.model.TimelineStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nats.client.Message;
import io.nats.client.Subscription;
import twitter4j.Status;

/**
 * @author WentaoZhou
 * @Date 10/17/2020
 *
 */
@Service
public class SubscriberService {

	@Autowired
	TwitterService twitterService;
	
	@Autowired
	NatsClientService natsService;
	
	@Autowired
	TwitterTimelineService timelineService;
	
	private final static Logger logger = LoggerFactory.getLogger(MessageService.class);
	
	/**
	 * Read messages published by the subject from NATS
	 * @param timelineSubscription
	 */
	public void subscriberTwitterTimeline(Subscription timelineSubscription) {
		
		try {
			int i = 0;
			Message message 
				= timelineSubscription.nextMessage(Duration.ofMillis(2000));
			// read all published messages
			while (message != null) {
				logger.info("Finished the {}th message for subject {}", i++, message.getSubject());
				Status timeline = (Status) SerializationUtils.deserialize(message.getData());
				if (timeline != null) {
					persistTimeline(timeline);
				}
				// pick next message
				message 
					= timelineSubscription.nextMessage(Duration.ofMillis(2000));
			}
			
		} catch (IllegalStateException | InterruptedException e) {
			logger.error("Error to read message", e);
		}	
	}
	
	/**
	 * Save the timeline to the PostgreSQL database
	 * @param timeline
	 */
	public void persistTimeline(Status timeline) {
		TimelineStatus timelineStatus = new TimelineStatus();
		Long id = timeline.getId();
		String text = timeline.getText();
		String source = timeline.getSource();
		String place = 
				timeline.getPlace() == null ? "" : timeline.getPlace().getFullName();
		boolean isFavorited = timeline.isFavorited();
		
		timelineStatus.setId(id);
		timelineStatus.setText(text);
		timelineStatus.setSource(source);
		timelineStatus.setPlace(place);
		timelineStatus.setFavorited(isFavorited);
		timelineService.persistTimeline(timelineStatus);
		
		logger.info("Success to save the timeline {}", id);
	}
}
