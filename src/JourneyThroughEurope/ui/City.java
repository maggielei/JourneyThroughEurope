package JourneyThroughEurope.ui;
import java.util.ArrayList;
/**
 *
 * @author Maggie
 */
public class City {
    private String name;
    private ArrayList<String> land = new ArrayList();
    private ArrayList<String> sea = new ArrayList();
    private boolean hasInstruction = false;
    private boolean isChosen = false;
    private String color;
    private int quadrant;
    private double x;
    private double y;
    
    public City(String name, ArrayList land, ArrayList sea){
        this.name = name;
        this.land = land;
        this.sea = sea;
    }
    public City(boolean hasInstruction, String color, int quadrant, double x, double y){
        this.hasInstruction = hasInstruction;
        this.color = color;
        this.quadrant = quadrant;
        this.x = x;
        this.y = y;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setColor(String color){
        this.color = color;
    }
    public String getColor(){
        return this.color;
    }
    public ArrayList getLand(){
        return land;
    }
    public ArrayList getSea(){
        return sea;
    }
    public void setHasInstruction(boolean x){
        this.hasInstruction = x;
    }
    public boolean getHasInstruction(){
        return hasInstruction;
    }
    public void setIsChosen(boolean x){
        this.isChosen = x;
    }
    public boolean getIsChosen(){
        return isChosen;
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public void setX(double newX){
        this.x = newX;
    }
    public void setY(double newY){
        this.y = newY;
    }
    public int getQuad(){
        return this.quadrant;
    }
    public void setQuad(int newQuad){
        this.quadrant = newQuad;
    }
}
