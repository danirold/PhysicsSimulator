package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	
	private PhysicsSimulator p_simulator;
	private Factory<Body> b_factory;
	private Factory<ForceLaws> fl_factory;
	
	public Controller(PhysicsSimulator p_simulator, Factory<Body> b_factory, Factory<ForceLaws> fl_factory) {
		this.p_simulator = p_simulator;
		this.b_factory = b_factory;
		this.fl_factory = fl_factory;
	}
	
	public void loadData(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray groups = jsonInput.getJSONArray("groups");
		for (int i = 0; i < groups.length(); ++i){
			p_simulator.addGroup(groups.getString(i));
		}
		
		if(jsonInput.has("laws")) {                    
			JSONArray laws = jsonInput.getJSONArray("laws");
			for(int i = 0; i < laws.length(); ++i) {
				JSONObject obj1 = laws.getJSONObject(i).getJSONObject("laws");
                JSONObject obj2 = new JSONObject();
                if(obj1.has("data")) {
                	obj2.put("data", obj1.getJSONObject("data"));
                }
                if(obj1.has("type")) {
                	obj2.put("type", obj1.getString("type"));
                }
                obj2.put("id", laws.getJSONObject(i).getString("id"));
				ForceLaws instance = fl_factory.createInstance(obj2);
				p_simulator.setForceLaws(laws.getJSONObject(i).getString("id"), instance); 
			}
		}
		
		JSONArray bodies = jsonInput.getJSONArray("bodies");
		for(int i = 0; i < bodies.length(); ++i) {
			Body body = b_factory.createInstance(bodies.getJSONObject(i));
			p_simulator.addBody(body);
		}
	}
	
	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out); 
		p.println("{");
        p.println("\"states\": [");
        p.print(p_simulator.toString());
        p.print(",");

        for (int i = 0; i < (n - 1); ++i) {
            p_simulator.advance();
            p.println(p_simulator.toString());
            p.print(",");
        }
        if(n >= 1) {
        	p_simulator.advance();
            p.println(p_simulator.toString());
        }

        p.println("]");
        p.println("}");
	}
	
	public void reset() {
		p_simulator.reset();
	}
	
	public void setDeltaTime(double dt) {
		p_simulator.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		p_simulator.addObserver(o);
	}
	
	public void removeObserver(SimulatorObserver o) {
		p_simulator.removeObserver(o);
	}
	
	public List<JSONObject> getForceLawsInfo(){
		return fl_factory.getInfo();
	}
	
	public void setForceLaws(String gId, JSONObject info) {
		ForceLaws f = fl_factory.createInstance(info);
		p_simulator.setForceLaws(gId, f);
	}
	
	public void run(int n) {
		for (int i = 0; i < n; ++i) {
            p_simulator.advance();
        }
		
	}
	
	
	
	
	
	
}
