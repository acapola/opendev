package uk.co.nimp;

import java.util.ArrayList;
import java.util.List;

public class GenericCounter {
    final List<Integer> upperBounds = new ArrayList<Integer>();
    final List<Integer> count = new ArrayList<Integer>();
    final int length;
    public GenericCounter(List<Integer> upperBounds){
        length = upperBounds.size();
        this.upperBounds.addAll(upperBounds);
        reset();
    }
    public List<Integer> reset(){
        count.clear();
        for(int i=0;i<length;i++) count.add(0);
        return count;
    }
    public boolean next(){
        for(int i=0;i<length;i++){
            int nextVal = count.get(i)+1;
            if(nextVal<upperBounds.get(i)){
                count.set(i,nextVal);
                return true;
            }
            count.set(i,0);
        }
        return false;
    }

    public List<Integer> getCount() {
        return count;
    }

    public int getLength() {
        return length;
    }
}
