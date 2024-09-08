import React, { useState, useEffect } from 'react';
import { View, Image, StyleSheet, Dimensions, PanResponder } from 'react-native';
import { GameContext } from '../context/GameContext';

// Load the player sprites
const sprites = {
  slow : require('../sprites/SkydiverSlowFall.png'),
  fast : require('../sprites/SkydiverFastFall.png'),
  neutral: require('../sprites/SkydiverIdle.png'),
};

const { width, height } = Dimensions.get('window'); // Get device dimensions

export default function Player({ playerX, playerY, setPlayerX, setPlayerY }) {
  const [currentSprite, setCurrentSprite] = useState(sprites.neutral); // Default sprite
  const [speedState, setSpeedState] = useState('neutral'); // Tracks the current speed state (neutral, slow, fast)


/** 
* first we will handle the movement of the player, we will use a pan responder to do this
* we will include the global movement speed as a dependency
* the faster/slower we move, the faster/slower the global speed is, simulating the player's speed
* this allows us to pretend that the player is falling, but in reality, the obstacles are moving up
* we will use a pan responder to handle the movement of the player
* the pan responder will track the movement of the player's finger
* and update the player's position accordingly
* we must remember that after the player has moved up or down a certain amount
* that we can consider their state to hve changed from idle to fast or slow
* we will use a threshold to determine this, and update the sprite accordingly
*/
  
  // Calculate the equator line 40% from the bottom
  const equatorLine = height * 0.6; // 40% up from the bottom = 60% down from the top

  // PanResponder to track user touch and update player position
  const panResponder = PanResponder.create({
    onMoveShouldSetPanResponder: () => true,
    onPanResponderMove: (evt, gestureState) => {
      // Get the finger's Y position
      const fingerY = gestureState.moveY;

      // Determine if the finger is above or below the equator line
      if (fingerY > equatorLine) {
        // Below the equator line -> Fast state
        setCurrentSprite(sprites.fast);
        setSpeedState('fast');
      } else if (fingerY <= equatorLine) {
        // Above the equator line -> Slow state
        setCurrentSprite(sprites.slow);
        setSpeedState('slow');
      } else {
        // Neutral state, for any other edge case
        setCurrentSprite(sprites.neutral);
        setSpeedState('neutral');
      }

      // Calculate new X position based on gesture movements (keep Y fixed based on logic)
      const newX = playerX + gestureState.dx;

      // Ensure player stays within screen bounds horizontally
      setPlayerX(Math.max(0, Math.min(newX, width - 50))); // Assuming player width is 50
    },
    onPanResponderRelease: () => {
      // When the user releases the touch, reset sprite to neutral
      setCurrentSprite(sprites.neutral);
      setSpeedState('neutral');
    },
  });

  // useEffect to update global speed based on the player's speed state
  useEffect(() => {
    if (speedState === 'fast') {
      // try adn increase speed
      changeSpeed(GameContext.globalSpeed + 1);
      } else if (speedState === 'slow') {
      // try to decrease speed
      changeSpeed(GameContext.globalSpeed - 1);
    }
  }, [speedState]); // This hook runs whenever the speedState changes

  return (
    <View
      {...panResponder.panHandlers} // Apply panResponder to the View
      style={[
        styles.player,
        {
          left: playerX,
          top: playerY, // You may want to keep this dynamic if the player can move up/down as well
        },
      ]}
    >
      <Image source={currentSprite} style={styles.image} />
    </View>
  );
}

const styles = StyleSheet.create({
  player: {
    position: 'absolute',
    width: 50, // Set your desired player width
    height: 100, // Set your desired player height
  },
  image: {
    width: '100%',
    height: '100%',
    resizeMode: 'contain', // Ensure the image is scaled correctly
  },
});