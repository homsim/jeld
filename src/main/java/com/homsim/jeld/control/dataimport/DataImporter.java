package com.homsim.jeld.control.dataimport;

import java.io.File;
import java.io.IOException;

/**
 * Import utility class to be used in order to persist user's finance data into the application.
 */
public final class DataImporter {
    private DataImporter() {
    }

    /**
     * Import data and persists it in the database.
     *
     * @param dataFile The file to import data from
     */
    public static void importData(File dataFile) throws UnknownDataFormatException, IOException {
        FormatDecisionMaker formatDecisionMaker = new FormatDecisionMaker(dataFile);
        formatDecisionMaker.decide();

        switch (formatDecisionMaker.getDecision()) {
            case CSV ->
                    importCsvData(dataFile);
            case CAMT ->
                    importCamtData(dataFile);
            case OTHER ->
                    throw new UnknownDataFormatException(
                            "Unable to import file " + dataFile.getName() +
                                    ": Unknown format."
                    );
        }
    }

    /* maybe also add a bulk import to process multiple files of same format at once. */

    /**
     * Import data from a CAMT-formatted XML file.
     *
     * @param dataFile The file to import data from
     */
    private static void importCamtData(File dataFile) {

    }

    /**
     * Magically import data from a CSV-file. Not sure yet how, or if-at-all this is possible.
     *
     * @param dataFile The file to import data from
     */
    private static void importCsvData(File dataFile) {

    }
}
