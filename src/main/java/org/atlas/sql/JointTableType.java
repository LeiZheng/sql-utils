package org.atlas.sql;

public enum JointTableType {
    LEFT_JOIN {
        @Override
        public String toString() {
            return "LEFT JOIN";
        }
    },
    RIGHT_JOIN {
        @Override
        public String toString() {
            return "RIGHT JOIN";
        }
    },
    JOIN {
        @Override
        public String toString() {
            return "JOIN";
        }
    }
}
