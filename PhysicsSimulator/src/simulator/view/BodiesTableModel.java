package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	   private static String[] _header = { "Id", "gId", "Mass", "Velocity", "Position", "Force"};
	   List<Body> _bodies;
	   BodiesTableModel(Controller ctrl) {
	         _bodies = new ArrayList<>();
	         ctrl.addObserver(this);
	         // TODO registrar this como observer
	   }
	   
	   public String getColumnName(int col) {
		   return _header[col];
	   }
	   
	   
	   //Duda en el advance, si hay que cambiar la lista por una nueva, y sobre si hacer los métodos ·"manualmente".
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		_bodies.clear();
		for (String s: groups.keySet()) {
			for (Body b: groups.get(s)) {
				_bodies.add(b);
			}
		}
		fireTableDataChanged();
	}
	
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_bodies.clear();
		fireTableDataChanged();
		
	}
	
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_bodies.clear();
		for (String k: groups.keySet()) {
			for (Body b: groups.get(k)) {
				_bodies.add(b);
			}
		}
		fireTableDataChanged();
		
	}
	
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		for (Body b : g) {
			_bodies.add(b);
		}
		fireTableDataChanged();
		
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_bodies.add(b);
		fireTableDataChanged();
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return _bodies.size();
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return _header.length;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if (columnIndex == 0) {
			return _bodies.get(rowIndex).getId();
		}
		else if (columnIndex == 1) {
			return _bodies.get(rowIndex).getgId();
		}
		else if (columnIndex == 2) {
			return _bodies.get(rowIndex).getMass();
		}
		else if (columnIndex == 3) {
			return _bodies.get(rowIndex).getVelocity();
		}
		else if (columnIndex == 4) {
			return _bodies.get(rowIndex).getPosition();
		}
		else if (columnIndex == 5) {
			return _bodies.get(rowIndex).getForce();
		}
		return null;
	}
	
}
	// TODO el resto de métodos van aquí... }
