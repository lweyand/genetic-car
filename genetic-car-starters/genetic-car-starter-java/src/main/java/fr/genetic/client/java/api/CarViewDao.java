package fr.genetic.client.java.api;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by Mikael on 05.04.2017.
 */
public class CarViewDao {

    public static final String CARS_JSON = "./cars.json";

    public void save(Collection<CarView> carViews) throws Exception{
        Gson gson  = new Gson();
        CarView[] carViewArray = new ArrayList<CarView>(carViews).toArray(new CarView[]{});
        FileWriter fw = new FileWriter(CARS_JSON);
        gson.toJson(carViewArray,fw );
        System.out.println(        gson.toJson(carViewArray));
        fw.flush();
        fw.close();
    }

    public List<CarView> restoreLastSaved(){
        try {
            Gson gson = new Gson();
            FileReader fr = new FileReader(CARS_JSON);
            CarView[] carViews = gson.fromJson(fr, CarView[].class);

            List<CarView> result = Arrays.asList(carViews);
            return result;
        }catch (Exception ex){
          ex.printStackTrace();
        }
        return Collections.emptyList();
    }

}
