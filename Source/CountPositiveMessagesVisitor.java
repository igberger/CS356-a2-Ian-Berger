import java.util.ArrayList;

//Ian Berger
//CS 356
//Assignment 2
public class CountPositiveMessagesVisitor implements UserGroupVisitor {
	private int messageCounter;
	private int positiveMessageCounter;
	//lower case phrases used to determine a positive message
	private static final String[] positivePhrases = {"good", "great", "excellent", "cool"};
	
	public CountPositiveMessagesVisitor() {
		positiveMessageCounter = 0;
	}
	
	public int getPositiveMessagePercentage() {
		return (int)((double)positiveMessageCounter/messageCounter);
	}
	
	@Override
	public void visitUser(User user) {
		///Increase  positiveMessageCount by the number of positive messages in user's news feed
		NewsFeed newsFeed = user.getNewsFeed();
		messageCounter += newsFeed.size();
		for(int i=0; i<newsFeed.size(); i++) {
			boolean notPositive = true;
			String tweetMessage = newsFeed.get(i).getMessage().toLowerCase();
			for(int j=0; j<positivePhrases.length && notPositive; j++) {
				if(tweetMessage.contains(positivePhrases[j])) {
					notPositive = false;
					positiveMessageCounter++;
				}
			}
		}
	}

	@Override
	public void visitGroup(Group user) {
		//Groups have no messages themselves - do nothing
	}

}
