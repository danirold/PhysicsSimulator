package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class ForceLawsDialog extends JDialog implements SimulatorObserver {
	   private DefaultComboBoxModel<String> _lawsModel;
	   private DefaultComboBoxModel<String> _groupsModel;
	   private DefaultTableModel _dataTableModel;
	   private int _status;
	   private int _selectedLawsIndex;
	   private JTable table;
	   private JComboBox laws;
	   private JComboBox groups;
	   private int active_rows;
	  
	   private Controller _ctrl;
	   private List<JSONObject> _forceLawsInfo;
	   private String[] _headers = { "Key", "Value", "Description" };
	   
	   // TODO en caso de ser necesario, añadir los atributos aquí...
	   ForceLawsDialog(Frame parent, Controller ctrl) {
	         super(parent, true);
	         _ctrl = ctrl;
	         initGUI();
	         _ctrl.addObserver(this);
	         active_rows = 0;
	         // TODO registrar this como observer;
	   }
	   private void initGUI() {
		   setTitle("Force Laws Selection");
	       JPanel mainPanel = new JPanel();
	       mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); 
	       setContentPane(mainPanel);
	       JLabel help = new JLabel("<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> (default values are used for parametes with no value).</p></html>");
	       help.setAlignmentX(CENTER_ALIGNMENT); 
	       mainPanel.add(help); 
	       mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
	         // _forceLawsInfo se usará para establecer la información en la tabla
	       _forceLawsInfo = _ctrl.getForceLawsInfo();
	         // TODO crear un JTable que use _dataTableModel, y añadirla al panel
	       
	       
	       
	         _dataTableModel = new DefaultTableModel(13, 3) { 	
	  

	@Override
	                public boolean isCellEditable(int row, int column) {
		                return column == 1;
				
		            // TODO hacer editable solo la columna 1
	                } 
	
 
	          };
	          
	          _dataTableModel.setColumnIdentifiers(_headers);
	          
	          table = new JTable(_dataTableModel);
	          
	          table.getColumnModel().getColumn(2).setPreferredWidth(250);
	          
	          JScrollPane scroll = new JScrollPane(table);
	          
	          mainPanel.add(scroll);
	          
	    
	         

	         _lawsModel = new DefaultComboBoxModel<>();
	         // TODO añadir la descripción de todas las leyes de fuerza a _lawsModel
	         for (JSONObject obj : _forceLawsInfo) {
	        	 _lawsModel.addElement(obj.getString("desc"));
	         }
	         
	         
	         
	         
	         // TODO crear un combobox que use _lawsModel y añadirlo al panel
	         laws = new JComboBox(_lawsModel);
	         laws.addActionListener((e) -> select_law());
	         
	         
	         
	         JPanel panel1 = new JPanel();
	         panel1.setLayout(new FlowLayout());
	         mainPanel.add(panel1);
	         
	         JPanel panel2 = new JPanel();
	         panel2.setLayout(new FlowLayout());
	    
	         panel2.add(new JLabel("Force Law:"));
	         panel2.add(laws);
	         
	         _groupsModel = new DefaultComboBoxModel<>();
	         
	         groups = new JComboBox(_groupsModel);
	         panel2.add(new JLabel("Group: "));
	         panel2.add(groups);
	         
	         mainPanel.add(panel2);
	         JPanel panel3 = new JPanel();
	         // TODO crear un combobox que use _groupsModel y añadirlo al panel
	         // TODO crear los botones OK y Cancel y añadirlos al panel
	         JButton OK = new JButton();//pensar si dejarlo aqui o sacarlo fuera
	         OK.setText("OK");
	         OK.addActionListener((e) -> button_OK());
	         JButton Cancel = new JButton();
	         Cancel.setText("Cancel");
	         Cancel.addActionListener((e) -> button_cancel());
	         panel3.add(Cancel);
	         panel3.add(OK);
	         
	         //inicializacion de la tabla
	         select_law();
	         
	         
	         mainPanel.add(panel3);
	         
	         setPreferredSize(new Dimension(700, 400));
	         pack();
	         setResizable(false);
	         setVisible(false);
	   }
	   
	   
	   
	   public int open() {
	         if (_groupsModel.getSize() == 0) return _status;
	         // TODO Establecer la posición de la ventana de diálogo de tal manera que se
	         // abra en el centro de la ventana principal  
	         setLocationRelativeTo(null);
	         pack();
	         setVisible(true);
	         return _status;
	   }
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_groupsModel.removeAllElements();
		
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {//rellenarlo
		_groupsModel.removeAllElements();
		for (String k : groups.keySet()) {
			_groupsModel.addElement(groups.get(k).getId());
		}
		
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_groupsModel.addElement(g.getId());
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
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
	
	/*private Object valueAt(int row, int col) {
		String clave;
		if (col > 0) {
			clave = (String) table.getValueAt(row, col - 1);
			if (clave.equals("c")) {
				JSONArray j = new JSONArray();
				String valor = (String) table.getValueAt(row, col);
				String[] sol = valor.trim().split(",");
				sol[0] = sol[0].replace('[', ' ');
				sol[1] = sol[1].replace(']', ' ');
			    Double x = Double.parseDouble(sol[0]);
			    Double y = Double.parseDouble(sol[1]);
			    j.put(0, x);
			    j.put(1, y);
				
			    return j;
			}
			else {
				return table.getValueAt(row, col);
			}	
		}	
		else {
			return table.getValueAt(row, col);
			
		}
	}*/
	
	
	
	public String getData() {
		StringBuilder b  = new StringBuilder();
		b.append('{');
		for (int i = 0; i < active_rows; i++) {
			String clave = table.getValueAt(i, 0).toString();
			String valor = table.getValueAt(i, 1).toString();
			if (!valor.isEmpty()) {
				b.append('"');
				b.append(clave);
				b.append('"');
				b.append(':');
				b.append(valor);
				b.append(',');
			}
		}
		if (b.length() > 1) {
			b.deleteCharAt(b.length() - 1);
		}
		b.append('}');
		return b.toString();
	}
	
	
	
	public void button_OK() {
		try {
			JSONObject obj = new JSONObject("{\"type\":" + _forceLawsInfo.get(laws.getSelectedIndex()).getString("type") + ", \"data\":" + getData() + "}");
			/*JSONObject obj = new JSONObject();
			for (int i = 0; i < active_rows; ++i) {
				if (table.getValueAt(i, 1) != null) obj.put(valueAt(i, 0).toString(), valueAt(i, 1));
			}
			
			JSONObject obj2 = new JSONObject();
			obj2.put("data", obj);
			obj2.put("type", _forceLawsInfo.get(_selectedLawsIndex).getString("type"));*/
			_ctrl.setForceLaws(groups.getSelectedItem().toString(), obj);
			this.setVisible(false);
			
			_status = 1;
		}
		catch (Exception e) {
			Utils.showErrorMsg("An error ocurred");
		}

		
	}
	
	public void button_cancel() {
		_status = 0;
		this.setVisible(false);
	}
	
	private void select_law() {
		
		boolean change = _selectedLawsIndex != laws.getSelectedIndex();
		
		if (change) {
			for (int i = 0; i < _dataTableModel.getRowCount(); ++i) {
				for (int j = 0; j < _dataTableModel.getColumnCount(); ++j) {
					table.setValueAt(null, i, j);
				}
			}
		}
	
		
		
		_selectedLawsIndex = laws.getSelectedIndex();
		JSONObject info = _forceLawsInfo.get(_selectedLawsIndex);
		JSONObject data = (JSONObject) info.get("data");
		
		active_rows = 0;
		
		int i = 0;
		for (String s : data.keySet()) {
			table.setValueAt(s, i, 0);
			if (change) table.setValueAt(null, i, 1);
			table.setValueAt(data.get(s), i, 2);
			
			++i;
			active_rows++;
		}
	}
	
	   

//TODO el resto de métodos van aquí...
	   
	   
}
