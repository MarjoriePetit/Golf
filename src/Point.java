
public class Point {
	private double x;
	private double y;
	
	public Point(double x,double y){
		int n;
		x = x*10000;
		n = (int)x;
		if ((n % 10) > 5){
			n++;
		}
		n = n/10;
		x = n;
		x = x/1000;
		this.x = x;
		y = y*10000;
		n = (int)y;
		if ((n % 10) > 5){
			n++;
		}
		n = n/10;
		y = n;
		y = y/1000;
		this.y = y;
	}
	
	public void Affiche(){
		System.out.println("(" + this.x + "," + this.y + ")");
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public Point clone(){
		return new Point(this.x ,this.y);
	}
	
	//test égalité entre deux points
	public boolean equals(Object obj){
		boolean result = false;
	    if (obj instanceof Point) {
	        Point that = (Point) obj;
	        result = (this.getX() == that.getX() && this.getY() == that.getY());
	    }
	    return result;
	}
}
