package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;

public class MovingBodyBuilder extends Builder<Body> {
	
	public MovingBodyBuilder() {
		super("mv_body", "MovingBody");
	}

	public MovingBodyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}

	@Override
	protected Body createInstance(JSONObject data) throws IllegalArgumentException {
		if(!data.has("id") || !data.has("gid") || !data.has("p") || !data.has("v") || !data.has("m")) {
			throw new IllegalArgumentException();
		}
		
		Vector2D position = isVector2D(data.getJSONArray("p"));
		Vector2D velocity = isVector2D(data.getJSONArray("v"));
		return new MovingBody(data.getString("id"), data.getString("gid"), position, velocity, data.getDouble("m"));
	}

	private Vector2D isVector2D(JSONArray data_vector) {
		if(data_vector.length() != 2) {
			throw new IllegalArgumentException();
		}
		double x = data_vector.getDouble(0);
		double y = data_vector.getDouble(1);
		
		return new Vector2D(x, y);
	}
}
