package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
	    super("Physics Simulator");
	    _ctrl = ctrl;
	    initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		// TODO crear ControlPanel y a�adirlo en PAGE_START de mainPanel
		ControlPanel controlPanel = new ControlPanel(_ctrl);
		mainPanel.add(controlPanel, BorderLayout.PAGE_START);
	    // TODO crear StatusBar y a�adirlo en PAGE_END de mainPanel
		StatusBar statusBar = new StatusBar(_ctrl);
		mainPanel.add(statusBar, BorderLayout.PAGE_END);
	    // Definici�n del panel de tablas (usa un BoxLayout vertical)
	    JPanel contentPanel = new JPanel();
	    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
	    mainPanel.add(contentPanel, BorderLayout.CENTER);
	    // TODO crear la tabla de grupos y a�adirla a contentPanel.
	    // Usa setPreferredSize(new Dimension(500, 250)) para fijar su tama�o
	    GroupsTableModel t1 = new GroupsTableModel(_ctrl);
	    InfoTable groups_table = new InfoTable("Groups", t1);
	    groups_table.table.getColumnModel().getColumn(1).setPreferredWidth(270);
	    
	    groups_table.setPreferredSize(new Dimension(500, 250));
	    
	    
	    contentPanel.add(groups_table);
	   // TODO crear la tabla de cuerpos y a�adirla a contentPanel.
	   // Usa setPreferredSize(new Dimension(500, 250)) para fijar su tama�o
	    BodiesTableModel t2 = new BodiesTableModel(_ctrl);
	    InfoTable bodies_table = new InfoTable("Bodies", t2);
	    bodies_table.table.getColumnModel().getColumn(3).setPreferredWidth(200);
 	    bodies_table.table.getColumnModel().getColumn(4).setPreferredWidth(200);
 	    bodies_table.table.getColumnModel().getColumn(5).setPreferredWidth(200);
	    bodies_table.setPreferredSize(new Dimension(500, 250));
	    contentPanel.add(bodies_table);
	   // TODO llama a Utils.quit(MainWindow.this) en el m�todo windowClosing
	    addWindowListener(new WindowAdapter() { 
	    	public void windowClosing(WindowEvent e) {
	    		Utils.quit(MainWindow.this);
	    	}
	    });
	    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    pack();
	    setVisible(true);
	}

}
