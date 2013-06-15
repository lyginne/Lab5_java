import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFrame;

public class OutlineFrame extends JFrame  {    
    OutlineFrame(){
            ResourceBundle  messages = ResourceBundle .getBundle("Locale", Localization.getLocale());
            setTitle(messages.getString("WindowTitle"));
            setSize(300, 200);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(1,0));

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            
            ControlPanel controlPanel = new ControlPanel();
            
            DrawOutlinePanel drawPanel = new DrawOutlinePanel();
            add(drawPanel, BorderLayout.CENTER);
            
            drawPanel.addNewMarkEventListener(controlPanel);
            controlPanel.addNewMarkEventListener(drawPanel);
            controlPanel.addRChangedListener(drawPanel);            
            
            add(controlPanel, BorderLayout.SOUTH);
            this.setMinimumSize(new Dimension(800, 500));

    }       
}
