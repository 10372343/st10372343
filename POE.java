
package com.mycompany.poe;


import javax.swing.JOptionPane;

public class POE {
    private static String[] developerArray;
    private static String[] taskNamesArray;
    private static String[] taskIDArray;
    private static int[] taskDurationArray;
    private static String[] taskStatusArray;
    private static int totalHours = 0;
    private static int taskCount = 0;

    public static void main(String[] args) {
        int numTasks = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of tasks:"));
        taskArrays(numTasks);

        displayWelcomeMessage();

        int menuChoice;
        do {
            menuChoice = Integer.parseInt
        (JOptionPane.showInputDialog(null,
                "Menu:\n" +
                "1. Add tasks\n" +
                "2. Display the Developer and Duration of the task with the longest duration\n" +
                "3. Search for a task by Task Name\n" +
                "4. Search for tasks assigned to a developer\n" +
                "5. Delete a task by Task Name\n" +
                "6. Display a task report\n" +
                "7. Quit"));

            switch (menuChoice) {
                case 1:
                    addTasks();
                    break;
                case 2:
                    displayLongestDurationTask();
                    break;
                case 3:
                    searchTaskByName();
                    break;
                case 4:
                    searchTasksByDeveloper();
                    break;
                case 5:
                    deleteTaskByName();
                    break;
                case 6:
                    displayTaskReport();
                    break;
                case 7:
                    JOptionPane.showMessageDialog(null, "Thank you for using EasyKanban!");
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
                    break;
            }
        } while (menuChoice != 7);
    }

    private static void taskArrays(int numTasks) {
        developerArray = new String[numTasks];
        taskNamesArray = new String[numTasks];
        taskIDArray = new String[numTasks];
        taskDurationArray = new int[numTasks];
        taskStatusArray = new String[numTasks];
    }

    private static void displayWelcomeMessage() {
        JOptionPane.showMessageDialog(null, "Welcome to EasyKanban");
    }

    

    private static void addTasks() {
        for (int i = 0; i < taskNamesArray.length; i++) {
            String taskName = JOptionPane.showInputDialog("Enter the Task Name:");
            String taskDescription = JOptionPane.showInputDialog("Enter the Task Description:");
            String developer = JOptionPane.showInputDialog("Enter the Developer Details (First Last):");
            int taskDuration = Integer.parseInt(JOptionPane.showInputDialog("Enter the Task Duration (in hours):"));

            Task task = new Task(taskName, taskDescription, developer, taskDuration);
            if (!task.checkTaskDescription()) {
                JOptionPane.showMessageDialog(null, "Please enter a task description of less than 50 characters");
                i--;
                continue;
            }

            taskNamesArray[i] = task.getTaskName();
            taskDurationArray[i] = task.getTaskDuration();
            taskIDArray[i] = task.createTaskID();
            developerArray[i] = task.getDeveloper();
            taskStatusArray[i] = task.getTaskStatus();

            totalHours += task.getTaskDuration();
            taskCount++;

            JOptionPane.showMessageDialog(null, "Task successfully captured:\n" + task.printTaskDetails());
        }
    }

    private static void displayLongestDurationTask() {
        int longestDuration = -1;
        int longestDurationIndex = -1;

        for (int i = 0; i < taskDurationArray.length; i++) {
            if (taskDurationArray[i] > longestDuration) {
                longestDuration = taskDurationArray[i];
                longestDurationIndex = i;
            }
        }

        if (longestDurationIndex != -1) {
            String developer = developerArray[longestDurationIndex];
            int duration = taskDurationArray[longestDurationIndex];
            JOptionPane.showMessageDialog(null, "Developer: " + developer + "\nDuration: " + duration + " hours");
        } else {
            JOptionPane.showMessageDialog(null, "No tasks found.");
        }
    }

    private static void searchTaskByName() {
        String taskName = JOptionPane.showInputDialog("Enter the Task Name to search for:");

        boolean taskFound = false;
        for (int i = 0; i < taskNamesArray.length; i++) {
            if (taskNamesArray[i] != null && taskNamesArray[i].equalsIgnoreCase(taskName)) {
                String developer = developerArray[i];
                String taskStatus = taskStatusArray[i];

                JOptionPane.showMessageDialog(null,
                        "Task Name: " + taskNamesArray[i] + "\nDeveloper: " + developer + "\nTask Status: " + taskStatus);
                taskFound = true;
                break;
            }
        }

        if (!taskFound) {
            JOptionPane.showMessageDialog(null, "Task not found.");
        }
    }

    private static void searchTasksByDeveloper() {
        String developer = JOptionPane.showInputDialog("Enter the Developer Name to search for:");

        boolean tasksFound = false;
        for (int i = 0; i < developerArray.length; i++) {
            if (developerArray[i] != null && developerArray[i].equalsIgnoreCase(developer)) {
                String taskName = taskNamesArray[i];
                String taskStatus = taskStatusArray[i];

                JOptionPane.showMessageDialog(null,
                        "Task Name: " + taskName + "\nTask Status: " + taskStatus);
                tasksFound = true;
            }
        }

        if (!tasksFound) {
            JOptionPane.showMessageDialog(null, "No tasks found for the developer.");
        }
    }

    private static void deleteTaskByName() {
        String taskName = JOptionPane.showInputDialog("Enter the Task Name to delete:");

        boolean taskDeleted = false;
        for (int i = 0; i < taskNamesArray.length; i++) {
            if (taskNamesArray[i] != null && taskNamesArray[i].equalsIgnoreCase(taskName)) {
                taskNamesArray[i] = null;
                taskDurationArray[i] = 0;
                taskIDArray[i] = null;
                developerArray[i] = null;
                taskStatusArray[i] = null;

                totalHours -= taskDurationArray[i];
                taskCount--;

                JOptionPane.showMessageDialog(null, "Task deleted.");
                taskDeleted = true;
                break;
            }
        }

        if (!taskDeleted) {
            JOptionPane.showMessageDialog(null, "Task not found.");
        }
    }

    private static void displayTaskReport() {
        StringBuilder report = new StringBuilder("Task Report:\n");

        for (int i = 0; i < taskNamesArray.length; i++) {
            if (taskNamesArray[i] != null) {
                String taskStatus = taskStatusArray[i];
                String developer = developerArray[i];
                int taskNumber = i;
                String taskName = taskNamesArray[i];
                String taskDescription = taskNamesArray[i];
                String taskID = taskIDArray[i];
                                int taskDuration = taskDurationArray[i];

                report.append("Task Status: ").append(taskStatus).append("\n")
                        .append("Developer: ").append(developer).append("\n")
                        .append("Task Number: ").append(taskNumber).append("\n")
                        .append("Task Name: ").append(taskName).append("\n")
                        .append("Task Description: ").append(taskDescription).append("\n")
                        .append("Task ID: ").append(taskID).append("\n")
                        .append("Duration: ").append(taskDuration).append(" hours").append("\n\n");
            }
        }

        JOptionPane.showMessageDialog(null, report.toString());
    }

    private static class Task {
        private String taskName;
        private String taskDescription;
        private String developer;
        private int taskDuration;
        private String taskStatus;

        public Task(String taskName, String taskDescription, String developer, int taskDuration) {
            this.taskName = taskName;
            this.taskDescription = taskDescription;
            this.developer = developer;
            this.taskDuration = taskDuration;
            this.taskStatus = "ToDo";
        }

        public String getTaskName() {
            return taskName;
        }

        public int getTaskDuration() {
            return taskDuration;
        }

        public String getDeveloper() {
            return developer;
        }

        public String getTaskStatus() {
            return taskStatus;
        }

        public boolean checkTaskDescription() {
            return taskDescription.length() <= 50;
        }

        public String createTaskID() {
            String taskID = taskName.substring(0, 2).toUpperCase() + ":" + taskCount + ":" + developer.substring(developer.length() - 3).toUpperCase();
            return taskID;
        }

        public String printTaskDetails() {
            return "Task Status: " + taskStatus + "\n" +
                    "Developer Details: " + developer + "\n" +
                    "Task Number: " + taskCount + "\n" +
                    "Task Name: " + taskName + "\n" +
                    "Task Description: " + taskDescription + "\n" +
                    "Task ID: " + taskIDArray[taskCount - 1] + "\n" +
                    "Duration: " + taskDuration + " hours";
        }
    }

    private static int returnTotalHours() {
        return totalHours;
    }
}



