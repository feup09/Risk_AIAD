package risk;
import java.util.Random;

public class Agent {
    int personalidade;
    Pais[] paises = new Pais[4];
    int cards = 1;
    
    public void setPersonality()
    {
        Random gerador = new Random();
        this.personalidade = gerador.nextInt(8); //0 a 7
    }
    
    public int[] throwDice(int number)
    {
        Random gerador = new Random();
        int dice[] = new int[number];
        for (int i=0; i< number; i++)
            dice[i] = (gerador.nextInt(6) +1); //1 a 6
        return dice;
    }

}
