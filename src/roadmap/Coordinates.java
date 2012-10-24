package roadmap;

public class Coordinates {

	private int xCoord;
	private int yCoord;

	public Coordinates(){
		xCoord = yCoord = -1;
	}
	
	public Coordinates(int xCoord, int yCoord){
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
	
	public int getxCoord() {
		return xCoord;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

}