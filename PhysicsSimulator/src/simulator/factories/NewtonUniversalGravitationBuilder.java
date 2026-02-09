package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{
	private JSONObject _data;
	
	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton’s law of universal gravitation");
		_data = new JSONObject();
		_data.put("G", "Gravitational constant, e.g, 6.67E-11");
		
	}

	public NewtonUniversalGravitationBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		if(data.has("G")) {
			return new NewtonUniversalGravitation(data.getDouble("G"));
		}
		else return new NewtonUniversalGravitation();
	}
	
	public JSONObject getInfo() {		
		JSONObject info = new JSONObject();
		info.put("type", _typeTag);
		info.put("desc", _desc);
		info.put("data", _data);
		return info;
	}
	
	

}
