/**
 * Tracks long-term player performance metrics.
 *
 * Records session statistics like high score, total games played,
 * and average score over time. Works alongside a persistent
 * storage mechanism (KeyValueStorage --> platform-specific storage) to preserve data.
 *
 * Responsibilities:
 * - Update stats at end of session.
 * - Provide data for stats screen overlays.
 * - Reset or retrieve aggregate performance data.
 *
 * This is persistent across sessions unless reset explicitly.
 *
 * @author Cole McGregor
 */
