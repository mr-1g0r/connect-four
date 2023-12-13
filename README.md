# Stage 3/4: Filling the board
## Description
Have you noticed the issues in the last stage? When you click on a cell in the top row, it fills that particular cell even if the cells below are empty. In Connect Four, pieces are placed column by column. They are placed onto either the uppermost game piece in that column or, if the column is empty, onto the board bottom. In this stage, we will change the way players place the pieces. We need to designate a separate class that can check the first free cell in each column. The rest remains the same as in the previous stage.

## Objectives
In this stage, your program should:
1. Fill the cells according to the rule above — not by the position of the click but by the column that was clicked on;
2. Continue to alternate between the X and O pieces after each click.
