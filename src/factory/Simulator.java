package factory;


import java.util.ArrayList;
import java.util.HashSet;

public class Simulator implements Cloneable {
    private int lastCheckpoint = -1;
    //    private int restartedSuperstep = -1;
    private int supersteps = 0; //total supersteps
    private int recoveryOverhead = 0;
    private int[] checkpointCost;
    private int[] computeTime;
    private HashSet<Integer> failureSteps = new HashSet<Integer>();
    private int[] failureTime;
    private CheckpointStrategy checkpointStrategy;

    //    private ArrayList<simple.IterationUnit> iterationUnits =new ArrayList<simple.IterationUnit>(); //each iteration's running time result
    public Simulator() {
    }

    ;

    public Simulator(int supersteps, int recoveryOverhead, CheckpointStrategy checkpointStrategy) {
        this.supersteps = supersteps;
        this.checkpointStrategy = checkpointStrategy;
        this.recoveryOverhead = recoveryOverhead;
    }

    public static void main(String[] args) throws Exception {
        String resultPath = "result.csv";
        // set up checkpoint strategy.
        CheckpointStrategy checkpointStrategy = CheckpointStrategyFactory.getClass(StaticCheckpoint.class);
        checkpointStrategy.setInterval(1);
        // configure simulator
        Simulator simulator = new Simulator(47, 1, checkpointStrategy);

        simulator.recoveryOverhead = 30000;
        // pre-generate checkpointCost Pool, actual checkpoint number is less, will be added during running.
//        simulator.checkpointCost = DataGenerator.getSameData(simulator.supersteps + 2, 10);
//        simulator.checkpointCost = DataGenerator.modifyData(DataGenerator.getNormalDistributionData(simulator.supersteps + 1, 10, 1), 1, 0);
//        simulator.computeTime = DataGenerator.modifyData(DataGenerator.getNormalDistributionDensity(
//                simulator.supersteps + 1, 16, 4), 100000, 1500);
//        simulator.computeTime = DataGenerator.getSameData(simulator.supersteps + 1, 2);
        //1500,1500,1500,1500,1500,1500,1500,1500,1500,2157,3238,4566,6049,7528,8802,9667,9974,9667,8802,7528,6049,4566,3238,2157,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,
        int[] computeTimeList = {10577, 1640, 1504, 1554, 1596, 1626, 1465, 1486, 1665, 2074, 1886, 1943, 2690, 4253, 6163, 7967, 9426, 9405, 8493, 6865, 5502, 4485, 3686, 2955, 2637, 2483, 2049, 1949, 1940, 1884, 1801, 1745, 1643, 1696, 1737, 1605, 1640, 1700, 1584, 1604, 1606, 1642, 1776, 1584, 1584, 1733, 1706, 1626};
        simulator.computeTime = computeTimeList;
        System.out.println("computeTime:");
        for (int ft : computeTimeList) {
            System.out.print(ft + ",");
        }
        System.out.println();
        int[] checkpointTimeList = {11611, 7710, 8122, 9157, 6366, 9188, 8474, 6847, 8971, 8570, 8505, 7563, 7571, 6634, 7548, 9486, 7410, 6493, 7103, 8425, 7529, 9562, 8125, 8780, 7725, 8038, 8343, 8576, 8672, 6270, 10851, 7417, 7355, 8330, 7763, 6738, 9006, 8582, 7763, 7009, 9089, 8224, 6692, 8923, 7565, 6613, 7331, 7343};
        simulator.checkpointCost = checkpointTimeList;
        System.out.println("checkpointTime:");
        for (int ft : checkpointTimeList) {
            System.out.print(ft + ",");
        }
        System.out.println();

        //set up failure step up, failure time interval follows exponential distribution.
        // assume the whole running time without failure is 1, lambda is the expected failure numbers.
//        simulator.failureSteps = simulator.generateFailureSteps(3);
//        System.out.println("failureSteps:" + simulator.failureSteps.toString());

//        simulator.failureTime = DataGenerator.modifyData(DataGenerator.getExponentialDistributionData(100, 3), 1000, 1);
        simulator.failureTime = simulator.generateFailureTime(1, 60000, 1, 1000);
        System.out.println("failureTime:");
        for (int ft : simulator.failureTime) {
            System.out.print(ft + ",");
        }
        System.out.println();

        System.out.println("checkpoint cost:");
        for (int ft : simulator.checkpointCost) {
            System.out.print(ft + ",");
        }
        System.out.println();
//        simulator.printResult(simulator.computeTime, simulator.failureSteps);

//        ArrayList<IterationUnit> iterationUnits = simulator.generateResultByStep(simulator.failureSteps);
//        Simulator simulator1=(Simulator) simulator.clone();
//        CheckpointStrategy checkpointStrategy1 = CheckpointStrategyFactory.getClass(StaticCheckpoint.class);
//        checkpointStrategy1.setInterval(2);
//        simulator1.checkpointStrategy=checkpointStrategy1;
        simulator.failureSteps.add(10);
        simulator.failureSteps.add(20);
        simulator.failureSteps.add(30);
        simulator.failureSteps.add(40);
        System.out.println(simulator.failureSteps);
//        for (int i = 1; i <= 10; i++) {
//            ArrayList<IterationUnit> iterationUnits = simulator.generateResultByTime(simulator.failureTime);
//        ArrayList<IterationUnit> iterationUnits = simulator.generateResultByStep(new HashSet<Integer>());
                ArrayList<IterationUnit> iterationUnits = simulator.generateResultByStep(simulator.failureSteps);

//            simulator.checkpointStrategy.setInterval(i);
//            System.out.println(simulator.checkpointStrategy.getClass().getName() + ",interval:" + i + ",attempt:" + iterationUnits.get(iterationUnits.size() - 1).getAttepmt() + ",totaltime:" + iterationUnits.get(iterationUnits.size() - 1).getComputeEnd());
//        }
//        ArrayList<IterationUnit> iterationUnits1 = simulator1.generateResultByTime(simulator1.failureTime);
//        CSVUtils.exportCsv(resultPath, iterationUnits);

        for (int i = 0; i < iterationUnits.size(); i++) {
            System.out.println(iterationUnits.get(i));
        }

//        for (int i = 0; i < iterationUnits1.size(); i++) {
//            System.out.println(iterationUnits1.get(i));
//        }
    }

    // failure steps' time interval follows exponential distribution
//    public HashSet<Integer> generateFailureSteps(int lambda) throws CloneNotSupportedException {
//        HashSet<Integer> failureSteps = new HashSet<Integer>();
//        Simulator stemp = (Simulator) this.clone();
//        ArrayList<IterationUnit> iterationUnitsWithoutFailure = stemp.generateResultByStep(failureSteps);
//        long totalTime = iterationUnitsWithoutFailure.get(iterationUnitsWithoutFailure.size() - 1).getComputeEnd();
////        System.out.println(totalTime);
//        double[] failureIntervalList = DataGenerator.getExponentialDistributionData(stemp.supersteps, lambda);
//        double timer = failureIntervalList[0] * totalTime;
//
//        int failureCounter = 0;
//        for (IterationUnit i : iterationUnitsWithoutFailure) {
////            System.out.println("timer:"+timer);
//            if (i.getCheckpointStart() <= timer && i.getComputeEnd() >= timer) {
//                failureSteps.add(i.getSuperstep());
//                failureCounter++;
//                timer += failureIntervalList[failureCounter] * totalTime;
//            }
//        }
//        return failureSteps;
//
//    }

    public int[] generateFailureTime(int lambda, int period, int min, int size) throws CloneNotSupportedException {

        Simulator stemp = (Simulator) this.clone();
        ArrayList<IterationUnit> iterationUnitsWithoutFailure = stemp.generateResultByStep(new HashSet<Integer>());
        long totalTime = iterationUnitsWithoutFailure.get(iterationUnitsWithoutFailure.size() - 1).getComputeEnd();
//        System.out.println(totalTime);
        int[] failureIntervalList = DataGenerator.modifyData(DataGenerator.getExponentialDistributionData(size, lambda), period, min);
        int[] failureTime = new int[size];
        for (int i = 1; i < stemp.supersteps; i++) {
            failureTime[i] = failureTime[i - 1] + failureIntervalList[i];
        }
        return failureTime;

    }

    //    public ArrayList<IterationUnit> generateResultByStep(HashSet<Integer> failureSteps) {
//        ArrayList<IterationUnit> iterationUnits = new ArrayList<>();
//        int superstep = 0;
//        long time = 0;
//        int checkpointCounter = 0;
//        ArrayList<Integer> checkpointCostActual = new ArrayList<Integer>();
//        do {
////            System.out.println(superstep);
//            IterationUnit iterationUnit = new IterationUnit();
//            if (iterationUnits.size() != 0) {
//                if (iterationUnits.get(iterationUnits.size() - 1).getRecoveryOverheadStart() == 0 || iterationUnits.get(iterationUnits.size() - 1).getComputeEnd() != 0) {//if it is not the a just restarted superstep
//                    iterationUnit.setAttepmt(iterationUnits.get(iterationUnits.size() - 1).getAttepmt());// attempt equals to the last one
//                    iterationUnit.setSuperstep(superstep);
//                    iterationUnits.add(iterationUnit);
////                    System.out.println("not just restarted. add new");
//                }
//            } else {
//                iterationUnits.add(iterationUnit);
////                System.out.println("size 0 add new");
//            }
//            CheckpointStatus checkpointStatus = this.checkpointStrategy.getCheckpointStatus(superstep, checkpointCostActual, this.recoveryOverhead, this.lastCheckpoint, this.computeTime);
//            if (checkpointStatus == CheckpointStatus.CHECKPOINT) {
//                iterationUnit.setCheckpointStart(time);
//                time += checkpointCost[checkpointCounter];
//                checkpointCostActual.add(checkpointCost[checkpointCounter]);
//                checkpointCounter++;
//                iterationUnit.setCheckpointEnd(time);
//                this.lastCheckpoint = superstep;
////                System.out.println("do checkpoint");
//            }
//            if (failureSteps.contains(superstep)) {
////                System.out.println("do kill");
//                iterationUnit.setKillTime(time);
////                System.out.println(iterationUnit);
//                if (checkpointStrategy.getInterval() == 0 && this.lastCheckpoint == -1) {//no checkpoint. job stopped
////                    System.out.println("ck none. jump");
//                    return iterationUnits;
//                } else {//restart
////                    System.out.println("restarted");
//                    IterationUnit iterationUnit2 = new IterationUnit();
//                    iterationUnit2.setAttepmt(iterationUnits.get(iterationUnits.size() - 1).getAttepmt() + 1);//attempt incremented
//                    iterationUnit2.setSuperstep(this.lastCheckpoint);
//                    iterationUnit2.setRecoveryOverheadStart(time);
//                    time += recoveryOverhead;
//                    iterationUnit2.setRecoveryOverheadEnd(time);
//                    iterationUnits.add(iterationUnit2);
//                    failureSteps.remove(superstep);
//                    superstep = lastCheckpoint;
////                    System.out.println(iterationUnit2);
//                    continue;
//                }
//            } else {
//                iterationUnits.get(iterationUnits.size() - 1).setComputeStart(time);
////                System.out.println("superstep:"+superstep);
//                time += computeTime[superstep];
//                iterationUnits.get(iterationUnits.size() - 1).setComputeEnd(time);
////                System.out.println("compute");
////                System.out.println(iterationUnits.get(iterationUnits.size()-1));
//            }
//            superstep++;
//        } while (superstep <= this.supersteps);
//        return iterationUnits;
//
//    }
    //only fail at compute part.
    public ArrayList<IterationUnit> generateResultByStep(HashSet<Integer> failureSteps) {
        ArrayList<IterationUnit> iterationUnits = new ArrayList<>();
        int superstep = 0;
        long time = 0;
        int checkpointCounter = 0;
        int failureCounter = 0;
        ArrayList<Integer> checkpointCostActual = new ArrayList<Integer>();
        do {
//            System.out.println(superstep);
            IterationUnit iterationUnit = new IterationUnit();

            //start, set up attempt and superstep, check if it's a restarted.
            if (iterationUnits.size() != 0) {
                if (iterationUnits.get(iterationUnits.size() - 1).getKillTime() == 0) {//if it is not the a just restarted superstep
                    iterationUnit.setAttepmt(failureCounter);// attempt equals to the last one
                    iterationUnit.setSuperstep(superstep);
                    iterationUnits.add(iterationUnit);
//                    System.out.println("not just restarted. add new");
                } else { //a restart
                    iterationUnit.setAttepmt(failureCounter);// attempt equals to the last one
                    iterationUnit.setSuperstep(superstep);
                    iterationUnits.add(iterationUnit);
                    iterationUnit.setRecoveryOverheadStart(time);

                    time += recoveryOverhead;
                    iterationUnit.setRecoveryOverheadEnd(time);
                }
            } else {
                iterationUnits.add(iterationUnit);
//                System.out.println("size 0 add new");
            }
            //do checkpoint
            CheckpointStatus checkpointStatus = this.checkpointStrategy.getCheckpointStatus(superstep, checkpointCostActual, this.recoveryOverhead, this.lastCheckpoint, this.computeTime);
            if (checkpointStatus == CheckpointStatus.CHECKPOINT) {
                iterationUnit.setCheckpointStart(time);
//

                time += checkpointCost[checkpointCounter];
                checkpointCostActual.add(checkpointCost[checkpointCounter]);
                checkpointCounter++;
                iterationUnit.setCheckpointEnd(time);
                this.lastCheckpoint = superstep;
//                System.out.println("do checkpoint");
            }
            //do compute

            iterationUnit.setComputeStart(time);
            if (failureSteps.contains(superstep)) {
                iterationUnit.setKillTime(time);
//                time = computeTime[superstep];
                System.out.println(time);
                if (failureCounter < this.supersteps - 1) {
                    failureCounter++;
                }

                if (this.checkpointStrategy.getClass() == NoCheckpoint.class) {
                    return iterationUnits;
                }
                superstep = (lastCheckpoint == -1) ? 0 : lastCheckpoint;
                failureSteps.remove(superstep);
                continue;
            }
            time += computeTime[superstep];

            iterationUnit.setComputeEnd(time);

            superstep++;
        } while (superstep <= this.supersteps);
        //reset simulator status: lastCheckpoint,
        this.lastCheckpoint = -1;

        return iterationUnits;

    }

    public ArrayList<IterationUnit> generateResultByTime(int[] failureTime) {
        ArrayList<IterationUnit> iterationUnits = new ArrayList<>();
        int superstep = 0;
        long time = 0;
        int checkpointCounter = 0;
        int failureCounter = 0;
        ArrayList<Integer> checkpointCostActual = new ArrayList<Integer>();
        do {
//            System.out.println(superstep);
            IterationUnit iterationUnit = new IterationUnit();

            //start, set up attempt and superstep, check if it's a restarted.
            if (iterationUnits.size() != 0) {
                if (iterationUnits.get(iterationUnits.size() - 1).getKillTime() == 0) {//if it is not the a just restarted superstep
                    iterationUnit.setAttepmt(failureCounter);// attempt equals to the last one, which is also the same as failure Counter.
                    iterationUnit.setSuperstep(superstep);
                    iterationUnits.add(iterationUnit);
//                    System.out.println("not just restarted. add new");
                } else { //a restart,need to add recoveryoverhead and deal with failure during recovery.
                    iterationUnit.setAttepmt(failureCounter);// attempt equals to the last one
                    iterationUnit.setSuperstep(superstep);
                    iterationUnits.add(iterationUnit);
                    iterationUnit.setRecoveryOverheadStart(time);
                    if (failureTime[failureCounter] >= time && failureTime[failureCounter] <= time + recoveryOverhead) {//fail during recovery
                        iterationUnit.setKillTime(failureTime[failureCounter]);
                        time = failureTime[failureCounter];
                        if (failureCounter < this.supersteps - 1) {
                            failureCounter++;
                        }
                        if (this.checkpointStrategy.getClass() == NoCheckpoint.class) {
                            return iterationUnits;
                        }
//                        System.out.println("fas;s:"+lastCheckpoint+","+superstep);
                        superstep = (lastCheckpoint == -1) ? 0 : lastCheckpoint;


                        continue;
                    }
                    time += recoveryOverhead;
                    iterationUnit.setRecoveryOverheadEnd(time);
                }
            } else {
                iterationUnits.add(iterationUnit);
//                System.out.println("size 0 add new");
            }
            //do checkpoint
            CheckpointStatus checkpointStatus = this.checkpointStrategy.getCheckpointStatus(superstep, checkpointCostActual, this.recoveryOverhead, this.lastCheckpoint, this.computeTime);
            if (checkpointStatus == CheckpointStatus.CHECKPOINT) {
                iterationUnit.setCheckpointStart(time);

                if (failureTime[failureCounter] >= time && failureTime[failureCounter] <= time + checkpointCost[checkpointCounter]) {
                    iterationUnit.setKillTime(failureTime[failureCounter]);
                    time = failureTime[failureCounter];

                    if (failureCounter < this.supersteps - 1) {
                        failureCounter++;
                    }

                    if (this.checkpointStrategy.getClass() == NoCheckpoint.class) {
                        return iterationUnits;
                    }
                    superstep = (lastCheckpoint == -1) ? 0 : lastCheckpoint;

                    continue;
                }
                time += checkpointCost[checkpointCounter];
                checkpointCostActual.add(checkpointCost[checkpointCounter]);
                checkpointCounter++;
                iterationUnit.setCheckpointEnd(time);
                this.lastCheckpoint = superstep;
//                System.out.println("do checkpoint");
            }
            //do compute

            iterationUnit.setComputeStart(time);
            if (failureTime[failureCounter] >= time && failureTime[failureCounter] <= time + computeTime[superstep]) {
                iterationUnit.setKillTime(failureTime[failureCounter]);
                time = failureTime[failureCounter];

                if (failureCounter < this.supersteps - 1) {
                    failureCounter++;
                }

                if (this.checkpointStrategy.getClass() == NoCheckpoint.class) {
                    return iterationUnits;
                }
                superstep = (lastCheckpoint == -1) ? 0 : lastCheckpoint;

                continue;
            }
            time += computeTime[superstep];

            iterationUnit.setComputeEnd(time);

            superstep++;
        } while (superstep <= this.supersteps);
        //reset simulator status: lastCheckpoint,
        this.lastCheckpoint = -1;

        return iterationUnits;

    }

    public void printResult(int[] computeTime, HashSet<Integer> failureSteps) {
        int superstep = 0;
        long time = 0;
        int checkpointCounter = 0;
        ArrayList<Integer> checkpointCostActual = new ArrayList<Integer>();

        do {
            CheckpointStatus checkpointStatus = this.checkpointStrategy.getCheckpointStatus(superstep, checkpointCostActual, this.recoveryOverhead, this.lastCheckpoint, this.computeTime);
            long endCheckpoint = time;
            if (checkpointStatus == CheckpointStatus.CHECKPOINT) {
                endCheckpoint = time + checkpointCost[checkpointCounter];
                System.out.println("superstep:" + superstep + "; checkpoint start:" + time + "; end:" + endCheckpoint +
                        "; duration:" + checkpointCost[checkpointCounter]);
                this.lastCheckpoint = superstep;
                time += checkpointCost[checkpointCounter];
                checkpointCostActual.add(checkpointCost[checkpointCounter]);
                checkpointCounter++;
            }
            if (failureSteps.contains(superstep)) {
                System.out.println("kill at superstep:" + superstep);
                if (checkpointStrategy.getInterval() == 0 && this.lastCheckpoint == -1) {
                    break;
                } else {
                    System.out.println("restart from superstep:" + this.lastCheckpoint);
                    System.out.println("recovery overhead start:" + time + "; end:" + (time + recoveryOverhead) +
                            "; duration:" + recoveryOverhead);
                    time += recoveryOverhead;
                    failureSteps.remove(superstep);
                    superstep = lastCheckpoint;
                    continue;
                }
            } else {
                long endCompute = endCheckpoint + computeTime[superstep];
                System.out.println("superstep:" + superstep + "; compute start:" + endCheckpoint + "; end:" +
                        endCompute + "; duration:" + (endCompute - endCheckpoint));
                time = endCompute;
            }
            superstep++;
        } while (superstep <= this.supersteps);

    }


}
