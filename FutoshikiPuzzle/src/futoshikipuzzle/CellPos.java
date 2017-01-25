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
public final class CellPos {
 
    final int column, row;
    
    public CellPos(int cellColumn, int cellRow)
    {
        this.column = cellColumn;
        this.row = cellRow;
    }

    public String toString()
    {
        return "(" + column + "," + row + ")";
    }
    
    @Override
    public int hashCode()
    {
        return 31 * column + row;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof CellPos))
            return false;
        final CellPos other = (CellPos) obj;
        if (column != other.column)
            return false;
        if (row != other.row)
            return false;
        return true;
    }
}
