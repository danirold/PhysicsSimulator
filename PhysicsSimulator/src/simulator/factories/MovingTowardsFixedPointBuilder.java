package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	
	private JSONObject _data;
	
	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towards a fixed point");
		_data = new JSONObject();
		_data.put("c", "A 2D vector, e.g, [1e14, 1.4e10]");
		_data.put("g", "Acceleration towards the fixpoint, e.g, 9.8");
	}

	public MovingTowardsFixedPointBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		if(!data.has("c") && !data.has("g")) {
			return new MovingTowardsFixedPoint();
		}
		else if (!data.has("c") && data.has("g")) {
			return new MovingTowardsFixedPoint(data.getDouble("g"));
		}
		else if (data.has("c") && !data.has("g")) {
			return new MovingTowardsFixedPoint(c_constructor(data));
		}
		else return new MovingTowardsFixedPoint(c_constructor(data), data.getDouble("g"));
	}
	
	private Vector2D c_constructor(JSONObject data_object) {
		JSONArray array = data_object.getJSONArray("c");
		double x = array.getDouble(0);
		double y = array.getDouble(1);
		return new Vector2D(x, y);
	}
	
	public JSONObject getInfo() {		
		JSONObject info = new JSONObject();
		info.put("type", _typeTag);
		info.put("desc", _desc);
		info.put("data", _data);
		return info;
	}
}