package entities;

import OSPAnimator.AnimImageItem;
import OSPAnimator.AnimShapeItem;

import java.awt.geom.Point2D;
import java.util.Random;

public abstract class Entity {
    protected AnimShapeItem animShapeItem;
    protected Point2D currPosition;

    public AnimImageItem getAnimImageItem() {
        return animImageItem;
    }

    public void setAnimImageItem(AnimImageItem animImageItem) {
        this.animImageItem = animImageItem;
    }

    protected AnimImageItem animImageItem;
    public AnimShapeItem getAnimShapeItem() {
        return animShapeItem;
    }

    public void assignRandomPosition() {
        Random rand = new Random();
        this.currPosition = new Point2D.Double(rand.nextDouble() * 1500 + 1500, rand.nextDouble() * 1500);
    }

    public abstract void initAnimationObject();
    public void setAnimShapeItem(AnimShapeItem animShapeItem) {
        this.animShapeItem = animShapeItem;
    }

    public Point2D getCurrPosition() {
        return currPosition;
    }

    public void setCurrPosition(Point2D currPosition) {
        this.currPosition = currPosition;
    }
}
