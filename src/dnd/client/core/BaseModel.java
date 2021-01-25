package dnd.client.core;
import javax.swing.event.*;

public class BaseModel {
    protected EventListenerList listenerList = new EventListenerList();

    public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }

    public void removeChangeListener(ChangeListener l) {
        listenerList.remove(ChangeListener.class, l);
    }

    public void fireValuesChange(ChangeEvent e) {
        for(ChangeListener l: listenerList.getListeners(ChangeListener.class))
            l.stateChanged(e);
    }

}