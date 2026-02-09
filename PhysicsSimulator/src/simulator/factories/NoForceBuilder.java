package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{
	private JSONObject _data;
	
	public NoForceBuilder() {
		super("nf", "No force");
		_data = new JSONObject();
	}

	public NoForceBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		return new NoForce();
	}
	
	public JSONObject getInfo() {		
		JSONObject info = new JSONObject();
		info.put("type", _typeTag);
		info.put("desc", _desc);
		info.put("data", _data);
		return info;
	}
}
