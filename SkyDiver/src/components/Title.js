import React from 'react';
import { Image, StyleSheet } from 'react-native';

/**
 * Title component displays the game title "SKY DIVER" as an image.
 * It is positioned at the top of the screen.
 *
 * @component
 * @returns {JSX.Element} An image of the game title.
 */
export default function Title() {
  return (
    <Image
      source={require('../sprites/Title.png')}
      style={styles.title}
    />
  );
}

const styles = StyleSheet.create({
  title: {
    width: 404,
    height: 232, 
    position: 'absolute',
    top: 100, 
    alignSelf: 'center',
    //a z index is used to stack elements on top of each other, 
    //consider it like adding a z direction to the 2D plane, with each thing sitting a single unit above the last
    //more than one thing can have the same z index, in which case they are ordered by the order they are declared in the code
    zIndex: 1, // Ensure the title stays on top of the background
  },
});
