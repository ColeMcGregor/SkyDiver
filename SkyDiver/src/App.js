//import React so the rest of the code makes sense
import React from 'react';

//import the necessary components from react-navigation to create the navigation stack
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

//import the screens that will be used in the app
    //SplashScreen is the first screen that will be shown when the app is opened
import { SplashScreen } from './screens/SplashScreen';
    //StartScreen is the screen that will be shown after the SplashScreen
import { StartScreen } from './screens/StartScreen';        
    //GameScreen is the screen that will be shown after the StartScreen
import { GameScreen } from './screens/GameScreen';
    //EndScreen is the screen that will be shown after the GameScreen
import { EndScreen } from './screens/EndScreen';
    //StatsScreen is the screen that will be shown alternatively after the start screen
import { StatsScreen } from './screens/StatsScreen';
    //import the GameProvider component from the GameContext file
import { GameProvider } from './context/GameContext';


//create the stack navigator, which will be used to navigate between the screens
const Stack = createStackNavigator();

//create the App component, which will be the main component of the app
export default function App() {
    return (
        <NavigationContainer>
        <Stack.Navigator initialRouteName="Splash">
            <Stack.Screen name="Splash" component={SplashScreen} options={{ headerShown: false }} />
            <Stack.Screen name="Start" component={StartScreen} options={{ headerShown: false }} />
            <Stack.Screen name="Game" component={GameScreen} options={{ headerShown: false }} />
            <Stack.Screen name="End" component={EndScreen} options={{ headerShown: false }} />
            <Stack.Screen name="Stats" component={StatsScreen} options={{ headerShown: false }} />
        </Stack.Navigator>
        </NavigationContainer>
    );
}