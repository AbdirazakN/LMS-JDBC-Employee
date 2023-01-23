package peaksoft;

import peaksoft.config.Util;
import peaksoft.models.Employee;
import peaksoft.models.Job;
import peaksoft.services.employee.ServiceEmployee;
import peaksoft.services.employee.ServiceEmployeeImpl;
import peaksoft.services.job.ServiceJob;
import peaksoft.services.job.ServiceJobImpl;

import java.util.Scanner;

/**
 * Assalaamu alaikum!
 */
public class App {
    public static void main(String[] args) {
        /**Жаны проект ачып томонку методдорду реализация кылабыз. Тапшырманы Dao паттерн мн кылабыз.

         model:

         Employee:
         private Long id;
         private String firstName;
         private String lastName;
         private int age;
         private String email;
         private int jobId;(reference)

         Job:
         private Long id;
         private String position;("Mentor","Management","Instructor") ушундай маанилер берилсин
         private String profession;("Java","JavaScript")
         private String description;("Backend developer","Fronted developer")
         private int experience;(1,2,3........) опыт работы

         EmployeeDao:

         void createEmployee();
         void addEmployee(Employee employee);
         void dropTable();
         void cleanTable();
         void updateEmployee(Long id,Employee employee);
         List<Employee>getAllEmployees();
         Employee findByEmail(String email);
         Map<Employee, Job> getEmployeeById(Long employeeId);
         List<Employee> getEmployeeByPosition(String position);

         JobDao:

         void createJobTable();
         void addJob(Job job);
         Job getJobById(Long jobId);
         List<Job> sortByExperience(String ascOrDesc);
         Job getJobByEmployeeId(Long employeeId);
         void deleteDescriptionColumn();

         Deadline: Понедельник саат 11:00
         */

        ServiceEmployee employee = new ServiceEmployeeImpl();
        ServiceJob job = new ServiceJobImpl();
        for (;; ) {
            System.out.println("""
                    Enter 1 to Employee
                    Enter 2 to Job
                    Enter 3 to Connect Tables
                    Enter 0 to Break""");
            int cmd = new Scanner(System.in).nextInt();
            if (cmd == 1) {
                while (true) {
                    System.out.println("""
                            Enter 1 to create Employee
                            Enter 2 to add Employee
                            Enter 3 to drop table
                            Enter 4 to clean table
                            Enter 5 to update Employee
                            Enter 6 to get all Employees
                            Enter 7 to find by email
                            Enter 8 to get Employee by ID
                            Enter 9 to get Employee by Position
                            Enter 0 to Return
                            """);
                    int cmd1 = new Scanner(System.in).nextInt();
                    if (cmd1==0) break;
                    switch (cmd1) {
                        case 1:
                            employee.createEmployee();
                            break;
                        case 2:
                            System.out.print("Enter first name: " +
                                    "Enter last name: " +
                                    "Enter age: " +
                                    "Enter E-mail: " +
                                    "Enter job ID: ");
                            employee.addEmployee(new Employee(new Scanner(System.in).nextLine(),new Scanner(System.in).nextLine(),
                                    new Scanner(System.in).nextInt(),new Scanner(System.in).nextLine(),new Scanner(System.in).nextInt()));
                            break;
                        case 3:
                            employee.dropTable();
                            break;
                        case 4:
                            employee.cleanTable();
                            break;
                        case 5:
                            System.out.println("Enter ID: ");
                            long id = new Scanner(System.in).nextLong();
                            System.out.print("Enter first name: " +
                                    "Enter last name: " +
                                    "Enter age: " +
                                    "Enter E-mail: " +
                                    "Enter job ID: ");
                            employee.updateEmployee(id, new Employee(new Scanner(System.in).nextLine(),new Scanner(System.in).nextLine(),
                                    new Scanner(System.in).nextInt(),new Scanner(System.in).nextLine(),new Scanner(System.in).nextInt()));
                            break;
                        case 6:
                            System.out.println(employee.getAllEmployees());
                            break;
                        case 7:
                            System.out.println("Enter E-mail to find: ");
                            System.out.println(employee.findByEmail(new Scanner(System.in).nextLine()));
                            break;
                        case 8:
                            System.out.println("Enter ID to find: ");
                            System.out.println(employee.getEmployeeById(new Scanner(System.in).nextLong()));
                            break;
                        case 9:
                            System.out.println("Enter Position to find: ");
                            System.out.println(employee.getEmployeeByPosition(new Scanner(System.in).nextLine()));
                            break;
                    }
                }
            }if (cmd == 2) {
                while (true) {
                    System.out.println("""
                            Enter 1 to create Job
                            Enter 2 to add Job
                            Enter 3 to get Job by ID
                            Enter 4 to sort by Experience
                            Enter 5 to get Job by Employee
                            Enter 6 to delete Description column
                            Enter 0 to Return
                            """);
                    int cmd1 = new Scanner(System.in).nextInt();
                    if (cmd1==0) break;
                    switch (cmd1) {
                        case 1:
                            job.createJobTable();
                            break;
                        case 2:
                            System.out.println("Enter Position: " +
                                    "\nEnter Profession: " +
                                    "\nEnter Description: " +
                                    "\nEnter Experience: ");
                            job.addJob(new Job(new Scanner(System.in).nextLine(),new Scanner(System.in).nextLine(),
                                    new Scanner(System.in).nextLine(),new Scanner(System.in).nextInt()));
                            break;
                        case 3:
                            System.out.println("Enter ID to find: ");
                            job.getJobById(new Scanner(System.in).nextLong());
                            break;
                        case 4:
                            System.out.println("Enter type sort (asc or desc) : ");
                            job.sortByExperience(new Scanner(System.in).nextLine());
                            break;
                        case 5:
                            System.out.println("Enter Employee ID to find Job: ");
                            job.getJobByEmployeeId(new Scanner(System.in).nextLong());
                            break;
                        case 6:
                            job.deleteDescriptionColumn();
                            break;
                    }
                }
            }if (cmd == 3) System.out.println(Util.getConnection());
            if (cmd == 0) break;
            if (cmd < 0 || cmd>3) System.out.println("Invalid command!");
        }
    }
}
