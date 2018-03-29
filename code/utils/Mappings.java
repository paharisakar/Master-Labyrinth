package code.utils;

import java.util.HashMap;

/**
 * This class contains the mappings from the
 * int representing the a token  to
 * the string representing the corresponding ingredient
 * vice versa
 * @author David
 *
 */
public class Mappings {
	public static final String [] INGREDIENTS = {
			"Crab Apple",
			"Pine Cone",
			"Oak Leaf",
			"Oil of Black Slugs",
			"Four Leaf Clover",
			"Garlic",
			"Raven's Feather",
			"Henbane",
			"Spider",
			"Skull",
			"Magic Wand Made of Blindworm",
			"Quartz",
			"Toad",
			"Fire Salamander",
			"Weasel Spit",
			"Silver Thistle",
			"Snake",
			"Emerald",
			"Root of Mandrake",
			"Black Rooster",
			"Berries of Mistletoe"
	};
	
	/**
	 * Methods maps a number to the corresponding ingredient.
	 * @param num number of ingredient
	 * @return the string representing the ingredient
	 * @author Tyler
	 */
	
	public static String numToIngredient(int num) {
		
		if(num >= 1 && num <= 20)  return INGREDIENTS[num -1];
		if(num == 25) return INGREDIENTS[20];
		else return null;
	}
	
	/**
	 * maps the string ingredient to the corresponding
	 * @param s - the string representing the ingredient
	 * @return - the integer representation of the ingredient
	 * @author Nick
	 */
	@SuppressWarnings("finally")
	public static int ingredientToNum(String s) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for(int i = 0; i < INGREDIENTS.length; i++) {
			if(i == 20) map.put(INGREDIENTS[i], 25);
			else map.put(INGREDIENTS[i], i+1);
		}
		
		try {
			return map.get(s);
		}
		catch (NullPointerException n) {
			s = "Black Rooster";
		}
		finally {
			//System.out.println(s);
			return map.get(s);
		}
		
	}
	/**
	 * Over loads String numToIngredient But takes a string (that represents an int) for argument.
	 * It then parses said string into an int and passes and then returns the value of the function it
	 * is overloading
	 * @param string showing int of ingredient
	 * @return string representation of ingredient
	 */
	public static String numToIngredient(String string) {
		return numToIngredient(Integer.parseInt(string));
	}
}
