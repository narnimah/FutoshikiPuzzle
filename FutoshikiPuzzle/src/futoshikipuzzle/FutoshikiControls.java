/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikipuzzle;

import static com.sun.prism.paint.Gradient.PAD;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Nikitha Mahesh
 */
public class FutoshikiControls {
    
    
  private final FutoshikiPanel fp;
  //private final JLabel vlcLabel = new JLabel("(Unknown)");
  
 private final JButton solve = new JButton("Solve");
  private final JButton clear = new JButton("Clear");
   private final JComponent futoshikiControlPanel;
    //private final JComboBox size = new JComboBox(sizeNames());
  
     public FutoshikiControls(FutoshikiPanel futoshikiPanel)
    {
        this.fp = futoshikiPanel;
        
        futoshikiControlPanel = createControlPanel();
        
     

        solve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("solve method called");
             
              fp.solve();
                
            }
        });

        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
               
                fp.clearFutoshiki();
            }
        });

    }
     
 
    private static class ValidityLabelChanger implements PropertyChangeListener
    {
        private final JLabel label;
        
        public ValidityLabelChanger(JLabel label)
        {
            this.label = label;
        }
        
        public void propertyChange(PropertyChangeEvent evt)
        {
            if (evt.getPropertyName().equals("futoshiki.valid")) {
                setValid(Boolean.TRUE.equals(evt.getNewValue()));
            }
        }
        
        void setValid(boolean v)
        {
            if (v) {
                label.setText("Valid");
                label.setForeground(null);
            } else {
                label.setText("Invalid");
                label.setForeground(Color.RED);
            }
        }
    }
      private JComponent createControlPanel()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(PAD, PAD, PAD, PAD);

        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(createStatusPanel(), gbc);

          gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(solve, gbc);
        //panel.add(undo, gbc);
        
        gbc.gridy = 2;
        panel.add(clear, gbc);
        //panel.add(editButton, gbc);
      
       
        
       /* gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(size, gbc);*/
        
        return panel;
    }
      
          private JComponent createStatusPanel()
    {
        Box b = Box.createHorizontalBox();
        
        Border bdr = BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
                        " Menu"),
                BorderFactory.createEmptyBorder(PAD, PAD, PAD, PAD));
        b.setBorder(bdr);

        b.add(Box.createHorizontalGlue());
//        b.add(vlcLabel);
        b.add(Box.createHorizontalGlue());
        return b;
    }
    
      
      
      
      
          public JComponent getControlPanel()
    {
        return futoshikiControlPanel;
    }
         private static String[] sizeNames()
    {
        String[] sa = new String[9];
        for (int s = 1; s <= sa.length; s++) {
            sa[s - 1] = s + "x" + s;
        }
        return sa;
    }
}
