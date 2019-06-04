public  class Simulation {

    enum Hely
    {
        Bal,
        Jobb,
        Fenn,
        Lenn,
        Kozep
    }

    static Hely hely;

    //Véletlenszerűen kiválaszt egy helyet a legalacsonyabb pontok közül.
   static int veletlenKitores (String [] alacsonyPontok)
    {
       int hely = 0;
       do
       {
           hely=Terrain.rnd.nextInt(alacsonyPontok.length);
       }
       while (alacsonyPontok[hely]==null);

       String [] temp = alacsonyPontok[hely].split(" ");
       int [] helyErtek = new int [2];
       for (int i=0;i<temp.length;i++)
       {
           helyErtek[i]= Integer.parseInt(temp[i]);
       }

       return  helyErtek[0]*10+helyErtek[1];
    }

   static boolean szelenVan(Terrain terrain, int a, int b)
    {
        if (a==0 || b==0 || a==terrain.getTerrainTomb().length-1 || b==terrain.getTerrainTomb()[0].length-1)
        {
          return  true;
        }
        else
        {
            return  false;
        }
    }

    static void szimulal(Terrain terrain, String [] alacsonyPontok)
    {
        int randomErtek = veletlenKitores(alacsonyPontok);
        int a=randomErtek/10;
        int b=randomErtek%10;
        int []feltoltottHelyek = new int [terrain.getX()*terrain.getY()];

        terrain.getTerrainTomb()[a][b]*=-1;

        while (!feltoltott(terrain))
        {
            int kornyezetiLegkisebbHely = kornyezetiLegkisebb(terrain,a,b)[4];
            int x = kornyezetiLegkisebbHely/10;
            int y = kornyezetiLegkisebbHely%10;
            int i=0;
            while (Math.abs(terrain.getTerrainTomb()[a][b])!=terrain.getTerrainTomb()[x][y])
            {
                if (terrain.getTerrainTomb()[a][b]>0)
                {
                    int value = terrain.getTerrainTomb()[a][b]*(-1);
                    terrain.setTerrainTomb(a,b,value);
                    Terrain.kiir(terrain);
                }
                else
                {
                    int value= (Math.abs(terrain.getTerrainTomb()[a][b]+1)*(-1));
                    terrain.setTerrainTomb(a,b, value);
                    Terrain.kiir(terrain);
                    feltoltottHelyek[i]=a*10+b;
                }
            }
            if(feltoltottHelyek[i]!=0)
            {
                i++;
            }
            x=kornyezetiLegkisebb(terrain,a,b)[4]/10;
            y=kornyezetiLegkisebb(terrain,a,b)[4]%10;
            if (terrain.getTerrainTomb()[x][y]<0 && terrain.getTerrainTomb()[a][b]==terrain.getTerrainTomb()[x][y])
            {
                i--;
            }
            a=x;
            b=y;


        }


       /* while (!feltoltott(terrain))
        {
            for (int i=0;i<terrain.getX()-1;i++)
            {
                for (int j=0;j<terrain.getY()-2;j++)
                {
                    if ((i ==0 || i==terrain.getX()-2) && terrain.getTerrainTomb()[i][j]>0 && (terrain.getTerrainTomb()[i][j+1]<0 || terrain.getTerrainTomb()[i+1][j]<0))
                    {
                        while (Math.abs(terrain.getTerrainTomb()[i][j])!= terrain.getTerrainTomb()[i][j+1] || Math.abs(terrain.getTerrainTomb()[i][j])!= terrain.getTerrainTomb()[i+1][j] )
                        {

                            int value= (Math.abs(terrain.getTerrainTomb()[a][b])+1)*(-1);
                            terrain.setTerrainTomb(a,b,value);
                            // System.out.println(terrain.getTerrainTomb()[a][b]);
                             Terrain.kiir(terrain);
                             System.out.println();
                        }

                    }
                    else if(i>0 && i<terrain.getX()-2 && terrain.getTerrainTomb()[i][j]>0 && (terrain.getTerrainTomb()[i][j+1]<0 || terrain.getTerrainTomb()[i-1][j]<0 || terrain.getTerrainTomb()[i+1][j]<0))
                    {
                        while (Math.abs(terrain.getTerrainTomb()[i][j])!= terrain.getTerrainTomb()[i][j+1] || Math.abs(terrain.getTerrainTomb()[i][j])!= terrain.getTerrainTomb()[i+1][j] || Math.abs(terrain.getTerrainTomb()[i][j])!= terrain.getTerrainTomb()[i-1][j] )
                        {
                            int value = (Math.abs(terrain.getTerrainTomb()[a][b]) + 1) * (-1);
                            terrain.setTerrainTomb(a, b, value);
                            Terrain.kiir(terrain);
                        }
                    }
                }
            }

        }*/
    }

    static boolean feltoltott(Terrain terrain)
    {
        int szamolo=0;
        for (int i=0;i<terrain.getTerrainTomb().length;i++)
        {
            for (int j=0;j<terrain.getTerrainTomb()[0].length;j++)
            {
                if(terrain.getTerrainTomb()[i][j]<0)
                {
                    szamolo++;
                }
            }
        }
        if (szamolo==terrain.getX()*terrain.getY())
        {
            return  true;
        }
        else
        {
            return  false;
        }
    }
    static int []  kornyezetiLegkisebb(Terrain terrain, int a, int b)
    {
        int [] kornyezetiTomb = new int[5];
        int [] ertekek = new int [4];
        if (szelenVan(terrain,a,b) && a==0 && b>0 && b<terrain.getTerrainTomb()[0].length-1)
        {
            ertekek[0]=terrain.getTerrainTomb()[a][b-1];
            ertekek[1]=terrain.getTerrainTomb()[a][b+1];
            ertekek[2]=terrain.getTerrainTomb()[a+1][b];

            kornyezetiTomb[0]=a*10+(b-1);
            kornyezetiTomb[1]=a*10+(b+1);
            kornyezetiTomb[2]=(a+1)*10+b;
            hely=Hely.Fenn;
        }
        else if(szelenVan(terrain,a,b) && a>0 && b==0 && a<terrain.getTerrainTomb().length-1)
        {
            ertekek[0]=terrain.getTerrainTomb()[a-1][b];
            ertekek[1]=terrain.getTerrainTomb()[a+1][b];
            ertekek[2]=terrain.getTerrainTomb()[a][b+1];

            kornyezetiTomb[0]=(a-1)*10+b;
            kornyezetiTomb[1]=(a+1)*10+b;
            kornyezetiTomb[2]=a*10+(b+1);
            hely=Hely.Bal;
        }
        else if(szelenVan(terrain,a,b) && a==terrain.getTerrainTomb().length-1 && b>0 && b<terrain.getTerrainTomb()[0].length-1)
        {
            ertekek[0]=terrain.getTerrainTomb()[a][b-1];
            ertekek[1]=terrain.getTerrainTomb()[a][b+1];
            ertekek[2]=terrain.getTerrainTomb()[a-1][b];

            kornyezetiTomb[0]=a*10+(b-1);
            kornyezetiTomb[1]=a*10+(b+1);
            kornyezetiTomb[2]=(a-1)*10+b;
            hely=Hely.Lenn;
        }
        else if(szelenVan(terrain,a,b) && b==terrain.getTerrainTomb()[0].length-1 && a>0 && a<terrain.getTerrainTomb().length-1)
        {
            ertekek[0]=terrain.getTerrainTomb()[a-1][b];
            ertekek[1]=terrain.getTerrainTomb()[a+1][b-1];
            ertekek[2]=terrain.getTerrainTomb()[a][b-1];

            kornyezetiTomb[0]=(a-1)*10+b;
            kornyezetiTomb[1]=(a+1)*10+b;
            kornyezetiTomb[2]=a*10+(b-1);
            hely=Hely.Jobb;
        }
        else if(szelenVan(terrain,a,b) && a==0 && b==0)
        {
            ertekek[0]=terrain.getTerrainTomb()[a+1][b];
            ertekek[1]=terrain.getTerrainTomb()[a][b+1];

            kornyezetiTomb[0]=(a+1)*10+b;
            kornyezetiTomb[1]=a*10+(b+1);
        }
        else if(szelenVan(terrain,a,b) && a==terrain.getTerrainTomb().length-1 && b==0)
        {
            ertekek[0]=terrain.getTerrainTomb()[a-1][b];
            ertekek[1]=terrain.getTerrainTomb()[a][b+1];

            kornyezetiTomb[0]=(a-1)*10+b;
            kornyezetiTomb[1]=a*10+(b+1);
        }
        else if(szelenVan(terrain,a,b) && a==0 && b==terrain.getTerrainTomb()[0].length-1)
        {
            ertekek[0]=terrain.getTerrainTomb()[a][b-1];
            ertekek[1]=terrain.getTerrainTomb()[a+1][b];

            kornyezetiTomb[0]=a*10+(b-1);
            kornyezetiTomb[1]=(a+1)*10+b;
        }
        else if(szelenVan(terrain,a,b) && a==terrain.getTerrainTomb().length-1 && b==terrain.getTerrainTomb()[0].length-1)
        {
            ertekek[0]=terrain.getTerrainTomb()[a][b-1];
            ertekek[0]=terrain.getTerrainTomb()[a-1][b];

            kornyezetiTomb[0]=a*10+(b-1);
            kornyezetiTomb[1]=(a-1)*10+b;
        }
        else
        {
            ertekek[0]=terrain.getTerrainTomb()[a-1][b];
            ertekek[1]=terrain.getTerrainTomb()[a+1][b];
            ertekek[2]=terrain.getTerrainTomb()[a][b+1];
            ertekek[3]=terrain.getTerrainTomb()[a][b-1];

            kornyezetiTomb[0]=(a-1)*10+b;
            kornyezetiTomb[1]=(a+1)*10+b;
            kornyezetiTomb[2]=a*10+(b+1);
            kornyezetiTomb[3]=a*10+(b-1);
            hely = Hely.Kozep;
        }
        kornyezetiTomb[4] = terrain.legKisebb(ertekek);
        for (int i=0;i<kornyezetiTomb.length-2;i++)
        {
            if (kornyezetiTomb[4]==kornyezetiTomb[i])
            {
                kornyezetiTomb[4]=kornyezetiTomb[i];
            }
        }


        return kornyezetiTomb;
    }

}
