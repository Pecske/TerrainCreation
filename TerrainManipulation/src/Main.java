import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        terrainGenerate();


    }

    static void terrainGenerate() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Terep méretei x*y: (Maximum 9x9-es lehet)");
        System.out.print("X: ");
        int x = scan.nextInt();
        System.out.print("Y: ");
        int y = scan.nextInt();
        System.out.println();
        Terrain terrain = new Terrain(x, y);
        scan.close();
        System.out.println("A magasságok 1 és 4 közötti értékek lehetnek, ahol 1: völgy, 2: sík, 3:domb, 4: magasdomb");
        terrain.kiir();
        String[] alacsonyPontok = terrain.legalacsonyabbPontok();
        System.out.println("---------------Legkisebb pontok koordinátái-----------");
        for (int i = 0; i < alacsonyPontok.length; i++) {
            if (alacsonyPontok[i] != null) {
                System.out.println(alacsonyPontok[i]);
            }
        }
        System.out.println("---------Feltörés szimulálás---------");
        Simulation.szimulal(terrain, alacsonyPontok);
        System.out.println("---------Szimuláció bejefezve--------");
    }
}
