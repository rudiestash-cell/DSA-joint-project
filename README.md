# Ghana Smart Service Operations Optimizer

## DCIT 204/308 – Data Structures and Algorithms Project

## Project Overview

The Ghana Smart Service Operations Optimizer is a console-based Java application designed to manage service operations in a Ghanaian context.

The system will store operational data in a database, load information into custom-built data structures, apply algorithms to solve real-world problems, test correctness, and analyse performance.

Our selected context:

**UG Campus Service Hub**

The system models campus services such as maintenance requests, resource allocation, and route planning between locations on campus.

---

# Project Objectives

The system should be able to:

- Store and retrieve service data using a database
- Manage service requests
- Prioritise urgent requests
- Search and sort records efficiently
- Find shortest routes between locations
- Demonstrate different data structures and algorithms
- Compare algorithm performance using experiments

---

# Technology Stack

Programming Language:
- Java

Database:
- SQLite

Version Control:
- GitHub

Application Type:
- Console-based Java Application



---

# GitHub Workflow

## IMPORTANT RULES

1. Do NOT push directly to the main branch.

2. Every member must work on their assigned branch.

3. Test your code before pushing.

4. Explain and understand your own contribution.

5. AI-generated code should not be used blindly. Every member must be able to explain their data structure and algorithm during the presentation.

---

# Branch Structure

## main

Stable version of the complete project.

Only merged and tested code should enter this branch.

---

## input-specification

Module:
M1 - Input/Output and Problem Specification

Responsibilities:

- Define the problem statement
- Define inputs and outputs
- State assumptions and constraints
- Create pseudocode and flowcharts for major operations

---

## database-loader

Module:
M2 - Database and Data Loader

Responsibilities:

- Create SQLite database
- Create database tables
- Prepare datasets
- Import CSV files
- Validate records
- Load database data into program structures

Files:

- locations.csv
- roads.csv
- service_requests.csv
- resources.csv

---

## data-structures

Module:
M3 - Custom Data Structure Library

Responsibilities:

Implement required structures from scratch:

- Dynamic Array
- Linked List
- Stack
- Queue
- Circular Queue
- Deque
- Priority Queue / Heap
- Binary Search Tree
- Hash Table
- Set / Map
- Disjoint Set

Evidence required:

- Unit tests
- Diagrams
- Demonstrations
- Edge case handling

---

## searching-sorting

Module:
M4 - Searching and Sorting Engine

Responsibilities:

Implement:

Searching:
- Linear Search
- Binary Search

Sorting:
- Selection Sort
- Insertion Sort
- Merge Sort
- Quick Sort

Provide:

- Test cases
- Complexity analysis
- Performance comparison

---

## service-scheduling

Module:
M5 - Service Scheduling Engine

Responsibilities:

Implement scheduling methods:

- FIFO queue scheduling
- Circular queue scheduling
- Deque operations
- Priority queue scheduling

Example:

Normal requests → Queue

Urgent requests → Priority Queue

---

## indexing-engine

Module:
M6 - Indexing Engine

Responsibilities:

Implement indexing using:

- BST
- Balanced tree concepts
- B-Tree concepts

Used for faster searching of:

- Requests
- Locations
- Resources

---

## graph-routing

Module:
M7 - Graph Route Engine

Responsibilities:

Implement:

Graph representation:
- Adjacency List
- Adjacency Matrix

Algorithms:

- BFS
- DFS
- Dijkstra
- Prim
- Kruskal

Main goal:

Find routes between campus locations.

---

## optimisation-engine

Module:
M8 - Optimisation Engine

Responsibilities:

Implement:

- Greedy algorithms
- Dynamic Programming

Examples:

- Resource allocation
- Request selection
- Budget/capacity optimisation

Include:

- Greedy failure example
- DP explanation

---

## testing-correctness

Module:
M9 - Testing and Correctness Evidence

Responsibilities:

Prepare:

- Unit tests
- Trace tables
- Edge cases
- Proof sketches
- Algorithm validation

Required examples:

- Binary Search trace
- Sorting trace
- Dijkstra trace
- MST trace
- DP table

---

## performance-analysis

Module:
M10 - Empirical Efficiency Lab

Responsibilities:

Measure:

- Runtime
- Memory usage

Generate:

- CSV results
- Graphs
- Performance comparisons

Experiments:

- Searching comparison
- Sorting comparison
- Hash table performance
- Tree performance
- Graph algorithm performance

---

# Contribution Process

## Step 1: Clone Repository

---

## Step 2: Create Your Branch

Example: git checkout -b graph-routing

---

## Step 3: Work on Your Module

Write clean and understandable code.

Add comments where necessary.

---

## Step 4: Commit Changes

Example: git add .
git commit -m "Implemented Dijkstra algorithm"

---

## Step 5: Push Changes
Example: git push origin graph-routing


---

## Step 6: Create Pull Request

The GitHub Lead will review and merge completed work.

---

# Coding Guidelines

- Use meaningful class and method names
- Keep code organised
- Avoid duplicate code
- Do not use built-in data structures for assessed implementations

Examples NOT allowed for core logic:

- HashMap
- TreeMap
- PriorityQueue
- Stack
- ArrayDeque

---

# Testing Requirements

Every implementation should include:

## Normal Cases

Example:

Adding elements to a queue.

## Boundary Cases

Example:

Removing from an empty queue.

## Invalid Cases

Example:

Searching unavailable records.

---

# Final Deliverables

The final submission must include:

## 1. Source Code

Complete Java project.

## 2. Database

SQLite database and seed data.

## 3. Technical Report

Including:

- Problem statement
- Dataset description
- Database schema
- Data structures
- Algorithms
- Testing evidence
- Performance analysis

## 4. Performance Results

Including:

- CSV files
- Graphs
- Analysis

## 5. Demonstration

Every member must be able to explain:

- At least one data structure
- At least one algorithm

---

# Team Reminder

This is a group project, but every member is individually accountable.

Do not only complete your code. Understand it.

The goal is not just to submit a program, but to demonstrate understanding of Data Structures and Algorithms.
