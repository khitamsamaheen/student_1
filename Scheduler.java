/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

class Scheduler {
  static class ArrivalComparator implements Comparator<Process> {
        @Override
        public int compare(Process a, Process b) {
            return Integer.compare(a.arrivalTime, b.arrivalTime);
        }
    }

    static class BurstComparator implements Comparator<Process> {
        @Override
        public int compare(Process a, Process b) {
            return Integer.compare(a.burstTime, b.burstTime);
        }
    }

    public static void FCFS(ArrayList<Process> processes, int contextSwitchTime) {
        int currentTime = 0;
        for (Process p : processes) {
            if (p.arrivalTime > currentTime)
                currentTime = p.arrivalTime;
            p.startTime = currentTime;
            currentTime += p.burstTime;
            p.finishTime = currentTime;
            p.turnaroundTime = p.finishTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;
            currentTime += contextSwitchTime;
        }
    }

    public static void SRT(ArrayList<Process> processes, int contextSwitchTime) {
        int n = processes.size();
        int currentTime = 0;
        int completed = 0;
        ArrayList<Integer> remainingTime = new ArrayList<>();
        for (int i = 0; i < n; ++i)
            remainingTime.add(processes.get(i).burstTime);

        while (completed < n) {
            int shortest = -1;
            for (int i = 0; i < n; ++i) {
                if (processes.get(i).arrivalTime <= currentTime && remainingTime.get(i) > 0) {
                    if (shortest == -1 || remainingTime.get(i) < remainingTime.get(shortest)) {
                        shortest = i;
                    }
                }
            }

            if (shortest == -1) {
                currentTime++;
                continue;
            }

            remainingTime.set(shortest, remainingTime.get(shortest) - 1);
            currentTime++;

            if (remainingTime.get(shortest) == 0) {
                completed++;
                processes.get(shortest).finishTime = currentTime;
                processes.get(shortest).turnaroundTime = processes.get(shortest).finishTime - processes.get(shortest).arrivalTime;
                processes.get(shortest).waitingTime = processes.get(shortest).turnaroundTime - processes.get(shortest).burstTime;
                currentTime += contextSwitchTime;
            }
        }
    }

    public static void RR(ArrayList<Process> processes, int quantum, int contextSwitchTime) {
        int n = processes.size();
        int currentTime = 0;
        int completed = 0;
        Queue<Integer> readyQueue = new LinkedList<>();
        ArrayList<Integer> remainingTime = new ArrayList<>();
        for (int i = 0; i < n; ++i)
            remainingTime.add(processes.get(i).burstTime);

        while (completed < n) {
            for (int i = 0; i < n; ++i) {
                if (processes.get(i).arrivalTime <= currentTime && remainingTime.get(i) > 0) {
                    int runTime = Math.min(quantum, remainingTime.get(i));
                    remainingTime.set(i, remainingTime.get(i) - runTime);
                    currentTime += runTime;
                    if (remainingTime.get(i) == 0) {
                        completed++;
                        processes.get(i).finishTime = currentTime;
                        processes.get(i).turnaroundTime = processes.get(i).finishTime - processes.get(i).arrivalTime;
                        processes.get(i).waitingTime = processes.get(i).turnaroundTime - processes.get(i).burstTime;
                        currentTime += contextSwitchTime;
                    } else {
                        readyQueue.add(i);
                    }
                }
            }

            if (!readyQueue.isEmpty()) {
                int nextProcess = readyQueue.poll();
                readyQueue.add(nextProcess);
                currentTime += contextSwitchTime;
            } else {
                currentTime++;
            }
        }
    }

    public static void printResults(ArrayList<Process> processes, String schedulerType, float cpuUtilization) {
        System.out.println(schedulerType + " Results:");
        System.out.println("Process\tStart Time\tFinish Time\tWaiting Time\tTurnaround Time");
        for (Process p : processes) {
            System.out.println(p.id + "\t" + p.startTime + "\t\t" + p.finishTime + "\t\t" + p.waitingTime + "\t\t" + p.turnaroundTime);
        }
        System.out.println("CPU Utilization: " + cpuUtilization);
    }
}
