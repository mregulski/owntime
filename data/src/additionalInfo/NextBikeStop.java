package additionalInfo;

public class NextBikeStop {
	private int number;
	private float lat;
	private float lng;
	private static String name;
	private static int bikes;
	
	public NextBikeStop(){
		
	}

	public NextBikeStop(int number, float lat, float lng, String name, int bikes){
		this.number=number;
		this.lat=lat;
		this.lng=lng;
		this.name=name;
		this.bikes=bikes;
	}
	
	public int getNumber(){
		return number;
	}
	
	public void setNumber(int number){
		this.number=number;
	}
	
	public float getLat(){
		return lat;
	}
	
	public void setLat(float lat){
		this.lat=lat;
	}
	
	public float getLng(){
		return lng;
	}
	
	public void setLng(float lng){
		this.lng=lng;
	}
	
	public static String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public static int getBikes(){
		return bikes;
	}
	
	public void setBikes(int bikes){
		this.bikes=bikes;
	}
}
