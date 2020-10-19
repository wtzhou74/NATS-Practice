package org.cyverse.technicalchallenge.service;

import org.cyverse.technicalchallenge.model.TimelineStatus;
import org.cyverse.technicalchallenge.repository.TimelineStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WentaoZhou
 * @Date 10/18/2020
 *
 */
@Service
public class TwitterTimelineService {

	@Autowired
	TimelineStatusRepository timelineRepo;
	
	public void persistTimeline(TimelineStatus timeline) {
		timelineRepo.saveAndFlush(timeline);
	}
}
