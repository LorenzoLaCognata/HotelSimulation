package Manager;

public class Main {
    public static void main(String[] ignoredArgs) {
        SimulationManager simulationManager = new SimulationManager();
        simulationManager.initHotel();
//        simulationManager.simulate(31);
        simulationManager.mainMenu();
    }
}