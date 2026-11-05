package fr.robie.craftengineconverter.common.stats;

public class ConversionStatistics {
    private int converted = 0;
    private int failed = 0;

    public void incrementConverted(){
        this.converted++;
    }

    public void incrementFailed(){
        this.failed++;
    }

    public int getConverted() {
        return this.converted;
    }

    public int getFailed() {
        return this.failed;
    }

    public int getTotal(){
        return this.converted+this.failed;
    }

    public double getSuccessRate(){
        int total = this.getTotal();
        return total > 0 ? (this.converted * 100.0) / total : 0;
    }

    public void reset(){
        this.converted = 0;
        this.failed = 0;
    }
}
