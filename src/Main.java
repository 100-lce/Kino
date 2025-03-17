import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        Kino k1 = new Kino();
        Scanner in = new Scanner(System.in);
        int choice = -1;

        while(true) {
            System.out.println("Co chcesz zrobic?");
            System.out.println("1. Kup bilet");
            System.out.println("2. Pokaż klientów");
            System.out.println("3. Pokaż Seanse");
            System.out.println("4. Wyjdz");
            choice = in.nextInt();
            if (choice == 1) {
                try {
                    k1.kupBilet();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    exit(-1);
                }

            } else if (choice == 2) {
                k1.pokazKlientow();
            } else if (choice == 3) {
                k1.pokazSeanse();
            } else {
                exit(0);
            }
        }

    }


}
/*
co to serializacja, do czego sluzy jak z tego skorzystac
rezerwacja biletow do kina
reprezetnacja miejsc w haszmapie - klucz litera rzedu - wartosc to nr siedzenia i czy zajete

rezerwacja miejsc w serializacji
klientow zapisz w posatci xml
*/