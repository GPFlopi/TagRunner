import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.security.Key;


public class main {

    public static JFrame frame = new JFrame();
    public static Map map = new Map(12,frame);

    public static void main(String[] args) throws FileNotFoundException {


        frame.setVisible(true);
        frame.setSize(500, 500);
        frame.setBackground(Color.blue);
        GridLayout grid = new GridLayout(12, 12, 3, 3);
        frame.setLayout(grid);

        map.readMap('2');
        map.compileMap();
        //map.testMap();
        map.update();

        KeyListener press = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            //X-Down/Up  Y-Right/Left
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(key == KeyEvent.VK_RIGHT){
                    if(map.isTileWall(map.getRunX(),map.getRunY()+1)){
                        map.replaceTile(map.getRunX(),map.getRunY()+1,map.getRunX(),map.getRunY());
                        map.update();
                        e.setKeyCode(KeyEvent.VK_F);
                        keyPressed(e);
                    }
                }else if(key == KeyEvent.VK_LEFT){
                    if(map.isTileWall(map.getRunX(),map.getRunY()-1)){
                        map.replaceTile(map.getRunX(),map.getRunY()-1,map.getRunX(),map.getRunY());
                        map.update();
                        e.setKeyCode(KeyEvent.VK_F);
                        keyPressed(e);
                    }
                }else if(key == KeyEvent.VK_UP){
                    if(map.isTileWall(map.getRunX()-1,map.getRunY())){
                        map.replaceTile(map.getRunX()-1,map.getRunY(),map.getRunX(),map.getRunY());
                        map.update();
                        e.setKeyCode(KeyEvent.VK_F);
                        keyPressed(e);
                    }
                }else if(key == KeyEvent.VK_DOWN){
                    if(map.isTileWall(map.getRunX()+1,map.getRunY())){
                        map.replaceTile(map.getRunX()+1,map.getRunY(),map.getRunX(),map.getRunY());
                        map.update();
                        e.setKeyCode(KeyEvent.VK_F);
                        keyPressed(e);
                    }
                }else if(key == KeyEvent.VK_D){ //temp Huner controlers
                    if(map.isTileWall(map.getHunX(),map.getHunY()+1)){
                        map.replaceTile(map.getHunX(),map.getHunY()+1,map.getHunX(),map.getHunY());
                        map.update();
                    }
                }else if(key == KeyEvent.VK_A){ //temp Huner controlers
                    if(map.isTileWall(map.getHunX(),map.getHunY()+-1)){
                        map.replaceTile(map.getHunX(),map.getHunY()-1,map.getHunX(),map.getHunY());
                        map.update();
                    }
                }else if(key == KeyEvent.VK_W){ //temp Huner controlers
                    if(map.isTileWall(map.getHunX()-1,map.getHunY())){
                        map.replaceTile(map.getHunX()-1,map.getHunY(),map.getHunX(),map.getHunY());
                        map.update();
                    }
                }else if(key == KeyEvent.VK_S){ //temp Huner controlers
                    if(map.isTileWall(map.getHunX()+1,map.getHunY())){
                        map.replaceTile(map.getHunX()+1,map.getHunY(),map.getHunX(),map.getHunY());
                        map.update();
                    }
                }else if(key == KeyEvent.VK_F){
                    int nextStep= map.pathFinder();
                    //System.out.println(nextStep);   //Where to step next(ID)

                    if(nextStep!=map.getRunID()) {
                        map.replaceTile(map.searchId(map.getHunID()), map.searchId(nextStep));
                        map.update();
                        map.resetValues();
                    }else {
                        JOptionPane.showMessageDialog(frame,"You lost");
                        map.endGame();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        frame.addKeyListener(press);


    }

}
