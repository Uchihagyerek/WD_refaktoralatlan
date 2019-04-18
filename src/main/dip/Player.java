package dip;

import javax.swing.*;
import java.util.Random;

public class Player extends Entity{
    public int exp;
    public int points;   //drawtext
    public int remainingExp;
    public int potionsCount;
    public int damage;
    Map map;
    public Player(String name, Map map){
        this.name=name;
        damage=2000;
        level=1;
        maxHealth=500;
        maxMana=500;
        health=maxHealth;
        mana=maxMana;
        exp=0;
        remainingExp=1000;
        this.map=map;
        potionsCount=3;
        points = 5000;
    }

    public void levelUp(){
        if(exp>=remainingExp) {
            if (remainingExp < exp)
                exp = exp - remainingExp;
            else
                exp = 0;
            level++;
            damage*=1.2;
            maxHealth += level * 100;
            maxMana += level * 100;
            remainingExp *= 1.2;
            health = maxHealth;
            mana = maxMana;
            System.out.println ("LEVEL UP! Your stats have been increased!");
            getStats();
            levelUp();
        }
    }

    public void getStats() {
        System.out.println ("Health: "+maxHealth);
        System.out.println ("Mana: "+maxMana);
        System.out.println ("Damage: "+damage);
    }

    int attack() {
        Random rand=new Random();
        int minDmg= (int) Math.floor(damage-damage*0.3);
        int maxDmg=(int) Math.ceil(damage+damage*0.3);
        int dmgDealt=rand.nextInt(maxDmg-minDmg)+minDmg;
        System.out.println(dmgDealt);
        return dmgDealt;
    }
    void die(){
        points-=3000;
        DataBase.setScore(points, name);
        int restart= JOptionPane.YES_NO_OPTION;
        restart=JOptionPane.showConfirmDialog(null,"Would you like to restart?","You died!",restart);
        if(restart==JOptionPane.YES_OPTION){
            map.playerDead=true;
        map.generateMap();
        }else if (restart==JOptionPane.NO_OPTION){
            System.exit(1);

        }
    }
}
