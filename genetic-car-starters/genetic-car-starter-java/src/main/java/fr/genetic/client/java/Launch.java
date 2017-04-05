package fr.genetic.client.java;

import fr.genetic.client.java.algo.Car;
import fr.genetic.client.java.api.CarScoreView;
import fr.genetic.client.java.api.CarView;
import fr.genetic.client.java.api.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class Launch implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Launch.class);

    @Value("${genetic.server.host}")
    private String host;

    @Autowired
    private RestTemplate restTemplate;

    private Team team = Team.RED;


    public static void main(String[] args) {
        SpringApplication.run(Launch.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            doMyAlgo();
        } catch (RestClientException restException) {
            LOGGER.error(restException.getMessage());
        }
    }

    private List<CarScoreView> evaluate(List<CarView> cars) {
        String url = host + "/simulation/evaluate/" + team.name();
        return restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity(cars), new ParameterizedTypeReference<List<CarScoreView>>() {
                }).getBody();
    }

    private static SecureRandom random = new SecureRandom();

    protected void doMyAlgo() {
        List<CarView> cars = IntStream.range(0, 20)
                .mapToObj(i -> Car.random().toCarView())
                .collect(Collectors.toList());
        List<CarScoreView> carScores = null;
        for (int i = 0; i < 10; i++) {
            carScores = evaluate(cars);
            // selection
            List<CarView> best = selectBest(carScores);

            // crossManyCars
            cars = crossManyCars(carScores);
            cars.addAll(best);

            // mutation
        }

        CarScoreView champion = carScores.stream()
                .max((carScore1, carScore2) -> Float.compare(carScore1.score, carScore2.score))
                .get();
        LOGGER.info("Mon champion est {}", champion);
    }

    private List<CarView> selectBest(List<CarScoreView> carScores) {
        List<CarView> best = carScores.stream() //
                .sorted((carScore1, carScore2) -> -Float.compare(carScore1.score, carScore2.score)) //
                .limit(4) //
                .map(c -> c.car) //
                .collect(Collectors.toList());
        List<Float> scores = carScores.stream() //
                .sorted((carScore1, carScore2) -> -Float.compare(carScore1.score, carScore2.score)) //
                .limit(4) //
                .map(c -> c.score) //
                .collect(Collectors.toList());
        System.out.println(scores);
        System.out.println(best);
        return best;
    }

    private List<CarView> crossManyCars(List<CarScoreView> carScores) {
        return IntStream.range(0, (int) (20 * 0.8))
                .mapToObj(j -> {
                    CarView car1 = carScores.get(random.nextInt(20)).car;
                    CarView car2 = carScores.get(random.nextInt(20)).car;
                    return car1.crossSimple(car2);
                }).collect(Collectors.toList());
    }

}
