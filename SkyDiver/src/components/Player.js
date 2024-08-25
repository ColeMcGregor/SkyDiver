import React, { useState, useEffect } from 'react';
import { View, Image, StyleSheet, Dimensions, PanResponder } from 'react-native';

// Load the player sprites
const sprites = {
  slow : require('../sprites/player_slow.png'),
  fast : require('../sprites/player_fast.png'),
  neutral: require('../sprites/player_neutral.png'),
};

const { width, height } = Dimensions.get('window'); // Get device dimensions

export default function Player({ playerX, playerY, setPlayerX, setPlayerY }) {
  const [currentSprite, setCurrentSprite] = useState(sprites.neutral); // Default sprite

  // Movement constants
  const upSpeed = 10; // Speed for upward movement
  const downSpeed = 4; // Speed for downward movement
  const sideSpeed = 5; // Speed for horizontal movement

  // PanResponder to handle touch and drag inputs for vector movement
  const panResponder = PanResponder.create({
    onMoveShouldSetPanResponder: () => true,
    onPanResponderMove: (evt, gestureState) => {
      const { dx, dy } = gestureState;
      const newX = playerX + dx * sideSpeed * 0.1;
      const newY = playerY + (dy < 0 ? dy * upSpeed * 0.1 : dy * downSpeed * 0.1);

      // Update the player's position based on the gesture
      setPlayerX(Math.max(0, Math.min(newX, width - 100))); // Keep player within horizontal bounds
      setPlayerY(Math.max(0, Math.min(newY, height - 100))); // Keep player within vertical bounds
    },
    onPanResponderRelease: () => {
      // Logic for when the player stops dragging (e.g., return to neutral sprite)
    },
  });

  // Determine which sprite to use based on y-axis 
  useEffect(() => {
    if (playerY < height / 2) {
      setCurrentSprite(sprites.slow); // Use the "up" sprite when moving up
    } else if (playerY > height / 2) {
      setCurrentSprite(sprites.fast); // Use the "down" sprite when moving down
    } else {
      setCurrentSprite(sprites.neutral); // Use the neutral sprite for balanced motion
    }
  }, [playerY]);

  return (
    <View
      {...panResponder.panHandlers}
      style={[styles.player, { left: playerX, top: playerY }]}
    >
      <Image source={currentSprite} style={styles.sprite} />
    </View>
  );
}

const styles = StyleSheet.create({
  player: {
    position: 'absolute',
    width: 100,
    height: 100,
  },
  sprite: {
    width: '100%',
    height: '100%',
    resizeMode: 'contain',
  },
});
