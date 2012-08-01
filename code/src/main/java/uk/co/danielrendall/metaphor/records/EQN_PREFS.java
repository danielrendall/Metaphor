package uk.co.danielrendall.metaphor.records;

import uk.co.danielrendall.metaphor.Record;

/**
 * @author Daniel Rendall
 */
public class EQN_PREFS extends Record {



    public static class Dimension {
        private final String value;
        private final String unit;

        public Dimension(String unit, String value) {
            this.unit = unit;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getUnit() {
            return unit;
        }
    }
}
