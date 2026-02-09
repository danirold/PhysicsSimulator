package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body {
	
	public MovingBody(String id, String gid, Vector2D position, Vector2D velocity, double mass) throws IllegalArgumentException {
		super(id, gid, position, velocity, mass);
	}

	@Override
	public void advance(double dt) {
		Vector2D acceleration = new Vector2D();
		if(mass != 0.0) {
			acceleration = force.scale(1.0/mass);
		}
		Vector2D sum = velocity.scale(dt);
		sum = sum.plus(acceleration.scale(0.5 * dt * dt));
		position = position.plus(sum);
		velocity = velocity.plus(acceleration.scale(dt));
	}

}
