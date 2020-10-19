package org.cyverse.technicalchallenge.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import twitter4j.DirectMessage;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Twitter APIs
 * @author WentaoZhou
 * @Date 10/17/2020
 *
 */
/**
 * @author WentaoZhou
 *
 */
@Service
public class TwitterService {

	private final static Logger logger = LoggerFactory.getLogger(TwitterService.class);
	
	/**
	 * Generate a twitter instance
	 * @return
	 */
	public Twitter getTwitterinstance() {
		
		Twitter twitter = TwitterFactory.getSingleton();
		return twitter;
		
	}
	
	/**
	 * create a new tweet
	 * @param tweet
	 * @return
	 * @throws TwitterException
	 */
	public String createTweet(String tweet) throws TwitterException {
		Twitter twitter = getTwitterinstance();
		Status status = twitter.updateStatus("Technical Challenge");
	        return status.getText();
	}
	
	/**
	 * get tweet timeline
	 * @return
	 */
	public List<Status> getTimeLine() {
		Twitter twitter = getTwitterinstance();
		List<Status> statuses = new ArrayList<>();
		try {
			statuses = twitter.getHomeTimeline();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			logger.error("Error disconnect NATs! ", e);
		}
		return statuses.stream().map(
				item -> item).collect(
						Collectors.toList());
	}
	
	/**
	 * send direct message to specified recipient
	 * @param recipientName
	 * @param msg
	 * @return
	 * @throws TwitterException
	 */
	public String sendDirectMessage(String recipientName, String msg) throws TwitterException {
		Twitter twitter = getTwitterinstance();
	        DirectMessage message = twitter.sendDirectMessage(recipientName, msg);
	        return message.getText();
	}
	
	
	/**
	 * Search tweeters
	 * @return
	 * @throws TwitterException
	 */
	public List<String> searchtweeters() throws TwitterException {
		Twitter twitter = getTwitterinstance();
	        Query query = new Query("source:twitter4j wentao");
	        QueryResult result = twitter.search(query);
	        List<Status> statuses = result.getTweets();
	        return statuses.stream().map(
				item -> item.getText()).collect(
						Collectors.toList());
	}
	
	/**
	 * twitter stream feed
	 */
	public void streamFeed() {
		
		StatusListener listener = new StatusListener(){

			@Override
			public void onException(Exception e) {
				e.printStackTrace();
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg) {
                                System.out.println("Got a status deletion notice id:" + arg.getStatusId());
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
                                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			@Override
			public void onStallWarning(StallWarning warning) {
                                System.out.println("Got stall warning:" + warning);
			}

			@Override
			public void onStatus(Status status) {
                                System.out.println(status.getUser().getName() + " : " + status.getText());
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}
		};
	
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		
        twitterStream.addListener(listener);
    
        twitterStream.sample();
		
	}
}
