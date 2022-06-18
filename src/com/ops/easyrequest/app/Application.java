package com.ops.easyrequest.app;

/**
 * 
 * @author Saswata Mukhopadhyay
 * 
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.w3c.dom.events.MouseEvent;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JTable;

public class Application extends JFrame {

	static OperationHandler ophandler = new OperationHandler();
	//Initializing variable to store output after hitting an endpoint
	Map<String,String> receivedFromHitMethod = new HashMap<>();//Map to store response received after hitting the method
	
	private JPanel contentPane;
	private JTextField endpoint;
	private JTextField queryParamTextFieldInput;
	private JTextField queryValueTextFieldInput;
	JTable queryParamValueDataTable;
	
	//Query params table
	String[] tableDataInputColName = {"Parameter","Value"};
//	DefaultTableModel tableDataInputColNameModel = new DefaultTableModel(
//	          new String[][] { { "a", "123"} , {"b", "456"} }, 
//	          new String[] { "Parameter", "Value" } );
	
	DefaultTableModel tableDataInputColNameModel = new DefaultTableModel();
	
	

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application frame = new Application();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Application() {
		setTitle("EasyRequest");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1092, 612);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Initializing and adding endpoint component
		endpoint = new JTextField();
		endpoint.setBounds(124, 10, 849, 19);
		contentPane.add(endpoint);
		endpoint.setColumns(10);
		
		//Initializing and adding request types component
		String[] requestTypes = {"GET","POST","PUT","PATCH","DELETE"};
		JComboBox requestCombo = new JComboBox(requestTypes);
		requestCombo.setBounds(16, 9, 104, 21);
		contentPane.add(requestCombo);
		
		//Initializing and adding send button component
		JButton sendBtn = new JButton("Send");
		sendBtn.setBounds(983, 9, 85, 21);
		contentPane.add(sendBtn);
			
		
		//LEFT SECTION
		
		//Initializing and adding panel for input component
		JPanel panel = new JPanel();
		panel.setBounds(16, 39, 547, 526);
		contentPane.add(panel);
		panel.setLayout(null);
		
		//Initializing and adding Headers radio option for input component
		JRadioButton rdbtnHeaderInput = new JRadioButton("Headers");
		rdbtnHeaderInput.setBounds(0, 33, 93, 21);
		panel.add(rdbtnHeaderInput);
		
		//Initializing and adding Query radio option for input component
		JRadioButton rdbtnQueryInput = new JRadioButton("Query");
		rdbtnQueryInput.setBounds(94, 33, 96, 21);
		panel.add(rdbtnQueryInput);
		
		//Initializing and adding Body radio option for input component
		JRadioButton rdbtnBodyInput = new JRadioButton("Body");
		rdbtnBodyInput.setBounds(187, 33, 103, 21);
		panel.add(rdbtnBodyInput);
		
		//Grouping these buttons together for proper functioning
		ButtonGroup outputRadioOptionsGroupInput = new ButtonGroup();
		outputRadioOptionsGroupInput.add(rdbtnHeaderInput);
		outputRadioOptionsGroupInput.add(rdbtnQueryInput);
		outputRadioOptionsGroupInput.add(rdbtnBodyInput);
		
		//Set the Content options selected by default
		rdbtnQueryInput.setSelected(true);
		
		//Text Field Init to add params and value to a query
		queryParamTextFieldInput = new JTextField();
		queryParamTextFieldInput.setBounds(5, 86, 150, 19);
		panel.add(queryParamTextFieldInput);
		queryParamTextFieldInput.setColumns(10);
		
		queryValueTextFieldInput = new JTextField();
		queryValueTextFieldInput.setBounds(157, 86, 326, 19);
		panel.add(queryValueTextFieldInput);
		queryValueTextFieldInput.setColumns(10);
		
		//Label to params and value
		JLabel lblNewLabel = new JLabel("Parameter");
		lblNewLabel.setBounds(6, 69, 79, 13);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Value");
		lblNewLabel_1.setBounds(158, 68, 45, 13);
		panel.add(lblNewLabel_1);
		
		//Button to add Key Value Pair in table
		JButton btnAddParamValuePair = new JButton("+");
		btnAddParamValuePair.setBounds(486, 86, 53, 20);
		panel.add(btnAddParamValuePair);
		
		//Table to show params and value in a query
		tableDataInputColNameModel.setColumnIdentifiers(tableDataInputColName);
		queryParamValueDataTable = new JTable(tableDataInputColNameModel);
		JScrollPane scroll = new JScrollPane(queryParamValueDataTable);
		scroll.setBounds( 6, 137, 535, 380 ); 
		panel.add(scroll);
		
		//Delete button for param value
		JButton btnDeleteParamValueInput = new JButton("Delete");
		btnDeleteParamValueInput.setBounds(224, 115, 85, 21);
		panel.add(btnDeleteParamValueInput);
		
		//ActionListener to add params~value key pair
		btnAddParamValuePair.addActionListener(a -> {
			String param = queryParamTextFieldInput.getText();
			String value = queryValueTextFieldInput.getText();
			Object[] newRowEntry = {param,value};
			tableDataInputColNameModel.addRow(newRowEntry);
			queryParamTextFieldInput.setText("");
			queryValueTextFieldInput.setText("");
		});
		
		//Table action listner so that user can select the data in the table
		queryParamValueDataTable.addMouseListener(new MouseInputAdapter() {
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int rowToEdit = queryParamValueDataTable.getSelectedRow();
				String param = (String) tableDataInputColNameModel.getValueAt(rowToEdit, 0);
				String value = (String) tableDataInputColNameModel.getValueAt(rowToEdit, 1);
				queryParamTextFieldInput.setText(param);
				queryParamTextFieldInput.setText(value);
			}
		});
		
		//Delete button for params~value pair
		btnDeleteParamValueInput.addActionListener(a -> {
			if(queryParamValueDataTable.getSelectedRow()==-1) {
				JOptionPane.showMessageDialog(null, "Select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				tableDataInputColNameModel.removeRow(queryParamValueDataTable.getSelectedRow());
			}
		});
		
				
		
		//RIGHT SECTION
		
		//Initializing and adding panel for output component
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(573, 39, 495, 526);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		//Initializing and adding Status code label component
		JLabel statuslabelText = new JLabel("Status:");
		statuslabelText.setBounds(10, 10, 39, 13);
		panel_1.add(statuslabelText);
		
		//Initializing and adding label where the actual value will be displayed component
		JLabel statusOutputLabel = new JLabel("");
		statusOutputLabel.setBounds(59, 10, 45, 13);
		panel_1.add(statusOutputLabel);
		
		//Initializing and adding area to show the output component
		JTextArea outputArea = new JTextArea();
		outputArea.setBounds(10, 58, 475, 458);
		outputArea.setEditable(false);
		panel_1.add(outputArea);

		//Initializing radiobutton to choose Content for output area
		JRadioButton rdbtnContentOuput = new JRadioButton("Content");
		rdbtnContentOuput.setBounds(10, 31, 84, 21);
		panel_1.add(rdbtnContentOuput);
		
		//Initializing radiobutton to choose headers for output area
		JRadioButton rdbtnHeaderOutput = new JRadioButton("Headers");
		rdbtnHeaderOutput.setBounds(91, 31, 79, 21);
		panel_1.add(rdbtnHeaderOutput);
		
		//Initializing radiobutton to choose HTML for output area
		JRadioButton rdbtnHtmlOuput = new JRadioButton("HTML");
		rdbtnHtmlOuput.setBounds(172, 31, 61, 21);
		panel_1.add(rdbtnHtmlOuput);
		
		//Grouping these buttons together for proper functioning
		ButtonGroup outputRadioOptionsGroup = new ButtonGroup();
		outputRadioOptionsGroup.add(rdbtnContentOuput);
		outputRadioOptionsGroup.add(rdbtnHeaderOutput);
		outputRadioOptionsGroup.add(rdbtnHtmlOuput);
		
		//Set the Content options selected by default
		rdbtnContentOuput.setSelected(true);
		
		//Initializing and adding actionlistner on send button component
		sendBtn.addActionListener(a -> {
			outputArea.setText("");
			String endPointToHit = endpoint.getText();
			String requestTypeSelected = (String) requestCombo.getSelectedItem();
						
			if(requestTypeSelected.equalsIgnoreCase(ophandler.GET_REQUEST)) {
				receivedFromHitMethod = ophandler.getRequest(endPointToHit);
			}
			else if (requestTypeSelected.equalsIgnoreCase(ophandler.POST_REQUEST)) {
				
				int totalTableRows = queryParamValueDataTable.getRowCount();
				//Map to store table data values as key value pair
				Map<Object,Object>map = new HashMap<>();
				for(int i=0;i<totalTableRows;i++) {
					map.put((String)queryParamValueDataTable.getValueAt(i, 0), (String)queryParamValueDataTable.getValueAt(i, 1));
				}
				receivedFromHitMethod = ophandler.sendRequest(endPointToHit,map);
				
//				String jsonString ="";
//				JSONObject jsonObject = null;
//				String transactionId = "";
//				ObjectMapper mapper = new ObjectMapper();
//				Map<String,String>map = new HashMap<>();
//				map = mapper.readValue(Paths.get(path).toFile(), Map.class);
//				jsonObject = new JSONObject(map);
//				transactionId = jsonObject.getString("transactionId");
//				String jsonStringEncoded = request.encryptJSONObjectBase64(jsonObject);
//				System.out.println(jsonStringEncoded);
//				//Creating form body data
//				Map<Object, Object> requestBodyData = new HashMap<>();
//				if (contentType.equalsIgnoreCase("application/x-www-form-urlencoded")) {
////					requestBodyData.put("requestJSON", jsonStringEncoded);
////					requestBodyData.put("orgId", orgId);
////					requestBodyData.put("userId", userId);
//					requestBodyData.put("transactionId", transactionId);
//				}
//				request.sendRequest(endPointToHit,requestBodyData);
			}
			
			//Showing HTTPS Status irrespective of what output radio option is selected
			statusOutputLabel.setText(receivedFromHitMethod.get("https_status_code"));
			
			//Displaying the output based on output radio options selected
			if(rdbtnContentOuput.isSelected()) {
				outputArea.setText(receivedFromHitMethod.get("Response"));
			}
			else if(rdbtnHeaderOutput.isSelected()) {
				outputArea.setText(receivedFromHitMethod.get("Headers"));
			}
			else if(rdbtnHtmlOuput.isSelected()) {
				
			}
						
		});
		
		//Setting eventListners on every output radio options so that users can switch to see what they received from a hit
		//Content
		rdbtnContentOuput.addActionListener(a -> {
			outputArea.setText(receivedFromHitMethod.get("Response"));
		});
		
		//Header
		rdbtnHeaderOutput.addActionListener(a -> {
			outputArea.setText(receivedFromHitMethod.get("Headers"));
		});
		
		//HTML
		rdbtnHtmlOuput.addActionListener(a -> {
			outputArea.setText("");
		});
		
		//Setting eventListners on every input radio options so that users can select what they want to pass in a request
		//Header
		rdbtnHeaderInput.addActionListener(a -> {
			
		});
		
		//Query
		rdbtnQueryInput.addActionListener(a -> {
			
		});
		
		//Body
		rdbtnBodyInput.addActionListener(a -> {
			
		});
		
	}
}
