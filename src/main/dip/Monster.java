package dip;

import java.util.Random;

public class Monster extends Entity {
    private int damage;
    private Player player;
    public Monster(String name, int health, int mana, int damage, Player player){
    this.name=name;
    this.health=health;
    this.mana=mana;
    this.damage=damage;
    this.player=player;
    this.maxHealth=health;
    }

    synchronized int attack() {

        Random rand=new Random();
        int minDmg= (int) Math.floor(damage-damage*0.3);
        int maxDmg=(int) Math.ceil(damage+damage*0.3);
        int dmgDealt=rand.nextInt(maxDmg-minDmg)+minDmg;
        System.out.println(dmgDealt);
        return dmgDealt;
    }
    void die(){
        player.exp+=2000;
        player.levelUp();
    }
}
