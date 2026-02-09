package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class GroupsTableModel extends AbstractTableModel implements SimulatorObserver {
	   private static String[] _header = { "Id", "Force Laws", "Bodies" };
	   List<BodiesGroup> _groups;
	   Controller _ctrl;
	   
	   GroupsTableModel(Controller ctrl) {
	         _groups = new ArrayList<>();
	         _ctrl = ctrl;
	         _ctrl.addObserver(this);
	         
	         // TODO registrar this como observador;
	   }
	   
	 
	
	public String getColumnName(int col) {
		return _header[col];
	}
	   
	@Override
	public int getRowCount() {
		return _groups.size();
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
			return _groups.get(rowIndex).getId();
		}
		else if (columnIndex == 1) {
			return _groups.get(rowIndex).getForceLawsInfo();
		}
		else if (columnIndex == 2) {
			String info = "";
			for (Body b : _groups.get(rowIndex)) {
				info += b.getId();
				info += " ";
			}
			return info;
		}
		else return null;
	}
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_groups.clear();
		fireTableDataChanged();
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		_groups.clear();
		for (String k: groups.keySet()) {
			_groups.add(groups.get(k));
		}
		fireTableDataChanged();
		
		
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_groups.add(g);
		fireTableDataChanged();
		
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
	    int index = _groups.indexOf(groups.get(b.getgId()));
	    _groups.get(index).addBody(b);
		
		fireTableDataChanged();
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		fireTableDataChanged();
		
		// TODO Auto-generated method stub	
	}
}
