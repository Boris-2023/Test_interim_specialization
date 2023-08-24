package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Controller.iGetModel;
import View.ViewRus;

public class ToysModel implements iGetModel {
    private List<Toy> toys;
    String fName = null;

    /**
     * @apiNote Creating new instance of toys shop - list of toys
     */
    public ToysModel(List<Toy> toys, String fname) {
        this.toys = toys;
        this.fName = fname;
    }

    /**
     * @apiNote adding new toy to the stock of toys
     * @param toy
     */
    public void addToy(Toy toy) {
        toys.add(toy);
    }

    /**
     * @apiNote making a random draw of the toys using their weights
     * @return Toy drown as a prize
     * @see ToysModel#toysDraw()
     */
    public Toy toysDraw() {

        Toy drawToy = null;

        if (!toys.isEmpty()) {

            // total sum of weights - cumulative sum
            double totalWeight = 0;

            for (Toy toy : toys) {
                totalWeight += toy.getWeight();
            }

            // random value in range 1 - totalWeigth
            Random rnd = new Random();
            double drawWeight = rnd.nextDouble() * totalWeight;

            // choosing corresponding toy

            totalWeight = 0;
            for (Toy toy : toys) {
                totalWeight += toy.getWeight();
                if (drawWeight <= totalWeight) {
                    drawToy = toy;
                    break;
                }
            }

        }
        return drawToy;

    }

    @Override
    public String toString() {
        String res = "";
        for (Toy toy : toys) {
            res = res + "\n" + toy.toString();
        }
        return res;
    }

    /**
     * @apiNote loading a list of toys from the file specified
     * 
     * @see ToysModel#getToysPresented()
     */
    @Override
    public List<String> getToysPresented() {
        List<String> presents = new LinkedList<>();
        try {
            FileReader fr = new FileReader(fName);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                presents.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return presents;
    }

    
    /**
     * @apiNote saving presented toy to the file
     * 
     * @param toyName - name of the toy that has been won and already presented
     * @see ToysModel#savePresentedToy(String)
     */
    public void savePresentedToy(String toyName) {
        try (FileWriter fw = new FileWriter(fName, true)) {
            fw.write(toyName);
            fw.append("\n");
            fw.flush();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
