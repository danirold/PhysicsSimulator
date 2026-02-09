package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Scrollbar;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class InfoTable extends JPanel {
	   JTable table;
	   String _title;
	   TableModel _tableModel;
	   boolean groups;
	   InfoTable(String title, TableModel tableModel) {
		   _title = title;
	       _tableModel = tableModel;
	       this.groups = groups;
	       
	       
	       initGUI(); 
	   }
	   
	   private void initGUI() {
		   this.setLayout(new BorderLayout());
		   Border b = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), _title);
		   this.setBorder(b); 
		   
		  
		   table = new JTable(_tableModel);

		   this.add(new JScrollPane(table));
		   
		   
	         // TODO cambiar el layout del panel a BorderLayout()
	         // TODO añadir un borde con título al JPanel, con el texto _title
	         // TODO añadir un JTable (con barra de desplazamiento vertical) que use
	         //      _tableModel
		   
	   }

	

	   
}
