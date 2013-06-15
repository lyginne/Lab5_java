public class Mark extends Thread{
        private float X;
        private float Y;
        private int markRadius;
        
        
        public volatile MarkState state;
        
        
        public Mark(float X, float Y){
            this.state = MarkState.ServerDoesNotRespond;


                this.X=X;
                this.Y=Y;
                
                markRadius=1;
                
                
                
                this.start();
        }
        @Override
        public void run(){
               for(;;){
                    for(int i=1; i<=10;i++){
                        markRadius=i;
                        try{
                           Thread.sleep(100);
                        }
                        catch(InterruptedException ie){
                        //If this thread was intrrupted by nother thread 
                        }
                    }
                    for (int i=10; i>=1;i--){
                        markRadius=i;
                        try{
                           Thread.sleep(100);
                        }
                        catch(InterruptedException ie){
                        //If this thread was intrrupted by nother thread 
                        }
                    }
            }
        }
        
        public float getX(){
                return X;
        }
        
        public float getY(){
                return Y;
        }
        public int getMarkRadius(){
                return markRadius;
        }
        
        @Override
        public String toString() {
            
               return String.format("X: %10.4f Y:%10.4f",  X , Y);
        }
}
