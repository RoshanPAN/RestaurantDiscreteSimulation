>>> Description about the project.
1. Time:
    a) The relative time is measured with minutes as its unit.
    b) whole duration is 120 min as required.
2. Scheduling:
    * Working load on each machine is updated dynamically by SimpleScheduler.
    a) TableScheduler: diner is scheduled to table according to current working load for each machine.
    b) SimpleScheduler: machine and order is scheduled by this scheduler by finding the bottle neck machine dynamically.

3. Eat time: 30min

>>> Directories
    - Input Data Dir: ./resource
    - Source Code Dir: ./src

>>> How to run the program.
    - Compile the code(in ./src folder):
            src > javac com/lowson/Main.java

    - Run the code(in ./src folder):
            * you can input any file as you wish from stdin.
            src > java com.lowson.Main < ../resource/data1.txt
