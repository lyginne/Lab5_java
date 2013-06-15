public class Outline{
        
        private int R;
        
        Outline(int R){
                this.R=R;
        }
        
        private boolean containsInFirstQuater(float X, float Y){
                return Math.sqrt(X*X+Y*Y)<=R&&X>=0&&Y>=0;
        }
        
        private boolean containsInSecondQuater(float X, float Y){
                return X>=Y-R&&X<=0&&Y>=0;
        }
        
        private boolean containsInThirdQuater(float X, float Y){
                return false;
        }
        
        private boolean containsInFourthQuater(float X, float Y){
                return X>=0&&X<=R&&Y>=-R&&Y<=0;
        }
        public boolean contains(float X, float Y){
            return containsInFirstQuater(X,Y)
                        ||containsInSecondQuater(X,Y)
                        ||containsInThirdQuater(X,Y)
                        ||containsInFourthQuater(X,Y);
        }
        
//        public boolean contains(Mark mark){
//
//                float X=mark.getX();
//                float Y=mark.getY();
//
//                return containsInFirstQuater(X,Y)
//                        ||containsInSecondQuater(X,Y)
//                        ||containsInThirdQuater(X,Y)
//                        ||containsInFourthQuater(X,Y);
//        }
}

