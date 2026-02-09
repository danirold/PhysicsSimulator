package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {

	private Vector2D c;
	private double g;
	
	public MovingTowardsFixedPoint(Vector2D c, double g) throws IllegalArgumentException {
		if(c == null || g <= 0) {
			throw new IllegalArgumentException();
		}
		this.c = c;
		this.g = g;
	}
	
	public MovingTowardsFixedPoint(){
		this(new Vector2D(), 9.81);
	}
	
	public MovingTowardsFixedPoint(Vector2D c){
		this(c, 9.81);
	}
	
	public MovingTowardsFixedPoint(double g){
		this(new Vector2D(), g);
	}
	
	@Override
	public void apply(List<Body> bs) {
		for(Body b: bs) {
			Vector2D f = c.minus(b.getPosition()).direction().scale(b.getMass() * g);
			b.addForce(f);
		}
	}
	
	public String toString() {
		return "Moving towards " + this.c.toString() + " with constant acceleration " + this.g;
	}
}
