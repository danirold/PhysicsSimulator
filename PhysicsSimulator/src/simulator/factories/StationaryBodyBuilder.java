package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.StationaryBody;

public class StationaryBodyBuilder extends Builder<Body>{
	
	public StationaryBodyBuilder() {
		super("st_body", "StationaryBody");
	}

	public StationaryBodyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}

	@Override
	protected Body createInstance(JSONObject data) throws IllegalArgumentException {
		if(!data.has("id") || !data.has("gid") || !data.has("p") || !data.has("m")) {
			throw new IllegalArgumentException();
		}
		Vector2D position = isVector2D(data.getJSONArray("p"));
		return new StationaryBody(data.getString("id"), data.getString("gid"), position, data.getDouble("m"));
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
