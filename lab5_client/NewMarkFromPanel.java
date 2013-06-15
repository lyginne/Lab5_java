import java.util.EventObject;

public class NewMarkFromPanel extends EventObject{
    
    private Mark mark;
    
    public NewMarkFromPanel(Object source, Mark mark){
        super(source);
        this.mark=mark;
    }
    
    public Mark getMark(){
        return mark;
    }
}
