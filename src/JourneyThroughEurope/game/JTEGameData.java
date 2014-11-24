/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JourneyThroughEurope.game;
import java.util.List;
import application.Main.JTEPropertyType;
import properties_manager.PropertiesManager;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
/**
 *
 * @author Maggie
 */

public class JTEGameData {
    
    public static List<List<JTECities>> loadCities() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String CityPath = props.getProperty(JTEPropertyType.CITIES_PATH);
        List<List<JTECities>> cities = new ArrayList<List<JTECities>>();
        cities.add(new ArrayList<>());
        cities.add(new ArrayList<>());
        cities.add(new ArrayList<>());
        cities.add(new ArrayList<>());
        
        /* Loads cities into an ArrayList */
        try{
            File csvFile = new File(CityPath);
            if(csvFile.exists()) {
                Scanner file = new Scanner(csvFile, "UTF-16LE");
                while(file.hasNextLine()){
                    String line = file.nextLine();
                    String elements[] = line.split("\t");
                    if(elements.length == 5) {
                        String name = elements[0];
                        String color = elements[1];
                        Integer quarter = Integer.parseInt(elements[2]);
                        Integer x = Integer.parseInt(elements[3]);
                        Integer y = Integer.parseInt(elements[4]);
                        JTECities city = new JTECities(name, color, quarter, x, y);
                        //separated by quadrant
                        cities.get(quarter - 1).add(city);   
                    }
                }
            } else {
                System.out.println(String.format("The file '%s' does not exist.", csvFile.getPath()));
            }
        }catch(Exception e){
           e.printStackTrace();
        }
        return cities;
    }
    
}
