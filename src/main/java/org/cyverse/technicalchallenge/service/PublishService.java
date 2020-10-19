package org.cyverse.technicalchallenge.service;

import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nats.client.Connection;
import twitter4j.Status;

/**
 * @author WentaoZhou
 * @Date 10/17/2020
 *
 */
@Service
public class PublishService {

	@Autowired
	TwitterService twitterService;
	
	/**
	 * publish twitter timeline to NATS
	 * @param conn
	 */
	public void publishTwitterTimeline(Connection conn) {
		
		// read timeline by calling twitter API
		List<Status> timelines = twitterService.getTimeLine();
		
		// publish timelines 
		for (Status timeline : timelines) {
			conn.publish("twitter-timeline", SerializationUtils.serialize(timeline));
		}	
	}
}
