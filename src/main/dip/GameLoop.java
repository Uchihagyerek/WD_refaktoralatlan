package dip;

public class GameLoop extends Thread {
    private Thread loop=new Thread() {
        public void run() {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };
    public void startLoop(){
        
    }
}
