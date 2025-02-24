import java.util.*;

// Step 1: Define the Student class
class Student {
    private int id;
    private String name;
    private int age;
    private String course;

    public Student(int id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Course: " + course;
    }
}

// Step 2: Authentication System
class Authentication {
    private static Map<String, String> userCredentials = new HashMap<>();
    private static Map<String, List<Student>> userStudents = new HashMap<>();
    
    public boolean register(String username, String password) {
        if (userCredentials.containsKey(username)) {
            System.out.println("User already exists! Please login.");
            return false;
        }
        userCredentials.put(username, password);
        userStudents.put(username, new ArrayList<>());
        System.out.println("Registration successful! You can now login.");
        return true;
    }
    
    public boolean login(String username, String password) {
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }
    
    public List<Student> getUserStudents(String username) {
        return userStudents.get(username);
    }
}

// Step 3: Create the Student Management System
class StudentManagement {
    private List<Student> students;
    private Scanner scanner = new Scanner(System.in);
    
    public StudentManagement(List<Student> students) {
        this.students = students;
    }
    
    public synchronized void addStudent() {
        try {
            System.out.print("Enter Student ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Age: ");
            int age = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Course: ");
            String course = scanner.nextLine();

            students.add(new Student(id, name, age, course));
            System.out.println("Student added successfully!\n");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter correct values.");
            scanner.nextLine();
        }
    }

    public synchronized void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found!\n");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public synchronized void updateStudent() {
        try {
            System.out.print("Enter Student ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            for (Student student : students) {
                if (student.getId() == id) {
                    System.out.print("Enter new Name: ");
                    student.setName(scanner.nextLine());

                    System.out.print("Enter new Age: ");
                    student.setAge(scanner.nextInt());
                    scanner.nextLine();

                    System.out.print("Enter new Course: ");
                    student.setCourse(scanner.nextLine());

                    System.out.println("Student updated successfully!\n");
                    return;
                }
            }
            System.out.println("Student not found!\n");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter correct values.");
            scanner.nextLine();
        }
    }

    public synchronized void deleteStudent() {
        try {
            System.out.print("Enter Student ID to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            students.removeIf(student -> student.getId() == id);
            System.out.println("Student deleted successfully (if existed)!\n");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter correct values.");
            scanner.nextLine();
        }
    }

    public void menu() {
        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> viewStudents();
                    case 3 -> updateStudent();
                    case 4 -> deleteStudent();
                    case 5 -> {
                        System.out.println("Exiting...\n");
                        return;
                    }
                    default -> System.out.println("Invalid choice! Please try again.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }
    }

}

// Step 4: Main class to run the system with authentication
public class StudentManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Authentication auth = new Authentication();
        
        System.out.println("1. Register\n2. Login");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        String username, password;
        if (choice == 1) {
            System.out.print("Enter new Username: ");
            username = scanner.nextLine();
            System.out.print("Enter new Password: ");
            password = scanner.nextLine();
            auth.register(username, password);
        }
        
        System.out.print("Enter Username: ");
        username = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        password = scanner.nextLine();
        
        if (auth.login(username, password)) {
            System.out.println("Login Successful!\n");
            StudentManagement system = new StudentManagement(auth.getUserStudents(username));
            system.menu();
        } else {
            System.out.println("Invalid credentials! Exiting...");
        }
    }
}
