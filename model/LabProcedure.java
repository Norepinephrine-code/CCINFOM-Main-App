package model;
import java.math.BigDecimal;

public class LabProcedure {

    /*
     * The BigDecimal data type handles money systems better (used in banking)
     * since double and float use IEEE 754 binary which can't accurately represent decimals.
     */

    private int procedureId = -1;
    private String name;
    private String category;
    private BigDecimal cost;

    // No-arg constructor
    public LabProcedure() {}

    // Constructor without ID (for inserts)
    public LabProcedure(String name, String category, BigDecimal cost) {
        this.name = name;
        this.category = category;
        this.cost = cost;
    }

    // Full constructor (for retrieval/display)
    public LabProcedure(int procedureId, String name, String category, BigDecimal cost) {
        this.procedureId = procedureId;
        this.name = name;
        this.category = category;
        this.cost = cost;
    }

    // Getters
    public int getProcedureId() { return procedureId; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public BigDecimal getCost() { return cost; }

    // Setters
    public void setProcedureId(int procedureId) { this.procedureId = procedureId; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setCost(BigDecimal cost) { this.cost = cost; }

    // Check if inserted in DB
    public boolean isPersisted() {
        return procedureId > 0;
    }

    // For display/logging
    @Override
    public String toString() {
        return procedureId + " | " + name + " | " + category + " | ₱" + cost.setScale(2);
    }
}
