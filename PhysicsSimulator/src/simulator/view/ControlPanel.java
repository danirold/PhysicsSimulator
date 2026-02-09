package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.desktop.QuitEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
	
	private Controller _ctrl;
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private boolean _stopped = true; // utilizado en los botones de run/stop
	private JButton _quitButton;
	private JButton files;
	private JButton fl_dialog;
	private JButton viewer_window;
	private JButton run;
	private JButton stop;
	
	private JSpinner steps;
	private JTextField textField;
	private ForceLawsDialog fld;
	// TODO a�ade m�s atributos aqu� �
	
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		_toolaBar = new JToolBar();
		add(_toolaBar, BorderLayout.PAGE_START);
		
		
		
		
		// TODO crear los diferentes botones/atributos y a�adirlos a _toolaBar.
		// Todos ellos han de tener su correspondiente tooltip. Puedes utilizar
		// _toolaBar.addSeparator() para a�adir la l�nea de separaci�n vertical
		// entre las componentes que lo necesiten
		// Quit Button
		
		//File_chooser
		// TODO crear el selector de ficheros
		_fc = new JFileChooser();
		files = new JButton();
		files.setToolTipText("Load an input file into the simulator");
		files.setIcon(new ImageIcon("resources/icons/open.png"));
		files.addActionListener((e) -> {
			try {
				select_file();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		_toolaBar.add(files);
		
		_toolaBar.addSeparator();
		
		//ForceLawsDialog
		fl_dialog = new JButton();
		fl_dialog.setToolTipText("Select force laws for groups");
		fl_dialog.setIcon(new ImageIcon("resources/icons/physics.png"));
		fl_dialog.addActionListener((e) -> fl_dialog());
		_toolaBar.add(fl_dialog);
		
		_toolaBar.addSeparator();
		
		//Viewer_window
		viewer_window = new JButton();
		viewer_window.setToolTipText("Open viewer window");
		viewer_window.setIcon(new ImageIcon("resources/icons/viewer.png"));
		viewer_window.addActionListener((e) -> viewer_window());
		_toolaBar.add(viewer_window);
		
		_toolaBar.addSeparator();
		
		//Run
		run = new JButton();
		run.setToolTipText("Run the simulator");
		run.setIcon(new ImageIcon("resources/icons/run.png"));
		run.addActionListener((e) -> run());
		_toolaBar.add(run);
		
		_toolaBar.addSeparator();
		
		//Stop
		stop = new JButton();
		stop.setToolTipText("Stop the simulator");
		stop.setIcon(new ImageIcon("resources/icons/stop.png"));
		stop.addActionListener((e) -> stop());
		_toolaBar.add(stop);
		
		_toolaBar.addSeparator();
		
		
		//Steps
		JLabel label1 = new JLabel("Steps");
		_toolaBar.add(label1);
		steps = new JSpinner();
		steps.setPreferredSize(new Dimension(75, 10));
		steps.setMinimumSize(new Dimension(100, 100));
		steps.setMaximumSize(new Dimension(100, 100));
		steps.setToolTipText("Simulation steps to run: 1-10000");
		_toolaBar.add(steps);
		
		
		
		_toolaBar.addSeparator();
		
		//DT
		JLabel label2 = new JLabel("Delta-Time");
		_toolaBar.add(label2);
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(80,10));
		textField.setMinimumSize(new Dimension(100, 100));
		textField.setMaximumSize(new Dimension(100, 100));
		textField.setToolTipText("Real time (seconds) corresponding to a step");//ajustar tamaño del delta time
		_toolaBar.add(textField);
		
		
		//Quit
		_toolaBar.add(Box.createGlue()); //this aligns the button to the right
		_toolaBar.addSeparator();
		_quitButton = new JButton();
		_quitButton.setToolTipText("Exit");
		_quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> Utils.quit(this));
		_toolaBar.add(_quitButton);
		
		
	
	// TODO el resto de m�todos van aqu�
	}
	
	private void select_file() throws FileNotFoundException {
		int n = _fc.showOpenDialog(Utils.getWindow(this));
		if (n == _fc.APPROVE_OPTION) {
			_ctrl.reset();                                 
            _ctrl.loadData(new FileInputStream(_fc.getSelectedFile()));
			//hay que cargar fichero
		}
	}
	
	private void fl_dialog() {
		if (fld == null) fld = new ForceLawsDialog(new JFrame(), _ctrl);//mirar el jframe
		fld.open();
		
	}
	
	private void viewer_window() {
		ViewerWindow vw = new ViewerWindow(new JFrame(), _ctrl);//mirar jframe
	}
	
	private void run() {
		files.setEnabled(false);
		fl_dialog.setEnabled(false);
		viewer_window.setEnabled(false);
		_stopped = false;
		_ctrl.setDeltaTime(Double.parseDouble(textField.getText()));
		run_sim((int) steps.getValue());
	}
	
	private void run_sim(int n) {
        if (n > 0 && !_stopped) {
            try {
                  _ctrl.run(1);
            } catch (Exception e) {
                  // TODO llamar a Utils.showErrorMsg con el mensaje de error que
                  //      corresponda
                  // TODO activar todos los botones
            	Utils.showErrorMsg("An error ocurred");
            	files.setEnabled(true);
            	fl_dialog.setEnabled(true);
        		viewer_window.setEnabled(true);
        		run.setEnabled(true);
        		stop.setEnabled(true);
        		_quitButton.setEnabled(true);
        		
            	
            	
                _stopped = true;
                return;
            }
            SwingUtilities.invokeLater(() -> run_sim(n - 1)); 
         } 
         else {
                  // TODO llamar a Utils.showErrorMsg con el mensaje de error que
                  //      corresponda
                  // TODO activar todos los botones
        	//Utils.showErrorMsg("An error ocurred");
         	files.setEnabled(true);
         	fl_dialog.setEnabled(true);
     		viewer_window.setEnabled(true);
     		run.setEnabled(true);
     		stop.setEnabled(true);
     		_quitButton.setEnabled(true);
            _stopped = true;

         }        
    }
	
	private void stop() {
		_stopped = true;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		textField.setText(String.valueOf(dt));
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	


}
