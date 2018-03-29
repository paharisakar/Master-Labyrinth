package code.utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import code.utils.*;
import code.model.AbstractTile;
import code.model.FixedTile;
import code.model.FormulaCard;
import code.model.GameBoard;
import code.model.MoveableTile;
import code.model.Player;
import code.model.Token;
/**
 * This class handles the save file input(save)
 * and output(load)
 * @author David, Nick, and Tyler
 *
 */
public class SaveFileIO {

	private String _playerLine;
	private String _illegalPushLine;
	private String _tileLine;
	private int _tTypeCount = 0;
	private int _lTypeCount = 0;
	private int _iTypeCount = 0;
	private Player[] _playersArray;
	private AbstractTile[][] _board;
	private int _illegalPushInt;

	/**
	 * the main method for our save functionality. Calls all others methods
	 * @param name - name of file to save to (excluding *.mls prefix)
	 * @param board - the game board of whose which content we will be saving
	 * @return boolean
	 */
	public static boolean saveFile(String name, GameBoard board) {
		String contentToSave =  saveFileContent(board);
		name += ".mls";
		PrintWriter outStream = null;
		try {
			outStream = new PrintWriter(name);
			outStream.print(contentToSave);
			return true;
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			outStream.close();
		}
		return true;
	}
	
	/**
	 * The string content that will be saved to file
	 * @param gb - relevant gameboard
	 * @return the string that will be saved to file.
	 * @author David, Nick, Tyler
	 */
	public static String saveFileContent (GameBoard gb) {
		return playerLineWrite(gb) + "\n" + 
			   tileLineWrite(gb) + "\n" + 
			   illegalPushLineWrite(gb);
	}
	/**
	 * Given the players in the game, a line representing all players
	 * will be written
	 * @param gb
	 * @return the line of text representing all players
	 * @author David, Nick, Tyler
	 */
	private static String playerLineWrite(GameBoard gb) {
		String playerLineData = "";
		Player [] playerArray = gb.getPlayers();
		int numPlayers = playerArray.length;
		int playerIndex  = gb.currentPlayerIndex;
		for (int i = 0; i < numPlayers; i++) {
			playerLineData +=  playerArray[playerIndex].toString(); //requires overhaul of player toStringMethod
			playerIndex += 1;
			playerIndex %= numPlayers;
			if(playerIndex != gb.currentPlayerIndex) {
				playerLineData += ",";
			}
		}
		return playerLineData;
	}
	
	/**
	 * Given the tiles in the game, a line representing all players
	 * will be written
	 * @param gb
	 * @return the line of text representing all of the tiles
	 * @author David, Nick, Tyler
	 */
	private static String tileLineWrite(GameBoard gb) {
		String tileLineData = "";
		for (int y = 0; y < 7; y++) {
			for (int x = 0; x < 7; x++) {
				AbstractTile t = gb.getTileFromCoordinates(x, y);
				tileLineData += t.toString(); // requires overhaul of AbstractTile
				if (!(x == 6 && y == 6)) {
					tileLineData += ",";
				}
			}
		}
		return tileLineData;
	}
	
	/**
	 * Given the last shift, this will write to a sting
	 * the int representation of the illegal shift.
	 * @param gb
	 * @return the line of text representing all of the tiles
	 * @author David, Nick, Tyler
	 */
	private static String illegalPushLineWrite(GameBoard gb) {
		return "" + gb.getIllegalPush();
	}
	
	/////////////////////////////
	// SAVE						
	// FUNCTIONALITY END HERE
	// LOAD
	// FUNCTIONALITY BEGINS
	/////////////////////////////
	/**
	 * Given a file name, this method initiates the process of loading the saved game.
	 * @param name
	 * @author David, Nick, Tyler
	 */
	public void loadFile(String name) {
		FileReader fr;
		try{
			fr =new FileReader(name);
			BufferedReader br = new BufferedReader(fr);
			_playerLine = br.readLine();
			_tileLine = br.readLine();
			_illegalPushLine = br.readLine();
			this.createComponents();
		}
		catch (IOException e) {
			
		}
	}

	/**
	 * This method calls the methods which create all of the appropritoe players and tiles
	 * @author David, Nick, Tyler
	 */
	private void createComponents() {
		_playersArray = playerLineRead(this._playerLine); 
		_board = tileLineRead(this._tileLine);
		_illegalPushInt = Integer.parseInt(_illegalPushLine);
	}

	/**
	 * Given a line of text representing the players, this method returns an array of those players
	 * @param playerLine
	 * @return an array of the players
	 * @author David, Nick, Tyler
	 */
	public Player [] playerLineRead(String playerLine) {
		String [] playerData = playerLine.split(",\\[");
		int numPlayers = playerData.length / 3;
		Player [] playersArray = new Player[numPlayers];
		for(int i = 0; i < playerData.length; i++) {
			if(i%3 == 0 && playerData[i].charAt(0) == '[') {playerData[i] = playerData[i].substring(1, playerData[i].length());}
			if(i%3 == 1) {playerData[i] = playerData[i].substring(0, playerData[i].length()-1);}
			if(i%3 == 2) {playerData[i] = playerData[i].substring(0, playerData[i].length()-2);}
		}
		for (int j = 0; j < numPlayers; j++) {
			playersArray[j] = this.createPlayer(playerData[3*j], playerData[3*j + 1], playerData[3*j +2]) ;
		}
		return playersArray;	
	}

	/**
	 * This method (given string representations of various properties) create the correct player
	 * @param nameColorWandsString
	 * @param formulaCardString
	 * @param tokenCollString
	 * @return player for text representation
	 * @author David, Nick, Tyler
	 */
	public Player createPlayer(String nameColorWandsString, String formulaCardString, String tokenCollString) {
		System.out.println("Orignal Inputs");
		System.out.println(nameColorWandsString);
		System.out.println(formulaCardString);
		System.out.println(tokenCollString);
		String[] nameColorWandsArray = nameColorWandsString.split(",");
		String[] formulaCardArray = formulaCardString.split(",");
		String[] tokenCollArray = tokenCollString.split(",");
		System.out.println("Under transform");
		String playerName = nameColorWandsArray[0];// System.out.println(playerName); 
		String playerColor = nameColorWandsArray[1]; //System.out.println(playerColor);
		int playerWandCount = Integer.parseInt(nameColorWandsArray[2]); //System.out.println(playerWandCount);
		
		boolean cleanEnd = true;
		int j = 0;
		while(cleanEnd) {
			char c = formulaCardArray[2].charAt(j);
			if(c == '[' || c == ']') formulaCardArray[2] = formulaCardArray[2].substring(0, formulaCardArray[2].length() -1);
			else cleanEnd = false;
			j++;
		}
		System.out.println(formulaCardArray[2]);
		FormulaCard f = new FormulaCard(
				code.utils.Mappings.numToIngredient(formulaCardArray[0]),
				code.utils.Mappings.numToIngredient(formulaCardArray[1]),
				code.utils.Mappings.numToIngredient(formulaCardArray[2])
		);
		Player p =  new Player(playerName, playerColor, f, playerWandCount);
		
		for(int i = 0; i< tokenCollArray.length; i++) {
			String tokenString = tokenCollArray[i];
			int tokenNum = Integer.parseInt(tokenString);
			Token t = new Token(tokenNum, tokenString);
			p.addToken(t);
		}
		return p;
	}

	/**
	 * Given a line of text representing all tiles, this method returns an array of those tiles
	 * @param tileLine
	 * @return an array of the players
	 * @author David, Nick, Tyler
	 */
	public AbstractTile[][] tileLineRead(String tileLine) {
		AbstractTile[][] result = new  AbstractTile[7][7];
		String [] tileData = tileLine.split("\\],\\[");
		tileData[0] = tileData[0].substring(1, tileData[0].length());
		tileData[tileData.length -1] = tileData[tileData.length -1].substring(1, tileData[tileData.length -1].length());
		for(int y = 0; y < 7; y++) {
			for (int x = 0; x < 7; x++) {
				boolean fixedTile = (x%2 == 0 && y%2 ==0) ;
				result[x][y] = createTile(tileData[7*y + x], fixedTile);
			}
		}
		return result;
	}

	/**
	 * Given a textual represenation of a tile this method creates the tiles
	 * @param tileRep - string rep of tile
	 * @param fixedTile - wethe or not the tile to be created is fixed or moveable
	 * @return the correct tile
	 */
	public AbstractTile createTile(String tileRep, boolean fixedTile) {
		AbstractTile t;
		
		String[] tileData = tileRep.split(","); 
		String tileType = "" + tileData[0].charAt(1); //System.out.println("Tile type: \t" + tileType);
		int rotate = tileData[0].charAt(2) - 48; //System.out.println("Rotatiin num: \t" + rotate);
		String [] playerColors = new String[tileData.length -2];
		if(fixedTile) {t = new FixedTile(tileType);}
		else {
			t = new MoveableTile(tileType);
			if(tileType.equals("T")) this._tTypeCount++;
			if(tileType.equals("L")) this._lTypeCount++;
			if(tileType.equals("I")) this._iTypeCount++;
		}
		
		for(int i = 2; i < tileData.length; i++ ) {
			String s = tileData[i];
			if (i == 2) s = s.substring(1, s.length());
			if (i == tileData.length -1) s = s.substring(0, s.length()-2);
			if (s.length() != 0) s = s.substring(1, s.length());
			playerColors[i-2] = s;
		}
		//System.out.println("This array:");
		//for(int i = 0; i<playerColors.length; i++) System.out.println(playerColors[i]);
		
		
		int tokenNum = Integer.parseInt(tileData[1]); //System.out.println("Token num \t" + tokenNum);
		if (tokenNum != 0) {
			Token tokenToAdd = new Token(tokenNum, code.utils.Mappings.numToIngredient(tokenNum));
			t.setToken(tokenToAdd);
		}
//		String playerColorsString = tileData[2]; System.out.println("Player colors \t" + playerColorsString);
//		playerColorsString = (playerColorsString.length() != 2)? playerColorsString.substring(1, playerColorsString.length()-1): null;
//		//String [] playerColorsArray = (playerColorsString != null)? playerColorsString.split(","): null;
		for(int i = 0; i < rotate; i++) t.rotate(90);

		
		for (int j = 0; j < playerColors.length; j++) {
			for(int i = 0; i < this._playersArray.length; i++) {
				String colorInColorList = playerColors[j];
				Player playerOfInterest = _playersArray[i];
				String playerColor = playerOfInterest.getColor();
				if(colorInColorList.equals(playerColor)) t.addPlayer(playerOfInterest);
			}
		}
		
		return t;
	}

	/**
	 * getter method for the created array of players
	 * @return the array of created players
	 * @author David, Nick, Tyler
	 */
	public Player[] getPlayers() {
		return this._playersArray;
	}
	/**
	 * getter method for the created board array
	 * @return the 2d array of tiles on board
	 * @author David, Nick, Tyler
	 */
	public AbstractTile[][] getBoardArray() {
		return this._board;
	}
	
	/**
	 * getter method for illegal push
	 * @return the integer rep of illegal push
	 * @author David, Nick, Tyler
	 */
	public int getIllegalPushInt() {
		return this._illegalPushInt;
	}
	
	/**
	 * getter method for loose tile
	 * @return loose tile
	 * @author David, Nick, Tyler
	 */
	public MoveableTile getLooseTile() {
		if(this._iTypeCount < 13) return new MoveableTile("I");
		if(this._lTypeCount < 15) return new MoveableTile("L");
		else return new MoveableTile();
	}
}

//

