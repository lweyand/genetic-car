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
        fw.flush();
        fw.close();
    }

    public Collection<CarView> restoreLastSaved(){
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

    public static void main(String[] args) throws Exception{
        CarViewDao carViewDao = new CarViewDao();

        List<CarView> carViews = new ArrayList<>();
        CarView cv = new CarView();
        cv.chassis.vecteurs.add(new Float(1.1));
        cv.wheel1.radius=1.1f;
        cv.wheel1.density=1.1f;
        cv.wheel1.vertex=3;

        cv.wheel2.radius=2.1f;
        cv.wheel2.density=3.1f;
        cv.wheel2.vertex=3;

        carViews.add(cv);

        carViewDao.save(carViews);

        Collection<CarView> load = carViewDao.restoreLastSaved();
        System.out.println(load.iterator().next());

    }

}
