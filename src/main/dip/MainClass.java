package dip;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainClass {
    public static void main(String[] args){

    MainMenu menu=new MainMenu();
    menu.open(menu);

    }
}
