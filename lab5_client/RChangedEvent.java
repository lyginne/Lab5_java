import java.util.EventObject;

public class RChangedEvent extends EventObject{
    int R;
    public RChangedEvent(Object source, int R){
        super(source);
        this.R=R;
    }
    
    public int getR(){
        return R;
    }
}
