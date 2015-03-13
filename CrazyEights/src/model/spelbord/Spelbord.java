package model.spelbord;

/**
 * het spelbord heeft een trekstapel en aflegstapel
 */
public class Spelbord {
    private Aflegstapel aflegstapel;
    private Trekstapel trekstapel;

    public Spelbord() {
        aflegstapel = new Aflegstapel();
        trekstapel = new Trekstapel();
    }

    public Trekstapel getTrekstapel() {
        return trekstapel;
    }

    public Aflegstapel getAflegstapel() {
        return aflegstapel;
    }
}
