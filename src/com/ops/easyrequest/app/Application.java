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

import org.json.JSONObject;
import org.w3c.dom.events.MouseEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import javax.swing.JEditorPane;

public class Application extends JFrame {

	static OperationHandler ophandler = new OperationHandler();
	//Initializing variable to store output after hitting an endpoint
	Map<String,String> receivedFromHitMethod = new HashMap<>();//Map to store response received after hitting the method
	
	private JPanel contentPane;
	private JTextField endpoint;
	private JTextField queryParamTextFieldInput;
	private JTextField queryValueTextFieldInput;
	JTable queryParamValueDataTable;
	
	private JTextField headerParamInput;
	private JTextField headerValueInput;
	JTable headerValueDataTable;
	
	private JTextField bodyFormInput;
	private JTextField bodyFormValueInput;
	JTable bodyFormValueDataTable;
	
	private JTextField bodyFormEncodeInput;
	private JTextField bodyFormEncodeValueInput;
	JTable bodyFormEncodeValueDataTable;
	
	//Query params table
	String[] tableDataInputColName = {"Parameter","Value"};
	DefaultTableModel tableDataInputColNameModel = new DefaultTableModel();
	
	//Header params table
	String[] headerDataInputColName = {"Header","Value"};
	DefaultTableModel tableDataInputColNameModelHeader = new DefaultTableModel();
	
	//Body Form params table
	String[] bodyFormColName = {"Field Name","Value"};
	DefaultTableModel bodyFormInputModel = new DefaultTableModel();
	
	//Body Form Encode params table
	String[] bodyFormEncodeColName = {"Name","Value"};
	DefaultTableModel bodyFormEncodeInputModel = new DefaultTableModel();

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
		Image icon = Toolkit.getDefaultToolkit().getImage("D:\\Eclipse Artifacts\\easyrequest\\icon.png");
		setIconImage(icon);
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
			
			//Body options radio buttons initialization
			JRadioButton btnBodyJSON = new JRadioButton("JSON");
			btnBodyJSON.setBounds(7, 66, 93, 21);
			panel.add(btnBodyJSON);
			
			JRadioButton btnBodyXml = new JRadioButton("XML");
			btnBodyXml.setBounds(105, 66, 103, 21);
			panel.add(btnBodyXml);
			
			JRadioButton btnBodyText = new JRadioButton("Text");
			btnBodyText.setBounds(210, 66, 103, 21);
			panel.add(btnBodyText);
			
			JRadioButton btnBodyForm = new JRadioButton("Form");
			btnBodyForm.setBounds(315, 66, 103, 21);
			panel.add(btnBodyForm);
			
			JRadioButton btnBodyFormEncode = new JRadioButton("Form-encode");
			btnBodyFormEncode.setBounds(420, 66, 103, 21);
			panel.add(btnBodyFormEncode);
			
			//Grouping these buttons together for proper functioning
			ButtonGroup bodyBtnOptionsGroup = new ButtonGroup();
			bodyBtnOptionsGroup.add(btnBodyJSON);
			bodyBtnOptionsGroup.add(btnBodyXml);
			bodyBtnOptionsGroup.add(btnBodyText);
			bodyBtnOptionsGroup.add(btnBodyForm);
			bodyBtnOptionsGroup.add(btnBodyFormEncode);
			
			//Text Editor option initialization
			JEditorPane textEditorPane = new JEditorPane();
			textEditorPane.setBounds(10, 126, 527, 390);
			panel.add(textEditorPane);
			
			//JSON Editor option initialization
			JEditorPane jsonEditorPane = new JEditorPane();
			jsonEditorPane.setContentType("application/json");
			jsonEditorPane.setBounds(10, 126, 527, 390);
			panel.add(jsonEditorPane);
			
			//XML Editor option initialization
			JEditorPane xmlEditorPane = new JEditorPane();
			xmlEditorPane.setContentType("application/xml");
			xmlEditorPane.setBounds(10, 126, 527, 390);
			panel.add(xmlEditorPane);
		
			JLabel enterTextValueMsg = new JLabel("Enter Text");
			enterTextValueMsg.setBounds(15, 109, 70, 13);
			panel.add(enterTextValueMsg);
			
			JLabel enterJsonValueMsg = new JLabel("Enter JSON");
			enterJsonValueMsg.setBounds(15, 109, 70, 13);
			panel.add(enterJsonValueMsg);
			
			JLabel enterXmlValueMsg = new JLabel("Enter XML");
			enterXmlValueMsg.setBounds(15, 109, 70, 13);
			panel.add(enterXmlValueMsg);
				
//		Query Section
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
//		End of Query Section
			
			
//		Header Section
			//Text Field Init to add params and value to a heaqder
			headerParamInput = new JTextField();
			headerParamInput.setBounds(5, 86, 150, 19);
			panel.add(headerParamInput);
			headerParamInput.setColumns(10);
			
			headerValueInput = new JTextField();
			headerValueInput.setBounds(157, 86, 326, 19);
			panel.add(headerValueInput);
			headerValueInput.setColumns(10);

			//Label to params and value
			JLabel headerParamLabel = new JLabel("Header");
			headerParamLabel.setBounds(6, 69, 79, 13);
			panel.add(headerParamLabel);
			
			JLabel valueLabel = new JLabel("Value");
			valueLabel.setBounds(158, 68, 45, 13);
			panel.add(valueLabel);

			//Button to add Key Value Pair in table
			JButton btnAddHeaderValue = new JButton("+");
			btnAddHeaderValue.setBounds(486, 86, 53, 20);
			panel.add(btnAddHeaderValue);
			
			//Table to show params and value in a query
			tableDataInputColNameModelHeader.setColumnIdentifiers(headerDataInputColName);
			headerValueDataTable = new JTable(tableDataInputColNameModelHeader);
			JScrollPane headerScroll = new JScrollPane(headerValueDataTable);
			headerScroll.setBounds( 6, 137, 535, 380 ); 
			panel.add(headerScroll);

			//Delete button for param value
			JButton btnDeleteHeaderValueInput = new JButton("Delete");
			btnDeleteHeaderValueInput.setBounds(224, 115, 85, 21);
			panel.add(btnDeleteHeaderValueInput);
			
			//ActionListener to add params~value key pair
			btnAddHeaderValue.addActionListener(a -> {
				String param = headerParamInput.getText();
				String value = headerValueInput.getText();
				Object[] newRowEntry = {param,value};
				tableDataInputColNameModelHeader.addRow(newRowEntry);
				headerParamInput.setText("");
				headerValueInput.setText("");
			});

			//Table action listner so that user can select the data in the table
			headerValueDataTable.addMouseListener(new MouseInputAdapter() {
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					int rowToEdit = headerValueDataTable.getSelectedRow();
					String param = (String) tableDataInputColNameModelHeader.getValueAt(rowToEdit, 0);
					String value = (String) tableDataInputColNameModelHeader.getValueAt(rowToEdit, 1);
					headerParamInput.setText(param);
					headerParamInput.setText(value);
				}
			});

			//Delete button for params~value pair
			btnDeleteHeaderValueInput.addActionListener(a -> {
				if(headerValueDataTable.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(null, "Select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					tableDataInputColNameModelHeader.removeRow(headerValueDataTable.getSelectedRow());
				}
			});
			
//		End of Header Section
		
		
		
		//Body Form Section
		
			//Text Field Init to add params and value to a query
			bodyFormInput = new JTextField();
			bodyFormInput.setBounds(7, 129, 165, 19);
			panel.add(bodyFormInput);
			bodyFormInput.setColumns(10);
			
			bodyFormValueInput = new JTextField();
			bodyFormValueInput.setBounds(182, 129, 303, 19);
			panel.add(bodyFormValueInput);
			bodyFormValueInput.setColumns(10);
	
			//Label to params and value
			JLabel bodyFormInputLabel = new JLabel("Field Name");
			bodyFormInputLabel.setBounds(7, 106, 79, 13);
			panel.add(bodyFormInputLabel);
			
			JLabel bodyFormvalueLabel = new JLabel("Value");
			bodyFormvalueLabel.setBounds(182, 106, 303, 13);
			panel.add(bodyFormvalueLabel);
	
			//Button to add Key Value Pair in table
			JButton btnAddFormValue = new JButton("+");
			btnAddFormValue.setBounds(488, 129, 53, 20);
			panel.add(btnAddFormValue);
			
			//Table to show params and value in a query
			bodyFormInputModel.setColumnIdentifiers(bodyFormColName);
			bodyFormValueDataTable = new JTable(bodyFormInputModel);
			JScrollPane bodyFormScroller = new JScrollPane(bodyFormValueDataTable);
			bodyFormScroller.setBounds( 6, 190, 535, 327 ); 
			panel.add(bodyFormScroller);
	
			//Delete button for param value
			JButton btnDeletebodyFormValueInput = new JButton("Delete");
			btnDeletebodyFormValueInput.setBounds(228, 159, 85, 21);
			panel.add(btnDeletebodyFormValueInput);
			
			//ActionListener to add params~value key pair
			btnAddFormValue.addActionListener(a -> {
				String param = bodyFormInput.getText();
				String value = bodyFormValueInput.getText();
				Object[] newRowEntry = {param,value};
				bodyFormInputModel.addRow(newRowEntry);
				bodyFormInput.setText("");
				bodyFormValueInput.setText("");
			});
	
			//Table action listner so that user can select the data in the table
			bodyFormValueDataTable.addMouseListener(new MouseInputAdapter() {
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					int rowToEdit = bodyFormValueDataTable.getSelectedRow();
					String param = (String) bodyFormInputModel.getValueAt(rowToEdit, 0);
					String value = (String) bodyFormInputModel.getValueAt(rowToEdit, 1);
					bodyFormInput.setText(param);
					bodyFormInput.setText(value);
				}
			});
	
			//Delete button for params~value pair
			btnDeletebodyFormValueInput.addActionListener(a -> {
				if(bodyFormValueDataTable.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(null, "Select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					bodyFormInputModel.removeRow(bodyFormValueDataTable.getSelectedRow());
				}
			});
		
		//End of body Form Section
		
		
		
		//Body Form-encode Section
			//Text Field Init to add params and value to a query
			bodyFormEncodeInput = new JTextField();
			bodyFormEncodeInput.setBounds(7, 129, 165, 19);
			panel.add(bodyFormEncodeInput);
			bodyFormEncodeInput.setColumns(10);
			
			bodyFormEncodeValueInput = new JTextField();
			bodyFormEncodeValueInput.setBounds(182, 129, 303, 19);
			panel.add(bodyFormEncodeValueInput);
			bodyFormEncodeValueInput.setColumns(10);
	
			//Label to params and value
			JLabel bodyFormEncodeInputLabel = new JLabel("Name");
			bodyFormEncodeInputLabel.setBounds(7, 106, 79, 13);
			panel.add(bodyFormEncodeInputLabel);
			
			JLabel bodyFormEncodedValueLabel = new JLabel("Value");
			bodyFormEncodedValueLabel.setBounds(182, 106, 303, 13);
			panel.add(bodyFormEncodedValueLabel);
	
			//Button to add Key Value Pair in table
			JButton btnAddFormEncodedValue = new JButton("+");
			btnAddFormEncodedValue.setBounds(488, 129, 53, 20);
			panel.add(btnAddFormEncodedValue);
			
			//Table to show params and value in a query
			bodyFormEncodeInputModel.setColumnIdentifiers(bodyFormEncodeColName);
			bodyFormEncodeValueDataTable = new JTable(bodyFormEncodeInputModel);
			JScrollPane bodyFormEncodedScroller = new JScrollPane(bodyFormEncodeValueDataTable);
			bodyFormEncodedScroller.setBounds( 6, 190, 535, 327 ); 
			panel.add(bodyFormEncodedScroller);
	
			//Delete button for param value
			JButton btnDeletebodyFormEncodeValueInput = new JButton("Delete");
			btnDeletebodyFormEncodeValueInput.setBounds(228, 159, 85, 21);
			panel.add(btnDeletebodyFormEncodeValueInput);
			
			
			
			//ActionListener to add params~value key pair
			btnAddFormEncodedValue.addActionListener(a -> {
				String param = bodyFormEncodeInput.getText();
				String value = bodyFormEncodeValueInput.getText();
				Object[] newRowEntry = {param,value};
				bodyFormEncodeInputModel.addRow(newRowEntry);
				bodyFormEncodeInput.setText("");
				bodyFormEncodeValueInput.setText("");
			});
	
			//Table action listner so that user can select the data in the table
			bodyFormEncodeValueDataTable.addMouseListener(new MouseInputAdapter() {
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					int rowToEdit = bodyFormEncodeValueDataTable.getSelectedRow();
					String param = (String) bodyFormEncodeInputModel.getValueAt(rowToEdit, 0);
					String value = (String) bodyFormEncodeInputModel.getValueAt(rowToEdit, 1);
					bodyFormEncodeInput.setText(param);
					bodyFormEncodeInput.setText(value);
				}
			});
	
			//Delete button for params~value pair
			btnDeletebodyFormEncodeValueInput.addActionListener(a -> {
				if(bodyFormEncodeValueDataTable.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(null, "Select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					bodyFormEncodeInputModel.removeRow(bodyFormEncodeValueDataTable.getSelectedRow());
				}
			});
			
		//End of body Form-encode Section
				
		
		
		//Set query tab to show it's properties only on the first run without actions
		if(rdbtnQueryInput.isSelected()==true) {
			//Show query frontend
			btnDeleteParamValueInput.setVisible(true);
			queryParamValueDataTable.setVisible(true);
			btnAddParamValuePair.setVisible(true);
			lblNewLabel_1.setVisible(true);
			lblNewLabel.setVisible(true);
			queryValueTextFieldInput.setVisible(true);
			queryParamTextFieldInput.setVisible(true);
			scroll.setVisible(true);
			
			//Hide header frontend
			btnDeleteHeaderValueInput.setVisible(false);
			headerScroll.setVisible(false);
			headerValueDataTable.setVisible(false);
			btnAddHeaderValue.setVisible(false);
			valueLabel.setVisible(false);
			headerParamLabel.setVisible(false);
			headerValueInput.setVisible(false);
			headerParamInput.setVisible(false);
			
			//Hide body frontend
			btnBodyJSON.setVisible(false);
			btnBodyXml.setVisible(false);
			btnBodyText.setVisible(false);
			btnBodyForm.setVisible(false);
			btnBodyFormEncode.setVisible(false);
			textEditorPane.setVisible(false);
			jsonEditorPane.setVisible(false);
			xmlEditorPane.setVisible(false);
			bodyFormInput.setVisible(false);
			bodyFormValueInput.setVisible(false);
			bodyFormInputLabel.setVisible(false);
			bodyFormvalueLabel.setVisible(false);
			btnAddFormValue.setVisible(false);
			bodyFormValueDataTable.setVisible(false);
			bodyFormScroller.setVisible(false);
			btnDeletebodyFormValueInput.setVisible(false);
			bodyFormEncodeInput.setVisible(false);
			bodyFormEncodeValueInput.setVisible(false);
			bodyFormEncodeInputLabel.setVisible(false);
			bodyFormEncodedValueLabel.setVisible(false);
			btnAddFormEncodedValue.setVisible(false);
			bodyFormEncodeValueDataTable.setVisible(false);
			bodyFormEncodedScroller.setVisible(false);
			btnDeletebodyFormEncodeValueInput.setVisible(false);
			enterTextValueMsg.setVisible(false);
			enterJsonValueMsg.setVisible(false);
			enterXmlValueMsg.setVisible(false);
		}
			
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
		//END OF RGHT SECTION
		
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
				//Map to store table data values as key value pair for query
				Map<Object,Object>requestMap = new HashMap<>();
				for(int i=0;i<totalTableRows;i++) {
					requestMap.put((String)queryParamValueDataTable.getValueAt(i, 0), (String)queryParamValueDataTable.getValueAt(i, 1));
				}
				
				
				//Map to store header
				Map<Object,Object>headerMap = new HashMap<>();
				for(int i=0;i<totalTableRows;i++) {
					headerMap.put((String)headerValueDataTable.getValueAt(i, 0), (String)headerValueDataTable.getValueAt(i, 1));
				}
				
				JSONObject jsonData = new JSONObject();
				String xmlData = "";
				String textData = "";
				if(rdbtnQueryInput.isSelected()) {
					receivedFromHitMethod = ophandler.postRequest(endPointToHit,requestMap,headerMap,"query",jsonData,xmlData,textData);
				}
				else if(btnBodyJSON.isSelected()) {
					String json = jsonEditorPane.getText();
					try {
						jsonData = new ObjectMapper().readValue(json, JSONObject.class);
					} catch (JsonMappingException e1) {
						e1.printStackTrace();
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
					}  
					receivedFromHitMethod = ophandler.postRequest(endPointToHit,requestMap,headerMap,"json",jsonData,xmlData,textData);
				}
				else if(btnBodyXml.isSelected()) {
					xmlData = xmlEditorPane.getText();
					receivedFromHitMethod = ophandler.postRequest(endPointToHit,requestMap,headerMap,"xml",jsonData,xmlData,textData);
				}
				else if(btnBodyText.isSelected()) {
					textData = textEditorPane.getText();
					receivedFromHitMethod = ophandler.postRequest(endPointToHit,requestMap,headerMap,"text",jsonData,xmlData,textData);
				}
				else if(btnBodyForm.isSelected()) {
					//Map to store form table data values as key value pair
					Map<Object,Object>requestFormMap = new HashMap<>();
					for(int i=0;i<totalTableRows;i++) {
						requestFormMap.put((String)bodyFormValueDataTable.getValueAt(i, 0), (String)bodyFormValueDataTable.getValueAt(i, 1));
					}
					receivedFromHitMethod = ophandler.postRequest(endPointToHit,requestFormMap,headerMap,"form",jsonData,xmlData,textData);
				}
				else if(btnBodyFormEncode.isSelected()) {
					Map<Object,Object>requestFormEncodeMap = new HashMap<>();
					for(int i=0;i<totalTableRows;i++) {
						requestFormEncodeMap.put((String)bodyFormEncodeValueDataTable.getValueAt(i, 0), (String)bodyFormEncodeValueDataTable.getValueAt(i, 1));
					}
					receivedFromHitMethod = ophandler.postRequest(endPointToHit,requestFormEncodeMap,headerMap,"formencode",jsonData,xmlData,textData);
				}
			}
			
//			Parsing the output received from hitting the endpoint
			//Showing HTTPS Status irrespective of what output radio option is selected if there is no error
			if(receivedFromHitMethod.containsKey("Error")) {
				JOptionPane.showMessageDialog(null, "No endpoint provided", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
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
			//Show header frontend
			btnDeleteHeaderValueInput.setVisible(true);
			headerScroll.setVisible(true);
			headerValueDataTable.setVisible(true);
			btnAddHeaderValue.setVisible(true);
			valueLabel.setVisible(true);
			headerParamLabel.setVisible(true);
			headerValueInput.setVisible(true);
			headerParamInput.setVisible(true);
			
			//Hide query frontend and body frontend
			btnDeleteParamValueInput.setVisible(false);
			queryParamValueDataTable.setVisible(false);
			btnAddParamValuePair.setVisible(false);
			lblNewLabel_1.setVisible(false);
			lblNewLabel.setVisible(false);
			queryValueTextFieldInput.setVisible(false);
			queryParamTextFieldInput.setVisible(false);
			scroll.setVisible(false);
			
			//Hide body frontend
			btnBodyJSON.setVisible(false);
			btnBodyXml.setVisible(false);
			btnBodyText.setVisible(false);
			btnBodyForm.setVisible(false);
			btnBodyFormEncode.setVisible(false);
			textEditorPane.setVisible(false);
			jsonEditorPane.setVisible(false);
			xmlEditorPane.setVisible(false);
			bodyFormInput.setVisible(false);
			bodyFormValueInput.setVisible(false);
			bodyFormInputLabel.setVisible(false);
			bodyFormvalueLabel.setVisible(false);
			btnAddFormValue.setVisible(false);
			bodyFormValueDataTable.setVisible(false);
			bodyFormScroller.setVisible(false);
			btnDeletebodyFormValueInput.setVisible(false);
			bodyFormEncodeInput.setVisible(false);
			bodyFormEncodeValueInput.setVisible(false);
			bodyFormEncodeInputLabel.setVisible(false);
			bodyFormEncodedValueLabel.setVisible(false);
			btnAddFormEncodedValue.setVisible(false);
			bodyFormEncodeValueDataTable.setVisible(false);
			bodyFormEncodedScroller.setVisible(false);
			btnDeletebodyFormEncodeValueInput.setVisible(false);
			enterTextValueMsg.setVisible(false);
			enterJsonValueMsg.setVisible(false);
			enterXmlValueMsg.setVisible(false);
		});
		
		//Query(Params and value key~value pair)
		rdbtnQueryInput.addActionListener(a -> {
			//Show query fronend and body frontend
			btnDeleteParamValueInput.setVisible(true);
			queryParamValueDataTable.setVisible(true);
			btnAddParamValuePair.setVisible(true);
			lblNewLabel_1.setVisible(true);
			lblNewLabel.setVisible(true);
			queryValueTextFieldInput.setVisible(true);
			queryParamTextFieldInput.setVisible(true);
			scroll.setVisible(true);
			
			//Hide header frontend
			btnDeleteHeaderValueInput.setVisible(false);
			headerScroll.setVisible(false);
			headerValueDataTable.setVisible(false);
			btnAddHeaderValue.setVisible(false);
			valueLabel.setVisible(false);
			headerParamLabel.setVisible(false);
			headerValueInput.setVisible(false);
			headerParamInput.setVisible(false);
			
//			Hide body frontend
			btnBodyJSON.setVisible(false);
			btnBodyXml.setVisible(false);
			btnBodyText.setVisible(false);
			btnBodyForm.setVisible(false);
			btnBodyFormEncode.setVisible(false);
			textEditorPane.setVisible(false);
			jsonEditorPane.setVisible(false);
			xmlEditorPane.setVisible(false);
			bodyFormInput.setVisible(false);
			bodyFormValueInput.setVisible(false);
			bodyFormInputLabel.setVisible(false);
			bodyFormvalueLabel.setVisible(false);
			btnAddFormValue.setVisible(false);
			bodyFormValueDataTable.setVisible(false);
			bodyFormScroller.setVisible(false);
			btnDeletebodyFormValueInput.setVisible(false);
			bodyFormEncodeInput.setVisible(false);
			bodyFormEncodeValueInput.setVisible(false);
			bodyFormEncodeInputLabel.setVisible(false);
			bodyFormEncodedValueLabel.setVisible(false);
			btnAddFormEncodedValue.setVisible(false);
			bodyFormEncodeValueDataTable.setVisible(false);
			bodyFormEncodedScroller.setVisible(false);
			btnDeletebodyFormEncodeValueInput.setVisible(false);
			enterTextValueMsg.setVisible(false);
			enterJsonValueMsg.setVisible(false);
			enterXmlValueMsg.setVisible(false);
			
		});
		
		//Body
		rdbtnBodyInput.addActionListener(a -> {
			//Show body frontend
			btnBodyJSON.setVisible(true);
			btnBodyXml.setVisible(true);
			btnBodyText.setVisible(true);
			btnBodyForm.setVisible(true);
			btnBodyFormEncode.setVisible(true);
			btnBodyJSON.setSelected(true);
			
			//Hide query fronend and body frontend
			btnDeleteParamValueInput.setVisible(false);
			queryParamValueDataTable.setVisible(false);
			btnAddParamValuePair.setVisible(false);
			lblNewLabel_1.setVisible(false);
			lblNewLabel.setVisible(false);
			queryValueTextFieldInput.setVisible(false);
			queryParamTextFieldInput.setVisible(false);
			scroll.setVisible(false);
			
			//Hide header frontend
			btnDeleteHeaderValueInput.setVisible(false);
			headerScroll.setVisible(false);
			headerValueDataTable.setVisible(false);
			btnAddHeaderValue.setVisible(false);
			valueLabel.setVisible(false);
			headerParamLabel.setVisible(false);
			headerValueInput.setVisible(false);
			headerParamInput.setVisible(false);
			
			//Show JSON Option by default in body
			if(btnBodyJSON.isSelected()) {
				//Show json area and hide rest
				jsonEditorPane.setVisible(true);
				
				enterTextValueMsg.setVisible(false);
				enterJsonValueMsg.setVisible(true);
				enterXmlValueMsg.setVisible(false);
				textEditorPane.setVisible(false);
				xmlEditorPane.setVisible(false);
				bodyFormInput.setVisible(false);
				bodyFormValueInput.setVisible(false);
				bodyFormInputLabel.setVisible(false);
				bodyFormvalueLabel.setVisible(false);
				btnAddFormValue.setVisible(false);
				bodyFormValueDataTable.setVisible(false);
				bodyFormScroller.setVisible(false);
				btnDeletebodyFormValueInput.setVisible(false);
				bodyFormEncodeInput.setVisible(false);
				bodyFormEncodeValueInput.setVisible(false);
				bodyFormEncodeInputLabel.setVisible(false);
				bodyFormEncodedValueLabel.setVisible(false);
				btnAddFormEncodedValue.setVisible(false);
				bodyFormEncodeValueDataTable.setVisible(false);
				bodyFormEncodedScroller.setVisible(false);
				btnDeletebodyFormEncodeValueInput.setVisible(false);
			}
			
		});
		
		
		
		//Body options actionlistener to activate their options
		btnBodyJSON.addActionListener(a->{
			//Show json area and hide rest
			jsonEditorPane.setVisible(true);
			
			enterTextValueMsg.setVisible(false);
			enterJsonValueMsg.setVisible(true);
			enterXmlValueMsg.setVisible(false);
			textEditorPane.setVisible(false);
			xmlEditorPane.setVisible(false);
			bodyFormInput.setVisible(false);
			bodyFormValueInput.setVisible(false);
			bodyFormInputLabel.setVisible(false);
			bodyFormvalueLabel.setVisible(false);
			btnAddFormValue.setVisible(false);
			bodyFormValueDataTable.setVisible(false);
			bodyFormScroller.setVisible(false);
			btnDeletebodyFormValueInput.setVisible(false);
			bodyFormEncodeInput.setVisible(false);
			bodyFormEncodeValueInput.setVisible(false);
			bodyFormEncodeInputLabel.setVisible(false);
			bodyFormEncodedValueLabel.setVisible(false);
			btnAddFormEncodedValue.setVisible(false);
			bodyFormEncodeValueDataTable.setVisible(false);
			bodyFormEncodedScroller.setVisible(false);
			btnDeletebodyFormEncodeValueInput.setVisible(false);
		});
		
		btnBodyXml.addActionListener(a->{
			xmlEditorPane.setVisible(true);
			
			enterTextValueMsg.setVisible(false);
			enterJsonValueMsg.setVisible(false);
			enterXmlValueMsg.setVisible(true);
			textEditorPane.setVisible(false);
			jsonEditorPane.setVisible(false);
			bodyFormInput.setVisible(false);
			bodyFormValueInput.setVisible(false);
			bodyFormInputLabel.setVisible(false);
			bodyFormvalueLabel.setVisible(false);
			btnAddFormValue.setVisible(false);
			bodyFormValueDataTable.setVisible(false);
			bodyFormScroller.setVisible(false);
			btnDeletebodyFormValueInput.setVisible(false);
			bodyFormEncodeInput.setVisible(false);
			bodyFormEncodeValueInput.setVisible(false);
			bodyFormEncodeInputLabel.setVisible(false);
			bodyFormEncodedValueLabel.setVisible(false);
			btnAddFormEncodedValue.setVisible(false);
			bodyFormEncodeValueDataTable.setVisible(false);
			bodyFormEncodedScroller.setVisible(false);
			btnDeletebodyFormEncodeValueInput.setVisible(false);
		});
		
		btnBodyText.addActionListener(a->{
			textEditorPane.setVisible(true);
			
			enterTextValueMsg.setVisible(true);
			enterJsonValueMsg.setVisible(false);
			enterXmlValueMsg.setVisible(false);
			xmlEditorPane.setVisible(false);
			jsonEditorPane.setVisible(false);
			bodyFormInput.setVisible(false);
			bodyFormValueInput.setVisible(false);
			bodyFormInputLabel.setVisible(false);
			bodyFormvalueLabel.setVisible(false);
			btnAddFormValue.setVisible(false);
			bodyFormValueDataTable.setVisible(false);
			bodyFormScroller.setVisible(false);
			btnDeletebodyFormValueInput.setVisible(false);
			bodyFormEncodeInput.setVisible(false);
			bodyFormEncodeValueInput.setVisible(false);
			bodyFormEncodeInputLabel.setVisible(false);
			bodyFormEncodedValueLabel.setVisible(false);
			btnAddFormEncodedValue.setVisible(false);
			bodyFormEncodeValueDataTable.setVisible(false);
			bodyFormEncodedScroller.setVisible(false);
			btnDeletebodyFormEncodeValueInput.setVisible(false);
		});
		
		btnBodyForm.addActionListener(a->{
			bodyFormInput.setVisible(true);
			bodyFormValueInput.setVisible(true);
			bodyFormInputLabel.setVisible(true);
			bodyFormvalueLabel.setVisible(true);
			btnAddFormValue.setVisible(true);
			bodyFormValueDataTable.setVisible(true);
			bodyFormScroller.setVisible(true);
			btnDeletebodyFormValueInput.setVisible(true);
			
			bodyFormEncodeInput.setVisible(false);
			bodyFormEncodeValueInput.setVisible(false);
			bodyFormEncodeInputLabel.setVisible(false);
			bodyFormEncodedValueLabel.setVisible(false);
			btnAddFormEncodedValue.setVisible(false);
			bodyFormEncodeValueDataTable.setVisible(false);
			bodyFormEncodedScroller.setVisible(false);
			btnDeletebodyFormEncodeValueInput.setVisible(false);
			textEditorPane.setVisible(false);
			xmlEditorPane.setVisible(false);
			jsonEditorPane.setVisible(false);
			enterTextValueMsg.setVisible(false);
			enterJsonValueMsg.setVisible(false);
			enterXmlValueMsg.setVisible(false);
		});

		btnBodyFormEncode.addActionListener(a->{
			bodyFormEncodeInput.setVisible(true);
			bodyFormEncodeValueInput.setVisible(true);
			bodyFormEncodeInputLabel.setVisible(true);
			bodyFormEncodedValueLabel.setVisible(true);
			btnAddFormEncodedValue.setVisible(true);
			bodyFormEncodeValueDataTable.setVisible(true);
			bodyFormEncodedScroller.setVisible(true);
			btnDeletebodyFormEncodeValueInput.setVisible(true);
			
			bodyFormScroller.setVisible(false);
			bodyFormInput.setVisible(false);
			bodyFormValueInput.setVisible(false);
			bodyFormInputLabel.setVisible(false);
			bodyFormvalueLabel.setVisible(false);
			btnAddFormValue.setVisible(false);
			bodyFormValueDataTable.setVisible(false);
			btnDeletebodyFormValueInput.setVisible(false);
			textEditorPane.setVisible(false);
			xmlEditorPane.setVisible(false);
			jsonEditorPane.setVisible(false);
			enterTextValueMsg.setVisible(false);
			enterJsonValueMsg.setVisible(false);
			enterXmlValueMsg.setVisible(false);
			
		});
		
	}
}
