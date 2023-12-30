package son;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import son.Process.Priority;

public class Dispatcher {
    private Queue<Process> realTimeQueue;
    private Queue<Process> userQueue;
    private List<MemoryBlock> memoryBlocks;
    private List<Printer> printers;
    private List<Scanner> scanners;
    private List<Modem> modems;
    private List<CDDrive> cdDrives;
    private List<Process> processes;  // Add this list to store all processes

    public Dispatcher() {
        realTimeQueue = new LinkedList<>();
        userQueue = new LinkedList<>();
        memoryBlocks = new ArrayList<>();
        printers = new ArrayList<>();
        scanners = new ArrayList<>();
        modems = new ArrayList<>();
        cdDrives = new ArrayList<>();
        processes = new ArrayList<>();  // Initialize the list

        // Initialize other resources...

        // Load processes from input.txt
        loadProcessesFromFile("src/input.txt");
    }

    private void loadProcessesFromFile(String fileName) {
        int pidCounter = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(", ");
                if (tokens.length == 8) {
                    int arrivalTime = Integer.parseInt(tokens[0]);
                    int priority = Integer.parseInt(tokens[1]);
                    int processTime = Integer.parseInt(tokens[2]);
                    int requiredMemory = Integer.parseInt(tokens[3]);
                    int printers = Integer.parseInt(tokens[4]);
                    int scanners = Integer.parseInt(tokens[5]);
                    int modems = Integer.parseInt(tokens[6]);
                    int cdDrives = Integer.parseInt(tokens[7]);
                    int pid = pidCounter++;
                    Process process = new Process(pid, arrivalTime, Priority.HIGH, processTime, requiredMemory, printers, scanners, modems, cdDrives);
                    if (priority == 0) {
                        realTimeQueue.add(process);
                    } else {
                        userQueue.add(process);
                    }
                    processes.add(process);  // Add the process to the list
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void executeRealTime(Process process) {
        // Check if the process requests too many resources
        if (checkResourceLimit(process)) {
            String errorMessage = "HATA - Proses çok sayıda kaynak talep ediyor - proses silindi";
            process.setStatus(Process.Status.ERROR);
            printProcessStatus(process, errorMessage);
            return;
        }

        // Check if a real-time process requests more memory than allowed
        if (process.getPriority() == Process.Priority.REAL_TIME && process.getRequiredMemory() > 64) {
            String errorMessage = String.format("HATA - Gerçek-zamanlı proses (%dMB) tan daha fazla bellek talep ediyor - proses silindi", process.getRequiredMemory());
            process.setStatus(Process.Status.ERROR);
            printProcessStatus(process, errorMessage);
            return;
        }

        // Execute real-time process logic
        // ...

        // Example: Set process status to RUNNING
        process.setStatus(Process.Status.RUNNING);

        printProcessStatus(process, "RUNNING");
    }

    private void executeUser(Process process) {
        // Check if the process requests too many resources
        if (checkResourceLimit(process)) {
            String errorMessage = "HATA - Proses çok sayıda kaynak talep ediyor - proses silindi";
            process.setStatus(Process.Status.ERROR);
            printProcessStatus(process, errorMessage);
            return;
        }

        // Execute user process logic
        // ...

        // Example: Set process status to COMPLETED
        process.setStatus(Process.Status.COMPLETED);
        printProcessStatus(process, "COMPLETED");
    }

    private boolean checkResourceLimit(Process process) {
        // Example: Check if the process requests too many resources
        int totalResources = process.getPrinters() + process.getScanners() + process.getModems() + process.getCdDrives();
        return totalResources > 3 || process.getRequiredMemory() > 960;
    }

    private void printProcessStatus(Process process, String message) {
        System.out.printf("%-3d %-9s %-4d %-4d %-6d %-4d %-4d %-4d %-4d %-10s %s%n",
                process.getPid(), process.getPriority(), process.getArrivalTime(),
                process.getProcessTime(), process.getRequiredMemory(), process.getPrinters(),
                process.getScanners(), process.getModems(), process.getCdDrives(),
                process.getStatus(), message);
    }

    private void printFinalStatus() {
        // Iterate through all processes and print their final status
        System.out.println("Pid varış  öncelik cpu MBytes prn  scn  modem cd   status");
        System.out.println("============================================================================");
        for (Process process : processes) {
            System.out.printf("%-3d %-9s %-4d %-4d %-6d %-4d %-4d %-4d %-4d %-10s%n",
                    process.getPid(), process.getPriority(), process.getArrivalTime(),
                    process.getProcessTime(), process.getRequiredMemory(), process.getPrinters(),
                    process.getScanners(), process.getModems(), process.getCdDrives(),
                    process.getStatus());
        }
    }

    public void runSchedulers() {
        long startTime = System.currentTimeMillis();
        while (!realTimeQueue.isEmpty() || !userQueue.isEmpty()) {
            if (!realTimeQueue.isEmpty()) {
                Process process = realTimeQueue.poll();
                executeRealTime(process);
            } else if (!userQueue.isEmpty()) {
                Process process = userQueue.poll();
                executeUser(process);
            }

            // Check for timeout (20 seconds)
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime > 20000) {
                System.out.println("HATA - Proses zaman aşımı (20 sn de tamamlanamadı)");
                break;
            }
        }
    }

    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.runSchedulers();
        dispatcher.printFinalStatus();
    }
}
