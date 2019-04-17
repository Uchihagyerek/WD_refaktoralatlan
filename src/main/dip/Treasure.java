package dip;

import java.util.Random;

public class Treasure {
    private int type;     //1:HP, 2:MANA, 3:DAMAGE
    private int value;

    public Treasure(){
        Random rand=new Random();
        type=rand.nextInt (3)+1;
        if(type<3)
            value=rand.nextInt (150)+100;
        else
            value=rand.nextInt (10)+10;
    }

    public int getType(){
        return type;
    }

    public int getValue() {
        return value;
    }
}
