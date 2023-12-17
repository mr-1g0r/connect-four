# Stage 4/4: Players Ready
## Description
In this stage, we will finalize our game to play it with a friend! For this, we need to come up with an algorithm to check if a player has connected four pieces in a row after every click. The check must be carried out in three directions: horizontal, vertical, and diagonal. When the algorithm detects a winning move, the program should highlight these four cells so that the players can easily see who's won.

We will also add the baseline background color for the cells from the start of the game and a distinct color for the cells that form a win condition. You can choose any colors that you want as long as the baseline and winning colors are different.

Ensure that your program processes invalid moves in the following way. A click on a filled column should do nothing, and the player must be given another try. Additionally, a click on any cell after a game has been finished should lead to nothing.

Finally, don't forget to add the reset button!

## Objectives
In this stage, implement the following features:

1. Add the baseline color for all cells at the start of the application;
2. Add a reset button that extends the JButton named ButtonReset. The button should be enabled. Clicking on it should clear all cells and return them to their baseline color;
3. Check if either player has connected four in a row vertically, horizontally, or diagonally after each move;
4. Once a winner has been detected, change the color of the four winning cells to the winning color. The baseline color and the winning color should be two different colors;
5. A click on any board cell in any filled column must lead to nothing. The gameplay continues with the same player's turn;
6. A click on any board cell once a game is finished must lead to nothing.
