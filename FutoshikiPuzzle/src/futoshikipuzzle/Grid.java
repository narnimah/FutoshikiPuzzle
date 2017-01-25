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
public class Grid {
    protected final int length;
    
      public Grid(int length)
    {
        if ((length < 1) || (length > 9)) {
            throw new IllegalArgumentException("Size must be 1 to 9: " + length);
        }
        
        this.length = length;
    }
     public int getLength()
    {
        return length;
    }
    
    
     public int idx(int column, int row)
    {
        if (column < 1 || column > length)
            throw new IllegalArgumentException("Bad column " + column);

        if (row < 1 || row > length)
            throw new IllegalArgumentException("Bad row " + row);

        return idxInternal(column, row);
    }
       protected int idxInternal(int column, int row)
    {
        return (row - 1) * length + (column - 1);
    }
    
}
