import javax.management.InstanceAlreadyExistsException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.Executors;

/**
 * IBM - DC Tower Elevator Challenge
 * @author Philipp Zabka
 * @version 1.0
 */
public final class ElevatorSystem {
    private class ElevatorRequest implements Runnable{
        private final int currentFloor;
        private final int destinationFloor;
        private final Enum<Directions> direction;

        /**
         *
         * @param currentFloor The floor the elevator is starting at
         * @param destinationFloor The floor the elevator is going to
         * @param direction The direction the elevator is heading
         */
        public ElevatorRequest(int currentFloor, int destinationFloor, Directions direction) {
            this.direction = direction;
            this.currentFloor = currentFloor;
            this.destinationFloor = destinationFloor;
        }

        public void run() {
            try {
                simulateElevatorProcess();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Simulate the elevator moving between floors
         * @throws InterruptedException
         */
        private void simulateElevatorProcess() throws InterruptedException {
            long elevatorId = Thread.currentThread().getId();
            System.out.println("Elevator " + elevatorId + " starts at current floor " + currentFloor);

            if (direction.equals(Directions.UP))
            {
                for(int i = currentFloor; i < destinationFloor; i++){
                    System.out.println("Elevator " + Thread.currentThread().getId() + " goes " + direction + " to floor " + (i+1));
                    // Simulate moving between floors with Thread.sleep
                    Thread.sleep(100);
                }
            }
            else
            {
                for (int i = currentFloor; i > destinationFloor; i--) {
                    System.out.println("Elevator " + Thread.currentThread().getId() + " goes " + direction + " to floor " + (i-1));
                    Thread.sleep(100);
                }
            }
            System.out.println("Elevator " + elevatorId + " arrives at destination floor " + destinationFloor);
        }
    }

    private final int numElevators = 7;
    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numElevators);
    private final int maxFloors = 55;
    private static ElevatorSystem INSTANCE = null;

    private ElevatorSystem(){}

    /**
     * @return Instance of ElevatorSystem class
     * @throws InstanceAlreadyExistsException
     */
    public static ElevatorSystem getInstance() throws Throwable {
        if(INSTANCE == null)
        {
            INSTANCE = new ElevatorSystem();
            return INSTANCE;
        }
        else {
            throw new InstanceAlreadyExistsException();
        }
    }

    /**
     * @return Random number between 1 and 55 (including both)
     */
    private int getRandomFloor(){
        return (int) Math.floor(Math.random() * (maxFloors - 1 + 1) + 1);
    }

    /**
     * Prints the number of vacant elevators
     */
    public void checkAvailableElevators(){
        System.out.println(numElevators - executor.getActiveCount() + " Elevator(s) available");
    }

    /**
     * Add a new elevator request into the queue
     */
    public void addRequest() {
        Directions[] directions = Directions.values();
        Directions direction = directions[ (int) Math.round( Math.random())];

        if(direction.equals(Directions.UP))
        {
            executor.execute(new ElevatorRequest(0, getRandomFloor(), direction));
        }
        else
        {
            executor.execute(new ElevatorRequest(getRandomFloor(), 0, direction));
        }
    }

    /**
     * Initiate system shutdown, no more requests can be accepted
     */
    public void shutdown(){
        executor.shutdown();
    }
}
