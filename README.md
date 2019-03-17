# Vehicle-Collision-Binary-Search

### Program Description

This program prompts the user for an input zipcode, start date and end date and outputs the details of vehicles collisions in the area and dates provided. The program validates the user input and uses a binary tree to the store the data.

If the tree becomes imbalanced the program applies rotation to the tree (AVL tree) to ensure that the tree is balanced and therefore binary sarch is efficient.

The program continues to prompt the user for details until the user hits quit.

### Files

1. Collision.java
2. CollisionInfo.java
3. CollisionsData.java
4. Date.java

### Guidelines to build and run the application
1. Clone the repository
2. From the root of the repository , compile the project using the following command: 
`javac *.java`
3. To run the compiled program, execute the command: 
`java CollisionInfo`
4. The program will ask for a zip code, start date and end date to retrieve the collision data.
5. Since the program validates input, please ensure you input the date in the format specified by the program.
6. The program will output a summary report of the motor vehicle collisions for the specified zip code and date range.
