package com.homsim.jeld.control.dataimport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.google.inject.Inject;
import com.homsim.jeld.data.BankAccount;
import com.homsim.jeld.data.FinanceEntry;
import com.homsim.jeld.data.camt.CamtAdapter;
import com.homsim.jeld.data.camt.CamtDocument;
import com.homsim.jeld.service.BankAccountService;
import com.homsim.jeld.service.FinanceEntryService;
import com.homsim.jeld.service.FinanceTimeSeriesService;
import com.homsim.jeld.service.SpendingService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

/**
 * Import utility class to be used in order to persist user's finance data into the application.
 */
public final class DataImporter {
    @Inject
    private BankAccountService bankAccountService;
    @Inject
    private FinanceEntryService financeEntryService;
    @Inject
    private FinanceTimeSeriesService financeTimeSeriesService;
    @Inject
    private SpendingService serviceService;

    private DataImporter() {
    }

    /**
     * Import data and persists it in the database.
     *
     * @param dataFile The file to import data from
     */
    public static void importData(File dataFile) throws UnknownDataFormatException, IOException, XMLStreamException {
        FormatDecisionMaker formatDecisionMaker = new FormatDecisionMaker(dataFile);
        formatDecisionMaker.decide();

        switch (formatDecisionMaker.getDecision()) {
            case CSV ->
                    CsvImporter.importCsvData(dataFile);
            case CAMT ->
                    CamtImporter.importCamtData(dataFile);
            case OTHER ->
                    throw new UnknownDataFormatException(
                            "Unable to import file " + dataFile.getName() +
                                    ": Unknown format."
                    );
        }
    }

    /* maybe also add a bulk import to process multiple files of same format at once. */

    /**
     * Import utility class for the CAMT format. Decides automatically on the version of the XML based on the document.
     */
    private static final class CamtImporter {

        private CamtImporter() {
        }

        /**
         * Import data from a CAMT-formatted XML file. (Relies heavily on reflection. In the future it might be better to implement adapters for each format through a common interface.)
         *
         * @param dataFile The file to import data from
         */
        private static void importCamtData(File dataFile) throws UnknownDataFormatException, XMLStreamException {
            final String basePackageName = "com.homsim.jeld.data.parse";
            final String namespace = detectNamespace(dataFile);

            try {
                String packageName = basePackageName + "." + namespace;
                JAXBContext jaxbContext = JAXBContext.newInstance(packageName);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                Object documentObj = unmarshaller.unmarshal(dataFile);
                Class<?> documentClass = Class.forName(packageName + ".Document");
                Object document = documentClass.cast(((JAXBElement<?>) documentObj).getValue());

                CamtDocument camtDocument = CamtAdapter.createAdapter(namespace, document);
                FinanceEntry financeEntry = camtDocument.parseFinanceEntry();
                BankAccount bankAccount = camtDocument.parseUserBankAccount();

                // todo: persist the data

            } catch (JAXBException exc) {
                throw new XMLStreamException("Failed to unmarshal XML file: " + dataFile.getAbsolutePath(), exc);
            } catch (ClassNotFoundException exc) {
                throw new UnknownDataFormatException(
                        "CAMT format " + namespace.replace("_", ".")
                                + " not supported.");
            }
        }

        /**
         * Detects the namespace from the XML root element
         *
         * @param dataFile The file to import data from
         */
        private static String detectNamespace(File dataFile) throws XMLStreamException {
            XMLInputFactory factory = XMLInputFactory.newInstance();

            try (FileInputStream fis = new FileInputStream(dataFile)) {
                XMLStreamReader reader = factory.createXMLStreamReader(fis);

                try {
                    while (reader.hasNext()) {
                        if (reader.isStartElement()) {
                            String namespaceURI = reader.getNamespaceURI();
                            return Arrays.stream(namespaceURI.split(":")).toList().getLast().replace(".", "_");
                        }
                        reader.next();
                    }
                    throw new XMLStreamException("No root element found");
                } finally {
                    reader.close();
                }
            } catch (
                    IOException e) {
                throw new XMLStreamException("Failed to read file: " + dataFile.getAbsolutePath(), e);
            }
        }
    }

    /**
     * Import utility class for the CSV format.
     */
    private static final class CsvImporter {
        private CsvImporter() {
        }

        /**
         * Magically import data from a CSV-file. Not sure yet how, or if-at-all this is possible.
         *
         * @param dataFile The file to import data from
         */
        private static void importCsvData(File dataFile) {
            // todo
        }
    }
}
