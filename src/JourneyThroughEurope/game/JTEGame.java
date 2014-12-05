package JourneyThroughEurope.game;

import java.util.ArrayList;
import JourneyThroughEurope.ui.City;
import JourneyThroughEurope.ui.JTEEventHandler;
import JourneyThroughEurope.ui.JTEUI;
import java.util.Collections;

/**
 *
 * @author Maggie
 *
 * Created when the start button is pressed. Contains a startTurn method
 * Contains a diceRoll method Contains a movePlayer method Contains a dealCards
 * method ArrayList of Player objects
 *
 */
public class JTEGame {
    private int totalPlayers;
    private boolean isOver;
    private ArrayList<City> allCities;
    private boolean dealtCards;
    private ArrayList<Player> allPlayers;
    private JTEUI ui;
    private City redCard;
    private City greenCard;
    private City yellowCard;
    
    public JTEGame(int numPlayers, ArrayList<City> allCities) {
        this.totalPlayers = numPlayers;
        this.isOver = false;
        this.dealtCards = false;
        //this.allCities = ui.getAllCities();
        this.allCities = allCities;
    }

    public void startTurn() {
        while(isOver == false){
            for(int i = 0; i < allPlayers.size(); i++){
                
                i++;
                if(i == allPlayers.size() - 1){
                    i = 0; 
                }
            }
        }
    }   

    public void rollDice() {
    //random # between 1-6, load imge of random #

    }

    public void movePlayer() {
        //moves player to correct coordinate
        
    }

    public void endTurn() {
        //moves to next player object
    }

    public void dealCards(Player player) {
        ArrayList<City> redRackCities = new ArrayList();
        ArrayList<City> greenRackCities = new ArrayList();
        ArrayList<City> yellowRackCities = new ArrayList();
        for (City rackCity : allCities) {
            if (rackCity.getColor().equalsIgnoreCase("red") && (rackCity.getIsChosen() == false)) {
//                rackCity.setIsChosen(true);
                redRackCities.add(rackCity);
                
            }
        }
        for (City rackCity : allCities) {
            if (rackCity.getColor().equalsIgnoreCase("green") && (rackCity.getIsChosen() == false)) {
                //rackCity.setIsChosen(true);
                greenRackCities.add(rackCity);
                
            }
        }
        for (City rackCity : allCities) {
            if (rackCity.getColor().equalsIgnoreCase("yellow") && (rackCity.getIsChosen() == false)) {
                //rackCity.setIsChosen(true);
                yellowRackCities.add(rackCity);
            }
        }

        Collections.shuffle(redRackCities);
        Collections.shuffle(greenRackCities);
        Collections.shuffle(yellowRackCities);

//        System.out.println(redRackCities.size());
        redCard = redRackCities.get(0);
        greenCard = greenRackCities.get(0);
        yellowCard = yellowRackCities.get(0);
        
        player.setRedCard(redCard);
        player.setGreenCard(greenCard);
        player.setYellowCard(yellowCard);
        
        System.out.println(redCard.getName());
        System.out.println(greenCard.getName());
        System.out.println(yellowCard.getName());
    }
    public void setAllPlayers(ArrayList<Player> allPlayers){
        this.allPlayers = allPlayers;
    }
    public ArrayList<Player> getAllPlayers(){
        return this.allPlayers;
    }
}
