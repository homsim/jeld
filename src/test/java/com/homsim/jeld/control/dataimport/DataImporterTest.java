package com.homsim.jeld.control.dataimport;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.stream.XMLStreamException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataImporterTest {
    Path resourceDirectory = Paths.get("src", "test", "resources", "dataimport");

    @Mock
    EntityManagerFactory emf;
    @Mock
    EntityManager em;

    /**
     * Test that the import of a CAMT.052.001.08 works and is persisted.
     */
    //@Test
    public void testImportDataFromCamt52V8() {
        File file = new File(resourceDirectory.toFile(), "2025.12.06.xml");
        try {
            DataImporter.importData(file);
            /* todo:
            Setup a mock database and -connection
             */

        } catch (
                UnknownDataFormatException |
                IOException |
                XMLStreamException exc) {
            System.err.println(exc.getCause().toString());
            assertNotNull(exc);
        }
    }

    /**
     * Test that the import of an invalid file format leads to an UnknownDataFormatException.
     */
    @Test
    public void testImportDataFromInvalidDataFormat() {
        File file = new File(resourceDirectory.toFile(), "some.jpg");
        Exception exc = assertThrows(
                UnknownDataFormatException.class,
                () -> DataImporter.importData(file)
        );
        // this assertion heavily relies on the thrown exception message.
        assertEquals("Unable to import file some.jpg: Unknown format.",
                exc.getMessage());
    }
}
