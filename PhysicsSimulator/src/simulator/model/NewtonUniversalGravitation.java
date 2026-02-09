
package simulator.model;

import java.util.List;

public class NewtonUniversalGravitation implements ForceLaws{

	private double G;
	
	public NewtonUniversalGravitation() {
		this.G = 6.67E-11;
	}
	
	public NewtonUniversalGravitation(double G) throws IllegalArgumentException {
		if(G <= 0) {
			throw new IllegalArgumentException();
		}
		this.G = G;
	}
	
	@Override
	public void apply(List<Body> bs) {
		for(Body b1: bs) {
			double force_b1 = 0.0;
			for(Body b2: bs) {
				if (!b1.getPosition().equals(b2.getPosition())) {
					force_b1 = G * b1.getMass() * b2.getMass() / (b2.getPosition().distanceTo(b1.getPosition()) * b2.getPosition().distanceTo(b1.getPosition())); 
					b1.addForce(b2.getPosition().minus(b1.getPosition()).direction().scale(force_b1));
				}
			}
		}
	}
	
	public String toString() {
		return "Newton's Universal Gravitation with G=" + this.G;
	}

}
