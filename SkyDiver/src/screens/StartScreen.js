import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';

export default function StartScreen({ navigation }) {
  return (
    <View style={styles.container}>
      <TouchableOpacity
        style={styles.button}
        onPress={() => navigation.navigate('Game')}
      >
        <Text style={styles.buttonText}>Start Game</Text>
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
    backgroundColor: '#f5f5f5',  
  },
  button: {
    backgroundColor: '#6200ee',  
    paddingVertical: 15,
    paddingHorizontal: 30,
    borderRadius: 5,
    marginVertical: 10,  
  },
  buttonText: {
    color: '#fff',  
    fontSize: 18,
    fontWeight: 'bold',
  },
});
