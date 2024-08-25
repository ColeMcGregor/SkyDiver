//First screen that is displayed when the app is opened

import React, { useEffect } from 'react';
import { View, Image, StyleSheet } from 'react-native';


// make the splash screen a function to use hooks
//takes in navigation as a prop, to allow for navigation to the start screen
export default function SplashScreen({ navigation }) {
  useEffect(() => {
    // Set a timeout to navigate to the Start screen after 5 seconds
    const timer = setTimeout(() => {
      navigation.replace('Start');  // Use replace to keep it from going back to navigation
    }, 5000);  // in milliseconds

    // Cleanup the timer if the component unmounts
    return () => clearTimeout(timer);
  }, [navigation]);

  return (
    <View style={styles.container}>
      <Image
        source={require('../sprites/SplashImage.jpg')}
        style={styles.image}
      />
    </View>
  );
}
//create the basic styles for the splash screen
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#000',  
  },
  image: {
    // Make the image fit the screen
    width: '100%',  
    height: '100%',
    // Ensure the image covers the screen while maintaining aspect ratio
    resizeMode: 'cover',  
  },
});
