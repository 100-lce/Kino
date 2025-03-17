import java.io.Serializable;
import java.util.HashMap;

public class Seans implements Serializable {
     String tytul, godzina;
     public static enum DzienTygodnia {PONIEDZIALEK, WTOREK, SRODA, CZWARTEK, PIATEK, SOBOTA, NIEDZIELA};
     DzienTygodnia dzien;
     int ograniczenieWiekowe;
     HashMap<Character, HashMap<Integer, Boolean>> liczbaMiejsc;

    public Seans(String tytul, String godzina, DzienTygodnia dzien, int ograniczenieWiekowe, HashMap<Character, HashMap<Integer, Boolean>> liczbaMiejsc) {
        this.tytul = tytul;
        this.godzina = godzina;
        this.dzien = dzien;
        this.ograniczenieWiekowe = ograniczenieWiekowe;
        this.liczbaMiejsc = liczbaMiejsc;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getGodzina() {
        return godzina;
    }

    public void setGodzina(String godzina) {
        this.godzina = godzina;
    }

    public DzienTygodnia getDzien() {
        return dzien;
    }

    public void setDzien(DzienTygodnia dzien) {
        this.dzien = dzien;
    }

    public int getOgraniczenieWiekowe() {
        return ograniczenieWiekowe;
    }

    public void setOgraniczenieWiekowe(int ograniczenieWiekowe) {
        this.ograniczenieWiekowe = ograniczenieWiekowe;
    }

    public HashMap<Character, HashMap<Integer, Boolean>> getLiczbaMiejsc() {
        return liczbaMiejsc;
    }

    public void setLiczbaMiejsc(HashMap<Character, HashMap<Integer, Boolean>> liczbaMiejsc) {
        this.liczbaMiejsc = liczbaMiejsc;
    }

    @Override
    public String toString() {
        return tytul + " " + godzina + " " + dzien;
    }
}
