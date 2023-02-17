package poker;

import java.util.Arrays;
public class Poker{	
		private final static String[] suits={"Hearts","Diamonds","Clubs","Spades"};
	//	private String[] ranks={"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};
		private final static String[] ranks={"Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace"};
		private final static String dirPath="C:\\Users\\andrus\\Documents\\Study_College\\MetropolitanStateUniversity\\ICS_141_01_Programming_with_Objects\\workspace\\PokerGUIFinal\\src\\cardPictures\\150\\";
		private final static String pictureType=".png";
		private int cardNumber;
		public int deckSize=52;
		public int numberOfPlayers=0;
		final static int HAND_SIZE=5;
		final int RANK_SIZE=ranks.length;
		private int[] deck=new int[deckSize];
		protected int[][] hands;//initiate two dimension array for players cards
		public boolean[] holds=new boolean[HAND_SIZE];
		public String[] outputWinningMessages={"Your winning card is: ","Your winning is Pair.","Your winning is Two Pairs.", 
				"Your winning is Three of the kind.","Your winning is Straight.","Your winning is Flush.","Your winning is Full House.","Your winning is Four of kind.","Your winning is Straight Flush."};
		
		private double moneyAmount = 100;
		private int bitAmount=1;
		private double minAlloudAmount=0;
		public double getMinAlloudAmount() {
			return minAlloudAmount;
		}


		public void setMinAlloudAmount(double minAlloudAmount) {
			this.minAlloudAmount = minAlloudAmount;
		}

		private double lastWinningAmount=0;
		private String playerName="Poker Star";

		PokerUI pokerUI;

	Poker(){
		hands=new int[1][HAND_SIZE];
		deckOf52Cards();
	}	
	
	
	Poker(int newNumOfPlayers,PokerUI pokerUI){			
		hands=new int[newNumOfPlayers][HAND_SIZE];
		deckOf52Cards();
		this.pokerUI=pokerUI;////////??????????????????????????????????????????????????????????????
		
	}
	public void serfCards(){
		for(int i=0;i<hands.length;i++){
			//System.out.println("This is hand N "+(i+1)+" :");
			//System.out.println(getPlayerName()+"Your cards are: ");
				for (int k=0;k<hands[i].length;k++){
					hands[i][k]=deck[--deckSize];
				//	System.out.println((k+1)+"."+getCardRank(hands[i][k])+" of "+getCardSuit(hands[i][k]));
					String tmp=dirPath+hands[i][k]+pictureType;
					pokerUI.setIconImages(tmp,k+1);
				}
		}		
	}
	public int getDeckSize(){
		return this.deckSize;	
	}
	public void resetDeckSize(){
		this.deckSize=52;
	}
	public String getCardSuit(int newCardNumber){
			cardNumber=newCardNumber;
			String suit=suits[cardNumber/RANK_SIZE];
			return suit;
	}
	public String getCardRank(int newCardNumber){
			cardNumber=newCardNumber;
			String rank=ranks[cardNumber%RANK_SIZE];	
			return rank;
	}
	public int getCardNumber(int cardNumber){
		return this.cardNumber;	
	}
	///Method for generate deck of 52 cards////////////
	public void deckOf52Cards(){
		//Initialize cards
		for(int i=0;i<deck.length;i++)
			deck[i]=i;	
		//Shuffle the cards
		for(int i=0;i<deck.length;i++){
			//Generate an index randomly
			int index=(int)(Math.random()*deck.length);
			int temp=deck[i];
			deck[i]=deck[index];
			deck[index]=temp;				
		}		
	}//end of deckOf52Cards method
	
	///Method for get name of card depends on it number
	public String getNameOfCard(int newCardNumber){
		cardNumber=newCardNumber;
		String suit=suits[cardNumber/RANK_SIZE];
		String rank=ranks[cardNumber%RANK_SIZE];	
		String nameOfCard=rank+" of "+suit;
		return nameOfCard;	
	}
	
	//Method for count how many cards(unplayed) left in the deck
	public int cardCounter(){	
		int cardsCounter=deck.length-1;
		for(int i=0;i<deck.length;i++)
			if( deck[i]==99)
				cardsCounter=cardsCounter-1;
									
		return cardsCounter+1;
	}//end of cardCounter method
	
	//method for serve the cards
	public int dispenseCards(){	
		for(int o=0;o<holds.length;o++)
			if(holds[o]==true){
				hands[0][o]=deck[deckSize-1];
				this.deckSize--;		 
			 }
		return hands[0][1];
	}//end of dispenseCards method
	public void clearHoldsArray(){
		for(int i=0;i<holds.length;i++)
			holds[i]=true;
	}
	public void dispenseHands(){
		for(int i=0;i<hands.length;i++){
			//System.out.println("This is hand N "+(i+1)+" :");
			//System.out.println("Your cards after changing are: ");
			pokerUI.setWinningMessage("Choose cards to Change");
				for (int k=0;k<hands[i].length;k++){
					String tmp=dirPath+hands[i][k]+pictureType;					
					pokerUI.setIconImages(tmp,k+1);
				}
		}
  	}
  	public String getWinnings(){
	  	int[][] sortedHand=new int[3][HAND_SIZE];//three is for Number, Rank and suite of card
	  	System.arraycopy(hands[0],0,sortedHand[0],0,HAND_SIZE);//copy user hand into sortedHand
		Arrays.sort(sortedHand[0]);
		for(int i=sortedHand[0].length-1;i>=0;i--){
			sortedHand[1][i]=sortedHand[0][i]%RANK_SIZE;//rank
			sortedHand[2][i]=sortedHand[0][i]/RANK_SIZE;//suite
		}
		
		if(isFlush(sortedHand)&&isStraight(sortedHand)){
			getWinningMoney((int)8);
			return (String)outputWinningMessages[8];
		}
		if(isFlush(sortedHand)){
			getWinningMoney((int)5);
			return (String)outputWinningMessages[5];
		}
					
		if(isStraight(sortedHand)){
			getWinningMoney((int)4);
			return (String)outputWinningMessages[4];
		}
		
		int[] result=countInstencesOfEachCard(sortedHand);
		winningArray(result);

		int[] winningArraySortedInversely= new int[winningArray(result).length];
		System.arraycopy(winningArray(result),0,winningArraySortedInversely,0,winningArraySortedInversely.length);
		
		for(int i=0,k=winningArraySortedInversely.length-1;i<=k;i++,k--){
			int tmp=winningArraySortedInversely[i];
			winningArraySortedInversely[i]=winningArraySortedInversely[k];
			winningArraySortedInversely[k]=tmp;
		}
			
		if (winningArray(result).length==HAND_SIZE&&!isStraight(sortedHand)&&!isFlush(sortedHand)){
			getWinningMoney((int)0);
			return (String)(outputWinningMessages[0]+getCardRank(getBiggestCard(sortedHand[1])));}
		else
			if(winningArraySortedInversely[0]==4){
				getWinningMoney((int)7);
				return (String)outputWinningMessages[7];
				}
			else
				if(winningArraySortedInversely[0]==3&&winningArraySortedInversely[1]==2){	
					getWinningMoney((int)6);
					return (String)outputWinningMessages[6];
					}
				else
					if(winningArraySortedInversely[0]==3){
						getWinningMoney((int)3);
						return (String)outputWinningMessages[3];
						}
					else
						if(winningArraySortedInversely[0]==2&&winningArraySortedInversely[1]==2){
							getWinningMoney((int)2);
							return (String)outputWinningMessages[2];
							}
						else
							if(winningArraySortedInversely[0]==2){
								getWinningMoney((int)1);
								return (String)outputWinningMessages[1];
							
							}
							else{			
									return "No Winings";
								}
					

		
	}//end of getWinnings
	public void getWinningMoney(int winningNumber){
		switch (winningNumber){
		case 0:setLastWinningAmount(0); pokerUI.setInfoPanelMessages();break; 
		case 1:setLastWinningAmount(((getBitAmount())*1)); pokerUI.setInfoPanelMessages();break;
		case 2:setLastWinningAmount(((getBitAmount())*1.5)); pokerUI.setInfoPanelMessages();break;
		case 3:setLastWinningAmount(((getBitAmount())*3)); pokerUI.setInfoPanelMessages();break;
		case 4:setLastWinningAmount(((getBitAmount())*4)); pokerUI.setInfoPanelMessages();break;
		case 5:setLastWinningAmount(((getBitAmount())*7)); pokerUI.setInfoPanelMessages();break;
		case 6:setLastWinningAmount(((getBitAmount())*25)); pokerUI.setInfoPanelMessages();break;
		case 7:setLastWinningAmount(((getBitAmount())*150)); pokerUI.setInfoPanelMessages();break;
		case 8:setLastWinningAmount(((getBitAmount())*1000));pokerUI.setInfoPanelMessages();break;
		default:break;
		}	
	}
  	
	public int getBiggestCard(int[] hand){
		int[] newSortedHand = new int[hand.length];
		System.arraycopy(hand,0,newSortedHand,0,hand.length);
		Arrays.sort(newSortedHand);
		return newSortedHand[newSortedHand.length-1];
	}
	
	public int[] winningArray(int[] result){
		int[] newResult=new int[result.length];
		System.arraycopy(result,0,newResult,0,result.length);
		int count=0;
		for(int i=0;i<newResult.length;i++)
			if(newResult[i]!=0)
				count++;
		int[] winning=new int[count];
		Arrays.sort(newResult);
		System.arraycopy(newResult,newResult.length-count,winning,0,count);
		return winning;		
	}//end of winningArray	
	
	public boolean isFlush(int[][] sortedHand){
		Arrays.sort(sortedHand[2]);
		if(sortedHand[2][0]==sortedHand[2][1]&&sortedHand[2][1]==sortedHand[2][2]
			&&sortedHand[2][2]==sortedHand[2][3]&&sortedHand[2][3]==sortedHand[2][4])
			return true;
		else
			return false;		
	}//end of isFlush
	
	public boolean isStraight(int[][] sortedHand){
		Arrays.sort(sortedHand[1]);
		if(sortedHand[1][0]+1==sortedHand[1][1]&&sortedHand[1][1]+1==sortedHand[1][2]
			&&sortedHand[1][2]+1==sortedHand[1][3]&&sortedHand[1][3]+1==sortedHand[1][4])
			return true;
		else
			return false;
	}//end of is Straight
	
	private int[] countInstencesOfEachCard(int[][] sortedHands){
		int[] result=new int[RANK_SIZE];
		for(int i=0;i<RANK_SIZE;i++)
			for (int k=0;k<HAND_SIZE;k++)
				if(i==sortedHands[1][k])
					result[i]+=1;												
		return result;		
	}//end of countInstancesOfEachCard
	
	   public void goActionPerformed(java.awt.event.ActionEvent evt) {
		   	dispenseCards();
			dispenseHands();
			System.out.println("Now in Deck is: " +getDeckSize()+" card(s)");	
			System.out.println("Now in Deck is: " +getDeckSize()+" card(s)");
			
			
			pokerUI.setWinningMessage(getWinnings());//this is winning message
			System.out.println("\n");
	    }
		
		
		public double getMoneyAmount() {
			return moneyAmount;
		}


		public void setMoneyAmount(double moneyAmount) {
			this.moneyAmount = moneyAmount;
		}

		public double getLastWinningAmount() {
			return lastWinningAmount;
		}


		public void setLastWinningAmount(double lastWinningAmount) {
			this.lastWinningAmount = lastWinningAmount;
		}
		
		
		
		public int getBitAmount() {
			return bitAmount;
		}


		public void setBitAmount(int bitAmount) {
			this.bitAmount = bitAmount;
		}
		public String getPlayerName() {
			return playerName;
		}

		public void setPlayerName(String playerName) {
			this.playerName = playerName;
		}

}