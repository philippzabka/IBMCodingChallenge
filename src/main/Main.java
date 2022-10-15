public class Main {
    public static void main(String[] args) throws Throwable {
        ElevatorSystem elevatorSystem = ElevatorSystem.getInstance();
        elevatorSystem.addRequest();
        elevatorSystem.addRequest();
        elevatorSystem.addRequest();
        elevatorSystem.checkAvailableElevators();
        elevatorSystem.addRequest();
        elevatorSystem.addRequest();
        elevatorSystem.addRequest();
        elevatorSystem.addRequest();
        Thread.sleep(2000);
        elevatorSystem.checkAvailableElevators();
        elevatorSystem.addRequest();
        elevatorSystem.addRequest();
        Thread.sleep(1000);
        elevatorSystem.checkAvailableElevators();
        Thread.sleep(1000);
        elevatorSystem.checkAvailableElevators();
        elevatorSystem.shutdown();
    }
}
