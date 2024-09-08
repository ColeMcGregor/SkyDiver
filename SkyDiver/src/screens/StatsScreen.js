import React, { useContext } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { GameContext } from '../GameContext';

/**
 * StatsScreen component 
 * displays the highest score achieved
 * displays the most recent score
 *
 * @component
 * @example
 * return (
 *   <StatsScreen />
 * )
 */

export default function StatsScreen() {
  const { highestScore, recentScore } = useContext(GameContext);

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Statistics</Text>
      <Text style={styles.label}>Last Game Score: {recentScore}</Text>
      <Text style={styles.label}>Highest Score Achieved: {highestScore}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f5f5f5', //a light grey color
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    color: '#333', //a dark grey color
  },
  label: {
    fontSize: 18,
    marginVertical: 10,
  },
});
