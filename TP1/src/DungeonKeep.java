import java.util.Random;
import java.util.Scanner;

public class DungeonKeep {

	enum Direction {
		UP, DOWN, RIGHT, LEFT
	};

	enum GameStat {
		LOSE, WIN, RUNNING
	}

	private final int HEIGHT1 = 10;
	private final int WIDTH1 = 10;
	private final int HEIGHT2 = 9;
	private final int WIDTH2 = 9;
	private final int exit_door_1_y = 5;
	private final int exit_door_2_y = 6;
	private final int exit_door_1_x = 0;
	private final int exit_door_2_x = 0;
	private int height = HEIGHT1;
	private int current_map = 1;
	private int guardian_x = 8;
	private int guardian_y = 1;
	private int guardian_index = 0;
	private int hero_x = 1;
	private int hero_y = 1;
	private int ogre_x = 4;
	private int ogre_y = 1;
	private Direction direction;
	private GameStat game_stat = GameStat.RUNNING;
	private final int[] guardian_path_x = { 8, 7, 7, 7, 7, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8 };
	private final int[] guardian_path_y = { 1, 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 5, 4, 3, 2 };
	private char map1[][] = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', },
			{ 'X', 'H', ' ', ' ', 'I', ' ', 'X', ' ', 'G', 'X', },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X', },
			{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X', },
			{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X', },
			{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X', },
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', } };
	private char map2[][] = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', },
			{ 'I', ' ', ' ', ' ', 'O', ' ', ' ', 'k', 'X', }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', 'H', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', } };
	private char map[][] = map1;

	public void printMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
	}

	private void moveHero() {

		map[hero_y][hero_x] = ' ';

		switch (direction) {
		case UP:
			hero_y--;
			break;
		case DOWN:
			hero_y++;
			break;
		case RIGHT:
			hero_x++;
			break;
		case LEFT:
			hero_x--;
			break;
		}

		map[hero_y][hero_x] = 'H';

	}

	private void moveGuard() {
		map[guardian_y][guardian_x] = ' ';
		guardian_index++;
		if (guardian_index >= guardian_path_x.length || guardian_index >= guardian_path_y.length) {
			guardian_index = 0;
		}
		guardian_x = guardian_path_x[guardian_index];
		guardian_y = guardian_path_y[guardian_index];
		map[guardian_y][guardian_x] = 'G';
	}

	private void moveOgre(int dir) {

		map[ogre_y][ogre_x] = ' ';

		switch (dir) {
		case 0:
			ogre_y--;
			break;
		case 1:
			ogre_y++;
			break;
		case 2:
			ogre_x++;
			break;
		case 3:
			ogre_x--;
			break;
		}

		map[ogre_y][ogre_x] = 'O';

	}

	private void nextPosOgre() {
		Random rand = new Random();
		int dir = rand.nextInt(4);
		Boolean insideCanvas = false;
		char nextCharacter = '\0';
		switch (dir) {
		case 0:
			if (ogre_y > 0) {
				insideCanvas = true;
				nextCharacter = map[ogre_y - 1][ogre_x];
			}
			break;
		case 1:
			if (ogre_y < height - 1) {
				insideCanvas = true;
				nextCharacter = map[ogre_y + 1][ogre_x];
			}
			break;
		case 2:
			if (ogre_x < height - 1) {
				insideCanvas = true;
				nextCharacter = map[ogre_y][ogre_x + 1];

			}
			break;
		case 3:
			if (ogre_x > 0) {
				insideCanvas = true;
				nextCharacter = map[ogre_y][ogre_x - 1];
			}
			break;
		default:
			return;
		}
		if (insideCanvas) {
			if (nextCharacter == ' ')
				this.moveOgre(dir);
		}
	}

	private void checkOgre() {
		if ((hero_x == ogre_x && hero_y == ogre_y)
				|| (hero_x == ogre_x && (hero_y == ogre_y + 1 || hero_y == ogre_y - 1))
				|| (hero_y == ogre_y && (hero_x == ogre_x + 1 || hero_x == ogre_x - 1))) {
			this.game_stat = GameStat.LOSE;
		}
	}
	
	private void processInput() {
		Boolean insideCanvas = false;
		char nextCharacter = '\0';
		switch (direction) {
		case UP:
			if (hero_y > 0) {
				insideCanvas = true;
				nextCharacter = map[hero_y - 1][hero_x];
			}
			break;
		case DOWN:
			if (hero_y < height - 1) {
				insideCanvas = true;
				nextCharacter = map[hero_y + 1][hero_x];
			}
			break;
		case RIGHT:
			if (hero_x < height - 1) {
				insideCanvas = true;
				nextCharacter = map[hero_y][hero_x + 1];

			}
			break;
		case LEFT:
			if (hero_x > 0) {
				insideCanvas = true;
				nextCharacter = map[hero_y][hero_x - 1];
			}
			break;
		default:
			return;
		}

		if (insideCanvas) {
			if (nextCharacter == ' ') {
				this.moveHero();
				if (current_map == 1) {
					this.moveGuard();
					this.checkGuard();
				} else {
					this.nextPosOgre();
					this.checkOgre();
				}
				// this.moveGuard();
			} else if (nextCharacter == 'S') {
				if (current_map == 1)
					this.gotoMap2();
				else
					this.game_stat = DungeonKeep.GameStat.WIN;
			} else if (nextCharacter == 'k') {
				this.moveHero();
				if (current_map == 1) {
					this.moveGuard();
					this.checkGuard();
				} else {
					this.nextPosOgre();
					this.checkOgre();
				}
				// this.moveGuard();
				this.openDoors();
			}
			// this.checkGuard();
		}
	}

	private void gotoMap2() {
		current_map = 2;
		map = map2;
		height = HEIGHT2;
		hero_x = 1;
		hero_y = 7;
	}

	private void openDoors() {
		map1[exit_door_1_y][exit_door_1_x] = 'S';
		map1[exit_door_2_y][exit_door_2_x] = 'S';
	}

	private void checkGuard() {
		if ((hero_x == guardian_x && hero_y == guardian_y)
				|| (hero_x == guardian_x && (hero_y == guardian_y + 1 || hero_y == guardian_y - 1))
				|| (hero_y == guardian_y && (hero_x == guardian_x + 1 || hero_x == guardian_x - 1))) {
			this.game_stat = GameStat.LOSE;
		}
	}

	private Boolean getInput(Scanner keyboard_scanner) {
		System.out.print("Move: ");
		String input = keyboard_scanner.nextLine();
		switch (input) {
		case "w":
			direction = Direction.UP;
			return true;
		case "a":
			direction = Direction.LEFT;
			return true;
		case "s":
			direction = Direction.DOWN;
			return true;
		case "d":
			direction = Direction.RIGHT;
			return true;
		default:
			return false;
		}
	}

	public void run() {
		Scanner keyboard_scanner = new Scanner(System.in);
		this.printMap();
		while (this.game_stat == GameStat.RUNNING) {
			if (this.getInput(keyboard_scanner)) {
				this.processInput();
				this.printMap();
			}
		}
		keyboard_scanner.close();
		if (this.game_stat == GameStat.LOSE) {
			System.out.println("You lost!");
		} else if (this.game_stat == GameStat.WIN) {
			System.out.println("You won!");
		}
	}

	public static void main(String[] args) {
		DungeonKeep dungeonKeep = new DungeonKeep();
		dungeonKeep.run();
	}
}