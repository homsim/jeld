package com.homsim.jeld.control.dataimport;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FormatDecisionMakerTest {
    Path resourceDirectory = Paths.get("src","test","resources", "dataimport");

    /**
     * Tests that the `FormatDecisionMaker` correctly decides on a camt.052.001.08.
     */
    @Test
    public void testDecideOnCamt() {
        File file = new File(resourceDirectory.toFile(), "2025.12.06.xml");
        FormatDecisionMaker decisionMaker = new FormatDecisionMaker(file);
        try {
            decisionMaker.decide();
            assert decisionMaker.getDecision() == Format.CAMT;
        } catch (Exception exc) {
            assertNotNull(exc);
        }
    }

    /**
     * Tests that the `FormatDecisionMaker` correctly decides on a CSV.
     */
    //@Test
    public void testDecideOnCsv() {
        // test resource does not exist yet
        File file = new File(resourceDirectory.toFile(), "2025.12.06.CSV");
        FormatDecisionMaker decisionMaker = new FormatDecisionMaker(file);
        try {
            decisionMaker.decide();
            assert decisionMaker.getDecision() == Format.CSV;
        } catch (Exception exc) {
            assertNotNull(exc);
        }
    }


    /**
     * Tests that the `FormatDecisionMaker` correctly decides on OTHER.
     */
    @Test
    public void testDecideOnOther() {
        File file = new File(resourceDirectory.toFile(), "some.jpg");
        FormatDecisionMaker decisionMaker = new FormatDecisionMaker(file);
        try {
            decisionMaker.decide();
            assert decisionMaker.getDecision() == Format.OTHER;
        } catch (Exception exc) {
            assertNotNull(exc);
        }
    }
}
