/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JourneyThroughEurope.game;
/**
 *
 * @author Maggie
 */
public class JTECities {
    private String name;
    private String color;
    private int quarter;
    private int x;
    private int y;
    
    public JTECities(String name, String color, int quarter, int x, int y){
        this.name = name;
        this.color = color;
        this.quarter = quarter;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("Name: %s Color: %s, Quarter: %d, X: %d, Y: %d", 
                this.name, this.color, this.quarter, this.x, this.y);
    } 
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }   
}
