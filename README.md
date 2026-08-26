# Ghana Smart Service Operations Optimizer

## Current runnable implementation

The project now includes from-scratch array, linked-list, stack, queue, circular queue, deque, binary heap, BST, hash table and disjoint-set implementations, together with CSV loading, scheduling, graph routing (BFS/DFS/Dijkstra/Prim/Kruskal), greedy and DP optimisation, and CSV benchmarking.

Compile and run from the repository root (JDK 17+):

```powershell
javac -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object FullName)
java -cp out structures.StructureTest
java -cp out searchingandsorting.TestRunner
java -cp out Main demo
```

Run `java -cp out Main` for the interactive examiner menu. It uses the seeded local CSVs in `src/data`; benchmark output is written to `src/data/algorithm_runs_generated.csv`.

`database.SqliteRepository` creates and seeds the SQLite schema whenever the SQLite JDBC driver is included on the runtime classpath. Without that optional driver, the same validated CSV seeds are used and the console explicitly reports that fallback.

## DCIT 204 / DCIT 308 – Data Structures and Algorithms Project
*University of Ghana (UG) Campus Service Hub*

---

## 📌 Project Overview

The **Ghana Smart Service Operations Optimizer** is a console-based Java application designed to optimize and automate service operations, resource scheduling, facility lookups, shortest-path route planning, and decision-making within the University of Ghana (UG), Legon campus.

The system integrates an SQLite database, custom data structures implemented strictly from scratch, algorithmic search/sort/graph/optimization engines, empirical efficiency benchmarking, and comprehensive unit testing to address real-world operational challenges on campus.

### 🏢 Selected Context: UG Campus Service Hub
The application models real campus service operations, including:
- **Campus Facilities & Locations**: UGCS, Balme Library, Great Hall, Halls of Residence (Commonwealth, Volta, Legon, Akuafo, Sarbah, Sarbah Annex), University Hospital, Academic Departments (Physics, Chemistry, Math, Engineering, Law, Business, Health Sciences), Commercial Hubs (Night Market, Banking Square, Bush Canteen, Central Cafeteria), and Emergency Services (UG Stadium, Fire Station, Legon Police Station).
- **Service Requests**: Maintenance, Electrical, Plumbing, ICT, Network, Furniture, Sanitation, and Emergency requests categorized with urgency levels (1 to 5) and operational status (`Pending`, `In Progress`, `Resolved`).
- **Campus Road Network**: Weighted graph representing campus road connections and physical distances (in km) between key landmarks.

---

## 🎯 Project Objectives

- **Data Persistence & Ingestion**: Store and manage service data using an SQLite database and load records from CSV files into program structures.
- **Custom Data Structure Library**: Implement core linear and non-linear data structures strictly from scratch without relying on standard Java collection frameworks for core logic.
- **Efficient Search & Sorting**: Enable fast searching and sorting of service requests by urgency, location, category, and request ID.
- **Service Dispatch & Scheduling**: Implement priority queues, circular queues, deques, and FIFO queues for fair and urgent request scheduling.
- **Fast Indexing**: Build binary search tree (BST) and balanced indexing structures for fast entity lookups.
- **Campus Route Planning**: Represent campus topology as a weighted graph to compute optimal routes (BFS, DFS, Dijkstra) and minimal network coverage (Prim's & Kruskal's MST).
- **Resource Optimization**: Maximize service completion under resource, time, and budget constraints using Greedy algorithms and Dynamic Programming (0/1 Knapsack).
- **Empirical Efficiency Benchmarking**: Quantify execution time ($O(N)$) and space usage across varied dataset sizes, generating CSV outputs and comparison graphs.
- **Testing & Correctness**: Maintain rigorous unit tests, boundary condition handling, and algorithm trace tables.

---

## 🛠 Technology Stack

- **Programming Language**: Java (JDK 17+)
- **Database**: SQLite (via JDBC)
- **Version Control**: Git & GitHub
- **Application Type**: Console-based Core Java Application

---

## 🌿 GitHub Workflow & Team Rules

### 🚨 Critical Contribution Guidelines
1. **Never push directly to `main`**: The `main` branch is reserved exclusively for stable, tested, and reviewed code.
2. **Work on Assigned Module Branches**: Every team member must work within their designated feature branch corresponding to their assigned module allocation (e.g., `input-specification`, `database-loader`, `searching-sorting`, etc.).
3. **Compilation & Testing Required**: Verify that code compiles cleanly and passes all unit tests prior to pushing or opening a Pull Request (PR).
4. **No Unexplained AI Code**: Every team member must understand their custom data structures and algorithm implementations in depth and be prepared to explain and demonstrate them.
5. **No Java Built-in Collections for Assessed Logic**: Built-in collections (e.g., `java.util.ArrayList`, `HashMap`, `PriorityQueue`, `LinkedList`, `Stack`, `ArrayDeque`, `TreeMap`) are strictly forbidden for core assessed data structure and algorithm implementations. Custom data structures must be built from scratch.

---

## 📂 Module Allocations (M0 – M9)

The project is structured into **10 core modules**, starting from **M0 to M9**:

### 🔹 M0: Input/Output and Problem Specification
- **Branch**: `input-specification`
- **Module ID**: **M0**
- **Responsibilities**:
  - Formulate problem statement, system constraints, inputs, and outputs.
  - Design flowchart diagrams and formal pseudocode for primary system workflows.
  - Document domain entity attributes (Location, Road, Service Request, Resource).

---

### 🔹 M1: Database and Data Loader
- **Branch**: `database-loader`
- **Module ID**: **M1**
- **Responsibilities**:
  - Create SQLite database schema (`schema.sql`) and tables (`locations`, `roads`, `service_requests`).
  - Manage CSV dataset files (`locations.csv`, `roads.csv`, `service_requests.csv`).
  - Build Java Data Loader to validate and ingest SQLite/CSV records into program data structures.

---

### 🔹 M2: Custom Data Structure Library
- **Branch**: `data-structures`
- **Module ID**: **M2**
- **Responsibilities**:
  - Implement core data structures from scratch:
    - **Linear**: Dynamic Array, Singly & Doubly Linked Lists, Stack, Queue, Circular Queue, Deque.
    - **Non-Linear**: Priority Queue / Min-Max Heap, Binary Search Tree (BST), Hash Table (with collision resolution), Set / Map, Disjoint Set (Union-Find).
  - Provide unit tests, visual structure diagrams, and edge-case handling for each implementation.

---

### 🔹 M3: Searching and Sorting Engine
- **Branch**: `searching-sorting`
- **Module ID**: **M3**
- **Responsibilities**:
  - **Searching**: Linear Search, Binary Search (search by Request ID, urgency, location).
  - **Sorting**: Selection Sort, Insertion Sort, Merge Sort, Quick Sort (sort by urgency, request ID, distance).
  - Provide test suites, complexity analysis, trace tables, and performance comparison metrics.

---

### 🔹 M4: Service Scheduling Engine
- **Branch**: `service-scheduling`
- **Module ID**: **M4**
- **Responsibilities**:
  - **FIFO Queue Scheduling**: Standard request queueing by arrival order.
  - **Circular Queue Scheduling**: Round-robin technician service dispatch.
  - **Deque Operations**: Emergency front-of-queue insertion and request cancellation from the back.
  - **Priority Queue Scheduling**: Urgent request dispatching (Urgency level 5 processed first).

---

### 🔹 M5: Indexing Engine
- **Branch**: `indexing-engine`
- **Module ID**: **M5**
- **Responsibilities**:
  - Implement indexing using Binary Search Trees (BST), AVL/Balanced BST concepts, and B-Tree concepts.
  - Enable fast indexed lookups ($O(\log N)$) for locations, resources, and service requests.

---

### 🔹 M6: Graph Route Engine
- **Branch**: `graph-routing`
- **Module ID**: **M6**
- **Responsibilities**:
  - Graph representation of campus topology using Adjacency List and Adjacency Matrix.
  - Traversal algorithms: Breadth-First Search (BFS) and Depth-First Search (DFS).
  - Shortest Path: Dijkstra's Algorithm for calculating shortest physical routes between campus locations.
  - Minimum Spanning Tree (MST): Prim's and Kruskal's Algorithms for optimal campus utility layout.

---

### 🔹 M7: Optimisation Engine
- **Branch**: `optimisation-engine`
- **Module ID**: **M7**
- **Responsibilities**:
  - Resource allocation and request selection under time, budget, and capacity constraints.
  - **Greedy Algorithms** vs. **Dynamic Programming** (0/1 Knapsack optimization model).
  - Demonstrate concrete examples where the Greedy approach fails and DP achieves global optimality.

---

### 🔹 M8: Testing and Correctness Evidence
- **Branch**: `testing-correctness`
- **Module ID**: **M8**
- **Responsibilities**:
  - Compile unit tests, boundary condition coverage, and formal verification trace tables.
  - Provide step-by-step trace tables for Binary Search, Sorting algorithms, Dijkstra's algorithm, MST, and DP matrices.

---

### 🔹 M9: Empirical Efficiency Lab (Performance Analysis)
- **Branch**: `performance-analysis`
- **Module ID**: **M9**
- **Responsibilities**:
  - Run empirical timing (nanoseconds/milliseconds) and memory benchmarks across scaled datasets ($N$).
  - Export performance metrics to CSV format and generate comparison plots/charts.
  - Conduct empirical vs. theoretical Big-O complexity analysis ($O(1)$, $O(\log N)$, $O(N)$, $O(N \log N)$, $O(N^2)$).

---

## 🔄 Contribution & Branching Process

### Step 1: Clone Repository
```bash
git clone https://github.com/rudiestash-cell/DSA-joint-project.git
cd DSA-joint-project
```

### Step 2: Create / Checkout Your Branch
```bash
git checkout <branch-name>
# Example: git checkout searching-sorting
```

### Step 3: Work on Your Module
- Write clean, modular, and well-commented Java code.
- Add unit tests covering normal, boundary, and edge cases.

### Step 4: Commit & Push Changes
```bash
git add .
git commit -m "Implemented BinarySearch algorithm and test suite"
git push origin <branch-name>
```

### Step 5: Create Pull Request (PR)
- Open a PR against the `main` branch on GitHub.
- Maintainers will review and merge completed, tested code.

---

## 🧪 Testing Guidelines

Every implementation must include unit testing evidence covering:
- **Normal Cases**: Standard operation inputs (e.g., inserting/retrieving valid records, sorting standard arrays).
- **Boundary / Edge Cases**: Empty structures, single-element collections, duplicate keys, extreme capacity bounds.
- **Invalid / Error Cases**: Non-existent target lookups, popping empty stacks/queues, invalid SQLite records.

---

## 📦 Final Project Deliverables

1. **Source Code**: Complete Java codebase built with modular architecture and clean code practices.
2. **Database & Data Files**: SQLite database (`.db`) with schema (`schema.sql`) and seed CSV datasets (`locations.csv`, `roads.csv`, `service_requests.csv`).
3. **Technical Project Report**: Comprehensive report covering problem statement, schema, custom data structures, algorithm design, trace tables, and performance analysis.
4. **Performance Empirical Results**: Benchmarking CSV logs, performance graphs, and Big-O efficiency analysis.
5. **Demonstration & Defense**: Individual explanation and live demonstration of assigned data structures and algorithms by each team member.

---

## 💡 Team Reminders & Academic Integrity

- This is a collaborative group project, but **individual accountability** is strictly required.
- Do not merely write code—understand it thoroughly. Every team member will be evaluated individually during defense.
- The core objective is demonstrating a deep, working understanding of Data Structures and Algorithms.
