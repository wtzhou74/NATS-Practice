package org.cyverse.technicalchallenge.service;

import java.util.List;

import org.junit.jupiter.api.Test;

import twitter4j.Status;

import static org.junit.Assert.assertEquals;

public class TwitterServiceTest {

	TwitterService twitterService = new TwitterService();
	
	@Test
	public void twitterApiTest() {
		List<Status> timeLines = twitterService.getTimeLine();
		
		assertEquals(20, timeLines.size());
	}
}
