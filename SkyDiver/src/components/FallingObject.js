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
      return (
        this.x < playerX + playerWidth &&
        this.x + this.width > playerX &&
        this.y < playerY + playerHeight &&
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
    applyEffect(gameContext) {
      // Default behavior can be overridden by subclasses
    }
  }
  