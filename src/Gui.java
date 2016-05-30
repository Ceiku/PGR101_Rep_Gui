import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by Daniel on 30/05/2016.
 */

public class Gui extends JFrame implements ActionListener {
    public static final String about = "This is a non-licensed software free for distribution. " +
            "No security or integrity checks are made.\n" +
            "This is a very simple notepad made semi live for the purpose of teaching Java basics and Swing.";

    JMenuBar menuBar;
    JMenu file, edit, help;
    JScrollPane pane;
    JTextArea txtArea;

    String[] menuNames = {"File", "Edit", "Help"};

    String[][] menuItems = {
            {"Open", "Save", "Exit"}, {"Clear", "Discard changes", "Zoom +", "Zoom -"}, {"About"}};


    Gui(){
        super("Test gui");

        menuBar = makeMenuBar(menuNames, menuItems); // egen metode
        setJMenuBar(menuBar);

        txtArea = new JTextArea();
        txtArea.setLineWrap(true);
        pane = new JScrollPane(txtArea);
        add(pane, BorderLayout.CENTER);



        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // navnene er hardkodet for leslighet
        if (command == "Open")
            txtArea.setText(openFile(this));

        else if (command == "Save")
            saveFile(this, txtArea.getText());

        else if (command == "Exit"){
            // fill in
        }


    }

    public JMenu makeMenu(String menuName, String[] itemNames){
        JMenu menu = new JMenu(menuName);

        for (String name : itemNames){
            JMenuItem item = new JMenuItem(name);
            item.setActionCommand(name);
            item.addActionListener(this);
            menu.add(item);
        }

        return menu;
    }

    public JMenuBar makeMenuBar(String[] menuNames, String[][] items){
        JMenuBar bar = new JMenuBar();

        for (int i = 0; i < menuNames.length; i++){
            JMenu menu = makeMenu(menuNames[i], items[i]);
            bar.add(menu);
        }
        return bar;
    }
    public static String openFile(JFrame txt) {
        File file = null;
        String textFromFile = null;
        String line = null;

        JFileChooser explorer = new JFileChooser();

        int returnVal = explorer.showOpenDialog(txt);
        if(returnVal == JFileChooser.APPROVE_OPTION)
            file = explorer.getSelectedFile();
        else if (returnVal == JFileChooser.CANCEL_OPTION)
            return null;

        //hint2?
        BufferedReader inReader = null;
        try {
            inReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // hint?
        do{
            try {
                //System.out.println(line);
                line = inReader.readLine();
                textFromFile+= line;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(line != null);

        return textFromFile;

    }
    public static void saveFile(JFrame frame, String currentText) {
        JFileChooser explorer = new JFileChooser();
        int returnVal = explorer.showOpenDialog(frame);
        File file = explorer.getSelectedFile();
        BufferedWriter writer = null;
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                writer = new BufferedWriter( new FileWriter( file.getAbsolutePath()+".txt"));
                writer.write(currentText);
                writer.close( );
                JOptionPane.showMessageDialog(frame, "The Message was Saved Successfully!",
                        "Success!", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(frame, "The Text could not be Saved!",
                        "Error!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
