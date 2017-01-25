/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikipuzzle;

/**
 *
 * @author Nikitha Mahesh
 */
public final class GtRule {
       final int columnA, rowA, columnB, rowB;
    
    public GtRule(int columnA, int rowA, int columnB, int rowB)
    {
        this.columnA = columnA;
        this.rowA = rowA;
        this.columnB = columnB;
        this.rowB = rowB;
    } 
      public GtRule getCanonPosForm()
    {
        if (rowB < rowA || (rowB == rowA && columnB < columnA)) {
            return new GtRule(columnB, rowB, columnA, rowA);
        } else {
            return this;
        }
    }
       public int getGreaterColumn()
    {
        return columnA;
    }

    
    public int getGreaterRow()
    {
        return rowA;
    }
    
    public int getLesserColumn()
    {
        return columnB;
    }
    
    public int getLesserRow()
    {
        return rowB;
    }
}
