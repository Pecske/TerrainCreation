import java.util.Random;

public class Terrain {
    private int x;
    private int y;
    private int[][] terrainTomb;
    static Random rnd = new Random();
    static  int lepesSzam=0;


    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (x <= 9) {
            this.x = x;
        } else {
            System.out.println("9-nél kisebb szám megadása szükséges");
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (y <= 9) {
            this.y = y;
        } else {
            System.out.println("9-nél kisebb szám megadása szükséges");
        }
    }

    public int[][] getTerrainTomb() {
        return terrainTomb;
    }

    public void setTerrainTomb(int[][] terrainTomb) {
        this.terrainTomb = terrainTomb;
    }

    public void setTerrainTomb(int positionA, int positionB, int value) {
        try {
            this.terrainTomb[positionA][positionB] = value;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    // Példányosításkor a létrehozza a terepet véletlenszerű értékekkel.
    public Terrain(int x, int y) {
        this.setX(x);
        this.setY(y);
        terrainTomb = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {

                terrainTomb[i][j] = randomSzam(1, 3);
                if (!magassagMeres(terrainTomb, i, j) && terrainTomb[i][j] < 3) {
                    terrainTomb[i][j] += rnd.nextInt(2);
                } else if (!magassagMeres(terrainTomb, i, j) && terrainTomb[i][j] == 3) {
                    terrainTomb[i][j] += randomSzam(-2, 1);
                } else if (!magassagMeres(terrainTomb, i, j) && terrainTomb[i][j] > 3) {
                    terrainTomb[i][j] += randomSzam(-2, -1);
                }
            }
        }
    }

    // Megvizsgálja, hogy a terep adott pontjának környezetéhez képest nem-e túl nagy a magassági eltérés.
    boolean magassagMeres(int[][] tomb, int x, int y) {
        if (x >= 1 && y >= 1 && y < tomb[x].length - 1 && x < tomb.length - 1 && (tomb[x][y] + 2 > tomb[x - 1][y] || tomb[x][y] + 2 > tomb[x][y - 1] || tomb[x][y] + 2 > tomb[x + 1][y] || tomb[x][y] + 2 > tomb[x][y + 1])) {
            return false;
        } else if (x == 0 && y >= 1 && tomb[x][y] + 2 > tomb[x][y - 1]) {
            return false;
        } else if (x == tomb.length - 1 && y >= 1 && tomb[x][y] + 2 > tomb[x][y - 1]) {
            return false;
        } else if (x >= 1 && y == 0 && tomb[x][y] + 2 > tomb[x - 1][y]) {
            return false;
        } else if (x >= 1 && y == tomb[x].length - 1 && tomb[x][y] + 2 > tomb[x][y - 1]) {
            return false;
        } else {
            return true;
        }
    }

     void kiir() {
         System.out.println(lepesSzam+" .lépés");
         System.out.println("------------");
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                System.out.print(this.terrainTomb[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        lepesSzam++;
    }


    static int randomSzam(int min, int max) {
        int temp = 0;
        while (temp == 0) {
            temp = rnd.nextInt(max - min + 1) + min;
        }
        return temp;
    }

    // Legalacsonyabb pontok helyét adja vissza egy String tömbben
    String[] legalacsonyabbPontok() {
        String[] temp = new String[this.x * this.y];
        int legalacsonyabb = legkisebb();
        int szamolo = 0;

        //Legalacsonyabb pontok helyének megkeresése
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                if (this.terrainTomb[i][j] == legalacsonyabb) {
                    temp[szamolo] = i + " " + j;
                    szamolo++;
                }
            }

        }
        return temp;
    }

    int legkisebb() {
        int legalacsonyabb = this.terrainTomb[0][0];
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                if (this.terrainTomb[i][j] <= legalacsonyabb) {
                    legalacsonyabb = this.terrainTomb[i][j];
                }
            }

        }
        return legalacsonyabb;
    }



    static int legkisebbElem(int[] tomb) {
        int legalacsonyabb = tomb[0];
        for (int i = 0; i < tomb.length; i++) {
            if (tomb[i] > 0 && tomb[i] < legalacsonyabb) {
                legalacsonyabb = tomb[i];
            }
        }
        return legalacsonyabb;
    }



}
