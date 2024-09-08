import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import Background from './Background';
import Title from './Title';
/**
 * StartScreen component displays the main menu of the game with options to
 * start the game, view stats, or view credits.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {Object} props.navigation - The navigation object provided by React Navigation.
 * @returns {JSX.Element} A view with buttons to navigate to different screens.
 */

export default function StartScreen({ navigation }) {
  return (
    <View style={styles.container}>
      <Background/>
      <Title/>
      <TouchableOpacity
        style={styles.button}
        onPress={() => navigation.navigate('Game')}
      >
        <Text style={styles.buttonText}>Start</Text>
      </TouchableOpacity>
      
      <TouchableOpacity
        style={styles.button}
        onPress={() => navigation.navigate('Stats')}  
      >
        <Text style={styles.buttonText}>Stats</Text>
      </TouchableOpacity>
      
      <TouchableOpacity
        style={styles.button}
        onPress={() => console.log('Cole McGregor, Hawk Lindner')} 
      >
        <Text style={styles.buttonText}>Credits</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f5f5f5',  //a light grey color
  },
  button: {
    backgroundColor: '#6200ee', //a purple color 
    paddingVertical: 15,
    paddingHorizontal: 30,
    borderRadius: 5,
    marginVertical: 10,  
  },
  buttonText: {
    color: '#fff',  //a white color
    fontSize: 18,
    fontWeight: 'bold',
  },
});
