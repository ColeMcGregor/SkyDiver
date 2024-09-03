import React from 'react';
import { View, Image, StyleSheet, Dimensions } from 'react-native';
import { GameContext } from '../GameContext';

// Define the baseline duration for cloud movement
const CLOUD_BASELINE_DURATION = 15000;
//define the generation interval for the clouds
const CLOUD_GENERATION_INTERVAL = 300;


//define the dimensions of the screen
const { width, height } = Dimensions.get('window');



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
          const randomCloud = Math.floor(Math.random() * 8) + 1; // to pick a random cloud from sprites
          const randomX = Math.floor(Math.random() * width); // Random x position to spawn, clamped to screen width
          
          // Create a new cloud object, with the random cloud image, x position, and starting at the bottom of the screen
          const cloud = {
            id: cloudId,
            // Use require to dynamically import the cloud image based on the random number
            image: require(`../sprites/Cloud${randomCloud}.png`),
            x: randomX,
            y: height,
            animatedValue: new Animated.Value(height), // bottom of the screen
          };
          //add the cloud to the clouds array
          setClouds((prevClouds) => [...prevClouds, cloud]);
          //move the cloud up, and remove it once it moves off the screen
          Animated.timing(cloud.animatedValue, {
            toValue: 0, // Move to the top of the screen
            duration: CLOUD_BASELINE_DURATION / globalSpeed, // use the global speed to determine the duration
            useNativeDriver: true, //this means the animation is done on the native side, instead of the JS side
          }).start(() => {
            // Remove cloud once it moves off the screen
            //it knows its done when it reaches the top of the screen
            setClouds((prevClouds) => prevClouds.filter(cld => cld.id !== cloudId));
          });
        };
    
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