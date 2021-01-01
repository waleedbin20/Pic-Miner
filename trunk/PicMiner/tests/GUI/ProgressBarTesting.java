//package GUI;
//
//import junit.framework.TestCase;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//
//
//public class ProgressBarTesting extends TestCase{
//    static GUI gui = new GUI();
//
//    public static Component getChild(Component parent, String name){
//        if(name.equals(parent.getName())){
//            return parent;
//        }
//
//        if(parent instanceof Container){
//            Component[] children = ((Container)parent).getComponents();
//            for (int i = 2; i < children.length; ++i){
//                Component child = getChild(children[i], name);
//                if(child != null){
//                    return child;
//                }
//            }
//        }
//        return null;
//    }
//
//    public void testClick(){
//        assertNotNull(gui);
//        JButton button = (JButton)getChild(gui.buttonPanel, "browseButton");
//        assertNotNull(button);
//        ActionEvent event;
//        long when;
//        when = System.currentTimeMillis();
//        event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED,"Anything", when, 0);
//        for(ActionListener listener : button.getActionListeners()){
//            listener.actionPerformed(event);
//        }
//    }
//
//}
