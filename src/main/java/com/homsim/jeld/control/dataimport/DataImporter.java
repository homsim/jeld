package com.homsim.jeld.control.dataimport;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import com.google.inject.Inject;

/**
 * Import class to be used in order to persist user's finance data into the application.
 */
public final class DataImporter {
    private final CamtImporter camtImporter;
    private final CsvImporter csvImporter;

    @Inject
    public DataImporter(CamtImporter camtImporter, CsvImporter csvImporter) {
        this.camtImporter = camtImporter;
        this.csvImporter = csvImporter;
    }

    /**
     * Import data and persists it in the database.
     *
     * @param dataFile The file to import data from
     */
    public void importData(File dataFile) throws UnknownDataFormatException, IOException, XMLStreamException {
        FormatDecisionMaker formatDecisionMaker = new FormatDecisionMaker(dataFile);
        formatDecisionMaker.decide();

        switch (formatDecisionMaker.getDecision()) {
            case CSV ->
                    csvImporter.importCsvData(dataFile);
            case CAMT ->
                    camtImporter.importCamtData(dataFile);
            case OTHER ->
                    throw new UnknownDataFormatException(
                            "Unable to import file " + dataFile.getName() +
                                    ": Unknown format."
                    );
        }
    }

    /* todo: maybe also add a bulk import to process multiple files of same format at once. */
}
