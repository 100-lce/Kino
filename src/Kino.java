import com.thoughtworks.xstream.XStream;

import java.io.*;
import java.util.*;

public class Kino {
    private final String PLIK_SEANSE = "seanse.ser";
    private final String PLIK_KLIENCI = "klienci.xml";
    List<Seans> dostepneSeanse;
    List<Klient> klienci;

    public Kino() {
        System.out.println("Witaj w kinie!");
        this.klienci = odczytajKlientow();
        this.dostepneSeanse = odczytajSeanse();
    }

    public void pokazKlientow() {
        for(Klient klient : klienci) {
            System.out.println(klient);
        }
    }

    private List<Klient> odczytajKlientow() {
        List<Klient> klienci = new ArrayList<>();
        XStream xStream = new XStream();
        xStream.allowTypes(new Class[]{Klient.class});
        xStream.alias("klient", Klient.class);
        xStream.alias("klienci", List.class);

        try {
            FileReader reader = new FileReader(PLIK_KLIENCI);
            klienci = (List<Klient>) xStream.fromXML(reader);
            reader.close();

        } catch (IOException e) {
            return klienci;
        }


        return klienci;
    }

    private void zapiszKlientow(List<Klient> klienci) {
        XStream xStream = new XStream();
        xStream.alias("klient", Klient.class);
        xStream.alias("klienci", List.class);

        try (FileWriter writer = new FileWriter(PLIK_KLIENCI)) {
            writer.write(xStream.toXML(klienci));
        } catch (Exception e) {
            System.out.println("Blad zapisywania klientow!");
        }
    }

    private List<Seans> odczytajSeanse() {
        List<Seans> odczytaneSeanse = new ArrayList<>();

        try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(PLIK_SEANSE))) {
            while (true) {
                try {
                    Seans seans = (Seans) read.readObject();
                    odczytaneSeanse.add(seans);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Bląd wczytywania seansów. Tworze przykladowe seanse");
            List<Seans> przykladoweSeanse = new ArrayList<>();
            przykladoweSeanse.add(new Seans("Ogniem i Mieczem", "13:00,", Seans.DzienTygodnia.CZWARTEK, 12, stworzMapeMiejsc()));
            przykladoweSeanse.add(new Seans("Listy do M 5", "12:00,", Seans.DzienTygodnia.PONIEDZIALEK, 16, stworzMapeMiejsc()));
            przykladoweSeanse.add(new Seans("Extraction 3", "11:30,", Seans.DzienTygodnia.NIEDZIELA, 18, stworzMapeMiejsc()));

            zapiszSeanse(przykladoweSeanse);
            return przykladoweSeanse;
        }

        return odczytaneSeanse;
    }

    private void zapiszSeanse(List<Seans> seanse) {
        try (ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(PLIK_SEANSE))) {
            for(Seans seans : seanse) {
                write.writeObject(seans);
            }
        } catch (Exception e) {
            System.out.println("Błąd zapisu seansów!");
        }
    }

    private static void zbierzDaneKlienta(Klient klient) {
        Scanner in = new Scanner(System.in);
        System.out.println("Zanim przejdziemy do kupowania biletu, podaj swoje dane.");
        System.out.println("Imie: ");
        klient.setImie(in.next());
        System.out.println("Nazwisko: ");
        klient.setNazwisko(in.next());
        System.out.println("email: ");
        klient.setEmail(in.next());
        System.out.println("telefon: ");
        klient.setTelefon(in.next());
    }

    public void kupBilet() {
        Klient klient = new Klient(); //dane klienta
        zbierzDaneKlienta(klient);
        wypiszSeanse();
        int wybor = -1, miejsce = -1;
        char rzad = '-';
        Scanner in = new Scanner(System.in);

        wybor = in.nextInt();
        wybor -= 1;

        if(wybor < 0 || wybor >= dostepneSeanse.size()) {
            throw new IllegalArgumentException("Wybrano nieistniejacy seans");
        }

        System.out.println("Wybierz rząd");
        HashMap<Character, HashMap<Integer, Boolean>> dostepneMiejsca = dostepneSeanse.get(wybor).getLiczbaMiejsc();
        pokazMiejsca(dostepneMiejsca);

        rzad = in.next().charAt(0);
        if(rzad < 'a' || rzad > maksymalnyRzad(dostepneMiejsca)) {
            throw new IllegalArgumentException("Wybrano nieistniejacy rzad");
        } else {
            miejsce = in.nextInt();
            if (miejsce < 0 || miejsce > maksymalnyNumerSiedzenia(dostepneMiejsca.get(rzad)) || dostepneMiejsca.get(rzad).get(miejsce)) {
                throw new IllegalArgumentException("Wybrano nieistniejace lub zajęte miejsce");
            } else {
                dostepneMiejsca.get(rzad).put(miejsce, true);
                klient.setSeans(dostepneSeanse.get(wybor).tytul);
                klient.addMiejsce(new HashMap<>(Map.of(rzad, miejsce)));
                klienci.add(klient);
                zapiszSeanse(this.dostepneSeanse);
                zapiszKlientow(klienci);
            }
        }
        System.out.println("Zarezerwowano pomyślnie miejsce!");
    }

    private char maksymalnyRzad(HashMap<Character, HashMap<Integer, Boolean>> dostepneMiejsca) {
        return Collections.max(dostepneMiejsca.keySet());
    }

    private int maksymalnyNumerSiedzenia(HashMap<Integer, Boolean> dostepneMiejsca) {
        return Collections.max(dostepneMiejsca.keySet());
    }

    private void wypiszSeanse() {
        int i = 1;
        for (Seans seans : dostepneSeanse) {
            System.out.println(i + ". " + seans);
            i++;
        }
    }

    private void pokazMiejsca(HashMap<Character, HashMap<Integer, Boolean>> seats) {
        System.out.println("Dostępne siedzenia:");
        for(Map.Entry<Character, HashMap<Integer, Boolean>> rzad : seats.entrySet()) {
            System.out.print(rzad.getKey() + "\t");
            for(Map.Entry<Integer, Boolean> siedzenia : rzad.getValue().entrySet()) {
                System.out.print(siedzenia.getValue() ? "\t" : (siedzenia.getKey() + "\t"));
            }
            System.out.println();
        }
    }

    private HashMap<Character, HashMap<Integer, Boolean>> stworzMapeMiejsc() {
        HashMap<Character, HashMap<Integer, Boolean>> seats = new HashMap<>();

        for(char seatId = 'a'; seatId < 'f'; seatId++) {
            seats.put(seatId, new HashMap<>());
            for(int i = 1; i < 11; i++) {
                seats.get(seatId).put(i, false);
            }
        }
        return seats;
    }

    public void pokazSeanse() {
        for (Seans seans : dostepneSeanse) {
            System.out.println(seans);
        }
    }
}
