package de.dfg.schreiben;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.fields.merge.MailMerger.OutputField;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

public class SchreibenGenerator {

    public static void main(String[] args) throws Exception {

        // Whether to create a single output docx, or a docx per Map of input data.
        // Note: If you only have 1 instance of input data, then you can just invoke
        // performMerge
        boolean mergedOutput = true;

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
                .load(new java.io.File(System.getProperty("user.dir") + "/resources/Evaluation_Form_for_Cruise_Proposals_Template.docx"));
 
        List<Map<DataFieldName, String>> data = new ArrayList<Map<DataFieldName, String>>();

        // Instance 1
        Map<DataFieldName, String> map = new HashMap<DataFieldName, String>();
        map.put(new DataFieldName("Proposer"), "GZ-21/1-0815");
        map.put(new DataFieldName("Proposalno"), "Kapitän Nemo");
         data.add(map);
        
        org.docx4j.model.fields.merge.MailMerger.setMERGEFIELDInOutput(OutputField.KEEP_MERGEFIELD);

        WordprocessingMLPackage output = org.docx4j.model.fields.merge.MailMerger
                    .getConsolidatedResultCrude(wordMLPackage, data, true);

   
        output.save(new java.io.File(System.getProperty("user.dir") + "/Evaluation_Form_for_Cruise_Proposals_Ergebnis.docx"));

   
    

    }

}
