
Breakout
====
## High Level Design Goals
* There will be a total of three levels in the game. With each level, the game will get harder as the block gets closer and the number of "iron blocks" that could not be destroyed increases.
* The addition of flippers to the game makes the controls more complicated thus more challenging. Flippers would also become very useful for veteran players, so they bring a lot of potential to the game.
* The "bomb" blocks incorporates of the black ball in pool games, i.e. they are intended to be hit last so that their negative effect will not affect the player during the entire level.
* Powerups are rare, but each type has the same probability to appear. Some are more powerful than others so that increases randomness of the game.
* Score and leaderboard system allow multiplayer competitions.

## Adding New Features
If you want to add new features to the game, for example add component, add the component in the setupGameScene() method and count for the continuous movement of the component in the step() method.

For example, if I want to create a target in the game, I added the target object in the setupGameScene() method and check in every frame if the bouncer has hit the target.

## Major Design Choices
* All of the objects used were JavaFX.shape objects (Circle, Rectangle) instead of ImageViews to better record the boundary coordinates of the objects. This choice sacrifices the entire look of the game but manage collisions more precisely.

## Assumptions and simplifications
* The Flippers are intended not be hit from the bottom to avoid a bug that the bouncer would be stuck on the Flippers during multiple consecutive collisions. To fix this issue, I assumed that all interactions happened from the top so the bouncer's y-direction speed will all be negative (going up) after the collision.
* The collisions between the bouncer and the blocks are simplified so that x-direction collisions coming from the left are detected first, then from the right, top and bottom in an ordered matter. This is easier to implement but may cause bugs in the speed change after collisions since a collision may satisfy multiple conditions.