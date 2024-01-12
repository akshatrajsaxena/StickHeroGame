Akshat Raj Saxena(2022054) and Vansh Agarwaal(2022558)
Github Link: https://github.com/akshatrajsaxena/Stick_Hero_Game_JavaFx

Working
We made three FXML files, representing the Home screen, Gameplay, and game screen. Home Screen consists of functionality in which you can pause the background music and an exit button. We use CSS files to make the code interactive. In the gameplay screen, to extend the stick we use mouse events and then the stick falls just like in the actual game, and for that we use Timeline. The generatePlatform sets the layout value randomly for spawning the platforms randomly and appearing infinitely. And then we are using the method to check the length and compare it with the platform's distances.

DesignPattern Used
We used two Design Patterns named Singleton and Observer. Singleton Design Pattern is used to manage the Score management and Observer pattern is used to handle the player in the game.

Contributions
Akshat read and give the basic HomeScreen and GamePlay Screen and Vansh made the GameScreen and the GamePlay Screen. Vansh made both the design
patterns. Akshat successfully implemented the logic for cherry pick. Vansh made the Junit Test Cases. Akshat made the UML Diagrams.

Junit
We are checking that only a single instance of soundManager is formed.
We are checking the initialised platform generated using the platform generator

How to Play
Hold your mouse key in order to extend the Stick and release the mouse to fall the stick.