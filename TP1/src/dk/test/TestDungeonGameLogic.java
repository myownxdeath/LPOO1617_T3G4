package dk.test;

import static org.junit.Assert.*;
import org.junit.Test;
import dk.logic.*;

public class TestDungeonGameLogic {
	char map[][] = {{'X','X','X','X','X'},
			{'X','H',' ','G','X'},
			{'I',' ',' ',' ','X'},
			{'I','k',' ',' ','X'},
			{'X','X','X','X','X'}};

	@Test
	public void testMoveHeroIntoToFreeCell() {
		Game game = new Game(map);
		assertEquals(new Coordinates(1,1), game.getHero().getCoord());
		game.processInput(GameCharacter.Direction.DOWN);
		assertEquals(new Coordinates(1,2), game.getHero().getCoord()); 
	}
	
	@Test
	public void testMoveHeroIntoToWall() {
		Game game = new Game(map);
		assertEquals(new Coordinates(1,1), game.getHero().getCoord());
		game.processInput(GameCharacter.Direction.UP);
		assertEquals(new Coordinates(1,1), game.getHero().getCoord()); 
		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(1,1), game.getHero().getCoord()); 
	}

	@Test
	public void testHeroIsCapturedByGuard() {
		Game game = new Game(map);
		assertFalse(game.isGameOver());
		game.processInput(GameCharacter.Direction.RIGHT);
		assertTrue(game.isGameOver());
		assertEquals(Game.GameStat.LOSE, game.getGameStatus()); 
	}
	
	@Test
	public void testMoveHeroIntoToClosedDoors() {
		Game game = new Game(map);
		assertEquals(new Coordinates(1,1), game.getHero().getCoord());
		game.processInput(GameCharacter.Direction.DOWN);
		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals('I', game.getMap(new Coordinates(0,2)));
		assertEquals(new Coordinates(1,2), game.getHero().getCoord());
		assertEquals(Game.GameStat.RUNNING, game.getGameStatus());
	}
	
	@Test
	public void testMoveHeroOpensDoorsAndWins() {
		Game game = new Game(map);
		assertEquals(new Coordinates(1,1), game.getHero().getCoord());
		game.processInput(GameCharacter.Direction.DOWN);
		assertEquals('I', game.getMap(new Coordinates(0,2)));
		assertEquals('I', game.getMap(new Coordinates(0,3)));
		game.processInput(GameCharacter.Direction.DOWN);
		assertTrue(game.getHeroHasKey());
		assertNotEquals('X', game.getMap(new Coordinates(0,2)));
		assertEquals('S', game.getMap(new Coordinates(0,2)));
		assertEquals('S', game.getMap(new Coordinates(0,3)));
		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(0,3), game.getHero().getCoord());
		assertEquals(Game.GameStat.WIN, game.getGameStatus());
 
	}
	
}
