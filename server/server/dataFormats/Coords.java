package server.dataFormats;

/**
 *
 * @author Cirben
 */
class Coords {
	
	private double x;
	private double y;
	
	Coords(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double getX(){
		return x;		
	}	

	public double getY(){
		return y;
	}
}
