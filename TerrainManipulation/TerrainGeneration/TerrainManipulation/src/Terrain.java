import java.util.Random;
import java.util.logging.ConsoleHandler;

public class Terrain {
    private int x;
    private int y;
    private int [][] terrainTomb;
    static Random rnd = new Random();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int[][] getTerrainTomb() {
        return terrainTomb;
    }

    public void setTerrainTomb(int[][] terrainTomb) {
        this.terrainTomb = terrainTomb;
    }
    public void setTerrainTomb(int positionA, int positionB, int value)
    {
        try {
            this.terrainTomb[positionA][positionB]=value;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    // Példányosításkor a létrehozza a terepet véletlenszerű értékekkel.
    public  Terrain(int x, int y)
    {
        this.x=x;
        this.y=y;
        terrainTomb= new int[x][y];
      //  System.out.println(terrainTomb[0].length+" "+terrainTomb.length);
        for (int i=0;i<x;i++)
        {
            for (int j=0;j<y;j++)
            {

                    terrainTomb[i][j]=randomSzam(1,3);
                    if (!magassagMeres(terrainTomb,i,j) && terrainTomb[i][j]<3)
                    {
                        terrainTomb[i][j] += rnd.nextInt(2);
                    }
                   else if (!magassagMeres(terrainTomb,i,j) && terrainTomb[i][j]==3)
                    {
                        terrainTomb[i][j]+=randomSzam(-2,1);
                    }
                    else if (!magassagMeres(terrainTomb,i,j) && terrainTomb[i][j]>3)
                    {
                        terrainTomb[i][j]+=randomSzam(-2,-1);
                    }
            }
        }
    }

    // Megvizsgálja, hogy a terep adott pontjának környezetéhez képest nem-e túl nagy a magassági eltérés.
    boolean magassagMeres(int [][] tomb, int x, int y)
    {
        if(x>=1 && y>=1 && y<tomb[x].length-1 && x<tomb.length-1 && (tomb[x][y]+2>tomb[x-1][y] || tomb[x][y]+2>tomb[x][y-1] || tomb[x][y]+2>tomb[x+1][y] || tomb[x][y]+2>tomb[x][y+1]))
        {
            return false;
        }
        else if(x==0 && y>=1 && tomb[x][y]+2>tomb[x][y-1] )
        {
            return false;
        }
        else if(x==tomb.length-1 && y>=1 && tomb[x][y]+2>tomb[x][y-1])
        {
            return false;
        }
        else if(x>=1 && y==0 && tomb[x][y]+2>tomb[x-1][y])
        {
            return  false;
        }
        else if(x>=1 && y==tomb[x].length-1 && tomb[x][y]+2>tomb[x][y-1])
        {
            return  false;
        }
        else
        {
            return  true;
        }
    }
     static void kiir(Terrain terrain)
    {
        for (int i=0;i<terrain.x;i++)
        {
            for (int j=0;j<terrain.y;j++)
            {
                System.out.print(terrain.terrainTomb[i][j]+" ");
            }
            System.out.println();
        }
    }


    static int randomSzam(int min, int max)
    {
        int temp=0;
        while (temp==0 ) {
            temp= rnd.nextInt(max - min + 1) + min;
        }
        return  temp;
    }
    // Legalacsonyabb pontok helyét adja vissza egy String tömbben
     String [] legalacsonyabbPontok()
    {
        String []temp = new String [this.x*this.y];
        int legalacsonyabb =legKisebb();
        int szamolo=0;

        //Legalacsonyabb pontok helyének megkeresése
        for (int i=0;i<this.x;i++)
        {
            for (int j=0;j<this.y;j++)
            {
                if (this.terrainTomb[i][j]==legalacsonyabb)
                {
                    temp[szamolo]=i+" "+j;
                    szamolo++;
                }
            }

        }
        return  temp;
    }

        int legKisebb()
        {
            int legalacsonyabb = this.terrainTomb[0][0];
            for (int i=0;i<this.x;i++)
            {
                for (int j=0;j<this.y;j++)
                {
                    if (this.terrainTomb[i][j]<=legalacsonyabb)
                    {
                        legalacsonyabb=this.terrainTomb[i][j];
                    }
                }

            }
            return  legalacsonyabb;
        }
        int legKisebb(int [] tomb)
        {
            int legalacsonyabb = tomb[0];
            for (int i=1;i<tomb.length;i++)
            {
                if (tomb[i]<legalacsonyabb)
                {
                    legalacsonyabb=tomb[i];
                }
            }
            return  legalacsonyabb;
        }


}
