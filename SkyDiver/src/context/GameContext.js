import React, { createContext, useState } from 'react';

//create a new context called GameContext, it will be used to store the game state
//this is better than using a singletons or global variables, its also better than prop drilling
export const GameContext = createContext();

//create a new component called GameProvider, it will be used to wrap the rest of the app
//this will provide the game state to the rest of the app
export const GameProvider = ({ children }) => {
    //our game state will consist of the score, and gameOver state, or whatever you want
    const [score, setScore] = useState(0);
    const [gameOver, setGameOver] = useState(false);

    //create functions 

    //change score to accept in the value of whatever was hit, positive for good, negative for bad
    const changeScore = ( {value} ) => {
        setScore(score + value);
    };

    //reset the game, set the score to 0, and set the gameOver state to false
    const resetGame = () => {
        setScore(0);
        setGameOver(false);
    };

    return (
        <GameContext.Provider value={{ score, gameOver, changeScore, resetGame }}>
            {children}
        </GameContext.Provider>
    );
}