package simulator.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class ViewerWindow extends JFrame implements SimulatorObserver {
	   private Controller _ctrl;
	   private SimulationViewer _viewer;
	   private JFrame _parent;
	   ViewerWindow(JFrame parent, Controller ctrl) {
	         super("Simulation Viewer");
	         _ctrl = ctrl;
	         _parent = parent;
	          intiGUI();
	          _ctrl.addObserver(this);
	         // TODO registrar this como observador
	   }
	   private void intiGUI() {
	         JPanel mainPanel = new JPanel(new BorderLayout());
	         
	         setContentPane(new JScrollPane(mainPanel));
	        
	         // TODO poner contentPane como mainPanel con scrollbars (JScrollPane)
	         _viewer = new Viewer();
	         mainPanel.add(_viewer, BorderLayout.CENTER);
	         // TODO crear el viewer y añadirlo a mainPanel (en el centro)
	         
	         // TODO en el método windowClosing, eliminar ‘this’ de los observadores
	         addWindowListener(new WindowAdapter() { 
	 	    	public void windowClosing(WindowEvent e) {
	 	    		_ctrl.removeObserver(ViewerWindow.this);
	 	    	}
	 	     });
	         pack();
	         if (_parent != null)
	               setLocation(
	                     _parent.getLocation().x + _parent.getWidth()/2 - getWidth()/2,
	                     _parent.getLocation().y + _parent.getHeight()/2 - getHeight()/2);
	         setVisible(true);
	   }
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		_viewer.update();
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_viewer.reset();
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {//hay que hacerlo
		_viewer.reset();
		for (String k: groups.keySet()) {
			_viewer.addGroup(groups.get(k));
			for (Body b: groups.get(k)) {
				_viewer.addBody(b);
			}
			
		}
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_viewer.addGroup(g);
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_viewer.addBody(b);
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
}
	// TODO otros métodos van aquí.... }