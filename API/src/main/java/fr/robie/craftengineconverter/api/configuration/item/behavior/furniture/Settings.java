package fr.robie.craftengineconverter.api.configuration.item.behavior.furniture;

public class Settings {
    private final String item;
    private Integer hitTimes;
    private String breakSound;
    private String placeSound;
    private String hitSound;

    public Settings(String item) { this.item = item; }

    public String getItem() { return this.item; }
    public String getBreakSound() { return this.breakSound; }
    public void setBreakSound(String breakSound) { this.breakSound = breakSound; }
    public String getPlaceSound() { return this.placeSound; }
    public void setPlaceSound(String placeSound) { this.placeSound = placeSound; }
    public String getHitSound() { return this.hitSound; }
    public void setHitSound(String hitSound) { this.hitSound = hitSound; }
    public Integer getHitTimes() { return this.hitTimes; }
    public void setHitTimes(Integer hitTimes) { this.hitTimes = hitTimes; }
}
