package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JToolBar implements SimulatorObserver {
	// TODO A�adir los atributos necesarios, si hace falta �
	private int numGroups;//¿pàra cuando cambien los JLabel?
	private double time;
	
	JLabel time_label;
	JLabel num_groups;
	
	
	//Igual hay que tener un _ctrl
	Controller _ctrl;
	
	StatusBar(Controller ctrl) {
		initGUI();
		_ctrl = ctrl;
		_ctrl.addObserver(this);
		numGroups = 0;
		time = 0.0;
	// TODO registrar this como observador
	}
	
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		time_label = new JLabel("Time: 0.0");
		this.add(time_label);
		JSeparator s = new JSeparator(JSeparator.VERTICAL);
		s.setPreferredSize(new Dimension(10, 20));
		this.add(s);
		num_groups = new JLabel("Groups: 0");//ver si esto es así
		this.add(num_groups);
		// TODO Crear una etiqueta de tiempo y a�adirla al panel
		// TODO Crear la etiqueta de n�mero de grupos y a�adirla al panel
		// TODO Utilizar el siguiente c�digo para a�adir un separador vertical
		
		
	}
		// TODO el resto de m�todos van aqu�

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		if (!groups.isEmpty()) {
			this.time = time;
			time_label.setText("Time: " + this.time);
		}
		
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		this.time = 0;
		this.numGroups = 0;
		time_label.setText("Time: " + this.time);
		num_groups.setText("Groups: " + numGroups);	
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		numGroups++;
		num_groups.setText("Groups: " + numGroups);
		
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
}
