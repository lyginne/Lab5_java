import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.Timer;

    
public class DrawOutlinePanel extends JPanel 
                              implements NewMarkFromPanelListener, 
                              RChangedEventListener {
    //Graphics graphics;
    
    
    
    int R=1;
    
    volatile int markRadius;
    
    Vector<Mark> marks = new Vector<Mark>();
    
    private ArrayList<NewMarkFromPanelListener> listeners 
            = new ArrayList<NewMarkFromPanelListener>();
    private final Timer timer;
    //public static Drawing d = new Drawing(); 
    public DrawOutlinePanel(){
        this.setBackground(new Color(0xF5,0xF5,0xDC));
        addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                Mark mark 
                        = new Mark(
                        3*R*((float)(e.getX()-(getWidth()/2))/(getWidth()-40)),
                        -3*R*((float)(e.getY()-(getHeight()/2))/(getHeight()-40)));
                callServerToCheckMark(mark);
                marks.add(mark);
               
                fireNewMarkEvent(mark);
                repaint();
            }
            
        });
        timer=new Timer(100,new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                }
            });
            timer.start();
                
    }
    private void DrawXAxis(Graphics g){
        g.setColor(new Color(255,0,0));
        DrawXLine(g);
        DrawXRisks(g);
        DrawXArrow(g);
    }
    
    private void DrawYAxis(Graphics g){
        g.setColor(new Color(255,0,0)); 
        DrawYLine(g);
        DrawYRisks(g);
        DrawYArrow(g);
    }
    
    private void DrawXLine(Graphics g){
        g.drawLine(0+20,this.getHeight()/2,this.getWidth()-20,this.getHeight()/2);
    }
    
    private void DrawYLine(Graphics g){
        g.drawLine(this.getWidth()/2,0+20,this.getWidth()/2,this.getHeight()-20);
    }
    
    private void DrawXArrow(Graphics g){
        g.drawLine(this.getWidth()-20-10,this.getHeight()/2-5,this.getWidth()-20,this.getHeight()/2);
        g.drawLine(this.getWidth()-20-10,this.getHeight()/2+5,this.getWidth()-20,this.getHeight()/2);
    }
    
    private void DrawYArrow(Graphics g){
        g.drawLine(this.getWidth()/2-5,0+10+20,this.getWidth()/2,20);
        g.drawLine(this.getWidth()/2+5,0+10+20,this.getWidth()/2,20);
    }
    
    private void DrawXRisks(Graphics g){
        g.drawLine((this.getWidth()-2*20)/6+20,
                this.getHeight()/2-5,
                (this.getWidth()-2*20)/6+20,
                this.getHeight()/2+5);
        g.drawLine((this.getWidth()-2*20)/3+20,
                this.getHeight()/2-5,
                (this.getWidth()-2*20)/3+20,
                this.getHeight()/2+5);
        g.drawLine((this.getWidth()-2*20)/3*2+20,
                this.getHeight()/2-5,
                (this.getWidth()-2*20)/3*2+20,
                this.getHeight()/2+5);
        g.drawLine((this.getWidth()-2*20)/6*5+20,
                this.getHeight()/2-5,
                (this.getWidth()-2*20)/6*5+20,
                this.getHeight()/2+5);
    }
    
    private void DrawYRisks(Graphics g){
        g.drawLine((this.getWidth()/2)-5,
                (this.getHeight()-2*20)/6+20,
                (this.getWidth()/2)+5,(
                this.getHeight()-2*20)/6+20);
        g.drawLine((this.getWidth()/2)-5,
                (this.getHeight()-2*20)/3+20,
                (this.getWidth()/2)+5,
                (this.getHeight()-2*20)/3+20);
        g.drawLine((this.getWidth()/2)-5,
                (this.getHeight()-2*20)/3*2+20,
                (this.getWidth()/2)+5,
                (this.getHeight()-2*20)/3*2+20);
        g.drawLine((this.getWidth()/2)-5,
                (this.getHeight()-2*20)/6*5+20,
                (this.getWidth()/2)+5,
                (this.getHeight()-2*20)/6*5+20);
    }
    
    private void DrawAxis(Graphics g){
        DrawXAxis(g);
        DrawYAxis(g);
    }
    
    private void DrawOutline(Graphics g){
        if (R==0)
            return;
        g.setColor(Color.BLUE);
        g.fillArc((this.getWidth()-2*20)/6+20, 
                (this.getHeight()-2*20)/6+20,
                2*(this.getWidth()-40)/3, 
                2*(this.getHeight()-40)/3, 
                0, 
                90); 
        g.fillRect(this.getWidth()/2, 
                this.getHeight()/2-1, 
                (this.getWidth()-40)/3, 
                (this.getHeight()-40)/3);
        int xPoints[]={this.getWidth()/2-(this.getWidth()-40)/3, 
            this.getWidth()/2,
            this.getWidth()/2};
        int yPoints[]={this.getHeight()/2, 
            this.getHeight()/2-(this.getHeight()-40)/3,
            this.getHeight()/2};
        g.fillPolygon(xPoints, yPoints, 3);
    }
    
    private void DrawMarks(Graphics g){
        for(Mark mark : marks)
        {
            g.setColor(new Color(0,0,0));
            Outline outline = new Outline(R);
            int radiusbyHeigth= (int)((getHeight()-40)/(3*21));
            int radiusbyWidth= (int)((getWidth()-40)/(3*21));
            int tmpMarkRadius=radiusbyHeigth>radiusbyWidth ? 
                    radiusbyHeigth : 
                    radiusbyWidth;
            switch(mark.state){
                case DidNotBelongsToOutline:
                    g.setColor(new Color(0,0,0));
                    g.fillOval((int)(
                            (mark.getX()*(this.getWidth()-40)/3)/R)+ 
                            (this.getWidth()/2)-markRadius/2, 
                            -(int)((mark.getY()*(this.getHeight()-40)/3)/R)+
                            (this.getHeight()/2)-markRadius/2, 
                            mark.getMarkRadius(), 
                            mark.getMarkRadius());
                break;
                case BelongsToOutline:
                    g.setColor(new Color(0,255,0));
                    
                    g.fillOval((int)((mark.getX()*(this.getWidth()-40)/3)/R)+
                            (this.getWidth()/2)-tmpMarkRadius/2, 
                            -(int)((mark.getY()*(this.getHeight()-40)/3)/R)+
                            (this.getHeight()/2)-tmpMarkRadius/2, 
                            tmpMarkRadius, 
                            tmpMarkRadius);
                break;
                case ServerDoesNotRespond:
                    if(Connector.replies)
                        callServerToCheckMark(mark);
                    g.setColor(new Color(80,80,80));
                    g.fillOval((int)((mark.getX()*(this.getWidth()-40)/3)/R)+
                            (this.getWidth()/2)-tmpMarkRadius/2, 
                            -(int)((mark.getY()*(this.getHeight()-40)/3)/R)+
                            (this.getHeight()/2)-tmpMarkRadius/2, 
                            tmpMarkRadius, 
                            tmpMarkRadius);
                break;

                }
                
        }
        Connector.replies=true;
    }
    
    private void callServerToCheckMark(Mark mark){
        (new Connector(mark,R)).start();
    }
    
    @Override
    public void paint(Graphics g) {


        super.paint(g);
        //graphics=g;


        DrawAxis(g);
        DrawOutline(g);
        DrawMarks(g);
    }    
    
     public void addNewMarkEventListener(NewMarkFromPanelListener listener){
        listeners.add(listener);
    }

    public NewMarkFromPanelListener[] getComDataEnableEventListeners(){
        return listeners.toArray(
                new NewMarkFromPanelListener[listeners.size()]);
    }

    public void removeNewMarkEventListener(NewMarkFromPanelListener listener){
        listeners.remove(listener);
    }

    protected void fireNewMarkEvent(Mark mark){
        NewMarkFromPanel ev = new NewMarkFromPanel(this, mark);
        for (NewMarkFromPanelListener listener : listeners){
           listener.newMarkFromPanelListener(ev);
        }
    }

    @Override
    public void newMarkFromPanelListener(NewMarkFromPanel e) {
        marks.add(e.getMark());
        repaint();
    }

    @Override
    public void RChangedEventListener(RChangedEvent e) {
        R=e.getR();
        for (Mark mark : marks){
            callServerToCheckMark(mark);
        }
        repaint();
    }
}
