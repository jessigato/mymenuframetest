package mymenuframetest;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.Desktop;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.Toolkit;
import java.net.URI;
import java.net.URL;
import javax.swing.JOptionPane;

import java.util.Scanner;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import java.awt.print.*;

public class MyMenuFrame extends JFrame implements ActionListener
{
    JMenuBar menubar;
    JMenu file, edit, formatMenu, color, font, print, help;
    JMenuItem open, save, exit, changeColor, printer, about, visit;
    JTextArea displayLabel;
    
    private final JRadioButtonMenuItem [] fonts;
    private final JCheckBoxMenuItem [] styleItems;
    private final ButtonGroup fontButtonGroup;
    
    public MyMenuFrame()
    {
        setTitle("MyNotepad");
        
        //create menu
        menubar = new JMenuBar();
        
        //the main buttons that'll show up in the menubar
        file = new JMenu("File");
        edit = new JMenu("Edit");
        print = new JMenu("Print");
        help = new JMenu("Help");
        color = new JMenu("Color");
        font = new JMenu("Font");
        
        //adding JMenu to menubar, it will appear up top based on order of added
        menubar.add(file);
        menubar.add(edit);
        menubar.add(print);
        menubar.add(help);
        
        //set the Mnemonic
        file.setMnemonic('F');
        edit.setMnemonic('D');
        print.setMnemonic('P');
        help.setMnemonic('H');
        color.setMnemonic('C');
        font.setMnemonic('F');
        
        //OPEN
        open = new JMenuItem("Open");//creating open menu item
        open.setAccelerator(KeyStroke.getKeyStroke('O', CTRL_DOWN_MASK));//setting shortcut for open to Ctrl+O
        open.addActionListener(this);
        file.add(open);//adding this button to file
        file.addSeparator();//adds a separator between open and save, makes it look more organized
        
        //SAVE
        save = new JMenuItem("Save");//creating save menu item
        save.setAccelerator(KeyStroke.getKeyStroke('S', CTRL_DOWN_MASK));//setting shortcut for save to Ctrl+S
        save.addActionListener(this);
        file.add(save);//adding save to file
        file.addSeparator();
        
        //EXIT
        exit = new JMenuItem("Exit");//creating exit menu item
        exit.setAccelerator(KeyStroke.getKeyStroke('X', CTRL_DOWN_MASK));//setting shortcut for exit to Ctrl+X
        exit.addActionListener(this);
        file.add(exit);//adding exit to file, no separator needed cause moving onto next button
        
        //COLOR
        changeColor = new JMenuItem("Change Color");
        changeColor.setAccelerator(KeyStroke.getKeyStroke('C', CTRL_DOWN_MASK));//setting shortcut for changeColor to Ctrl+C
        changeColor.addActionListener(this);
        color.add(changeColor);//adding changeColor to color, it'll appear to the side when you hover over color
        edit.add(color);//adding color to edit button
        edit.addSeparator();
        
        //FONT
        String [] fontNames = {"Times New Roman", "Arial", "Serif"};//array containing the fonts
        ItemHandler itemHandler = new ItemHandler();//creating instance for the method ItemHandler
        fonts = new JRadioButtonMenuItem[fontNames.length];//creating radio buttons and setting it to the size of fontNames array
        fontButtonGroup = new ButtonGroup();//creating button group
        for(int count = 0; count < fontNames.length; count++)//for loop to interate through fontNames array
        {
            fonts[count] = new JRadioButtonMenuItem(fontNames[count]);//putting the fontNames array items into font
            font.add(fonts[count]);//adding fonts menu items into font button to display
            fontButtonGroup.add(fonts[count]);//adding the fonts to the button group
            fonts[count].addActionListener(itemHandler);
        }
        font.addSeparator();
        
        //STYLE
        String [] styleNames = {"Bold", "Italic"};//array containing the styles
        styleItems = new JCheckBoxMenuItem[styleNames.length];//creating check boxes for the styles
        StyleHandler styleHandler = new StyleHandler();//creating instance for the method StyleHandler
        for (int count = 0; count < styleNames.length; count++)//for loop to interate through styleNames array
        {
            styleItems[count] = new JCheckBoxMenuItem(styleNames[count]);//filling the check boxes with the styles
            font.add(styleItems[count]);//adding styleItems into font button to display
            styleItems[count].addItemListener(styleHandler);
        }
        edit.add(font);
        
        //PRINT
        printer = new JMenuItem("Send to Printer");
        printer.setAccelerator(KeyStroke.getKeyStroke('P', CTRL_DOWN_MASK));//setting shortcut for printer to Ctrl+P
        printer.addActionListener(this);
        print.add(printer);//adding printer to print button
        
        //ABOUT
        about = new JMenuItem("About");
        about.setAccelerator(KeyStroke.getKeyStroke('A', CTRL_DOWN_MASK));//setting shortcut for about to Ctrl+A
        about.addActionListener(this);
        help.add(about);//adding about to help button
        help.addSeparator();
        
        //VISIT
        visit = new JMenuItem("Visit Homepage");
        visit.setAccelerator(KeyStroke.getKeyStroke('V', CTRL_DOWN_MASK));//setting shortcut for visit to Ctrl+V
        visit.addActionListener(this);
        help.add(visit);//adding visit to help button
        
        setJMenuBar(menubar);
        setVisible(true);
        
        displayLabel = new JTextArea();//box for user to type into
        displayLabel.setFont(new Font("Serif", Font.PLAIN, 20));//setting the default font and size
        displayLabel.setForeground(Color.RED);//setting the default text color to red
        repaint();
        add(displayLabel, BorderLayout.CENTER);//adding the box into the notepad program
    }
    
    
    public void actionPerformed(ActionEvent ae)
    {   
        //if statement to exit the program if user clicks exit, or uses shortcut to exit
        if (ae.getSource() == this.exit)
            System.exit(0);
        
        else if (ae.getSource() == this.open){//if open is clicked
            JFileChooser openFile = new JFileChooser();//creating a file chooser variable
            String fileName = "";//empty but will hold what file name user types
            
            //show open file dialog, opens if it's approved or returns if it doesn't work
            if (openFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                fileName = openFile.getSelectedFile().getAbsolutePath();//fileName now holds what user typed
            }
            else {
                return;
            }
            
            try{
                BufferedReader reader = new BufferedReader(new FileReader(fileName));//
                String line = "";
                StringBuffer buffer = new StringBuffer();//creating StringBuffer variable to have the file appended to it
                
                //reading each line of the file and moves to the next line, stops when there are no more lines
                while ((line = reader.readLine()) != null){
                    buffer.append(line).append("\n");//appending the text to the buffer and adding \n to the end of each line
                    displayLabel.setText(buffer.toString());//turning the buffer into a string and putting it inside the displayLabel text area box
                }
                reader.close();//once while loop has finished, closes the file
            }
            catch (Exception e){
                //to print the error and where it occurred if it occurs
                System.err.format("Exception occurred trying to read '%s'.", fileName);
                e.printStackTrace();
            }
        }

        else if (ae.getSource() == this.save){//if save is clicked
            JFileChooser saveFile = new JFileChooser();//creating a file chooser variable
            int saveOption = saveFile.showSaveDialog(this);//storing the file chooser to saveOption so if user clicks save
            if (saveOption == JFileChooser.APPROVE_OPTION){//if user clicks save
                try{
                    BufferedWriter out = new BufferedWriter(new FileWriter(saveFile.getSelectedFile().getPath()));//creating a buffered writer to store what's in the text area
                    out.write(displayLabel.getText());//adding the text area to the buffered writer to be saved
                    out.close();//closes file chooser after file has been saved
                }
                catch (Exception e){
                    System.out.println(e.getMessage());//prints error message
                }
            }
        }
        
        else if (ae.getSource() == changeColor){//if change color is clicked
            Color selectColor = JColorChooser.showDialog(this, "Select a color", Color.RED);//opens ColorChooser and stores the picked color to selectColor
            displayLabel.setForeground(selectColor);//changes the color of the text in the displayLabel
        }
        
        else if (ae.getSource() == printer){//if printer is clicked
            //storing option dialog asking user if they want to print to printOutput
            int printOutput = JOptionPane.showOptionDialog(MyMenuFrame.this, "Do you want to print this file?", "Confirmation", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if(printOutput == 0){//if users selects OK in the option dialog
                //display information message saying file has been printed
                JOptionPane.showMessageDialog(this, "The file is successfully printed", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
        else if(ae.getSource() == about){//if about is clicked
            //display information message about the software
            JOptionPane.showMessageDialog(this, "This software is developed in 2019\nVersion is 1.0", "About", JOptionPane.INFORMATION_MESSAGE);
        }
        
        else if(ae.getSource() == visit){//if visit is clicked
            try{
                //open a webpage with the URL given
                Desktop.getDesktop().browse(new URL("http://www.microsoft.com").toURI());
            }
            catch (Exception e){
                e.printStackTrace();//prints error
            }
        }   
    }
    
    private class ItemHandler implements ActionListener
    {   
        @Override
        public void actionPerformed (ActionEvent event)
        {
            //font selection
            for (int i = 0; i < fonts.length; i++){//iterate through fonts, which contains the radio button names from the font array
                if(event.getSource() == fonts[i]){//if the user picks a font in the array from the radio buttons
                    displayLabel.setFont(new Font(fonts[i].getText(), Font.PLAIN, 20));//change the font to the font that was picked
                }
            }
            repaint();//redraw the application
        }
    }
    
    private class StyleHandler implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            String name = displayLabel.getFont().getName();//get current font
            Font font;//to store text font changes
            
            if(styleItems[0].isSelected () && styleItems[1].isSelected()){//if BOTH styles were picked in the checkboxes
                font = new Font(name, Font.BOLD + Font.ITALIC, 20);//then change the text to bold and italic, with a size 20 font
            }
            
            else if(styleItems[0].isSelected()){//if only bold was picked
                font = new Font (name, Font.BOLD, 20);//change text to bold
            }
            
            else if(styleItems[1].isSelected()){//if only italic was picked
                font = new Font (name, Font.ITALIC, 20);//change text to italic
            }
            
            else{//if neither are picked
                font = new Font (name, Font.PLAIN, 20);//make text plain
            }
            
            displayLabel.setFont(font);//sets the text area to what font was picked
            repaint();//redraws the text
        }
    }
}