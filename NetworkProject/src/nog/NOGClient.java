package nog;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NOGClient {

	public static void main(String[] args){
		
		NogGUI gui = new NogGUI();
        gui.start();
        
	}
	
}

class NogGUI extends Thread{
	
	Button button_connect;
	Button button_fetch;
	Button button_new;
	Button button_previous;
	Button button_next;
	Button button_first;
	Button button_last;
	Button button_edit;
	Button button_delete;
	Button button_stats;
	Label label_id;
	Label label_name;
	Label label_country;
	Label label_date;
	Label label_gender;
	Label label_weight;
	Label label_height;
	Label label_sport;
	JTextField nogSearchText;
	Insets insets;
	Dimension size;
	Dimension size2;
	Socket socket;
	BufferedReader in;
	ObjectInputStream inObj;
    PrintWriter out;
    boolean flag;
    Hashtable<Integer, Participant> ht;
    Enumeration<Integer> enumKey;
    
	public NogGUI(){
		super();
		
		
		JFrame nogFrame = new JFrame();
		JPanel nogPanel = new JPanel();
		
		//Window settings
		nogFrame.setVisible(true);
		nogFrame.setSize(400,400);
		nogFrame.setResizable(false);
		nogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nogFrame.setTitle("NOG Participant client 2015 v1.0");
		nogFrame.setLocationRelativeTo(null);
		
		nogFrame.add(nogPanel);
		
		/* Panel Components */
		//Buttons (10)
		button_connect = new Button("Connect");
		button_fetch = new Button("Fetch");
		button_new = new Button("New part.");
		button_previous = new Button("<");
		button_next = new Button(">");
		button_first = new Button("<<");
		button_last = new Button(">>");
		button_edit = new Button("Edit");
		button_delete = new Button("Delete");
		button_stats = new Button("Statistics");
		
		nogPanel.add(button_connect);
		nogPanel.add(button_fetch);
		nogPanel.add(button_new);
		nogPanel.add(button_previous);
		nogPanel.add(button_next);
		nogPanel.add(button_first);
		nogPanel.add(button_last);
		nogPanel.add(button_edit);
		nogPanel.add(button_delete);
		nogPanel.add(button_stats);
		
		//Labels (8)
		label_id = new Label("ID:");
		label_name = new Label("Name:"); //All labels + Participant record information.
		label_country = new Label("Country:");
		label_date = new Label("Date of Birth:");
		label_gender = new Label("Gender:");
		label_weight = new Label("Weight:");
		label_height = new Label("Height:");
		label_sport = new Label("Sport:");
		
		nogPanel.add(label_id);
		nogPanel.add(label_name);
		nogPanel.add(label_country);
		nogPanel.add(label_date);
		nogPanel.add(label_gender);
		nogPanel.add(label_weight);
		nogPanel.add(label_height);
		nogPanel.add(label_sport);
		//Search field
		nogSearchText = new JTextField();
		
		/* Layout configurations */
		
		nogPanel.setLayout(null);
		insets = nogPanel.getInsets();
		
		size = label_id.getPreferredSize(); //LABELS
		label_id.setBounds(15+insets.left, 60+insets.top,350,size.height);
		size2 = label_name.getPreferredSize();
		label_name.setBounds(15+insets.left, 90+insets.top,350,size2.height);
		size = label_country.getPreferredSize();
		label_country.setBounds(15+insets.left, 120+insets.top,350,size.height);
		size2 = label_date.getPreferredSize();
		label_date.setBounds(15+insets.left, 150+insets.top,350,size2.height);
		size = label_gender.getPreferredSize();
		label_gender.setBounds(15+insets.left, 180+insets.top,350,size.height);
		size2 = label_weight.getPreferredSize();
		label_weight.setBounds(15+insets.left, 210+insets.top,350,size2.height);
		size = label_height.getPreferredSize();
		label_height.setBounds(15+insets.left, 240+insets.top,350,size.height);
		size2 = label_sport.getPreferredSize();
		label_sport.setBounds(15+insets.left, 270+insets.top,350,size2.height);
		 // ---------------------------------------BUTTONS
		size2 = button_connect.getPreferredSize();
		button_connect.setBounds(15+insets.left, 20+insets.top,75,size2.height);
		size = button_fetch.getPreferredSize();
		button_fetch.setBounds(95+insets.left, 20+insets.top,size.width,size.height);
		size2 = button_new.getPreferredSize();
		button_new.setBounds(165+insets.left, 20+insets.top,size2.width,size2.height);
		size = button_stats.getPreferredSize();
		button_stats.setBounds(310+insets.left, 20+insets.top,size.width,size.height);
		
		size2 = button_first.getPreferredSize();
		button_first.setBounds(15+insets.left, 310+insets.top,size2.width,size2.height);
		size = button_previous.getPreferredSize();
		button_previous.setBounds(45+insets.left, 310+insets.top,size.width,size.height);
		size2 = button_edit.getPreferredSize();
		button_edit.setBounds(150+insets.left, 310+insets.top,size2.width,size2.height);
		size = button_delete.getPreferredSize();
		button_delete.setBounds(190+insets.left, 310+insets.top,size.width,size.height);
		size2 = button_next.getPreferredSize();
		button_next.setBounds(325+insets.left, 310+insets.top,size2.width,size2.height);
		size = button_last.getPreferredSize();
		button_last.setBounds(350+insets.left, 310+insets.top,size.width,size.height);
		/* ********
		 * Handlersection
		 * 
		 * OnClickListeners for many buttons. EventHandlers etc.
		 * 
		 * ********/
		button_edit.addActionListener(new ActionListener() {
		       public void actionPerformed(ActionEvent ae){
		           new EditGui();
		       }
		      });
		
		button_new.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				new NewGui();
			}
		});
		
		
		
		button_connect.addActionListener(new ActionListener(){
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent ae){
				if(!flag) {
					String ip = JOptionPane.showInputDialog(null,"IP");;
					String port = JOptionPane.showInputDialog(null,"Port");
				
					try{
						System.out.println("swagga");
						socket = new Socket(ip, Integer.parseInt(port));
						System.out.println("swag");
						out = new PrintWriter(socket.getOutputStream(), true);
						inObj = new ObjectInputStream(socket.getInputStream());
						
						flag = true;
						button_connect.setLabel("Disconnect");
						
						out.println("GET");
						ht = (Hashtable<Integer, Participant>)inObj.readObject();
						System.out.println("Hello");
						enumKey = ht.keys();
						//while(enumKey.hasMoreElements()){
							System.out.println("Before enum");
							Integer key = enumKey.nextElement();
							System.out.println("After enum");
							Participant value = ht.get(key);
							System.out.println("Before labeling");
							label_id.setText("ID: "+value.getId());
							label_name.setText("Name: "+value.getName());
							label_gender.setText("Gender: "+value.getGender());
							label_country.setText("Country: "+value.getCountry());
							label_date.setText("Date of Birth: "+value.getDate());
							label_height.setText("Height: "+value.getHeight());
							label_weight.setText("Weight: "+value.getWeight());
							label_sport.setText("Sport: "+value.getSport());
						//}
						
					} catch(Exception e){
						System.out.print(e);
					}
				}
				else {
					try {
						socket.close();
						flag = false;
						button_connect.setLabel("Connect");
					} catch(Exception e) {
						System.out.println(e);
					}
				}
			}
		});
		
		button_next.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent ae){
				   if(enumKey.hasMoreElements()){
			    System.out.println("Before enum IN NEXT RECORD");
			    Integer key = enumKey.nextElement();
			    System.out.println("After enum IN NEXT RECORD");
			    Participant value = ht.get(key);
			    System.out.println("Before labeling IN NEXT RECORD");
			    label_id.setText("ID: "+value.getId());
			    label_name.setText("Name: "+value.getName());
			    label_gender.setText("Gender: "+value.getGender());
			    label_country.setText("Country: "+value.getCountry());
			    label_date.setText("Date of Birth: "+value.getDate());
			    label_height.setText("Height: "+value.getHeight());
			    label_weight.setText("Weight: "+value.getWeight());
			    label_sport.setText("Sport: "+value.getSport());
				   }
			   }
			  });
		
	}
	
	public void run(){
		
	}
	
}
/**
 * 
 * @author Marcus
 *
 *This is the EditGUI Frame that will let the user edit the records.
 *
 */
class EditGui extends Thread{
	
	Button button_update;
	Button button_close;
	
	Label namelabel;
	Label countrylabel;
	Label datelabel;
	Label genderlabel;
	Label weightlabel;
	Label heightlabel;
	Label sportlabel;
	
	JTextField namefield;
	JTextField countryfield;
	JTextField datefield;
	JTextField genderfield;
	JTextField weightfield;
	JTextField heightfield;
	JTextField sportfield;
	
	String nf;
	String cf;
	String df;
	String gf;
	String wf;
	String hf;
	String sf;
	
	Insets einset;
	
	Dimension textfielddimension;
	Dimension size;
	
	public EditGui(){
		super();
		
		final JFrame editFrame = new JFrame();
		JPanel editPanel = new JPanel();
		
				//Window settings
				editFrame.setVisible(true);
				editFrame.setSize(400,200);
				editFrame.setResizable(false);
				editFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				editFrame.setTitle("Edit ID:");
				editFrame.setLocationRelativeTo(null);
		editPanel.setBounds(0,1000,0,400);		
		editFrame.add(editPanel);
		
		
		namefield = new JTextField(nf);
		countryfield = new JTextField(cf);
		datefield = new JTextField(df);
		genderfield = new JTextField(gf);
		weightfield = new JTextField(wf);
		heightfield = new JTextField(hf);
		sportfield = new JTextField(sf);
		
		namelabel = new Label("Name:");
		countrylabel = new Label("Country:");
		datelabel = new Label("DoB:");
		genderlabel = new Label("Gender:");
		weightlabel = new Label("Weight:");
		heightlabel = new Label("Height:");
		sportlabel = new Label("Sport:");
		
		button_update = new Button("Update");
		button_close = new Button("Close");
		
		textfielddimension = new Dimension(100,20);
		editPanel.add(namefield);
		namefield.setMaximumSize(textfielddimension);
		editPanel.add(countryfield);
		countryfield.setMaximumSize(textfielddimension);
		editPanel.add(datefield);
		datefield.setMaximumSize(textfielddimension);
		editPanel.add(genderfield);
		genderfield.setMaximumSize(textfielddimension);
		editPanel.add(weightfield);
		weightfield.setMaximumSize(textfielddimension);
		editPanel.add(heightfield);
		heightfield.setMaximumSize(textfielddimension);
		editPanel.add(sportfield);
		sportfield.setMaximumSize(textfielddimension);
		
		editPanel.add(namelabel);
		editPanel.add(countrylabel);
		editPanel.add(datelabel);
		editPanel.add(genderlabel);
		editPanel.add(weightlabel);
		editPanel.add(heightlabel);
		editPanel.add(sportlabel);
		
		editPanel.add(button_update);
		editPanel.add(button_close);
		
		editPanel.setLayout(null);
		einset = editPanel.getInsets();
		
		size = namelabel.getPreferredSize();
		namelabel.setBounds(15+einset.left, 15+einset.top,size.width,size.height);
		size = countrylabel.getPreferredSize();
		countrylabel.setBounds(15+einset.left, 40+einset.top,size.width,size.height);
		size = datelabel.getPreferredSize();
		datelabel.setBounds(15+einset.left, 65+einset.top,size.width,size.height);
		size = genderlabel.getPreferredSize();
		genderlabel.setBounds(15+einset.left, 90+einset.top,size.width,size.height);
		size = weightlabel.getPreferredSize();
		weightlabel.setBounds(200+einset.left, 15+einset.top,size.width,size.height);
		size = heightlabel.getPreferredSize();
		heightlabel.setBounds(200+einset.left, 40+einset.top,size.width,size.height);
		size = sportlabel.getPreferredSize();
		sportlabel.setBounds(200+einset.left, 65+einset.top,size.width,size.height);
		size = namefield.getPreferredSize(); //Textfields
		namefield.setBounds(70+einset.left, 15+einset.top,100,20);
		countryfield.setBounds(70+einset.left, 40+einset.top,100,20);
		datefield.setBounds(70+einset.left, 65+einset.top,100,20);
		genderfield.setBounds(70+einset.left, 90+einset.top,100,20);
		weightfield.setBounds(250+einset.left, 15+einset.top,100,20);
		heightfield.setBounds(250+einset.left, 40+einset.top,100,20);
		sportfield.setBounds(250+einset.left, 65+einset.top,100,20);
		
		size = button_update.getPreferredSize(); //Button
		button_update.setBounds(100+einset.left, 120+einset.top,size.width,size.height);
		size = button_close.getPreferredSize();
		button_close.setBounds(250+einset.left, 120+einset.top,size.width,size.height);
		
		button_close.addActionListener(new ActionListener() {
		       public void actionPerformed(ActionEvent ae){
		           editFrame.dispose();
		       }
		      });
	}
	
	public void run(){
		
	}
	
}
/**
 * 
 * @author Marcus
 *
 *This is the GUI which let the client insert new records into the database.
 *
 */
class NewGui{

	Button button_add;
	Button button_cls;
	
	Label namelabel;
	Label countrylabel;
	Label datelabel;
	Label genderlabel;
	Label weightlabel;
	Label heightlabel;
	Label sportlabel;
	
	JTextField namefield;
	JTextField countryfield;
	JTextField datefield;
	JTextField genderfield;
	JTextField weightfield;
	JTextField heightfield;
	JTextField sportfield;
	
	String nf;
	String cf;
	String df;
	String gf;
	String wf;
	String hf;
	String sf;
	
	Insets einset;
	
	Dimension textfielddimension;
	Dimension size;
	
	public NewGui(){
		super();
		
		final JFrame newFrame = new JFrame();
		JPanel newPanel = new JPanel();
		
				//Window settings
				newFrame.setVisible(true);
				newFrame.setSize(400,200);
				newFrame.setResizable(false);
				newFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				newFrame.setTitle("New Participant");
				newFrame.setLocationRelativeTo(null);
		newPanel.setBounds(0,1000,0,400);		
		newFrame.add(newPanel);
		
		
		namefield = new JTextField(nf);
		countryfield = new JTextField(cf);
		datefield = new JTextField(df);
		genderfield = new JTextField(gf);
		weightfield = new JTextField(wf);
		heightfield = new JTextField(hf);
		sportfield = new JTextField(sf);
		
		namelabel = new Label("Name:");
		countrylabel = new Label("Country:");
		datelabel = new Label("DoB:");
		genderlabel = new Label("Gender:");
		weightlabel = new Label("Weight:");
		heightlabel = new Label("Height:");
		sportlabel = new Label("Sport:");
		
		button_add = new Button("Add");
		button_cls = new Button("Close");
		
		textfielddimension = new Dimension(100,20);
		newPanel.add(namefield);
		namefield.setMaximumSize(textfielddimension);
		newPanel.add(countryfield);
		countryfield.setMaximumSize(textfielddimension);
		newPanel.add(datefield);
		datefield.setMaximumSize(textfielddimension);
		newPanel.add(genderfield);
		genderfield.setMaximumSize(textfielddimension);
		newPanel.add(weightfield);
		weightfield.setMaximumSize(textfielddimension);
		newPanel.add(heightfield);
		heightfield.setMaximumSize(textfielddimension);
		newPanel.add(sportfield);
		sportfield.setMaximumSize(textfielddimension);
		
		newPanel.add(namelabel);
		newPanel.add(countrylabel);
		newPanel.add(datelabel);
		newPanel.add(genderlabel);
		newPanel.add(weightlabel);
		newPanel.add(heightlabel);
		newPanel.add(sportlabel);
		
		newPanel.add(button_add);
		newPanel.add(button_cls);
		
		newPanel.setLayout(null);
		einset = newPanel.getInsets();
		
		size = namelabel.getPreferredSize();
		namelabel.setBounds(15+einset.left, 15+einset.top,size.width,size.height);
		size = countrylabel.getPreferredSize();
		countrylabel.setBounds(15+einset.left, 40+einset.top,size.width,size.height);
		size = datelabel.getPreferredSize();
		datelabel.setBounds(15+einset.left, 65+einset.top,size.width,size.height);
		size = genderlabel.getPreferredSize();
		genderlabel.setBounds(15+einset.left, 90+einset.top,size.width,size.height);
		size = weightlabel.getPreferredSize();
		weightlabel.setBounds(200+einset.left, 15+einset.top,size.width,size.height);
		size = heightlabel.getPreferredSize();
		heightlabel.setBounds(200+einset.left, 40+einset.top,size.width,size.height);
		size = sportlabel.getPreferredSize();
		sportlabel.setBounds(200+einset.left, 65+einset.top,size.width,size.height);
		size = namefield.getPreferredSize(); //Textfields
		namefield.setBounds(70+einset.left, 15+einset.top,100,20);
		countryfield.setBounds(70+einset.left, 40+einset.top,100,20);
		datefield.setBounds(70+einset.left, 65+einset.top,100,20);
		genderfield.setBounds(70+einset.left, 90+einset.top,100,20);
		weightfield.setBounds(250+einset.left, 15+einset.top,100,20);
		heightfield.setBounds(250+einset.left, 40+einset.top,100,20);
		sportfield.setBounds(250+einset.left, 65+einset.top,100,20);
		
		size = button_add.getPreferredSize(); //Button
		button_add.setBounds(100+einset.left, 120+einset.top,size.width,size.height);
		size = button_cls.getPreferredSize();
		button_cls.setBounds(250+einset.left, 120+einset.top,size.width,size.height);
		
		button_cls.addActionListener(new ActionListener() {
		       public void actionPerformed(ActionEvent ae){
		           newFrame.dispose();
		       }
		      });
	}
}