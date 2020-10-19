package org.cyverse.technicalchallenge.service;

import org.cyverse.technicalchallenge.BaseTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class MessageServiceTest extends BaseTestCase{

	@InjectMocks
	MessageService messageService;
	
	@Mock
	PublishService publishService;
	
	@Mock
	SubscriberService subscriberService;
	
	@Test
	@DisplayName("Subscribe Twitter timeline test")
	public void testSubscribeTwitterTimeline() {
		messageService.twitterTimelineMsg();
	}
}
