package com.homsim.jeld.control.dataimport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.homsim.jeld.data.BankAccount;
import com.homsim.jeld.data.FinanceEntry;
import com.homsim.jeld.data.camt.CamtAdapter;
import com.homsim.jeld.data.camt.CamtDocument;
import com.homsim.jeld.service.DataImporterService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

/**
 * Import class for the CAMT format. Decides automatically on the version of the XML based on the document.
 */
@Singleton
public final class CamtImporter {
    private final DataImporterService dataImporterService;

    @Inject
    public CamtImporter(DataImporterService dataImporterService) {
        this.dataImporterService = dataImporterService;
    }

    /**
     * Import data from a CAMT-formatted XML file. (Relies heavily on reflection. In the future it might be better to implement adapters for each format through a common interface.)
     *
     * @param dataFile The file to import data from
     */
    public void importCamtData(File dataFile) throws UnknownDataFormatException, XMLStreamException {
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
            dataImporterService.saveImportedData(bankAccount, List.of(financeEntry));


        } catch (
                JAXBException exc) {
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
