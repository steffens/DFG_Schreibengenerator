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

        // Instance 2
        map = new HashMap<DataFieldName, String>();
        map.put(new DataFieldName("Proposer"), "GZ 21/1-0815");
        map.put(new DataFieldName("Proposalno"), "Kapitän Nemo");
        data.add(map);

        if (mergedOutput) {
            /*
             * This is a "poor man's" merge, which generates the mail merge results as a
             * single docx, and just hopes for the best. Images and hyperlinks should be ok.
             * But numbering will continue, as will footnotes/endnotes.
             * 
             * If your resulting documents aren't opening in Word, then you probably need
             * MergeDocx to perform the merge.
             */

            // How to treat the MERGEFIELD, in the output?
            org.docx4j.model.fields.merge.MailMerger.setMERGEFIELDInOutput(OutputField.KEEP_MERGEFIELD);

            // System.out.println(XmlUtils.marshaltoString(wordMLPackage.getMainDocumentPart().getJaxbElement(),
            // true, true));

            WordprocessingMLPackage output = org.docx4j.model.fields.merge.MailMerger
                    .getConsolidatedResultCrude(wordMLPackage, data, true);

            // System.out.println(XmlUtils.marshaltoString(output.getMainDocumentPart().getJaxbElement(),
            // true, true));

            output.save(new java.io.File(System.getProperty("user.dir") + "/Evaluation_Form_for_Cruise_Proposals_Ergebnis.docx"));

        } else {
            // Need to keep the MERGEFIELDs. If you don't, you'd have to clone the docx, and
            // perform the
            // merge on the clone. For how to clone, see the MailMerger code, method
            // getConsolidatedResultCrude
            org.docx4j.model.fields.merge.MailMerger.setMERGEFIELDInOutput(OutputField.KEEP_MERGEFIELD);

            int i = 1;
            for (Map<DataFieldName, String> thismap : data) {
                org.docx4j.model.fields.merge.MailMerger.performMerge(wordMLPackage, thismap, true);
                wordMLPackage
                        .save(new java.io.File(System.getProperty("user.dir") + "/OUT_FieldsMailMerge_" + i + ".docx"));
                i++;
            }
        }

    }

}
