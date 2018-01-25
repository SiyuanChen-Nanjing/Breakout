
Breakout
====
## Introduction
Breakout is a game by Siyuan Chen. Some parts of it are based on Example_Bouncer.java by Robert Duvall. This game is the first assignment of CS308 Spring 2018 at Duke University. It was created on Jan. 16, 2018 and was finished on Jan. 23, 2018.
Run Breakout.java to start playing the game!

## Cheat Keys
* F: makes bouncer travel 1.8x faster in both x and y directions;
* R: reset paddle and bouncer;
* W: win the game;
* L: get an additional life;
* I: makes bouncer able to destroy iron blocks;
* 1-3: jump to level 1-3

## Design Issues
* Simplifications and Assumptions
	* Flippers could only interact with the bouncer on the top part and could only affect the bouncer's speed on the y-direction to avoid bugs in consecutive collisions.
* Known bugs
	* Due to the collision detection mechanism between the bouncer and the blocks; sometimes the bouncer would not change speed as physically expected.

## Extra Feature
Combined some features in Pinball to the game, in this case the two "flippers" in Pinball games.

## Reflection on the assignment
This assignment is a great introduction of JavaFX. It's difficulty and flexibility suits a programmer new to JavaFX.
