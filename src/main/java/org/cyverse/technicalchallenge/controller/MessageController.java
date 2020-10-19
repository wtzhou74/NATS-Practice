package org.cyverse.technicalchallenge.controller;

import org.cyverse.technicalchallenge.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WentaoZhou
 * @Date 10/17/2020
 *
 */
@RestController
@RequestMapping("/message")
public class MessageController {

	@Autowired
	MessageService messageService;
	
	@RequestMapping(value = "/timeline", method = RequestMethod.GET)
	public void getTimeline() {
		messageService.twitterTimelineMsg();
	}
}
