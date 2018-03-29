package code.model;

public class FormulaCard {
	/**
	 * The FormulaCards class defines an additional to score points in the game. Each formula card contains three
	 * Tokens, and each Token has a value and an ingredient. For each Token that a player obtains during the game
	 * that matches his/her respective ingredient on their FormulaCard, they receive an additional 20 extra points.
	 * @author Tyler
	 */
	private String _ingredient1;
	private String _ingredient2;
	private String _ingredient3;
	
	/**
	 * Each Formula Card accepts three arguments, which are all of type String. Since a Formula requires ingredients,
	 * the parameters are each referred to as ingredients.
	 * @param ingredient1 refers to the first Token in the ingredient list
	 * @param ingredient2 refers to the second Token in the ingredient list
	 * @param ingredient3 refers to the third Token in the ingredient list
	 * @author Tyler
	 */
	public FormulaCard(String ingredient1, String ingredient2, String ingredient3){
		_ingredient1 = ingredient1;
		_ingredient2 = ingredient2;
		_ingredient3 = ingredient3;
	}
	/**
	 * An accessor method for the first argument in the constructor
	 * @return the first String associated with its respective ingredient in the Formula Card 
	 * @author Tyler
	 */
	public String getToken1(){
		return _ingredient1;
	}
	/**
	 * An accessor method for the second argument in the constructor
	 * @return the second String associated with its respective ingredient in the Formula Card 
	 * @author Tyler
	 */
	public String getToken2(){
		return _ingredient2;
	}
	/**
	 * An accessor method for the third argument in the constructor
	 * @return the third String associated with its respective ingredient in the Formula Card 
	 * @author Tyler
	 */
	public String getToken3(){
		
		return _ingredient3;
		
	}
	
	@Override public String toString() {
		return "["  + code.utils.Mappings.ingredientToNum(_ingredient1) + "," +
					+ code.utils.Mappings.ingredientToNum(_ingredient2) + "," +
					+ code.utils.Mappings.ingredientToNum(_ingredient3) + "]";
	}
	
}
