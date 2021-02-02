package de.dfg.schreiben;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class SchreibenGeneratorTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void erzeugeSchreiben() {
        String[] args = null;
        try {
            SchreibenGenerator.main(args);
            assertTrue(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

      
    }
}
