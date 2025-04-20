package Generation;

import java.util.ArrayList;
import java.util.Random;

import Generators.*;

public class Generators {
    private final Exponential orderArrivalDist;
    private final EmpiricDiscrete typeOfOrderDist;
    private final EmpiricContinuous cuttingTableDist;
    private final UniformContinuous cuttingChairDist;
    private final UniformContinuous cuttingWardrobeDist;
    private final EmpiricContinuous paintingTableDist;
    private final UniformContinuous paintingChairDist;
    private final UniformContinuous paintingWardrobeDist;
    private final UniformContinuous pickilingTableDist;
    private final UniformContinuous pickilingChairDist;
    private final UniformContinuous pickilingWardrobeDist;


    private final UniformContinuous assemblyTableDist;
    private final UniformContinuous assemblyChairDist;
    private final UniformContinuous assemblyWardrobeDist;
    private final UniformContinuous montageWardrobeDist;
    private final Triangular timeSpentInStorageDist;
    private final Triangular timeMovingIntoStorageDist;
    private final Triangular timeMovingToAnotherWorkshopDist;
    private final Triangular accessingOrderDist;

    private final UniformDiscrete countOfFurnitureDist;
    private final Random realisePaintingDist;

    public Generators() {
        this.accessingOrderDist = new Triangular(2.0 * 60, 8.0 * 60, 4.0 *60);
        this.realisePaintingDist = new Random();
        Random rand = new Random();

        countOfFurnitureDist = new UniformDiscrete(1,6, rand.nextInt());
        orderArrivalDist = new Exponential(1800.0, rand.nextInt());
        ArrayList<EmpiricData<Integer>> typeList = new ArrayList<>();


        typeList.add(new EmpiricData<>(1, 2, 0.5, rand.nextInt()));
        typeList.add(new EmpiricData<>(2, 3, 0.15, rand.nextInt()));
        typeList.add(new EmpiricData<>(3, 4, 0.35, rand.nextInt()));
        typeOfOrderDist = new EmpiricDiscrete(typeList, rand.nextInt());


        ArrayList<EmpiricData<Double>> typeOneCuttingList = new ArrayList<>();
        typeOneCuttingList.add(new EmpiricData<>(10.0 * 60, 25.0 * 60, 0.6, rand.nextInt()));
        typeOneCuttingList.add(new EmpiricData<>(25.0 * 60, 50.0 * 60, 0.4, rand.nextInt()));

        ArrayList<EmpiricData<Double>> typeOnePaintingList = new ArrayList<>();
        typeOnePaintingList.add(new EmpiricData<>(50.0 * 60, 70.0 * 60, 0.1, rand.nextInt()));
        typeOnePaintingList.add(new EmpiricData<>(70.0 * 60, 150.0 * 60, 0.6, rand.nextInt()));
        typeOnePaintingList.add(new EmpiricData<>(150.0 * 60, 200.0 * 60, 0.3, rand.nextInt()));

        //first
        cuttingTableDist = new EmpiricContinuous(typeOneCuttingList, rand.nextInt());
        paintingTableDist = new EmpiricContinuous(typeOnePaintingList, rand.nextInt());
        pickilingTableDist = new UniformContinuous(200.0 * 60, 610.0 * 60, rand.nextInt());
        assemblyTableDist = new UniformContinuous(30.0 * 60, 60.0 * 60, rand.nextInt());

        // second
        cuttingChairDist = new UniformContinuous(12.0 * 60, 16.0 * 60, rand.nextInt());
        pickilingChairDist = new UniformContinuous(90.0 * 60, 400.0 * 60, rand.nextInt());
        paintingChairDist = new UniformContinuous(40.0 * 60, 200.0 * 60, rand.nextInt());
        assemblyChairDist = new UniformContinuous(14.0 * 60, 24.0 * 60, rand.nextInt());

        // third
        cuttingWardrobeDist = new UniformContinuous(15.0 * 60, 80.0 * 60, rand.nextInt());
        pickilingWardrobeDist = new UniformContinuous(300.0 * 60, 600.0 * 60, rand.nextInt());
        paintingWardrobeDist = new UniformContinuous(250.0 * 60, 560.0 * 60, rand.nextInt());
        assemblyWardrobeDist = new UniformContinuous(35.0 * 60, 75.0 * 60, rand.nextInt());
        montageWardrobeDist = new UniformContinuous(15.0 * 60, 25.0 * 60, rand.nextInt());

        //moving Dist
        timeMovingIntoStorageDist = new Triangular(60.0, 480.0, 120.0, rand.nextInt());
        timeSpentInStorageDist = new Triangular(300.0, 900.0, 500.0, rand.nextInt());
        timeMovingToAnotherWorkshopDist = new Triangular(120.0, 500.0, 150.0, rand.nextInt());



    }


    public Exponential getOrderArrivalDist() {
        return orderArrivalDist;
    }

    public EmpiricDiscrete getTypeOfOrderDist() {
        return typeOfOrderDist;
    }

    public EmpiricContinuous getCuttingTableDist() {
        return cuttingTableDist;
    }

    public UniformContinuous getCuttingChairDist() {
        return cuttingChairDist;
    }

    public UniformContinuous getCuttingWardrobeDist() {
        return cuttingWardrobeDist;
    }


    public UniformContinuous getPaintingChairDist() {
        return paintingChairDist;
    }

    public UniformContinuous getPaintingWardrobeDist() {
        return paintingWardrobeDist;
    }

    public UniformContinuous getAssemblyTableDist() {
        return assemblyTableDist;
    }

    public UniformContinuous getAssemblyChairDist() {
        return assemblyChairDist;
    }

    public UniformContinuous getAssemblyWardrobeDist() {
        return assemblyWardrobeDist;
    }

    public UniformContinuous getMontageWardrobeDist() {
        return montageWardrobeDist;
    }

    public Triangular getTimeSpentInStorageDist() {
        return timeSpentInStorageDist;
    }

    public Triangular getTimeMovingIntoStorageDist() {
        return timeMovingIntoStorageDist;
    }

    public Triangular getTimeMovingToAnotherWorkshopDist() {
        return timeMovingToAnotherWorkshopDist;
    }

    public Triangular getAccessingOrderDist() {
        return accessingOrderDist;
    }

    public EmpiricContinuous getPaintingTableDist() {
        return paintingTableDist;
    }

    public UniformContinuous getPickilingTableDist() {
        return pickilingTableDist;
    }

    public UniformContinuous getPickilingChairDist() {
        return pickilingChairDist;
    }

    public UniformContinuous getPickilingWardrobeDist() {
        return pickilingWardrobeDist;
    }

    public UniformDiscrete getCountOfFurnitureDist() {
        return countOfFurnitureDist;
    }

    public Random getRealisePaintingDist() {
        return realisePaintingDist;
    }
}
