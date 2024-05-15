

package student_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Student_1 {

    public static void main(String[] args) throws FileNotFoundException {
    
      Scanner inputFile = new Scanner(new File("C:\\Users\\alaa2023\\Desktop\\c.txt"));

        int contextSwitchTime = inputFile.nextInt();
        int quantum = inputFile.nextInt();
        ArrayList<Process> processes = new ArrayList<>();

        while (inputFile.hasNextInt()) {
            int id = inputFile.nextInt();
            int arrivalTime = inputFile.nextInt();
            int burstTime = inputFile.nextInt();
            processes.add(new Process(id, arrivalTime, burstTime));
        }

        inputFile.close();

        Collections.sort(processes, new Scheduler.ArrivalComparator());

        ArrayList<Process> fcfsProcesses = new ArrayList<>(processes);
        ArrayList<Process> srtProcesses = new ArrayList<>(processes);
        ArrayList<Process> rrProcesses = new ArrayList<>(processes);

        Scheduler.FCFS(fcfsProcesses, contextSwitchTime);
        Scheduler.SRT(srtProcesses, contextSwitchTime);
        Scheduler.RR(rrProcesses, quantum, contextSwitchTime);

        float totalExecutionTime = 0;
        for (Process p : processes) {
            totalExecutionTime += p.burstTime;
        }
        float cpuUtilization = (totalExecutionTime / (fcfsProcesses.get(fcfsProcesses.size() - 1).finishTime + contextSwitchTime)) * 100;

        Scheduler.printResults(fcfsProcesses, "FCFS", cpuUtilization);
        Scheduler.printResults(srtProcesses, "SRT", cpuUtilization);
        Scheduler.printResults(rrProcesses, "RR", cpuUtilization);
    }
}