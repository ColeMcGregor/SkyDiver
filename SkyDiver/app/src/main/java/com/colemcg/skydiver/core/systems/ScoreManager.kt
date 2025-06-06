/**
 * Tracks the player's score and combo streak during gameplay.
 *
 * applies point changes based on collision actions (e.g., collecting items, hitting obstacles),
 * and applies combo multipliers for consecutive successes.
 *
 * Responsibilities:
 * - Maintain current score and streak.
 * - Apply scoring logic and multipliers.
 * - Expose public getters for score-related overlays.
 *
 * Does not persist data across sessions; see StatsManager for history.
 *
 * @author 
 */
