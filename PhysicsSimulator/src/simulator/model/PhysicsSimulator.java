package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class PhysicsSimulator implements Observable<SimulatorObserver>{

	private double dt;
	private ForceLaws forcelaws;
	private double current_time;
	private HashMap<String,BodiesGroup> map;
	private List<String> id_list;
	
	private List<SimulatorObserver> observer_list;
	private Map<String, BodiesGroup> _groupsRO;

	
	public PhysicsSimulator(ForceLaws forcelaws, double dt) throws IllegalArgumentException {
		if(dt < 0 || forcelaws == null) {
			throw new IllegalArgumentException();
		}
		this.dt = dt;
		this.forcelaws = forcelaws;
		this.current_time = 0.0;
		this.map = new HashMap<String,BodiesGroup>();
		this.id_list = new ArrayList<String>();
		this.observer_list = new ArrayList<SimulatorObserver>();
		_groupsRO = Collections.unmodifiableMap(map);
	}
	
	public void advance() {
		for(String s: map.keySet()) {
			map.get(s).advance(dt);
		}
		this.current_time += this.dt;
		
		for (SimulatorObserver o : observer_list) {
			o.onAdvance(_groupsRO, current_time);
			
		}
	}
	
	public void addGroup(String id) throws IllegalArgumentException{
		BodiesGroup bg = new BodiesGroup(id, this.forcelaws);
		for(String s: map.keySet()) {
			if(map.get(s).getId().equals(id)) {
				throw new IllegalArgumentException();
			}
		}
		map.put(id, bg);
		id_list.add(id);
		
		for (SimulatorObserver o : observer_list) {
			o.onGroupAdded(_groupsRO, bg);
			
		}
		
	}
	
	public void addBody(Body b) throws IllegalArgumentException{
		boolean exist = false;
		for(String s: map.keySet()) {
			if(map.get(s).getId().equals(b.getgId())) {
				map.get(s).addBody(b);
				exist = true;
				break;
			}
		}
		if(!exist) {
			throw new IllegalArgumentException();
		}
		
		for (SimulatorObserver o : observer_list) {
			o.onBodyAdded(_groupsRO, b);
		}
		
		
	}
	
	public void setForceLaws(String id, ForceLaws fl){
		BodiesGroup bg = null;
		for(String s: map.keySet()) {
			if(map.get(s).getId().equals(id)) {
				bg = map.get(s);
				map.get(s).setForceLaws(fl);
				break;
			}
	    }
		
		for (SimulatorObserver o : observer_list) {
			o.onForceLawsChanged(bg);
			
		}
	}
	
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		jo.put("time", current_time);
		ArrayList<JSONObject> listjo = new ArrayList<JSONObject>(); 
		for(String s: id_list) {
			listjo.add(map.get(s).getState());
		}
		jo.put("groups", listjo);
		return jo; 
	}
	
	public String toString() {
		return getState().toString();
	}
	
	public void reset() {
		map.clear();
		id_list.clear();
		dt = 0.0;
		
		for (SimulatorObserver o : observer_list) {
			o.onReset(_groupsRO, current_time, dt);
		}
	}
	
	public void setDeltaTime(double dt) throws IllegalArgumentException{
		if(dt <= 0) {
			throw new IllegalArgumentException();
		}
		this.dt = dt;
		
		for (SimulatorObserver o : observer_list) {
			o.onDeltaTimeChanged(dt);
		}
	}
	
	public void addObserver(SimulatorObserver o) {
		if (!observer_list.contains(o)) {
			observer_list.add(o);
			observer_list.get(observer_list.size() - 1).onRegister(_groupsRO, current_time, dt);
		}
		
		
	}
	
	public void removeObserver(SimulatorObserver o) {
		if (observer_list.contains(o)) {
			observer_list.remove(o);
		}
	}

}
