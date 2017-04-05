package fr.genetic.client.java.api;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarView {
    private static SecureRandom random = new SecureRandom();

    public Chassis chassis = new Chassis();
    public Wheel wheel1 = new Wheel();
    public Wheel wheel2 = new Wheel();
    public CarView() {

    }

    public CarView(Chassis chassis, Wheel wheel1, Wheel wheel2) {
        this.chassis = chassis;
        this.wheel1 = wheel1;
        this.wheel2 = wheel2;
    }

    public CarView crossSimple(CarView other) {
        Chassis chassis = new Chassis();
        chassis.densite = firstOrOther(other).chassis.densite;
        List<Float> v = new ArrayList<>();
        for (int i = 0; i < this.chassis.vecteurs.size(); i++) {
            v.add(firstOrOther(other).chassis.vecteurs.get(i));
        }
        chassis.vecteurs = v;

        Wheel wheel1 = new Wheel();
        wheel1.density = firstOrOther(other).wheel1.density;
        wheel1.radius = firstOrOther(other).wheel1.radius;
        wheel1.vertex = firstOrOther(other).wheel1.vertex;
        Wheel wheel2 = new Wheel();
        wheel2.density = firstOrOther(other).wheel2.density;
        wheel2.radius = firstOrOther(other).wheel2.radius;
        wheel2.vertex = firstOrOther(other).wheel2.vertex;

        return new CarView(chassis, wheel1, wheel2);
    }

    private CarView firstOrOther(CarView other) {
        return random.nextBoolean() ? this : other;
    }

    public static class Chassis {
        public List<Float> vecteurs = new ArrayList<>();
        public float densite;

        @Override
        public String toString() {
            return "Chassis{" +
                    "vecteurs=" + vecteurs +
                    ", densite=" + densite +
                    '}';
        }
    }

    public static class Wheel {
        public float radius;
        public float density;
        public int vertex;

        @Override
        public String toString() {
            return "Wheel{" +
                    "radius=" + radius +
                    ", density=" + density +
                    ", vertex=" + vertex +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CarView{" +
                "chassis=" + chassis +
                ", wheel1=" + wheel1 +
                ", wheel2=" + wheel2 +
                '}';
    }
}
