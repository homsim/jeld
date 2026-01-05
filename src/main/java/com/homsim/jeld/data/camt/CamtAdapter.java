package com.homsim.jeld.data.camt;

import com.homsim.jeld.control.dataimport.UnknownDataFormatException;

public final class CamtAdapter {
    public static CamtDocument createAdapter(String namespace, Object document) throws UnknownDataFormatException {
        switch (namespace) {
            case "camt_052_001_01": return new Camt052_001_01Document((com.homsim.jeld.data.parse.camt_052_001_01.Document) document);
            case "camt_052_001_08": return new Camt052_001_08Document((com.homsim.jeld.data.parse.camt_052_001_08.Document) document);
            default: throw new UnknownDataFormatException("CAMT format " + namespace.replace("_", ".") + " not supported.");
        }
    }
}
