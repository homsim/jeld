package com.homsim.jeld.data.camt;

import com.homsim.jeld.control.dataimport.UnknownDataFormatException;

/**
 * It has to be possible to do this shit more elegantly.
 */
public final class CamtAdapter {
    public static CamtDocument createAdapter(String namespace, Object document) throws UnknownDataFormatException {
        switch (namespace) {
            case "camt_052_001_01": return new Camt052_001_01Document((com.homsim.jeld.data.parse.camt_052_001_01.Document) document);
            case "camt_052_001_02": return new Camt052_001_02Document((com.homsim.jeld.data.parse.camt_052_001_02.Document) document);
            case "camt_052_001_03": return new Camt052_001_03Document((com.homsim.jeld.data.parse.camt_052_001_03.Document) document);
            case "camt_052_001_04": return new Camt052_001_04Document((com.homsim.jeld.data.parse.camt_052_001_04.Document) document);
            case "camt_052_001_05": return new Camt052_001_05Document((com.homsim.jeld.data.parse.camt_052_001_05.Document) document);
            case "camt_052_001_06": return new Camt052_001_06Document((com.homsim.jeld.data.parse.camt_052_001_06.Document) document);
            case "camt_052_001_07": return new Camt052_001_07Document((com.homsim.jeld.data.parse.camt_052_001_07.Document) document);
            case "camt_052_001_08": return new Camt052_001_08Document((com.homsim.jeld.data.parse.camt_052_001_08.Document) document);
            case "camt_052_001_09": return new Camt052_001_09Document((com.homsim.jeld.data.parse.camt_052_001_09.Document) document);
            case "camt_052_001_10": return new Camt052_001_10Document((com.homsim.jeld.data.parse.camt_052_001_10.Document) document);
            case "camt_052_001_11": return new Camt052_001_11Document((com.homsim.jeld.data.parse.camt_052_001_11.Document) document);
            case "camt_052_001_12": return new Camt052_001_12Document((com.homsim.jeld.data.parse.camt_052_001_12.Document) document);
            case "camt_052_001_13": return new Camt052_001_13Document((com.homsim.jeld.data.parse.camt_052_001_13.Document) document);
            default: throw new UnknownDataFormatException("CAMT format " + namespace.replace("_", ".") + " not supported.");
        }
    }
}
