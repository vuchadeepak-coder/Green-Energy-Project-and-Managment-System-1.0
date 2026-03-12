import java.util.*;

// ================= MAIN CLASS ===================
// This class simulates the Green Energy Project Management System
// integrating multiple DSA concepts from your syllabus
public class GreenEnergyDSA {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // ProjectManager handles all data operations using DSA structures
        ProjectManager manager = new ProjectManager();
        
        // ---------- Sample cities with CO2 emissions ----------
        // Using ArrayList to store cities (List ADT)
        manager.addCity(new City("Delhi", 250));
        manager.addCity(new City("Mumbai", 180));
        manager.addCity(new City("Kolkata", 200));
        manager.addCity(new City("Bengaluru", 90));
        
        // ---------- Sample projects ----------
        // Using LinkedList to store projects (Linked List ADT)
        manager.addProject(new Project(101,"Solar Plant",5));
        manager.addProject(new Project(102,"Wind Farm",3));
        manager.addProject(new Project(103,"Hydro Plant",4));
        
        boolean exit = false;
        while(!exit){
            System.out.println("\n=== Green Energy DSA Project ===");
            System.out.println("1. View Top Polluted Cities");
            System.out.println("2. Search City Pollution");
            System.out.println("3. Add New Project");
            System.out.println("4. Process Project Tasks (Queue)");
            System.out.println("5. Priority Project Report (Heap)");
            System.out.println("6. Undo Last Project Action (Stack)");
            System.out.println("7. Exit");
            System.out.print("Choice: ");
            int ch = sc.nextInt();
            sc.nextLine();
            
            switch(ch){
                case 1: manager.showTopPollutedCities(); break; 
                // Sorting algorithm used to rank cities by CO2 emission (Sorting concept)
                
                case 2:
                    System.out.print("Enter city name: ");
                    String cityName = sc.nextLine();
                    manager.searchCity(cityName); break;
                // Linear search to find city (Searching concept)
                
                case 3:
                    System.out.print("Enter project ID: "); int pid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter project name: "); String pname = sc.nextLine();
                    System.out.print("Enter priority (1-5): "); int prio = sc.nextInt();
                    manager.addProject(new Project(pid,pname,prio)); break;
                // Adding to LinkedList, PriorityQueue, HashMap, Queue, and Stack
                
                case 4: manager.processProjectTasks(); break;
                // Processing tasks in FIFO order using Queue ADT
                
                case 5: manager.showPriorityProjects(); break;
                // PriorityQueue (Heap) to show high-priority projects
                
                case 6: manager.undoLastProject(); break;
                // Undo last action using Stack ADT
                
                case 7: exit=true; break;
                
                default: System.out.println("Invalid choice!"); break;
            }
        }
        sc.close();
        System.out.println("Exiting Green Energy DSA Project");
    }
}

// ==================== CITY CLASS ====================
// Stores information about a city and its CO2 emissions
// Used in ArrayList for easy storage and sorting
class City {
    String name;
    int co2Emission; // arbitrary emission units
    
    City(String name, int emission){
        this.name=name;
        this.co2Emission=emission;
    }
}

// ==================== PROJECT CLASS ====================
// Stores project details: ID, Name, Priority
// Will be stored in LinkedList, PriorityQueue, HashMap, and Queue
class Project {
    int id;
    String name;
    int priority; // 1(low) to 5(high)
    
    Project(int id,String name,int priority){
        this.id=id; this.name=name; this.priority=priority;
    }
    
    public String toString(){ return "ID:"+id+" Name:"+name+" Priority:"+priority; }
}

// ==================== PROJECT MANAGER ====================
// Handles all operations on cities and projects
class ProjectManager {
    
    // ---------- DATA STRUCTURES USED ----------
    ArrayList<City> cities = new ArrayList<>();
    // List ADT: stores cities; enables sorting & searching
    
    LinkedList<Project> projects = new LinkedList<>();
    // Linked List ADT: dynamic addition/removal of projects
    
    PriorityQueue<Project> priorityQueue = new PriorityQueue<>( (a,b)-> b.priority - a.priority);
    // Heap / Priority Queue ADT: to manage projects by priority
    
    Stack<String> actionStack = new Stack<>();
    // Stack ADT: stores undoable actions
    
    Queue<String> taskQueue = new LinkedList<>();
    // Queue ADT: manages project tasks in FIFO order
    
    HashMap<Integer, Project> projectMap = new HashMap<>();
    // Hashing concept: fast access to projects by ID
    
    // ---------- CITY OPERATIONS ----------
    void addCity(City c){ cities.add(c); }
    
    void showTopPollutedCities(){
        // Sorting concept: descending order by CO2 emission
        cities.sort((a,b)-> b.co2Emission - a.co2Emission);
        System.out.println("\nTop Polluted Cities:");
        for(City c: cities) System.out.println(c.name + " : " + c.co2Emission);
    }
    
    void searchCity(String name){
        // Linear search concept
        for(City c: cities){
            if(c.name.equalsIgnoreCase(name)){
                System.out.println("City: "+c.name+" CO2 Emission: "+c.co2Emission); return;
            }
        }
        System.out.println("City not found!");
    }
    
    // ---------- PROJECT OPERATIONS ----------
    void addProject(Project p){
        projects.add(p); // Linked List
        priorityQueue.add(p); // Heap
        projectMap.put(p.id,p); // Hashing
        actionStack.push("Added project ID:"+p.id); // Stack
        taskQueue.add("Process project "+p.name); // Queue
        System.out.println("Project added successfully!");
    }
    
    void processProjectTasks(){
        System.out.println("\nProcessing Project Tasks:");
        while(!taskQueue.isEmpty()){
            // Queue: FIFO processing
            System.out.println("Task: "+taskQueue.poll());
        }
    }
    
    void showPriorityProjects(){
        System.out.println("\nPriority Projects:");
        // Create a temporary heap to display without removing original
        PriorityQueue<Project> temp = new PriorityQueue<>( (a,b)-> b.priority - a.priority);
        temp.addAll(priorityQueue);
        while(!temp.isEmpty()) System.out.println(temp.poll());
    }
    
    void undoLastProject(){
        // Stack: LIFO undo operation
        if(!actionStack.isEmpty()){
            String last = actionStack.pop();
            System.out.println("Undo action: "+last);
        }else{
            System.out.println("No actions to undo.");
        }
    }
}