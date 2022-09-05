package MapTiles;

import java.awt.*;

public class Entity {
    protected int type;
    private Color color;
    protected final int id;
    protected boolean isWall;
    protected boolean canMove;
    public int parentId=-1;
    protected int entityX=-1,entityY=-1;
    protected int Gcost,Hcost,Fcost=-1;
    public Entity(int type, boolean isWall, boolean canMove, int id) {
        this.id = id;
        this.isWall = isWall;
        this.canMove = canMove;
        this.type=type;
        this.setParentId(0);

    }


    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public int getId() {
        return id;
    }

    public int getGcost() {
        return Gcost;
    }

    public void setGcost(int gcost) {
        Gcost = gcost;
    }

    public int getHcost() {
        return Hcost;
    }

    public void setHcost(int hcost) {
        Hcost = hcost;
    }

    public int getFcost() {
        return Fcost;
    }

    public void setFcost(int fcost) {
        Fcost = fcost;
    }

    public int getEntityX() {
        return entityX;
    }

    public void setEntityX(int entityX) {
        this.entityX = entityX;
    }

    public int getEntityY() {
        return entityY;
    }

    public void setEntityY(int entityY) {
        this.entityY = entityY;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
