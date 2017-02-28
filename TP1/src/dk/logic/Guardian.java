package dk.logic;
import dk.util.Coordinates;

public abstract class Guardian extends Character {
	
	protected Coordinates path[] = {
			new Coordinates(8,1),
			new Coordinates(7,1),
			new Coordinates(7,2),
			new Coordinates(7,3),
			new Coordinates(7,4),
			new Coordinates(7,5),
			new Coordinates(6,5),
			new Coordinates(5,5),
			new Coordinates(4,5),
			new Coordinates(3,5),
			new Coordinates(2,5),
			new Coordinates(1,5),
			new Coordinates(1,6),
			new Coordinates(2,6),
			new Coordinates(3,6),
			new Coordinates(4,6),
			new Coordinates(5,6),
			new Coordinates(6,6),
			new Coordinates(7,6),
			new Coordinates(8,6),
			new Coordinates(8,5),
			new Coordinates(8,4),
			new Coordinates(8,3),
			new Coordinates(8,2)
	};

	protected int indexPath = 0;
	protected Boolean sleeping = false;

	public Guardian() {
		coordinates = path[indexPath];
	}
	
	protected abstract boolean moveCharacter(Game game);
	
	public Boolean getIsSleeping(){
		return sleeping;
	}
}