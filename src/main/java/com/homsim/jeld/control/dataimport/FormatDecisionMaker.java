package com.homsim.jeld.control.dataimport;

import java.io.File;
import java.io.IOException;

import lombok.Getter;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;

/**
 * Decides which high-level data-format a given data-file is in.
 */
public class FormatDecisionMaker {
    private final File dataFile;
    private final TikaConfig tikaConfig;

    @Getter
    private Format decision;

    public FormatDecisionMaker(File dataFile) {
        this.dataFile = dataFile;

        try {
            this.tikaConfig = new TikaConfig();
        } catch (
                IOException |
                TikaException exc) {
            throw new AssertionError(exc.getMessage() + ". AFAIK there is no way these exceptions are thrown, as long as I do not read in a config-file");
        }
    }

    /**
     * Sets `decision` based on `file`.
     */
    public void decide() throws IOException {
        Metadata metadata = new Metadata();
        String type = String.valueOf(this.tikaConfig.getDetector().detect(
                TikaInputStream.get(this.dataFile.toPath(), metadata), metadata));

        switch (type) {
            case "text/csv" ->
                    this.decision = Format.CSV;
            case "application/xml" ->
                    this.decision = Format.CAMT;
            default ->
                    this.decision = Format.OTHER;
        }
    }
}
