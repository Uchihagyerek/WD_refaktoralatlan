package dip;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Battle extends Canvas {


    private int cooldown;
    private static JFrame thisFrame;
    private Map map;
    private Monster enemy;
    private JProgressBar enemyHealth;
    private JProgressBar playerHealth;
    private JProgressBar playerMana;
    private JButton attack;
    private JButton spell;
    private JButton potion;
    private JButton escape;
    private Player player;
    private boolean boss;
    public Battle(Map map, Player player, boolean boss) {

        Sounds.stopMusic ();
        String[] monster;
        setSize(900, 900);
        String music;
        this.boss=boss;
        if (!boss) {
            monster = DataBase.getMonster (0);
            music="battlemusic.wav";
        }
        else {
            monster = DataBase.getMonster (1);
            music="bossmusic.wav";
        }
        this.map = map;
        this.player = player;

        for (int i = 0; i < monster.length; i++) {
            System.out.println(monster[i] + ", ");
        }
        enemy = new Monster(monster[1], Integer.parseInt(monster[2]), Integer.parseInt(monster[3]), player);
        Sounds.playMusic (music);


    }


    @Override
    public void update(Graphics g) {paint(g);}
    @Override
    public void paint(Graphics ig) {
        BufferedImage image = new BufferedImage(900, 900, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect (0,0,900,900);
        BufferedImage sprite;
        try {
            sprite=Sprite.resize(ImageIO.read(new File("src/resources/sprites/"+enemy.name+".png")),450,500);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            sprite=null;
        }
        ig.drawImage(image, 0,0,this);

        ig.drawImage(sprite,sprite.getWidth()/2,20,null);

    }
    public void ui(JFrame frame){
        thisFrame=frame;
        attack=new JButton("Attack");
        attack.setBounds(50,750,200, 50);
        attack.setBackground(Color.WHITE);
        attack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                attackBtn(false);
            }
        });

        spell=new JButton("Spell");
        spell.setBounds(250,750,200, 50);
        spell.setBackground(Color.WHITE);
        spell.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                spell();
            }
        });

        potion =new JButton("Potion");
        potion.setBounds(50, 800,200,50);
        potion.setBackground(Color.WHITE);
        potion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                potion ();
            }
        });

        escape=new JButton("Escape");
        escape.setBounds(250, 800, 200,50);
        escape.setBackground(Color.WHITE);
        escape.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                escape();
            }
        });

        enemyHealth=new JProgressBar();

        enemyHealth.setMaximum(enemy.maxHealth);
        enemyHealth.setValue(enemy.maxHealth);
        enemyHealth.setBounds(0,0,900,30);
        enemyHealth.setString(enemy.name+": "+enemy.health+"/"+enemy.maxHealth);
        enemyHealth.setStringPainted(true);
        enemyHealth.setForeground(Color.RED);

        playerHealth=new JProgressBar();
        playerHealth.setMaximum(player.maxHealth);
        playerHealth.setValue(player.health);
        playerHealth.setBounds(500,750,350,50);
        playerHealth.setString("Health: "+player.health+"/"+player.maxHealth);
        playerHealth.setStringPainted(true);
        playerHealth.setForeground(Color.GREEN);

        playerMana=new JProgressBar ();
        playerMana.setMaximum (player.maxMana);
        playerMana.setValue (player.mana);
        playerMana.setBounds (500,800,350,50);
        playerMana.setString ("Mana: "+player.mana+"/"+player.maxMana);
        playerMana.setStringPainted (true);
        playerMana.setForeground (Color.BLUE);

        JProgressBar playerExp = new JProgressBar();
        playerExp.setMaximum(player.remainingExp);
        playerExp.setValue(player.exp);
        playerExp.setBounds(0,880,900,20);
        playerExp.setString("Exp: "+player.exp+"/"+player.remainingExp);
        playerExp.setStringPainted(true);
        playerExp.setForeground(Color.magenta);

        JLabel level = new JLabel();
        level.setText("Lvl: "+player.level);
        level.setBounds(430,870,50,10);
        level.setBackground(Color.lightGray);
        level.setVisible(true);

        frame.add(attack);
        frame.add(spell);
        frame.add(potion);
        frame.add(escape);
        frame.add(enemyHealth);
        frame.add(playerHealth);
        frame.add(playerMana);
        frame.add(playerExp);
        frame.add(level);

    }


    private void attackBtn(boolean spell) {
        attack(spell);
        manageButtons();
        setStats ();
        Thread myThread=new Thread() {
            public void run() {
                try {

                    sleep(1000);
                    if(!isDead (enemy))
                    Sounds.playSound ("monster.wav");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                enemyAttack();
                manageButtons();
            }
        };
        myThread.start();
    }
    private void attack(boolean spell){
        System.out.println("ATTACK!!");

        if(spell){
            enemy.health-=(int) (player.attack() * 1.8);
            Sounds.playSound ("fireball.wav");
        }else {
            enemy.health -= player.attack ();
            Sounds.playSound ("sword.wav");
        }

        checkKill();
    }

    private void spell() {
        if(player.mana>20) {

            System.out.println ("You used fireball");
            attackBtn (true);
            cooldown = 3;
            player.mana-=20;
        }else{

            System.out.println ("Not enough mana!");
        }

    }

    private void checkKill(){
        if (isDead(enemy)){
            Sounds.stopMusic ();
            enemy.die();
            map.defeated=true;
            if(boss)
                player.points+=500;
            else
                player.points+=50;

            endBattle ();

        }
    }

    private boolean isDead(Entity target){

        return target.health<=0;

    }

    private void enemyAttack(){

        player.health-=enemy.attack();
        setStats ();


        if(isDead(player)){

            player.die();
            player=new Player(player.name,map);
        }


    }

    private void setStats() {
        playerHealth.setValue(player.health);
        playerHealth.setString("Health: "+player.health+"/"+player.maxHealth);
        playerMana.setValue (player.mana);
        playerMana.setString("Mana: "+player.mana+"/"+player.maxMana);
        enemyHealth.setValue(enemy.health);
        enemyHealth.setString(enemy.name+": "+enemy.health+"/"+enemy.maxHealth);
    }


    private void potion() {
        if (player.potionsCount>0){
            if(player.health+100<=player.maxHealth)
                player.health+=100;
            else
                player.health=player.maxHealth;
            if(player.mana+100<=player.maxMana)
                player.mana+=100;
            else
                player.mana=player.maxMana;

            setStats ();
            player.potionsCount--;
        }else{
            System.out.println ("You don't have any potions left");
        }
    }
    private void escape() {

        map.defeated=false;
        endBattle ();
    }
    private void manageButtons(){
        if (attack.isEnabled()){
            attack.setEnabled(false);
            spell.setEnabled(false);
            potion.setEnabled(false);
            escape.setEnabled(false);
        }else{
            attack.setEnabled(true);
            if(cooldown==0) {
                spell.setEnabled (true);
                spell.setText ("Spell");
            }
            else{
                cooldown--;
                spell.setText ("Spell ("+(cooldown+1)+")");
            }
            potion.setEnabled(true);
            escape.setEnabled(true);
        }

    }

    private void endBattle(){

        map.setFocusable(true);
        map.requestFocus();
        thisFrame.dispose();
        map.started=false;
        if(map.defeated){
            map.repaint ();
        }
        Sounds.playMusic ("bgmusic.wav");

    }






}
