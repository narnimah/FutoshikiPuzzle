/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikipuzzle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Nikitha Mahesh
 */
class FutoshikiPanel extends JPanel implements FocusListener{
      private static final String TITLE = "Futoshiki Solver";
 
    private static final int MAX_UNDO = 10;
    
    public Futoshiki futoshiki = new Futoshiki();
    private Boolean valid;
    private Boolean undoable;
    private Set<CellPos> solvedCells = new HashSet<CellPos>();
      JPanel gridPanel = new JPanel();
         JPanel buttonPanel=new JPanel();
    private CellPos selected;
    public FutoshikiPuzzle f1= new FutoshikiPuzzle();
    private List<Futoshiki> undoRecord = new ArrayList<Futoshiki>();
    
    public FutoshikiPanel()
    {
        addMouseListener(new ClickListener());
        addKeyListener(new TypedNumberListener());
        setFocusable(true);
        
        addFocusListener(this);
    }
     public void focusGained(FocusEvent e)
    {
        repaint();
    }
    
    public void focusLost(FocusEvent e)
    {
        repaint();
    }
    
    @Override
    public Dimension getPreferredSize()
    {
        int maxWidth = 0,
            maxHeight = 0;
        
        Font f = getFont();

        FontMetrics fm = getFontMetrics(f);

        Graphics g = getGraphics();
        
        /* Get a bounding box for all digits */
        for (int i = 0; i < 10; i++) {
            String s = Integer.toString(i);
            Rectangle bounds = fm.getStringBounds(s, g).getBounds();

            maxWidth = Math.max(maxWidth, bounds.width);
            maxHeight = Math.max(maxHeight, bounds.height);
        }
        
        int m = Math.max(maxWidth, maxHeight);
        
        int l = (m + 10) * (futoshiki.getLength() * 3 + 1);
        
        return new Dimension(l, l);
    }
        public void clearFutoshiki()
    {

        
        setFutoshiki(new Futoshiki(futoshiki.getLength()));
        
    }
              public void solve()
    {
 //futoshiki.set(1, 1, 5);
            //repaint();   
               try{
                    String dir=System.getProperty("user.dir");
                    //System.out.println("dir "+dir);
        //  File file =new File(dir+"//input.sm");
    	
          //if(!file.exists()){
    	 //	file.createNewFile();
    	  //}
          
          FileOutputStream file=new FileOutputStream(dir+"//input.sm");
                                                                              
 
        /*  FileWriter fw = new FileWriter(file,true);
    	  BufferedWriter bw = new BufferedWriter(fw);
    	  PrintWriter pw = new PrintWriter(bw);
          //This will add a new line to the file content
    	  pw.println("");
          
          */

           int count=0;
int a[][]=new int[6][6];
        for(int i=1;i<=5;i++)
        {
            for(int j=1;j<=5;j++)
            {
                if(count==futoshiki.data.length) break;
            a[i][j]=futoshiki.data[count];
            if(a[i][j]==0)
                continue;
            String x="hint("+i+","+j+","+a[i][j]+").\n";
             byte[] b=x.getBytes();
                                               try {
                                                                                                file.write(b);
                                                                                               
                                                                                } catch (IOException e1) {
                                                                                                // TODO Auto-generated catch block
                                                                                                e1.printStackTrace();
                                                                                }
            count++;
            }
        }
       /* for(int i=1;i<=5;i++)
        {
            for(int j=1;j<=5;j++)
            {
                if(a[i][j] == 0) {
           System.out.println("cells are not completely filled");
            
                JOptionPane.showMessageDialog(gridPanel,
							    "Please enter a digit between 1 to 5 in the empty cells",
							    "Cells are not completely filled",
							    JOptionPane.WARNING_MESSAGE);
						System.exit(0);
                }
            }
        }*/

    	 //pw.close();
          
String FullDir=dir+"\\futoshikipuzzle\\OutputFiles";
	  System.out.println("Data successfully appended at the end of file"+FullDir);

Process p = Runtime.getRuntime().exec("cmd /C clingo input.sm futoshiki1.sm | mkatoms > output.sm");

     p.waitFor();
      

       
InputStream stdin = p.getInputStream();
InputStreamReader isr = new InputStreamReader(stdin);
BufferedReader br = new BufferedReader(isr);

int exitVal = p.waitFor();
System.out.println("Process exitValue: " + exitVal);
               }catch(IOException ioe){
    	   System.out.println("Exception occurred:");
    	   ioe.printStackTrace();
      }   catch (InterruptedException ex) {
              Logger.getLogger(FutoshikiPanel.class.getName()).log(Level.SEVERE, null, ex);
          }
       // FutoshikiPanel fileReaderService = new FutoshikiPanel();
        performExecute("x");
        
    }

  
          public Futoshiki getFutoshiki()
    {
        return this.futoshiki.clone();
    }
      public void setFutoshikiSize(int size)
    {
        if (size != futoshiki.getLength()) {
            setFutoshiki(new Futoshiki(size));
        }
    }  
        
        
    @Override
    public void paint(Graphics origGfx)
    {
        Graphics2D g = (Graphics2D) origGfx;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        Dimension d = getSize();


            g.setColor(Color.LIGHT_GRAY);

        
        g.fillRect(0, 0, d.width, d.height);

        int px = d.width / (futoshiki.getLength() * 3 + 1),
            py = d.height / (futoshiki.getLength() * 3 + 1);
        
        
        Font f = getFont();

        f = f.deriveFont((float) py);
        
        g.setFont(f);
        FontMetrics fm = g.getFontMetrics();

        Font bf = g.getFont().deriveFont(Font.BOLD);
        
        /* Numbers */
        for (int row = 1; row <= futoshiki.getLength(); row++) {
            for (int column = 1; column <= futoshiki.getLength(); column++) {
                
                int x = (column * 3 - 2) * px,
                    y = (row * 3 - 2) * py;
                
                g.setColor(Color.WHITE);
                g.fillRect(x, y, px * 2, py * 2);

                boolean isSelected = hasFocus()
                        && selected != null
                        && (column == selected.column && row == selected.row);
                
                if (isSelected) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLACK);
                }
                
                g.drawRect(x, y, px * 2, py * 2);
                
                int v = futoshiki.get(column, row);
                EditState es;
                if (solvedCells.contains(new CellPos(column, row))) {
                    es = EditState.AUTOMATIC;
                } else {
                    es = EditState.DESIGNED;
                }
                
                if (v > 0) {
                    String s = Integer.toString(v);
                    
                    Rectangle2D sb = fm.getStringBounds(s, g);
                    
                    int cx = (column * 3 - 1) * px,
                        cy = (row * 3 - 1) * py;
                    
                    float tx = (float) (cx - sb.getCenterX()),
                        ty = (float) (cy - sb.getCenterY());

                    switch (es) {
                        case DESIGNED:
                            g.setColor(Color.BLACK);
                            g.setFont(bf);
                            break;

                        case AUTOMATIC:
                            g.setColor(Color.BLUE);
                            g.setFont(f);
                            break;
                    }
                    
                    if (isSelected) {
                        g.setColor(Color.RED);
                    }
                    
                    g.drawString(s, tx, ty);
                }
            }
        }

        final int LW = 3;
        GeneralPath gp = new GeneralPath();
        gp.moveTo(10, 40 - LW);
        gp.lineTo(40, 50 - LW);
        gp.lineTo(40, 50 + LW);
        gp.lineTo(10, 60 + LW);
        gp.lineTo(10, 60 - LW);
        gp.lineTo(30, 50);
        gp.lineTo(10, 40 + LW);
        gp.closePath();
        
        GeneralPath gp2 = new GeneralPath(gp);
        gp2.transform(AffineTransform.getScaleInstance(-1, 1));
        gp2.transform(AffineTransform.getTranslateInstance(50, 0));
        
        g.setColor(Color.BLACK);

        for (GtRule r : futoshiki.getRules()) {
            RulePos rp = rulePosition(r);
            if (rp == null)
                continue;
            
            AffineTransform t;
            
            if (rp.horizontal) {
                int x = (0 + rp.column * 3) * px,
                    y = (-2 + rp.row * 3) * py;
                
                t = AffineTransform.getTranslateInstance(x, y);
                t.scale(px / 50.0, (py * 2) / 100.0);
            } else {
                int x = (-2 + rp.column * 3) * px,
                    y = (0 + rp.row * 3) * py;
                
                t = AffineTransform.getTranslateInstance(x + px * 2, y);
                t.scale((px * 2) / 100.0, py / 50.0);
                t.rotate(Math.PI / 2.0);
            }
            
            g.setColor(Color.BLACK);
            
            AffineTransform ot = g.getTransform();
            g.transform(t);
            g.fill(rp.gt ? gp : gp2);
            g.setTransform(ot);
        }
    }
        public static RulePos rulePosition(GtRule r)
    {
        /* Horizontal rule? */
        if (r.rowA == r.rowB) {
            if (r.columnA + 1 == r.columnB) {
                return new RulePos(r.columnA, r.rowA, true, true);
            } else if (r.columnA == r.columnB + 1) {
                return new RulePos(r.columnB, r.rowA, true, false);
            } else {
                /* These are not adjacent squares */
                return null;
            }
            
        } else if (r.columnA == r.columnB) { // Vertical
            if (r.rowA + 1 == r.rowB) {
                return new RulePos(r.columnA, r.rowA, false, true);
            } else if (r.rowA == r.rowB + 1) {
                return new RulePos(r.columnA, r.rowB, false, false);
            } else {
                /* These are not adjacent squares */
                return null;
            }
        } else {
            /* Not a valid rule */
            return null;
        }
    }

    private void performExecute(String inputPattern) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   ArrayList<Integer[]> matches = new ArrayList<>();
   int[][] finaloutput = new int[6][6];
   int [] outArray=new int[25];
   int i=0;
    try{
            FileInputStream fileInputStream = new FileInputStream("output.sm"); /*change your file path here*/
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
            String inp="";
           while ((inp=bufferedReader.readLine()) != null) {
                if (inp.contains(inputPattern)) {
                    //String[] splits = strLine.split(","); /*splits matching line with 3 consecutive white spaces*/
                    StringBuffer strLine= new StringBuffer(inp);
                    strLine.deleteCharAt(strLine.indexOf("("));
                    strLine.deleteCharAt(strLine.indexOf(")"));
                    strLine.deleteCharAt(strLine.indexOf("x"));
                    String[] splitInput=strLine.toString().split(",");
                   // System.out.println("slpit string is"+splitInput[2]);
                    int xco=Integer.parseInt(splitInput[0]);
					int yco=Integer.parseInt(splitInput[1]);
					//String outputValue=value.substring(value.lastIndexOf(',')+1,value.lastIndexOf(')'));
					int pos=((xco-1)*5)+yco-1;
					
                  outArray[pos]=Integer.parseInt(splitInput[2]);
                  i++;
            
                }
                
                 else if(inp.contains("no models found")){
                     
						JOptionPane.showMessageDialog(gridPanel,
							    "No solution exist for given set.",
							    "Futoshiki Warning",
							    JOptionPane.WARNING_MESSAGE);
						System.exit(0);
                     
                   }
            }
          JOptionPane.showMessageDialog(gridPanel,
							    "Congagulations!!",
							    "Puzzle Solved",
							    JOptionPane.WARNING_MESSAGE);
            
                        dataInputStream.close();
// System.out.println("size of final output is"+outArray.length);
   // display();
   Futoshiki f=getFutoshiki();
             int count1=0;
int a1[][]=new int[6][6];
        for(int i1=1;i1<=5;i1++)
        {
            for(int j1=1;j1<=5;j1++)
            {
                if(count1==outArray.length) break;
            a1[i1][j1]=outArray[count1];
            //System.out.println("data value in array\n"+a1[i1][j1]);
            f.set(j1, i1, a1[i1][j1]);
            count1++;
            }
        }
       
       // FutoshikiPanel fp = new FutoshikiPanel();
        setFutoshiki(f);
      //  repaint();
       // fp.re
       //   JPanel.display();
         // myJFrame.getContentPane().removeAll()
//myJFrame.getContentPane().invalidate()

//JFrame.getContentPane().add(futoshiki);
//myJFrame.getContentPane().revalidate()
       // changed();
        //display(futoshiki);   
     } catch (Exception e) {
            e.printStackTrace();
        }
   //FutoshikiPanel.super.repaint();
    //FutoshikiPanel.super.revalidate();
    
    }

    private void display(Futoshiki f) {
 setFutoshikiDisplay(new Futoshiki(futoshiki.getLength()));    
    }
        public void setFutoshikiDisplay(Futoshiki f)
    {
        int previousLength = futoshiki.getLength();
        
         writeSolutionCells(futoshiki);
        recordHistory();
        this.futoshiki = f.clone();
        
        changedSize(previousLength, futoshiki.getLength());
        changed();
    }  
      private void writeSolutionCells(Futoshiki f)
    {
     for (int row = 1; row <= 5; row++) {
            for (int column = 1; column <= 5; column++) {
//                f.set(column, row, a1[row - 1][column - 1]);
            }
        }

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
    }
          public static class RulePos
    {
        public final int row, column;
        public final boolean horizontal, gt;
        
        private RulePos(int column, int row, boolean horizontal, boolean gt)
        {
            this.row = row;
            this.column = column;
            this.horizontal = horizontal;
            this.gt = gt;
        }
    }  
    public void setFutoshiki(Futoshiki f)
    {
        
        int previousLength = futoshiki.getLength();
        
       clearSolutionCells();
        f.addGtRule(2, 2, 2, 1);
        f.addGtRule(4, 1, 4, 2);
        //f.addGtRule(2, 3, 3, 3);
        f.addGtRule(3, 3, 4, 3);
        f.addGtRule(1, 3, 1, 4);
        f.addGtRule(1, 4, 1, 5);
        f.addGtRule(2, 4, 2, 5);
        f.addGtRule(3, 5, 3, 4);
        f.addGtRule(2, 5, 1, 5);
        f.addGtRule(2, 5, 3, 5);
        f.addGtRule(4, 5, 3, 5);
        f.addGtRule(5, 5, 4, 5);
         recordHistory();
      
        this.futoshiki = f.clone();
        
       changedSize(previousLength, futoshiki.getLength());
       changed();
    }
    private void clearSolutionCells()
    {
        /* After a change, clear all transient solution cells */

        for (CellPos ecp : solvedCells) {
            //System.out.println("solved cell value"+ecp.column +ecp.row);
            futoshiki.clear(ecp.column, ecp.row);
        }
        
        solvedCells.clear();
    }
    private void changedSize(int oldSize, int newSize)
    {
        if (oldSize != newSize) {
            firePropertyChange("futoshiki.size", oldSize, newSize);
            invalidate();
        }
    }
        private void changed()
    {
        Boolean wasValid = valid;
        valid = Boolean.valueOf(futoshiki.isValid());
        firePropertyChange("futoshiki.valid", wasValid, valid);
        
        Boolean wasUndoable = undoable;
        undoable = Boolean.valueOf(isUndoable());
        firePropertyChange("futoshiki.undoable", wasUndoable, undoable);
        
        repaint();
    }
        
       private boolean isUndoable()
    {
        return !solvedCells.isEmpty() || !undoRecord.isEmpty();
    }  
        
        
       private void recordHistory()
    {
        if (undoRecord.size() >= MAX_UNDO) {
            undoRecord.remove(0);
        }
        
        undoRecord.add(futoshiki.clone());
    }
       
       
           public void cellClicked(int column, int row)
    {
        selected = new CellPos(column, row);
        requestFocus();
        repaint();
    }
     
           

               public void ruleClicked(RulePos rp)
    {
        clearSolutionCells();
        recordHistory();
        
        GtRule rk;
        
        if (rp.horizontal) {
            rk = new GtRule(rp.column, rp.row, rp.column + 1, rp.row);
        } else {
            rk = new GtRule(rp.column, rp.row, rp.column, rp.row + 1);
        }
        
        rk = rk.getCanonPosForm();
        
        GtRule rule = futoshiki.getRule(rk);
        
        if (rule == null) {
            futoshiki.addGtRule(rk.getGreaterColumn(), rk.getGreaterRow(),
                    rk.getLesserColumn(), rk.getLesserRow());
        } else if (rk.equals(rule)) {
            futoshiki.addGtRule(rk.getLesserColumn(), rk.getLesserRow(),
                    rk.getGreaterColumn(), rk.getGreaterRow());
        } else {
            futoshiki.removeRule(rk);
        }
        
        changed();
    }
           
           
       private class ClickListener extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            Point p = e.getPoint();
            
            Dimension d = getSize();

            int px = d.width / (futoshiki.getLength() * 3 + 1),
            py = d.height / (futoshiki.getLength() * 3 + 1);
            
            int x = p.x / px,
                y = p.y / py;
            
            /* Out of bounds in a way that messes up arithmetic? */
            if (x == 0 || y == 0) {
                return;
            }
            
            if ((x - 1) % 3 != 2) {
                int column = ((x - 1) / 3) + 1;

                if (column >= 1 && column <= futoshiki.getLength()) {
                    if ((y - 1) % 3 != 2) {
                        int row = ((y - 1) / 3) + 1;
                        
                        if (row >= 1 && row <= futoshiki.getLength()) {
                            cellClicked(column, row);
                        }
                    } else {
                        int rowAfter = ((y - 1) / 3) + 1;
                        
                        if (rowAfter >= 1 && rowAfter < futoshiki.getLength()) {
                            RulePos rp =
                                new RulePos(column, rowAfter, false, false);
                            ruleClicked(rp);
                        }
                    }
                }
            } else {
                int columnAfter = ((x - 1) / 3) + 1;
                if (columnAfter >= 1 && columnAfter < futoshiki.getLength()) {
                    if ((y - 1) % 3 != 2) {
                        int row = ((y - 1) / 3) + 1;
                        
                        if (row >= 1 && row <= futoshiki.getLength()) {
                            RulePos rp =
                                new RulePos(columnAfter, row, true, false);
                            ruleClicked(rp);
                        }
                    }
                }
            }
        }
    }
       
         public void numberCleared()
    {
        if (selected != null) {
            clearSolutionCells();
            recordHistory();
            
            futoshiki.clear(selected.column, selected.row);
            clearSolutionCells();
            changed();
        }
    }
         
            public void numberTyped(int n)
    {
        if (selected != null) {
            clearSolutionCells();
            recordHistory();

            if (n >= 0 && n <= futoshiki.getLength()) {
                futoshiki.set(selected.column, selected.row, n);
                changed();
            }
        }
    }
         private class TypedNumberListener extends KeyAdapter
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            char c = e.getKeyChar();
            
            int n = Character.digit(c, 10);
            if (n == 0) {
                numberCleared();
            } else if (n > 0) {
                numberTyped(n);
            }
        }
        
        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_DELETE
                || e.getKeyCode() == KeyEvent.VK_BACK_SPACE
                || e.getKeyCode() == KeyEvent.VK_SPACE)
            {
                numberCleared();
            } else if (e.getKeyCode() == KeyEvent.VK_C
                    && ((e.getModifiers() & Event.CTRL_MASK) != 0))
            {
                Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();

                Transferable trans =
                    new StringSelection(FutoshikiPrinter.toString(futoshiki));
                
                cb.setContents(trans, null);

            }
            
        }
    }  
         
           private enum EditState
    {
        DESIGNED,
//        MANUAL,
        AUTOMATIC;
    }
}
