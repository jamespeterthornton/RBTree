import java.applet.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Graphics2D;
import javax.swing.*;
//import javax.swing.JComponent.*;

//import java.awt.Container.*;
//import java.awt.Component.*;

public class Display extends Applet implements ActionListener {

    TextField numberField;

    RBTree mainTree;
    Button randomButton, insertButton, searchButton, deleteButton, clearButton;
    Panel center;
    Panel top;
    RBNode foundNode;
    
    public void init() {
    	
        // layout the grid for the input interface
    	
    	mainTree = new RBTree();
    	
        numberField = new TextField(4);

        //add the buttons, set their colors, and the action listener for each
        
        randomButton = new Button("Random");
        randomButton.setBackground(Color.WHITE);
        randomButton.addActionListener(this);
        insertButton = new Button("Insert");
        insertButton.setBackground(Color.WHITE);
        insertButton.addActionListener(this);
        searchButton = new Button("Search");
        searchButton.setBackground(Color.WHITE);
        searchButton.addActionListener(this);
        deleteButton = new Button("Delete");
        deleteButton.setBackground(Color.WHITE);
        deleteButton.addActionListener(this);
        clearButton = new Button("Clear");
        clearButton.setBackground(Color.WHITE);
        clearButton.addActionListener(this);
        
        //create the top panel and add the buttons to it
        
        top = new Panel();
        top.setLayout(new FlowLayout());
        top.add(numberField);
        top.add(randomButton);
        top.add(insertButton);
        top.add(searchButton);
        top.add(deleteButton);
        top.add(clearButton);
        
        top.setBackground(Color.BLUE);

        //add the panel
        
        this.setLayout(new BorderLayout());
        this.add("North", top);

    }

    //check if the input is a string
    
    public boolean isNumber(String string) {
        char[] c = string.toCharArray();
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isDigit(c[i])) {
                return false;
            }
        }
        return true;
    }
    
    //generate a random number, convert it to string, and put it in the input field
    
    public void random() {
    	int n = (int)(Math.random()*100);   	
    	String s = Integer.toString(n);
    	numberField.setText(s);
    }
    
    //if an action is performed, respond appropriately
    
    public void actionPerformed(ActionEvent e) {

    	//reset found node, so that the green color (from searches) will not persist 
    	
    	if (foundNode != null) foundNode.found = false;
    	
    	//if the random button was clicked, call random
    	
        if (e.getSource() == randomButton) {

        	System.out.println("Calling Random");
        	
        	this.random();

        //if the insert button was clicked, call the mainTree's insert method
        	
        } else if (e.getSource() == insertButton) {
        	
        	System.out.println("Insert");
        	
        	String s = numberField.getText();
        	
        	if(this.isNumber(s) == true) {
        		int x = Integer.parseInt(s);
        		mainTree.insert(x);
            	this.repaint();
        	}
        
        //if search button, call the tree's search method
        	
        } else if (e.getSource() == searchButton) {
        	
        	String s = numberField.getText();
        	
        	if(this.isNumber(s) == true) {

        		int x = Integer.parseInt(s);
        	
        		foundNode = mainTree.search(mainTree.root, x);
        	
        		this.repaint();
        	}
        	System.out.println("Search");
        	
        //if it was the deleteButton, search for the matching node and then pass it to the delete function
        	
        } else if (e.getSource() == deleteButton) {
        	
        	String s = numberField.getText();
        	if(this.isNumber(s) == true) {

        		int x = Integer.parseInt(s);
        	
        		foundNode = mainTree.search(mainTree.root, x);
        	
        	
        		if (foundNode.leaf != true) mainTree.RBDelete(foundNode);
        	
        		this.repaint();
        	}
        	
        	System.out.println("Delete");
        	
        //if it was the clear button, clear the tree
        	
        } else if (e.getSource() == clearButton) {
        	
        	RBTree newTree = new RBTree();
        	mainTree = newTree;
        	this.repaint();
        }
    }
    
    //draw the node
    
    public void drawNode(RBNode node, float x, float y, float width, Graphics2D g2) {
    	
    	//if there is no node or we are attempting to draw a leaf, return
    	
    	if((node == null) || (node.leaf == true)) return;
    	
    	float circleSize = 20.0f;
    	
    	//draw the lines to connect the circles
    	
    	g2.setColor(Color.BLACK);
    	if(node.left.leaf != true) {
    		g2.draw (new Line2D.Double(x + width/2, y + circleSize/2, x + width/4, y + circleSize + circleSize/2));
    	}
    	
    	if (node.right.leaf != true) {
    		g2.draw (new Line2D.Double(x + width/2, y + circleSize/2, x + 3*width/4, y + circleSize + circleSize/2));
    	}
    	
    	
    	
    	System.out.println(node.key);
    	
    	//make the circle for this node and set its color. if the user has just searched for it, found will be true and it should be green
    	
    	Shape circle = new Ellipse2D.Double(x + width/2 - circleSize/2, y, circleSize, circleSize);
    	
    	if (node.found == true) {
    		g2.setColor(Color.GREEN);
    		
    	//otherwise, set it to red or black. we store this data in a boolean because it is most efficient
    		
    	} else if(node.color == true) {
    		g2.setColor(Color.RED);
    	} else {
    		g2.setColor(Color.BLACK);
    	}
    	
    	//draw the circle
    	
    	g2.fill(circle);
    	
    	Rectangle bounds = circle.getBounds();
    	
    	g2.setColor(Color.WHITE);
    	
    	g2.drawString(Integer.toString(node.key), bounds.x + bounds.width/3 , bounds.y + (int)(1.5*bounds.height/2));
    	
    	//recursively call the two subtrees
    	
    	this.drawNode(node.left, x, y+ bounds.height, width/2, g2);
    	this.drawNode(node.right, x + width/2, y+ bounds.height, width/2, g2);
    	
    }   
    
    public void paint(Graphics g) {
    	
    	//call draw node on the tree
    	
    	Graphics2D g2 = (Graphics2D) g;

    	this.drawNode(mainTree.root, 0, top.getHeight(), this.getWidth(), g2);
    	System.out.println("painting");
    	
    }


}

