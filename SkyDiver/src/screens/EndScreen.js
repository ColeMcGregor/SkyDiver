import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import Background from './Background';

/**
 * EndScreen component is displayed when the game is over. It shows a "Game Over"
 * message and provides options to start the game again or exit the application.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {Object} props.navigation - The navigation object provided by React Navigation.
 * @returns {JSX.Element} A view with a background, "Game Over" message, and action buttons.
 */

export default function EndScreen({ navigation }) {
  return (
    <View style={styles.container}>
        {/* Display the background */}
      <Background />
        {/* Display the "Game Over" message */}
      <Text style={styles.gameOverText}>Game Over</Text>
        {/* Display the Start Again button */}
      <TouchableOpacity
        style={styles.button}
        onPress={() => navigation.replace('Game')}  
      >
        <Text style={styles.buttonText}>Start Again</Text>
      </TouchableOpacity>
      {/* Display the Exit button */}
      <TouchableOpacity
        style={styles.button}
        onPress={() => navigation.navigate('Start')}  
      >
        <Text style={styles.buttonText}>Exit</Text>
      </TouchableOpacity>
    </View>
  );
}

/**
 * Basic styles for the EndScreen component.
 */
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  gameOverText: {
    color: 'red',
    fontSize: 48,  
    fontWeight: 'bold',
    position: 'absolute',
    top: '40%',  
    textAlign: 'center',
    width: '80%',  
  },
  button: {
    backgroundColor: '#6200ee',
    paddingVertical: 15,
    paddingHorizontal: 30,
    borderRadius: 5,
    marginVertical: 10,
    position: 'absolute',
    bottom: '20%',  
  },
  buttonText: {
    color: '#fff',
    fontSize: 18,
    fontWeight: 'bold',
  },
});
