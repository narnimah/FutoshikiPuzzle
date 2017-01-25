/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikipuzzle;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author Nikitha Mahesh
 */
public class FutoshikiPuzzle {
//Futoshiki f = new Futoshiki();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       Futoshiki f = new Futoshiki();

       
       /* int[][] sample = {
                {5, 1, 4, 3, 2},
                {3, 2, 5, 1, 4},
                {4, 5, 3, 2, 1},
                {2, 4, 1, 5, 3},
                {1, 3, 2, 4, 5}
        };cdcd
                
        for (int row = 1; row <= 5; row++) {
            for (int column = 1; column <= 5; column++) {
                f.set(column, row, sample[row - 1][column - 1]);
            }
        }*/
        
        /* Include rules */
       
        f.addGtRule(2, 2, 2, 1);
        f.addGtRule(4, 1, 4, 2);
       // f.addGtRule(2, 3, 3, 3);
        f.addGtRule(3, 3, 4, 3);
        f.addGtRule(1, 3, 1, 4);
        f.addGtRule(1, 4, 1, 5);
        f.addGtRule(2, 4, 2, 5);
        f.addGtRule(3, 5, 3, 4);
        f.addGtRule(2, 5, 1, 5);
        f.addGtRule(2, 5, 3, 5);
        f.addGtRule(4, 5, 3, 5);
        f.addGtRule(5, 5, 4, 5); 
       
    
        SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
            
            FutoshikiPanel fp = new FutoshikiPanel();
                
           FutoshikiControls fc = new FutoshikiControls(fp);
          fp.setFutoshiki(f);
            JFrame jf = new JFrame();
               jf.getContentPane().setLayout(new BorderLayout());
        
                jf.add(fp, BorderLayout.CENTER);
                
               jf.add(fc.getControlPanel(), BorderLayout.AFTER_LINE_ENDS);
                
                jf.pack();
                jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                
                jf.setVisible(true);
            
           
            }
        
        
        });
    }
    
}
