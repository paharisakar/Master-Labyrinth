package test.save;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.model.AbstractTile;
import code.model.FixedTile;
import code.model.Player;
import code.model.Token;


public class lineTwoTest {
	@Test
	public void test00() {
		AbstractTile t = new FixedTile("T");
		t.setDirections(1,0,1,1);
		String expected = "[T2,0,[]]";
		String actual = t.toString();
		assertTrue("",expected.equals(actual));
	}
	@Test
	public void test01() {
		AbstractTile t = new FixedTile("T");
		t.setDirections(0,1,1,1);
		String expected = "[T2,0,[]]";
		String actual = t.toString();
		assertTrue("",expected.equals(actual));
	}
	@Test
	public void test02() {
		AbstractTile t = new FixedTile("L");
		t.setDirections(1,1,0,1);
		String expected = "[L0,0,[]]";
		String actual = t.toString();
		assertTrue("",expected.equals(actual));
	}
	@Test
	public void test03() {
		AbstractTile t = new FixedTile("L");
		t.setDirections(0,1,0,1);
		String expected = "[L0,0,[]]";
		String actual = t.toString();
		assertTrue("",expected.equals(actual));
	}
	@Test
	public void test04() {
		AbstractTile t = new FixedTile("L");
		t.setDirections(1,1,0,1);
		Token to = new Token(1, "apple"); 
		t.setToken(to);
		String expected = "[L0,1,[]]";
		String actual = t.toString();
		assertTrue("",expected.equals(actual));
	}
	@Test
	public void test05() {
		AbstractTile t = new FixedTile("L");
		t.setDirections(1,1,0,1);
		Token to = new Token(1, "apple"); 
		t.setToken(to);
		String expected = "[L0,1,[]]";
		String actual = t.toString();
		System.out.println(expected);
		System.out.println(actual);
		assertTrue("",expected.equals(actual));
	}
	@Test
	public void test06() {
		AbstractTile t = new FixedTile("L");
		t.setDirections(1,0,1,1);
		Token to = new Token(1, "apple"); 
		t.setToken(to);
		Player River = new Player("Blue");
		t.addPlayer(River);
		String expected = "[L0,1,[BLUE]]";
		String actual = t.toString();
		assertTrue("",expected.equals(actual));
	}
	
	@Test
	public void test07() {
		AbstractTile t = new FixedTile("L");
		t.setDirections(1,0,1,1);
		Token to = new Token(1, "apple"); 
		t.setToken(to);
		Player Carl = new Player("Green");
		t.addPlayer(Carl);
		String expected = "[L0,1,[GREEN]]";
		String actual = t.toString();
		assertTrue("",expected.equals(actual));
	}
}
