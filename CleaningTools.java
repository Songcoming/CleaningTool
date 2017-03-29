package cleaningtools;

import cleaningtools.*;

import java.awt.*;
import java.awt.event.*;

public class CleaningTools extends Frame implements ItemListener, ActionListener{

	List lst;
	Panel p1, p2;
	Checkbox cb1, cb2, cb3;
	Label lb1, lb2;
	TextField tf0, tf1, tf2, tf3;
	Button b1, b2, b3, b4;
	CheckboxGroup cbg;
	String[] itemselected;
	FindFiles1 ff1;
	FindFiles2 ff2;
	FindFiles3 ff3;
	SimpleDialog1 dialog1;
	SimpleDialog2 dialog2;

	public CleaningTools(String str){

		super(str);

		setLayout(new GridLayout(0, 2, 5, 5));

		p1 = new Panel();
		p1.setLayout(new GridLayout(0, 1));

		lb1 = new Label("The folder you want to search:");
		tf0 = new TextField(20);

		cbg = new CheckboxGroup();

		cb1 = new Checkbox("Searching files by extension:", cbg, true);
		cb1.addItemListener(this);
		tf1 = new TextField(20);
		tf1.setEditable(true);
		b1 = new Button("Searching");
		b1.addActionListener(this);

		cb2 = new Checkbox("Searching files by size:", cbg, false);
		cb2.addItemListener(this);
		tf2 = new TextField(20);
		tf2.setEditable(false);
		b2 = new Button("Searching");
		b2.setEnabled(false);
		b2.addActionListener(this);

		cb3 = new Checkbox("Searching files by name:", cbg, false);
		cb3.addItemListener(this);
		tf3 = new TextField(20);
		tf3.setEditable(false);
		b3 = new Button("Searching");
		b3.setEnabled(false);
		b3.addActionListener(this);

		p1.add(lb1);
		p1.add(tf0);

		p1.add(cb1);
		p1.add(tf1);
		p1.add(b1);

		p1.add(cb2);
		p1.add(tf2);
		p1.add(b2);

		p1.add(cb3);
		p1.add(tf3);
		p1.add(b3);

		add(p1);

		p2 = new Panel();
		p2.setLayout(new BorderLayout(3, 5));

		lb2 = new Label("The files have been found:");

		lst = new List(10, true);
		lst.addItemListener(this);

	    b4 = new Button("Delete");
	    b4.addActionListener(this);

	    p2.add(lb2, "North");
	    p2.add(lst);
	    p2.add(b4, "South");

	    add(p2);

	    addWindowListener(new WindowAdapter(){
	    	public void windowClosing(WindowEvent e){
	    		System.exit(0);
	    	}
	    });

	    setSize(500, 400);
	    setVisible(true);
	}

	public void itemStateChanged(ItemEvent e){

		Object source = e.getSource();

		if (source == cb1){
			if(e.getStateChange() == ItemEvent.SELECTED){
				b1.setEnabled(true);
				b2.setEnabled(false);
				b3.setEnabled(false);
				tf1.setEditable(true);
			    tf2.setEditable(false);
			    tf3.setEditable(false);
			}
			   
		}

		if (source == cb2){
			if(e.getStateChange() == ItemEvent.SELECTED){
				b1.setEnabled(false);
				b2.setEnabled(true);
				b3.setEnabled(false);
				tf1.setEditable(false);
			    tf2.setEditable(true);
			    tf3.setEditable(false);
			}
		}

		if (source == cb3){
			if(e.getStateChange() == ItemEvent.SELECTED){
				b1.setEnabled(false);
				b2.setEnabled(false);
				b3.setEnabled(true);
				tf1.setEditable(false);
			    tf2.setEditable(false);
			    tf3.setEditable(true);
			}
		}

		if (source == lst){
			itemselected = lst.getSelectedItems();

			/*for(String i : itemselected)
				System.out.println(i);*/
		}
	}

	public static void main(String[] args){
		CleaningTools a = new CleaningTools("Cleaning Tool");

		//System.out.println(a.itemselected);
	}

	public void actionPerformed(ActionEvent e) {

		Object source2 = e.getSource();

		if(source2 == b1){
			lst.removeAll();

			if(ff2 != null)
				ff2 = null;
			if(ff3 != null)
				ff3 = null;

			ff1 = new FindFiles1(tf0.getText(), tf1.getText());
			//System.out.println(ff1.result);

			for(String tmp : ff1.resultname){
				//System.out.println(tmp);
				lst.add(tmp);
			}
		}

		if(source2 == b2){
			lst.removeAll();

			if(ff1 != null)
				ff1 = null;
			if(ff3 != null)
				ff3 = null;

			ff2 = new FindFiles2(tf0.getText(), tf2.getText());

			for(String tmp : ff2.resultname){
				lst.add(tmp);
			}

		}

		if(source2 == b3){
			lst.removeAll();

			if(ff1 != null)
				ff1 = null;
			if(ff2 != null)
				ff2 = null;

			ff3 = new FindFiles3(tf0.getText(), tf3.getText());

			for(String tmp : ff3.resultname){
				lst.add(tmp);
			}
		}

		if(source2 == b4){
			if(ff1 != null | ff2 != null | ff3 != null){
				if(dialog1 == null){
					dialog1 = new SimpleDialog1(this, "Are you sure?");
				}

				dialog1.setVisible(true);

				if(dialog1.isDeleted){
			        for(String j : itemselected)
						lst.remove(j);
				}

				ff1 = null;
				ff2 = null;
				ff3 = null;
			}
	    }
	}
}

class SimpleDialog1 extends Dialog implements ActionListener{

	CleaningTools parents;
	Button yes, cancel;
	boolean isDeleted;
	SimpleDialog2 dialog2;

	SimpleDialog1(Frame ct, String title){
		super(ct, title, true);
		parents = (CleaningTools)ct;

		Panel p1 = new Panel();
		Label lb = new Label("Do you want to delete the selected file(s)?");
		p1.add(lb);
		add("Center", p1);

		Panel p2 = new Panel();
		p2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		yes = new Button("yes");
		cancel = new Button("cancel");
		yes.addActionListener(this);
		cancel.addActionListener(this);
		p2.add(yes);
		p2.add(cancel);
		add("South", p2);

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				setVisible(false);
			}
		});

		pack();
	}

	public void actionPerformed(ActionEvent e){

		Object source3 = e.getSource();

		if(source3 == yes){
			if(parents.ff1 != null){
				//System.out.println("1");
				//System.out.println(ff1.resultfile);
				DeleteFiles df1 = new DeleteFiles(parents.itemselected, parents.ff1.resultfile);
				isDeleted = df1.deleteSelectedFiles();
			}

			if(parents.ff2 != null){
				//System.out.println("2");
				DeleteFiles df2 = new DeleteFiles(parents.itemselected, parents.ff2.resultfile);
				//System.out.println(ff2.resultfile);
				//System.out.println(df2.selecteditems);
				//System.out.println(df2.searchresults);
				isDeleted = df2.deleteSelectedFiles();
			}

			if(parents.ff3 != null){
				//System.out.println("3");
				DeleteFiles df3 = new DeleteFiles(parents.itemselected, parents.ff3.resultfile);
				isDeleted = df3.deleteSelectedFiles();
			}

			if(dialog2 == null)
				dialog2 = new SimpleDialog2(parents, "results", this);

			dialog2.setVisible(true);
		}

		setVisible(false);
	}
}

class SimpleDialog2 extends Dialog implements ActionListener{
	CleaningTools parents;
	SimpleDialog1 brothers;
	Button ok;

	SimpleDialog2(Frame ct, String title, Dialog dia){
		super(ct, title, false);
		parents = (CleaningTools)ct;
		brothers = (SimpleDialog1)dia;

		Panel p1 = new Panel();
		Label lb1 = new Label("Delete files successfully.^_^");
		Label lb2 = new Label("Delete files unsuccessfully.T_T");
		add("Center", p1);

		Panel p2 = new Panel();
		p2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		ok = new Button("OK");
		ok.addActionListener(this);
		p2.add(ok);
		add("South", p2);

		if(brothers.isDeleted)
			p1.add(lb1);
		else if(brothers.isDeleted == false)
			p1.add(lb2);

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				setVisible(false);
			}
		});

		pack();
	}

	public void actionPerformed(ActionEvent e){
		setVisible(false);
	}
}