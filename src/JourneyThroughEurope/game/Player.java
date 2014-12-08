package JourneyThroughEurope.game;

import JourneyThroughEurope.ui.City;
import JourneyThroughEurope.ui.JTEUI;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Maggie
 */
public class Player{
    private ArrayList<City> allCities;
    private City redCard;
    private City greenCard;
    private City yellowCard;
    private JTEUI ui;
    //Player #1-6
    private int number;
    public Player(){
    }
    public void setRedCard(City redCard){
        this.redCard = redCard;
    }
    public void setGreenCard(City greenCard){
        this.greenCard = greenCard;
    }
    public void setYellowCard(City yellowCard){
        this.yellowCard = yellowCard;
    }
    public City getRedCard(){
        return this.redCard;
    }
    public City getGreenCard(){
        return this.greenCard;
    }
    public City getYellowCard(){
        return this.yellowCard;
    }
    public void initPosition(int i){
        this.number = i;
        int quad = this.redCard.getQuad();
        double x = this.redCard.getX() / 4.429 - 7;
        double y = this.redCard.getY() / 4.429 - 7;
        ui.addFigures(quad, x, y, i);
    }
    public void setNumber(int i){
        this.number = i;
    }
}
