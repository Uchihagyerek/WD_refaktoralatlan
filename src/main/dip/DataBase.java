package dip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataBase {
    Connection conn;

    public DataBase() {
        conn = null;
    }

    private void open(){
        try {
            String url = "jdbc:sqlite:src/db/maindb.db";
            conn = DriverManager.getConnection(url);



        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void close() {
        try {
            if (conn != null) {
                conn.close();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String[] getMonster() {
        open();
        String[] monster;
        List<String[]> monsters = new ArrayList<String[]>();
        Random rand = new Random();

        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM monsters WHERE boss=0");

            while (rs.next()) {
                monster = new String[5];
                monster[0] = rs.getString("id");
                monster[1] = rs.getString("name");
                monster[2] = rs.getString("health");
                monster[3] = rs.getString("mana");
                monster[4] = rs.getString("damage");

                monsters.add(monster);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        monster = monsters.get(rand.nextInt(monsters.size()));

        close();

        return monster;
    }

    public String[] getBoss() {
        open();
        String[] monster;
        List<String[]> monsters = new ArrayList<>();
        Random rand = new Random();


        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM monsters WHERE boss=1");

            while (rs.next()) {
                monster = new String[5];
                monster[0] = rs.getString("id");
                monster[1] = rs.getString("name");
                monster[2] = rs.getString("health");
                monster[3] = rs.getString("mana");
                monster[4] = rs.getString("damage");

                monsters.add(monster);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        monster = monsters.get(rand.nextInt(monsters.size()));
        close();

        return monster;
    }

    public void setScore(int score, String playerName) {
        open();

        String sql = "INSERT INTO scores(score,player_id) VALUES("+score+",(SELECT id FROM player WHERE name=\""+playerName+"\"))";



        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        close();
    }

    public String[] getPlayers(){
        open();
        String[] players=new String[50];
        int i=0;

        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT name FROM player");

            while (rs.next()){
              players[i]=rs.getString("name");
              i++;
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

        close();
        return players;
    }

    public void newPlayer(String name){
        open();

        String sql = "INSERT INTO player(name) VALUES(\""+name+"\")";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        close();

    }

    public List<String[]> getScores(){
        open();

        String sql="SELECT name, score FROM player INNER JOIN scores ON player.id = scores.player_id ORDER BY score DESC";

        String[] score;
        List<String[]> scores = new ArrayList<>();

        try{
            Statement stm=conn.createStatement();
            ResultSet rs=stm.executeQuery(sql);

            while (rs.next()){
                score=new String[2];
                score[0]=rs.getString("name");
                score[1]=rs.getString("score");
                scores.add(score);

            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        close();

        return scores;
    }


}
