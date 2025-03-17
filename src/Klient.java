import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Klient {
    private String nazwisko, imie, email, telefon, seans;
    List<HashMap<Character, Integer>> miejsca;

    public Klient() {
        miejsca = new ArrayList<>();
    }

    public Klient(String nazwisko, String imie, String email, String telefon, String seans, List<HashMap<Character, Integer>> miejsca) {
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.email = email;
        this.telefon = telefon;
        this.seans = seans;
        this.miejsca = miejsca;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getSeans() {
        return seans;
    }

    public void setSeans(String seans) {
        this.seans = seans;
    }

    public List<HashMap<Character, Integer>> getMiejsca() {
        return miejsca;
    }

    public void setMiejsca(List<HashMap<Character, Integer>> miejsca) {
        this.miejsca = miejsca;
    }

    public void addMiejsce(HashMap<Character, Integer> miejsce) {
        miejsca.add(miejsce);
    }

    @Override
    public String toString() {
        return "Klient{" +
                "nazwisko='" + nazwisko + '\'' +
                ", imie='" + imie + '\'' +
                ", email='" + email + '\'' +
                ", telefon='" + telefon + '\'' +
                ", seans='" + seans + '\'' +
                ", ZarezerwowaneMiejsce=" + miejsca +
                '}';
    }
}
