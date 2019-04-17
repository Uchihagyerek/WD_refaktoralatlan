package dip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MainMenu extends Canvas {
    static JFrame thisFrame;
    JButton start;
    JButton highScores;
    JButton exit;
    String name;
    public MainMenu(){
        setSize(900,900);
    }



    @Override
    public void update(Graphics g) {paint(g);}
    @Override
    public void paint(Graphics ig) {
        BufferedImage image = new BufferedImage(900, 900, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect (0,0,900,900);

        ig.drawImage(image, 0,0,this);


    }
    public void ui(JFrame frame){
        thisFrame=frame;


        start =new JButton("Play");
        start.setBounds(250,450,400,150);
        start.setBackground(Color.WHITE);
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        highScores=new JButton("Hi-Scores");
        highScores.setBounds(250,600,400,150);
        highScores.setBackground(Color.WHITE);
        highScores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                highScores();
            }
        });


        exit =new JButton("Exit");
        exit.setBounds(250,750,400, 150);
        exit.setBackground(Color.WHITE);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitButton();
            }
        });

        frame.add(start);
        frame.add(highScores);
        frame.add(exit);


    }

    private void highScores() {
        HighScores hs=new HighScores();
        JFrame hiscr= new JFrame("Battle");
        hs.ui(hiscr);
        hiscr.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        hiscr.getContentPane().add(hs);

        hiscr.pack();
        hiscr.setVisible(true);
        hiscr.setResizable(false);
        hiscr.requestFocus();

    }

    private void startGame(){

        name = JOptionPane.showInputDialog("Enter your name");
        Map map=new Map(name);
        map.start();


        thisFrame.dispose();
    }

    private void exitButton(){
        thisFrame.dispose();

    }
    public void open(MainMenu menu){
        final JFrame frame=new JFrame();
        menu.ui(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(menu);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        menu.requestFocus();
    }

}
