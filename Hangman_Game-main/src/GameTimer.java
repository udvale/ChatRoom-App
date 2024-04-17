import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Task executed!");
            }
        };
        timer.schedule(task, 20000); // Executes the task after a 20-second delay
        task.cancel();
task = new TimerTask() {
    @Override
    public void run() {
        System.out.println("Client did not respond in time. Ending game...");
        // End the game and declare the other client the winner
    }
};
timer.schedule(task, 20000);
    }
}
