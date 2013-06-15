import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControlPanel extends JPanel implements  NewMarkFromPanelListener{
    
    JButton okButton;
    JTextArea coordinatesLabel;
    JSlider rSlider;
    final ButtonGroup cbg;
    JList myList;
    JLabel rLabel;
    Vector<JRadioButton> radioButtonList = new Vector<JRadioButton>();
    
    final int beginning = -3;
    final int ending = 3;
    
    private ArrayList<NewMarkFromPanelListener> NewMarklisteners 
            = new ArrayList<NewMarkFromPanelListener>();
    private ArrayList<RChangedEventListener> Rlisteners 
            = new ArrayList<RChangedEventListener>();
    
    public ControlPanel(){
        
            setLayout(new FlowLayout (FlowLayout.LEADING));
            ResourceBundle  messages = ResourceBundle .getBundle("Locale", Localization.getLocale());
            
            
            
            JLabel CoordinatesHeader = new JLabel(messages.getString("Coordinates"));
            coordinatesLabel = new JTextArea();
            coordinatesLabel.setEnabled(false);
            coordinatesLabel.setMinimumSize(new Dimension(150,50));

            
            add(CoordinatesHeader);
            add(coordinatesLabel);
            add(new JLabel("X:")); 
            Vector<Integer> xNumbers = new Vector<Integer>();
            for(int i=-3; i<=3; i++) {
                xNumbers.addElement(i);
            }
            myList = new JList(xNumbers);
            add(myList);
            
            
            add(new JLabel("Y:"));
            cbg = new ButtonGroup();
            for (int i=-3; i<=3; i++){  
                JRadioButton radio 
                        = new JRadioButton(String.valueOf(i) , false);
                add(radio);
                cbg.add(radio);
                radioButtonList.add(radio);
            }
                        
            SpinnerModel sm = new SpinnerNumberModel(1, 0, 2147483647, 1);
            
            rSlider = new JSlider(1,10,1);
            add(rSlider);
            rLabel = new JLabel("1");
            add(rLabel);
            rSlider.addChangeListener(new ChangeListener(){
                @Override
                public void stateChanged(ChangeEvent e){
                       rLabel.setText(String.valueOf(rSlider.getValue()));
            }});
            okButton = new JButton(messages.getString("OKKey"));
            add(okButton);
            okButton.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e)
            { 
                int yCoordinate=0;
                boolean ySelected=false;
                for(int i=0;i<=ending-beginning;i++){
                    if(radioButtonList.get(i).isSelected())
                    {
                        ySelected=true;
                        yCoordinate=i+beginning;
                    }
                }
                
                if (ySelected&&myList.getSelectedValue()!= null){
                    Mark mark = new Mark(
                            (float)((Integer)(myList.getSelectedValue())),
                            yCoordinate
                            );
                    coordinatesLabel.setText(mark.toString());
                    fireNewMarkEvent(mark);
                  }
      
                    fireRChangedEvent((Integer)(rSlider.getValue()));
                }
            });
            this.setMinimumSize(this.getSize());
    }

    @Override
    public void newMarkFromPanelListener(NewMarkFromPanel e) {
        coordinatesLabel.setText(e.getMark().toString());
        cbg.clearSelection();
        myList.clearSelection();
        
    }
         public void addNewMarkEventListener(NewMarkFromPanelListener listener){
 	              NewMarklisteners.add(listener);
    }

    public NewMarkFromPanelListener[] getNewMarkEventListeners(){
                 return NewMarklisteners.toArray(
                         new NewMarkFromPanelListener[NewMarklisteners.size()]);
    }

    public void removeNewMarkEventListener(NewMarkFromPanelListener listener){
                  NewMarklisteners.remove(listener);
    }

    protected void fireNewMarkEvent(Mark mark){
        NewMarkFromPanel ev = new NewMarkFromPanel(this, mark);
        for (NewMarkFromPanelListener listener : NewMarklisteners){
           listener.newMarkFromPanelListener(ev);
        }
    }
        public void addRChangedListener(RChangedEventListener listener){
 	              Rlisteners.add(listener);
    }

    public RChangedEventListener[] getRChangedEventListeners(){
                 return Rlisteners.toArray(
                         new RChangedEventListener[Rlisteners.size()]);
    }

    public void removeRChangedEventListener(RChangedEventListener listener){
                  Rlisteners.remove(listener);
    }

    protected void fireRChangedEvent(int R){
        RChangedEvent ev = new RChangedEvent(this, R);
        for (RChangedEventListener listener : Rlisteners){
           listener.RChangedEventListener(ev);
        }
    }
}
