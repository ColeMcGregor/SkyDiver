import React, { createContext, useState } from 'react';

//create a new context called GameContext, it will be used to store the game state
//this is better than using a singletons or global variables, its also better than prop drilling
export const GameContext = createContext();

/**
 * GameProvider is a context provider component that wraps around the rest of the app.
 * It provides the game state and functions to manage the game state.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {ReactNode} props.children - The child components that will have access to the game state.
 * @returns {JSX.Element} A context provider that wraps the application and provides game state.
 */

export const GameProvider = ({ children }) => {

    /**
     * @typedef {Object} GameState
     * @property {number} score - The current score of the player.
     * @property {boolean} gameOver - The state of the game, true if the game is over.
     * @property {number} highestScore - The highest score achieved across all game sessions.
     * @property {number} recentScore - The score from the most recent game session.
     * @property {number} globalSpeed - The speed of the player, which determines the speed of obstacles.
     * @property {function} changeScore - Function to update the score based on in-game events.
     * @property {function} changeSpeed - Function to update the player's speed.
     * @property {function} endGame - Function to end the game and reset the score.
     * @property {function} resetGame - Function to reset the game state for a new session.
     */

    const [score, setScore] = useState(0);
    const [gameOver, setGameOver] = useState(false);
    const [highestScore, setHighestScore] = useState(0);  // New state for highest score
    const [recentScore, setRecentScore] = useState(0);  // New state for recent score
    const [globalSpeed, setGlobalSpeed] = useState(10);

   /**
     * Updates the score based on the value passed. Also updates the highest score
     * if the new score exceeds the current highest score.
     * 
     * @param {number} value - The value to add to the current score (positive for good, negative for bad).
     */

    const changeScore = (value) => {
        setScore((prevScore) => {
          const newScore = prevScore + value;
    
          // Update the highest score if the new score is greater
          if (newScore > highestScore) {
            setHighestScore(newScore);
          }
          return newScore;
        });
      };

/**
     * Updates the global speed, which simulates the player's speed in the game.
     * The higher the speed, the faster obstacles will move up the screen.
     * 
     * @param {number} value - The value to add to the current speed.
     */

    const changeSpeed = (value) => {
        setGlobalSpeed((prevSpeed) => prevSpeed + value);
    }
    
    /**
     * Ends the current game session, setting the most recent score and marking
     * the game as over.
     */ 

    const endGame = () => {
        setRecentScore(score);  
        setGameOver(true);
        setScore(0);  
      };
    
    /**
     * Resets the game state, clearing the score and marking the game as not over,
     * in preparation for a new game session.
     */

    const resetGame = () => {
        setGameOver(false);
        setScore(0);
      };

    return (
        <GameContext.Provider
          value={{
            score,
            gameOver,
            highestScore,
            recentScore,
            globalSpeed,
            changeScore,
            changeSpeed,
            endGame,
            resetGame,
          }}
        >
          {children}
        </GameContext.Provider>
      );
}