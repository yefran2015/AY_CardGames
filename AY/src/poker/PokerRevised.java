package poker;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PokerRevised extends Poker{
	private final static String savesFile="saves.txt";
	private final static int howManyPlayers=1;
	
	public static void main(String[] args){
		PokerUI pokerUI = new PokerUI();
		pokerUI.run();
		pokerUI.setWinningMessage("Choose Cards To Change");
		Poker game=new Poker(howManyPlayers,pokerUI);
		//Create an input stream for file temp.dat
		DataInputStream input;
		try {
			input = new DataInputStream(new FileInputStream(savesFile));
			try {
				game.setPlayerName(input.readUTF());
				game.setMoneyAmount(input.readDouble());
				game.setBitAmount(input.readInt());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {	
			System.out.println("Savings File not Found");
		}
		
		
		pokerUI.copyGameReferenceToSecondVariable(game);
		game.serfCards();
		game.setMoneyAmount(game.getMoneyAmount()-game.getBitAmount());
		pokerUI.setDeckSizeToLabel(game.getDeckSize());
		pokerUI.setInfoPanelMessages();
		
	}
	
}
