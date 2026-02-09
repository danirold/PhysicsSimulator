package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BodiesGroup implements Iterable<Body> {

	private String id;
	private ForceLaws forcelaws;
	private List<Body> bs;
	private List<Body> _bodiesRO;
	
	public BodiesGroup(String id, ForceLaws fl) throws IllegalArgumentException {
		if (id == null || id.trim().length() <= 0 || fl == null) {
			throw new IllegalArgumentException();
		}
		this.id = id;
		this.forcelaws = fl;
		this.bs = new ArrayList<Body>();
		_bodiesRO = Collections.unmodifiableList(bs);
	}
	
	public String getId() {
		return this.id;
	}
	
	void setForceLaws(ForceLaws fl) throws IllegalArgumentException {
		if(fl == null) {
			throw new IllegalArgumentException();
		}
		this.forcelaws = fl;
	}
	
	public void addBody(Body b) {
		if (b == null) {
			throw new IllegalArgumentException();
		}
		else {
			boolean exists = false;//parche
			for(Body body: bs) {
				if (b.equals(body)) {
					exists = true;
					//throw new IllegalArgumentException();
				}
			}
			if (!exists) bs.add(b);
		}
	}
	
	void advance(double dt) {
		if(dt <= 0) {
			throw new IllegalArgumentException();
		}
		for(Body b: bs) {
			b.resetForce();
		}
		forcelaws.apply(bs);
		for(Body b: bs) {
			b.advance(dt);
		}
	}
	
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		jo.put("id", this.id);
		JSONArray listjo = new JSONArray(); 
		for(Body b: bs) {
			listjo.put(b.getState());
		}
		jo.put("bodies", listjo);
		return jo; 
	}
	
	public String toString() {
		return getState().toString();
	}
	
	public String getForceLawsInfo() {
		return forcelaws.toString();
	}

	@Override
	public Iterator<Body> iterator() {
		return _bodiesRO.iterator();
	}
	
}
