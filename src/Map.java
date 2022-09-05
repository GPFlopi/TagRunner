import MapTiles.*;

import javax.crypto.SecretKey;
import javax.lang.model.util.Elements;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    private int idCounter=0;
    public  ArrayList<ArrayList<Entity>> Omap = new ArrayList<ArrayList<Entity>>();

    public int resetsCounter=0;
    public ArrayList<Entity> NeighbourOpen = new ArrayList<>();
    public ArrayList<Entity> NeighbourClosed = new ArrayList<>();

    public ArrayList<Entity> rawMap = new ArrayList<>();
    private static int MapSize;

    private int RunX,RunY=-1,RunID;
    private int HunX,HunY=-1,HunID;
    private int GoalX,GoalY=-1,GoalID;

    private boolean isSearhing=true;

    JFrame frame;


    public Map(int mapSize,JFrame frame) {
        MapSize = mapSize;
        this.frame=frame;
    }

    public void update(){
        frame.getContentPane().removeAll();
        frame.repaint();
        for(int i = 0; i< MapSize; i++){
            for(int j = 0; j< MapSize; j++){
                JButton button = new JButton();
                button.setEnabled(false);
                /*if(Omap.get(i).get(j).getType()==0) {
                    button.setText(String.valueOf("<html><center>"+Omap.get(i).get(j).getId()+"<br />"+Omap.get(i).get(j).getFcost()+/*"</center></html>"+"<br />G"
                            +String.valueOf(Omap.get(i).get(j).getGcost())+" + H"+
                            String.valueOf(Omap.get(i).get(j).getHcost())+"</center></html>"));
                }*/
                if (Omap.get(i).get(j).getType()==4) {
                    button.setBackground(Omap.get(i).get(j).getColor());
                }else if (Omap.get(i).get(j).getType()==0) {
                    button.setBackground(Omap.get(i).get(j).getColor());
                }else if (Omap.get(i).get(j).getType()==1) {
                    button.setBackground(Omap.get(i).get(j).getColor());
                }else if (Omap.get(i).get(j).getType()==2) {
                    button.setBackground(Omap.get(i).get(j).getColor());
                }else if (Omap.get(i).get(j).getType()==3) {
                    button.setBackground(Omap.get(i).get(j).getColor());
                }
                frame.add(button);
            }
        }

        frame.setVisible(true);


    }

    public void readMap(char lvl) throws FileNotFoundException {
        File myfile = new File("src/Levels/Level"+lvl);
        Scanner fileReader =  new Scanner(myfile);
        fileReader.useDelimiter(" ");
        fileReader.tokens();
        int i=0;
        while(fileReader.hasNext()){
            String readData = fileReader.next();
            switch (readData.strip()) {
                case "4" :
                    rawMap.add(new Obstacle(idCounter));
                    idCounter++;
                    break;
                case "0" :
                    rawMap.add(new Tile(idCounter));
                    idCounter++;
                    break;
                case "1" :
                    rawMap.add(new Hunter(idCounter));
                    idCounter++;
                    break;
                case "2" :
                    rawMap.add(new Runner(idCounter));
                    idCounter++;
                    break;
                case "3" :
                    rawMap.add(new Goal(idCounter));
                    idCounter++;
                    break;
                default:
                    System.err.println("Something is wrong with reading the file! - "+readData);
                    //map.add(new Obstacle());
                    break;

            }
            i++;
        }
        //System.out.println("This ran "+i+" times");
        fileReader.close();
    }

    public void testMap(){
        for(int i = 0; i< MapSize; i++){
            for(int j = 0; j< MapSize; j++) {
                System.out.print(Omap.get(i).get(j).getType());
            }
        }
        //System.out.println(map.get(1).size());
    }

    public  void compileMap(){
        int k=0;

        for(int i = 0; i< MapSize; i++){
            ArrayList<Entity> temp = new ArrayList<>();
            for(int j = 0; j< MapSize; j++){
                rawMap.get(k).setEntityX(i);
                rawMap.get(k).setEntityY(j);
                temp.add(rawMap.get(k));
                if(temp.get(temp.size()-1).getType()==1) {
                    HunX=i;
                    HunY=j;
                    HunID=temp.get(temp.size()-1).getId();

                }else if(temp.get(temp.size()-1).getType()==2) {
                    RunX=i;
                    RunY=j;
                    RunID=temp.get(temp.size()-1).getId();
                }else if(temp.get(temp.size()-1).getType()==3) {
                    GoalX=i;
                    GoalY=j;
                    GoalID=temp.get(temp.size()-1).getId();
                }
                k++;
            }
            Omap.add(temp);
        }
    }

    public void delMap(){
        Omap.clear();
        rawMap.clear();
    }

    public boolean isTileWall(int x, int y){
        return !Omap.get(x).get(y).isWall();
    }

    public void assignNeighbours(Entity curr) {
        int x=curr.getEntityX(),y=curr.getEntityY();
        /*x += 1;
        if(!Omap.get(x).get(y).isWall() && !NeighbourClosed.contains(Omap.get(x).get(y))) assignNeighbourHelper(x, y, curr);
        x -= 2;
        if(!Omap.get(x).get(y).isWall() && !NeighbourClosed.contains(Omap.get(x).get(y))) assignNeighbourHelper(x, y, curr);
        x += 1;
        y += 1;
        if(!Omap.get(x).get(y).isWall() && !NeighbourClosed.contains(Omap.get(x).get(y))) assignNeighbourHelper(x, y, curr);
        y -= 2;
        if(!Omap.get(x).get(y).isWall() && !NeighbourClosed.contains(Omap.get(x).get(y))) assignNeighbourHelper(x, y, curr);
        y += 1;*/

        for(int i=0;i<4;i++){
            switch (i) {
                case 0 -> {
                    x += 1;
                    if (!Omap.get(x).get(y).isWall() && !NeighbourClosed.contains(Omap.get(x).get(y)))
                        assignNeighbourHelper(x, y, curr);
                }
                case 1 -> {
                    x -= 2;
                    if (!Omap.get(x).get(y).isWall() && !NeighbourClosed.contains(Omap.get(x).get(y)))
                        assignNeighbourHelper(x, y, curr);
                }
                case 2 -> {
                    x += 1;
                    y += 1;
                    if (!Omap.get(x).get(y).isWall() && !NeighbourClosed.contains(Omap.get(x).get(y)))
                        assignNeighbourHelper(x, y, curr);
                }
                case 3 -> {
                    y -= 2;
                    if (!Omap.get(x).get(y).isWall() && !NeighbourClosed.contains(Omap.get(x).get(y)))
                        assignNeighbourHelper(x, y, curr);
                    y += 1;
                }
                default -> System.err.println("AssignNeigbours for switch case fault");
            }
        }
        NeighbourOpen.remove(Omap.get(x).get(y));
        addToClose(Omap.get(x).get(y));
    }

    public void assignNeighbourHelper(int x,int y, Entity curr){
            /*if (x == -1 || y == -1) {
                if (x == -1) x = 0;
                if (y == -1) y = 0;
            }
            if (x == 12 || y == 12) {
                if (x == 12) x = 11;
                if (y == 12) y = 11;
            }*/
        if (Omap.get(x).get(y).getFcost() == -1) {
            Omap.get(x).get(y).setParentId(curr.getId());
            FcostCalc(x, y);
            addToOpen(Omap.get(x).get(y));
        } else {
            int oldFcost = Omap.get(x).get(y).getFcost();
            FcostCalc(x, y);
            if (oldFcost < Omap.get(x).get(y).getFcost()) {
                Omap.get(x).get(y).setFcost(oldFcost);
                Omap.get(x).get(y).setParentId(curr.parentId);
            }

        }
    }


    public void FcostCalc(int x,int y){

        if(Omap.get(x).get(y).getFcost()==-1) {
            //hunter
            int tempGcost = 0, tempHcost = 0;
            tempGcost=countParent(Omap.get(x).get(y))*10;


            //runner
            int tempX2 = distanceCalc(x, getRunX());
            int tempY2 = distanceCalc(y, getRunY());
            if (tempX2 == 0 && tempY2 == 0) {
                tempHcost = 10;
            } else tempHcost += ((tempX2) + (tempY2)) * 10;

            Omap.get(x).get(y).setGcost(tempGcost);
            Omap.get(x).get(y).setHcost(tempHcost);
            Omap.get(x).get(y).setFcost(tempGcost + tempHcost);
        }
    }

    public Entity lowestFcost(){
        int min=9999;

        Entity returnEntity=NeighbourOpen.get(0);

        for(Entity e: NeighbourOpen){
            if(e.getFcost()<min){
                min=e.getFcost();
                returnEntity=e;
            }
        }
        return returnEntity;
    }

    public boolean isThereMultipleF(int lowFcost){
        int countMultiples=0;
        for(Entity e: NeighbourOpen){
            if(lowFcost==e.getFcost()){
                countMultiples++;
            }
        }

        if(countMultiples==1) return false;
        else return true;
    }

    public Entity lowestHcost(int lowFcost){
        int min=9999;
        Entity returnEntity=null;
        for(Entity e: NeighbourOpen){
            if(lowFcost==e.getFcost() && e.getHcost()<min){
                min=e.getHcost();
                returnEntity=e;
            }
        }
        return returnEntity;
    }

    public int countParent(Entity entity) throws NullPointerException {
        int ret = 0;


        boolean parentNotFound = true;
        while (parentNotFound) {
            if (searchId(entity.parentId).getType() == 1) {
                ret++;
                parentNotFound = false;
            } else {
                ret++;
                entity = searchId(entity.parentId);
            }
        }
        return ret;
    }

    public int distanceCalc(int a,int b){
        int ret;
        if(a>b) return a-b;
        else return b-a;
    }

    public int pathFinder(){
        int ret;
        System.out.println("start");
        addToOpen(searchId(HunID));

        while(isSearhing){
            containsRunner();
            Entity curr=lowestFcost();

            if(curr.getId()==51)
                System.out.println(curr.getId());
            if(isThereMultipleF(curr.getFcost())){
                lowestHcost(curr.getFcost());
            }
            if(curr.getType()==2){
                isSearhing=false;
            }else assignNeighbours(curr);
        }


        System.out.println("end");
        return FindThePath(RunID);
    }


    public int FindThePath(int id) throws NullPointerException {
        try {
            while (true) {
                if (searchId(searchId(id).parentId).getType() == 1) {
                    return searchId(id).getId();
                } else id = searchId(id).getParentId();
            }
        }catch (NullPointerException nullException){
            System.err.println(nullException);
            return 0;
        }
    }

    public void containsRunner(){
        if(NeighbourOpen.contains(searchId(RunID))){
            searchId(RunID).setFcost(0);
            isSearhing=false;

        }
    }


    public Entity searchId(int id){

        for (ArrayList<Entity> entities : Omap) {
            for (Entity e: entities){
                if(e.getId()==id) {
                    return e;
                }
            }
        }
        return null;
    }

    public void addToOpen(Entity e){
        //e.setColor(Color.green);
        NeighbourOpen.add(e);
        update();
    }
    public void addToClose(Entity e){
        //e.setColor(Color.red);
        NeighbourClosed.add(e);
        update();
    }

    public void resetValues(){
        NeighbourOpen.removeAll(NeighbourOpen);
        NeighbourClosed.removeAll(NeighbourClosed);
        isSearhing=true;
        resetHunterID();

        for(ArrayList<Entity> arr:Omap){
            for(Entity e: arr){
                e.setParentId(-1);
                e.setHcost(0);
                e.setGcost(0);
                e.setFcost(-1);
            }
        }
        resetsCounter++;

    }

    public void resetHunterID(){
        for(ArrayList<Entity> arr:Omap){
            for(Entity e: arr) {
                if(e.getType()==1){
                    setHunID(e.getId());
                    e.setEntityX(getHunX());
                    e.setEntityY(getHunY());


                }
            }
        }
    }

    public void replaceTile(Entity hunter, Entity nextloc){
        int temp1=nextloc.getType();
        boolean temp2=nextloc.isCanMove();
        Color temp3=nextloc.getColor();
        boolean temp4=nextloc.isWall();

        nextloc.setType(hunter.getType());
        nextloc.setCanMove(hunter.isCanMove());
        nextloc.setColor(hunter.getColor());
        nextloc.setWall(hunter.isWall());
        setHunX(nextloc.getEntityX());
        setHunY(nextloc.getEntityY());
        setHunID(nextloc.getId());
        hunter.setType(temp1);
        hunter.setCanMove(temp2);
        hunter.setColor(temp3);
        hunter.setWall(temp4);
    }

    public void replaceTile(int xt, int yt,int xo,int yo){

       Entity target = Omap.get(xt).get(yt);
       Entity origin = Omap.get(xo).get(yo);

       if(target.getType()!=3) {
           int tempType = origin.getType();
           boolean tempMove = origin.isCanMove();
           boolean tempWall = origin.isWall();
           Color tempColor = origin.getColor();

           origin.setType(target.getType());
           target.setType(tempType);

           origin.setCanMove(target.isCanMove());
           target.setCanMove(tempMove);

           origin.setWall(target.isWall());
           target.setWall(tempWall);

           origin.setColor(target.getColor());
           target.setColor(tempColor);

           if (target.getType() == 2) {
               setRunX(target.getEntityX());
               setRunY(target.getEntityY());
               setRunID(target.getId());
           } else if (target.getType() == 1) {
               setHunX(target.getEntityX());
               setHunY(target.getEntityY());
               setHunID(target.getId());
           }
       }else{
           JOptionPane.showMessageDialog(frame,"Congrats you won");
           endGame();
       }
    }

    public void endGame(){
        frame.setVisible(false);
    }

    //<editor-fold desc="Getter-Setter">
    public static int getMapSize() {
        return MapSize;
    }

    public static void setMapSize(int mapSize) {
        MapSize = mapSize;
    }

    public int getRunX() {
        return RunX;
    }

    public void setRunX(int runX) {
        RunX = runX;
    }

    public int getRunY() {
        return RunY;
    }

    public void setRunY(int runY) {
        RunY = runY;
    }

    public int getHunX() {
        return HunX;
    }

    public void setHunX(int hunX) {
        HunX = hunX;
    }

    public int getHunY() {
        return HunY;
    }

    public void setHunY(int hunY) {
        HunY = hunY;
    }

    public int getGoalX() {
        return GoalX;
    }

    public void setGoalX(int goalX) {
        GoalX = goalX;
    }

    public int getGoalY() {
        return GoalY;
    }

    public void setGoalY(int goalY) {
        GoalY = goalY;
    }

    public int getRunID() {
        return RunID;
    }

    public void setRunID(int runID) {
        RunID = runID;
    }

    public int getHunID() {
        return HunID;
    }

    public void setHunID(int hunID) {
        HunID = hunID;
    }

    public int getGoalID() {
        return GoalID;
    }

    public void setGoalID(int goalID) {
        GoalID = goalID;
    }

    //</editor-fold>
}
//TODO figure out why assignNeighbours is slow