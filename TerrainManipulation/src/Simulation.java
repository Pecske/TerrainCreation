import java.util.ArrayList;
import java.util.Collections;

public class Simulation {
    static ArrayList<Integer> modositott = new ArrayList<Integer>();
    static int[] kornyezetiTomb;
    static boolean valtozas;

    //Véletlenszerűen kiválaszt egy helyet a legalacsonyabb pontok közül.
    static int veletlenKitores(String[] alacsonyPontok) {
        int hely = 0;
        do {
            hely = Terrain.rnd.nextInt(alacsonyPontok.length);
        }
        while (alacsonyPontok[hely] == null);

        String[] temp = alacsonyPontok[hely].split(" ");
        int[] helyErtek = new int[2];
        for (int i = 0; i < temp.length; i++) {
            helyErtek[i] = Integer.parseInt(temp[i]);
        }

        return helyErtek[0] * 10 + helyErtek[1];
    }


    static void szimulal(Terrain terrain, String[] alacsonyPontok) {
        int randomErtek = veletlenKitores(alacsonyPontok);
        int a = randomErtek / 10;
        int b = randomErtek % 10;

        terrain.getTerrainTomb()[a][b] *= -1;
        modositott.add(a * 10 + b);
        terrain.kiir();

        int i = 0;
        while (!feltoltott(terrain) && i < modositott.size()) {

            //A vízzel ellepett pontok szomszédos pontjait menti el.
            ArrayList<Integer> modositottKornyezetHelyek = kornyezetiHelyekKereses(terrain);
            int[] modositottKornyezetErtekek = new int[modositottKornyezetHelyek.size()];
            //A vízzel határos pontoknak megadja az értékeit
            for (int j = 0; j < modositottKornyezetHelyek.size(); j++) {
                modositottKornyezetErtekek[j] = terrain.getTerrainTomb()[modositottKornyezetHelyek.get(j) / 10][modositottKornyezetHelyek.get(j) % 10];
            }
            int legkisebbErtek = Terrain.legkisebbElem(modositottKornyezetErtekek);
            //Megvizsgálja, hogy van-e vele egy szinten lévő érték a környezetében, ha igen feltöltöm, a vele határosakat is
            if (!valtozas && Math.abs(terrain.getTerrainTomb()[modositott.get(i) / 10][modositott.get(i) % 10]) == Math.abs(legkisebbErtek)) {
                elmos(terrain, terrain.getTerrainTomb()[modositott.get(i) / 10][modositott.get(i) % 10]);
                terrain.kiir();
                valtozas = true;
                if (feltoltott(terrain)) {
                    break;
                }
            }
            //Ha a vízzel ellepett elemekkel van olyan szomszédos érték, ami magasabb, azt az értéket megnöveli
            else if (!valtozas && Math.abs(terrain.getTerrainTomb()[modositott.get(i) / 10][modositott.get(i) % 10]) > legkisebbErtek) {
                for (int j = 0; j < modositottKornyezetHelyek.size(); j++) {
                    if (terrain.getTerrainTomb()[modositottKornyezetHelyek.get(j) / 10][modositottKornyezetHelyek.get(j) % 10] == legkisebbErtek) {
                        terrain.setTerrainTomb(modositottKornyezetHelyek.get(j) / 10, modositottKornyezetHelyek.get(j) % 10, terrain.getTerrainTomb()[modositottKornyezetHelyek.get(j) / 10][modositottKornyezetHelyek.get(j) % 10] * (-1));
                        terrain.kiir();
                        modositott.add(modositottKornyezetHelyek.get(j));
                        // novel++;
                        while (terrain.getTerrainTomb()[modositottKornyezetHelyek.get(j) / 10][modositottKornyezetHelyek.get(j) % 10] != terrain.getTerrainTomb()[modositott.get(0) / 10][modositott.get(0) % 10]) {
                            terrain.setTerrainTomb(modositottKornyezetHelyek.get(j) / 10, modositottKornyezetHelyek.get(j) % 10, (Math.abs(terrain.getTerrainTomb()[modositottKornyezetHelyek.get(j) / 10][modositottKornyezetHelyek.get(j) % 10]) + 1) * (-1));
                            terrain.kiir();
                        }
                    }
                    valtozas = true;
                }
                if (feltoltott(terrain)) {
                    break;
                }
            }
            //Ha van az elterített elemekkel határos náluk nagyobb elem, akkor feltölti vele egyszintre
            else if (!valtozas && terrain.getTerrainTomb()[modositott.get(i) / 10][modositott.get(i) % 10] < legkisebbErtek) {
                feltolt(terrain, legkisebbErtek);
                valtozas = true;
            }

            Collections.sort(modositott);
            //Ha történt változás, a sorba rendezett módosított kollekcióból előről kezdi a vizsgálatokat
            if (valtozas) {
                i = 0;
                valtozas = false;
            } else {
                i++;
            }
        }
    }

    //Megvizsgálja, hogy a terep minden eleme feltöltődött-e
    static boolean feltoltott(Terrain terrain) {
        if (modositott.size() == terrain.getX() * terrain.getY()) {
            return true;
        } else {
            return false;
        }
    }

    //Megadott pontok környezetének értékeit adja vissza
    static int[] pontKornyezetTomb(Terrain terrain, int a, int b) {
        if (a == 0 && b > 0 && b < terrain.getY() - 1) {
            kornyezetiTomb = new int[3];
            kornyezetiTomb[0] = a * 10 + (b - 1);
            kornyezetiTomb[1] = a * 10 + (b + 1);
            kornyezetiTomb[2] = (a + 1) * 10 + b;
        } else if (a > 0 && b == 0 && a < terrain.getX() - 1) {
            kornyezetiTomb = new int[3];
            kornyezetiTomb[0] = (a - 1) * 10 + b;
            kornyezetiTomb[1] = (a + 1) * 10 + b;
            kornyezetiTomb[2] = a * 10 + (b + 1);
        } else if (a == terrain.getX() - 1 && b > 0 && b < terrain.getY() - 1) {
            kornyezetiTomb = new int[3];
            kornyezetiTomb[0] = a * 10 + (b - 1);
            kornyezetiTomb[1] = a * 10 + (b + 1);
            kornyezetiTomb[2] = (a - 1) * 10 + b;
        } else if (b == terrain.getY() - 1 && a > 0 && a < terrain.getX() - 1) {
            kornyezetiTomb = new int[3];
            kornyezetiTomb[0] = (a - 1) * 10 + b;
            kornyezetiTomb[1] = (a + 1) * 10 + b;
            kornyezetiTomb[2] = a * 10 + (b - 1);
        } else if (a == 0 && b == 0) {
            kornyezetiTomb = new int[2];
            kornyezetiTomb[0] = (a + 1) * 10 + b;
            kornyezetiTomb[1] = a * 10 + (b + 1);
        } else if (a == terrain.getX() - 1 && b == 0) {
            kornyezetiTomb = new int[2];
            kornyezetiTomb[0] = (a - 1) * 10 + b;
            kornyezetiTomb[1] = a * 10 + (b + 1);
        } else if (a == 0 && b == terrain.getY() - 1) {
            kornyezetiTomb = new int[2];
            kornyezetiTomb[0] = a * 10 + (b - 1);
            kornyezetiTomb[1] = (a + 1) * 10 + b;
        } else if (a == terrain.getX() - 1 && b == terrain.getY() - 1) {
            kornyezetiTomb = new int[2];
            kornyezetiTomb[0] = a * 10 + (b - 1);
            kornyezetiTomb[1] = (a - 1) * 10 + b;
        } else {
            kornyezetiTomb = new int[4];
            kornyezetiTomb[0] = (a - 1) * 10 + b;
            kornyezetiTomb[1] = (a + 1) * 10 + b;
            kornyezetiTomb[2] = a * 10 + (b + 1);
            kornyezetiTomb[3] = a * 10 + (b - 1);
        }
        int[] kornyekiSzamok = new int[kornyezetiTomb.length];
        for (int i = 0; i < kornyekiSzamok.length; i++) {
            kornyekiSzamok[i] = terrain.getTerrainTomb()[kornyezetiTomb[i] / 10][kornyezetiTomb[i] % 10];
        }
        return kornyekiSzamok;
    }

    //Megvizsgálja, hogy egy adott értéket tartalmaz-e a kívánt tömb
    static boolean keres(int[] tomb, int value) {
        for (int i = 0; i < tomb.length; i++) {
            if (tomb[i] == value) {
                return true;
            }
        }
        return false;
    }

    //A megadott értékhez viszonyítva egy tömbbe összegyűjti az összes egyenlő magasságú elemet, majd azokat amiknek a szomszédjában van vízzel ellepett terület, elmossa.
    static void elmos(Terrain terrain, int value) {
        int[] temp = new int[terrain.getX() * terrain.getY()];
        int szamolo = 0;

        for (int i = 0; i < terrain.getX(); i++) {
            for (int j = 0; j < terrain.getY(); j++) {
                if (terrain.getTerrainTomb()[i][j] == Math.abs(value)) {
                    temp[szamolo] = i * 10 + j;
                    szamolo++;
                }
            }
        }

        for (int i = 0; i < szamolo; i++) {
            int[] kornyek = pontKornyezetTomb(terrain, temp[i] / 10, temp[i] % 10);
            if (keres(kornyek, value)) {
                terrain.setTerrainTomb(temp[i] / 10, temp[i] % 10, terrain.getTerrainTomb()[temp[i] / 10][temp[i] % 10] * (-1));
                modositott.add(temp[i]);
            }
        }
    }

    //A megadott értékig feltölti a már korábban vízzel ellepett pontokat
    static void feltolt(Terrain terrain, int value) {
        for (int i = 0; i < modositott.size(); i++) {
            while (Math.abs(terrain.getTerrainTomb()[modositott.get(i) / 10][modositott.get(i) % 10]) < value) {
                terrain.setTerrainTomb(modositott.get(i) / 10, modositott.get(i) % 10, (Math.abs(terrain.getTerrainTomb()[modositott.get(i) / 10][modositott.get(i) % 10]) + 1) * (-1));
                terrain.kiir();
                if (feltoltott(terrain)) {
                    break;
                }
            }


        }
    }

    //A vízzel ellepett összes pont környezetében lévő pontokat adja vissza, kivéve belőle a vízzel ellepett pontokat
    static ArrayList<Integer> kornyezetiHelyekKereses(Terrain terrain) {
        ArrayList<Integer> modositottKornyezetHelyek = new ArrayList<Integer>();
        for (int i = 0; i < modositott.size(); i++) {
            int[] modositottKornyezetErtekek = pontKornyezetTomb(terrain, modositott.get(i) / 10, modositott.get(i) % 10);
            for (int j = 0; j < modositottKornyezetErtekek.length; j++) {
                modositottKornyezetHelyek.add(kornyezetiTomb[j]);
            }
        }
        modositottKornyezetHelyek.removeAll(modositott);

        return modositottKornyezetHelyek;
    }

}
