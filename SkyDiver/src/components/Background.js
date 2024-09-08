import React from 'react';
import { View, Image, StyleSheet, Dimensions } from 'react-native';
import { GameContext } from '../GameContext';

// Define the baseline duration for cloud movement
const CLOUD_BASELINE_DURATION = 15000;
//define the generation interval for the clouds
const CLOUD_GENERATION_INTERVAL = 300;

//define the dimensions of the screen
const { width, height } = Dimensions.get('window');

//preload all the cloud images
const cloudImages = [
    require('../sprites/Cloud1.png'),
    require('../sprites/Cloud2.png'),
    require('../sprites/Cloud3.png'),
    require('../sprites/Cloud4.png'),
    require('../sprites/Cloud5.png'),
    require('../sprites/Cloud6.png'),
    require('../sprites/Cloud7.png'),
    require('../sprites/Cloud8.png'),
];

/**
 * Background component renders the background for all screens
 * a sky-blue background image
 * the sun positioned in the top-right corner.
 * Clouds Generating randomly and moving up at the global speed
 *
 * @component
 * @returns {JSX.Element} A view containing the background and sun images.
 */

export default function Background() {
    const { globalSpeed } = useContext(GameContext); // Access globalSpeed from context
    const [clouds, setClouds] = useState([]); //holds all my clouds

    //use a hook to generate the clouds, updates on global speed
    useEffect(() => {
        const generateCloud = () => {
          const cloudId = Date.now(); // Unique ID for each cloud, can skip an iterator
          const randomCloud = Math.floor(Math.random() * cloudImages.length); // To pick a random cloud from preloaded images
          const randomX = Math.floor(Math.random() * width); // Random x position to spawn, clamped to screen width
          
          // Create a new cloud object, with the random cloud image, x position, and starting at the bottom of the screen
          const cloud = {
            id: cloudId,
            image: cloudImages[randomCloud], // Use preloaded cloud image
            x: randomX,
            y: height,
            animatedValue: new Animated.Value(height), // Start at the bottom of the screen
          };
          // Add the cloud to the clouds array
          setClouds((prevClouds) => [...prevClouds, cloud]);
          // Move the cloud up, and remove it once it moves off the screen
          Animated.timing(cloud.animatedValue, {
            toValue: 0, // Move to the top of the screen
            duration: CLOUD_BASELINE_DURATION / globalSpeed, // Use the global speed to determine the duration
            useNativeDriver: true, // This means the animation is done on the native side, instead of the JS side
          }).start(() => {
            // Remove cloud once it moves off the screen
            setClouds((prevClouds) => prevClouds.filter(cld => cld.id !== cloudId));
          });
        };
        // Generate a cloud every generation interval
        const interval = setInterval(generateCloud, CLOUD_GENERATION_INTERVAL); // Generate a cloud every 0.3 seconds
        return () => clearInterval(interval);
    }, [globalSpeed]); // Re-run if globalSpeed changes
    
    //create the images to be used
    //the Background image
    const backgroundImage = (
        <Image
            source={require('../sprites/SkyblueBG.png')}
            style={{
            width: width,
            height: height,
            position: 'absolute',
            }}
        />
    );
    //the sun image
    const sunImage = (
        <Image
            source={require('../sprites/TheSun.png')}
            style={{
            width: 100,
            height: 100,
            position: 'absolute',
            top: 0,
            right: 0,
            }}
        />
    );
    //the clouds are special, they are generated randomly and move up
    return (
      <View style={styles.container}>
       {backgroundImage}
       {sunImage}

       {clouds.map(cloud => (
        <Animated.Image
          key={cloud.id}
          source={cloud.image}
          style={[
            styles.cloud,
            {
              left: cloud.x,
              transform: [{ translateY: cloud.animatedValue }],
            },
          ]}
        />
      ))}
      </View>
    );
}

const styles = StyleSheet.create({
    container: {
      position: 'absolute',
      top: 0,
      left: 0,
      right: 0,
      bottom: 0,
    },
    cloud: {
      position: 'absolute',
      width: 256,
      height: 128,
      resizeMode: 'center', //allows clipping of the image
    },
  });