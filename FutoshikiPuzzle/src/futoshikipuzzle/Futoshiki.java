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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Futoshiki extends Grid {
        
    public  byte[] data;
      public byte[] finaldata;
    private Iterable<ValidatingRule> vraCache;
    private Iterable<GtRule> origRuleIterable;
    private Map<GtRule, ValidatingRule> rules;
    
    //private int length;
    
        public Futoshiki()
    {
        this(5);
    }
    
    public Futoshiki(int length)
    {
        super(length);
        this.data = new byte[length * length];
       
    }
    
    
        public void set(int column, int row, int v)
    {
       
        if (v < 1 || v > length )
            throw new IllegalArgumentException("Bad cell value " + v);
        
        data[idx(column, row)] = (byte) v;

    }
        
    
        
   private Map<GtRule, ValidatingRule> ruleMap()
    {
        if (rules == null) {
            rules = new HashMap<GtRule, ValidatingRule>();
            if (vraCache != null) {
                for (ValidatingRule vr : vraCache) {
                    rules.put(vr.getOrigRule().getCanonPosForm(), vr);
                }
            }
        }
        
        return rules;
    }    
        
          
        
   private static class ValidatingRule
    {
        private final int idxA, idxB;
        private final GtRule origRule;
        
        ValidatingRule(GtRule gtr, Futoshiki f)
        {
            idxA = f.idx(gtr.getGreaterColumn(), gtr.getGreaterRow());
            idxB = f.idx(gtr.getLesserColumn(), gtr.getLesserRow());
            origRule = gtr;
        }
        
        boolean isValid(Futoshiki f)
        {
            /* Is either cell blank? */
            if (f.data[idxA] == 0 || f.data[idxB] == 0)
                return true;

            return f.data[idxA] > f.data[idxB];
        }
        
        GtRule getOrigRule()
        {
            return origRule;
        }
    } 
         public byte get(int column, int row)
    {
        return data[idx(column, row)];
    }
         
             public Iterable<? extends GtRule> getRules()
    {
        origRuleIterable = null;
        if (origRuleIterable == null || vraCache == null) {
            origRuleIterable = new OrigRuleIterable(getValidatingRules());
        }
        return origRuleIterable;
    }
        
     public void addGtRule(int columnA, int rowA, int columnB, int rowB)
    {
        GtRule newRule = new GtRule(columnA, rowA, columnB, rowB);

        GtRule k = newRule.getCanonPosForm();
        
        ruleMap().put(k, new ValidatingRule(newRule, this));
        vraCache = null;
        origRuleIterable = null;
    }  
         private Iterable<ValidatingRule> getValidatingRules()
    {
        if (vraCache == null) {
            if (rules != null) {
                vraCache = new ArrayList<ValidatingRule>(rules.values());
            } else {
                vraCache = Collections.emptyList();
            }
            
        }
        return vraCache;
    }
   
             public Futoshiki clone()
    {
        Futoshiki f = new Futoshiki(length);
        System.arraycopy(data, 0, f.data, 0, data.length);
        f.vraCache = getValidatingRules();
        f.origRuleIterable = origRuleIterable;
        return f;
    }
        public void clear(int column, int row)
    {
        data[idx(column, row)] = 0;
    }

        public GtRule getRule(GtRule ruleKey)
    {
        GtRule k = ruleKey.getCanonPosForm();

        ValidatingRule r = ruleMap().get(k);
        if (r != null) {
            return r.getOrigRule();
        } else {
            return null;
        }
    }
               
          public void removeRule(GtRule ruleKey)
    {
        GtRule k = ruleKey.getCanonPosForm();
        
        ruleMap().remove(k);
        vraCache = null;
        origRuleIterable = null;
    }  
          
       public Collection<CellPos> blankCells()
    {
        Collection<CellPos> blank = new ArrayList<CellPos>(length * length);
        
        for (int row = 1; row <= length; row++) {
            for (int column = 1; column <= length; column++) {
                if (data[idxInternal(column, row)] == 0) {
                    blank.add(new CellPos(column, row));
                }
            }
        }
        
        return blank;
    }    
          
          
          
          
          
     public boolean isValid()
    {
        /* Check for duplicates */
        
        BitSet columnMask = new BitSet(length * length);

        assert (length * length) <= columnMask.size();
        
        for (int row = 1; row <= length; row++) {
            int rowMask = 0;

            assert length < 32;
            
            for (int column = 1; column <= length; column++) {
                int v = data[idxInternal(column, row)];
                if (v == 0)
                    continue;
                
                int bit = (1 << v);
                if ((rowMask & bit) != 0)
                    return false;
                
                int columnContainsValueBit = (column - 1) * length + v;
                
                if (columnMask.get(columnContainsValueBit))
                    return false;
                
                rowMask |= bit;
                columnMask.set(columnContainsValueBit);
            }
        }
        
        /* Obey rules */
        for (ValidatingRule r : getValidatingRules()) {
            if (!r.isValid(this))
                return false;
        }
        
        /* No violations, so valid */
        return true;
    }    
        
        
        
      
             private static class OrigRuleIterable implements Iterable<GtRule>
    {
        private final Iterable<ValidatingRule> vra;
        
        private OrigRuleIterable(Iterable<ValidatingRule> vra)
        {
            this.vra = vra;
        }
        
        public Iterator<GtRule> iterator()
        {
            final Iterator<ValidatingRule> i = vra.iterator();
            
            return new Iterator<GtRule>() {
                public boolean hasNext()
                {
                    return i.hasNext();
                }

                public GtRule next()
                {
                    return i.next().getOrigRule();
                }
                
                public void remove()
                {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }
}
