import React from 'react';
import { Image } from 'react-native';


export default class FallingObject {
    constructor(type, x, y, speed, direction = { dx: 0, dy: 1 }, globalSpeed = 5, image) {
      this.type = type;                     // Type of object (used for switching behavior)
      this.x = x;                           // X position
      this.y = y;                           // Y position
      this.speed = speed;                   // Object's own speed, for the direction vector
      this.globalSpeed = globalSpeed;       // Global upward movement speed
      this.width = 50;                      // Default width
      this.height = 50;                     // Default height
      this.direction = direction;           // Direction vector for movement (dx, dy), which gives a slope
      this.image = image;                   // Path to the image/sprite
    }
  
    // Method to update the object's position based on its own movement and global upward movement
    fall() {
      // Global upward movement, which is how we pretend our skydiver is falling
      this.y -= this.globalSpeed;
  
      // Additional movement based on the object's direction vector, or 
        // the object's own speed, as each object can be different
      this.x += this.speed * this.direction.dx;
      this.y += this.speed * this.direction.dy;
    }
  
    // Method to check if the object has gone off-screen, for destroying the object
    isOffScreen(width, height) {
      return this.y + this.height < 0 || this.x < 0 || this.x > width;
    }
  
    // Method to check collision if this object has collided with the player, in a box collider way
    checkCollision(playerX, playerY, playerWidth, playerHeight) {
      // Check if the object's bounding box intersects with the player's bounding box, which is a simple box collider
      return (
        //remember that the cartesian coordinate system is flipped in the y direction in programming(yes i know)
        //first condition checks if the object's x position is less than the player's x position plus the player's width
        this.x < playerX + playerWidth &&
        //second condition checks if the object's x position plus the object's width is greater than the player's x position
        this.x + this.width > playerX &&
        //third condition checks if the object's y position is less than the player's y position plus the player's height
        this.y < playerY + playerHeight &&
        //fourth condition checks if the object's y position plus the object's height is greater than the player's y position
        this.y + this.height > playerY
      );
    }
    //need a method for rendering each object, which will be called in the GameScreen
    render() {
        return (
          <Image
            source={this.image}
            style={{
              width: this.width,
              height: this.height,
              position: 'absolute',
              left: this.x,
              top: this.y,
            }}
          />
        );
      }
  
    // Method to handle the effect of the object (to be overridden)
    //allows for each object to have a different effect when it collides with the player
    //like change score, or add a multiplier, or end the game
    applyEffect(gameContext, navigation) {
      // will be overriden by subclasses
    }
  }

  //override the FallingObject class to create different types of falling objects

  //first, a skydiver object that moves in a sine wave pattern, needing to be dodged
  class SkydiverObject extends FallingObject {
    constructor(x, y, globalSpeed) {
      super('skydiver', x, y, 0.5, { dx: 0, dy: 0.9 }, globalSpeed, images.skydiver);
      this.amplitude = 50;
      this.frequency = 0.05;
    }
  
    fall() {
      this.y -= this.globalSpeed;
      this.y += this.speed * this.direction.dy;
      this.x += this.amplitude * Math.sin(this.y * this.frequency);
    }
  
    applyEffect(gameContext, navigation) {
      // Skydiver object behavior (e.g., end the game or decrease points)
    }
  }
  
  //a hangglider object that ends the game when collided with, as you and mr hangglider cannot coexist in the same space
  class HangGliderObject extends FallingObject {
    constructor(x, y, globalSpeed) {
      super('hangglider', x, y, 2, { dx: 1, dy: 0 }, globalSpeed, images.hangglider);
    }
  
    applyEffect(gameContext, navigation) {
      // End the game and navigate to the end screen
      gameContext.setGameOver(true);
      navigation.replace('End');  // this takes us to the End screen, or game over screen
    }
  }
  
  //a bird object that decreases the player's score when collided with
  class BirdObject extends FallingObject {
    constructor(x, y, globalSpeed) {
      super('bird', x, y, 1, { dx: 0.3, dy: 0 }, globalSpeed, images.bird);
    }
  
    applyEffect(gameContext, navigation) {
      // Decrease player's score
      gameContext.changeScore(-5);
    }
  }
  
  //a balloon object that increases the player's score when collided with
  class BalloonObject extends FallingObject {
    constructor(x, y, globalSpeed) {
      super('balloon', x, y, 0, { dx: 0, dy: 0 }, globalSpeed, images.balloon);
    }
  
    applyEffect(gameContext, navigation) {
      // Increase player's score
      gameContext.changeScore(10);
    }
  }
  
  // here we use a factory pattern (function) to create different types of falling objects
  export function createFallingObject(type, x, y, globalSpeed) {
    switch (type) {
      case 'skydiver':
        return new SkydiverObject(x, y, globalSpeed);
      case 'hangglider':
        return new HangGliderObject(x, y, globalSpeed);
      case 'bird':
        return new BirdObject(x, y, globalSpeed);
      case 'balloon':
        return new BalloonObject(x, y, globalSpeed);
      default:
        return null;
    }
  }

  