package son;

public class MixedScheduler {
    public static void execute(Process process, Dispatcher dispatcher) {
        // Mixed scheduling logic
        String selectedAlgorithm = determineSchedulingAlgorithm(process);
        switch (selectedAlgorithm) {
            case "FCFS":
                FCFS.execute(process, dispatcher);
                break;
            case "GBG":
                GBG.execute(process, dispatcher);
                break;
            case "RR":
                RR.execute(process, dispatcher, 2); // Example time quantum of 2 seconds
                break;
            default:
                System.out.println("Unknown scheduling algorithm");
        }
    }

    private static String determineSchedulingAlgorithm(Process process) {
        // Implementation omitted for brevity
        return "FCFS";
    }
}
