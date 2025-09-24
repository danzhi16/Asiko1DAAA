package org.example;

public final class Csv {
    private Csv() {}
    public static String header() {
        return "algorithmName;runTimeNanos;counter;depth;allocationBytes;n";
    }
    public static String line(Metrics m) {
        return String.join(";",
                m.algorithmName(),
                Long.toString(m.runTimeNanos()),
                Long.toString(m.counter()),
                Integer.toString(m.depth()),
                Long.toString(m.allocationBytes()),
                Integer.toString(m.n())
        );
    }
}
