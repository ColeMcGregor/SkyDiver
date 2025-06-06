/**
 * Abstract game loop definition for platform-agnostic frame coordination.
 *
 * This interface defines the core lifecycle methods required to run
 * the main game loop, independent of platform-specific timing mechanisms.
 *
 * Platform-specific implementations (e.g., AndroidGameLoop, IOSGameLoop)
 *      
 * should provide frame scheduling and tie into system rendering callbacks.
 *
 * Responsibilities:
 * - Coordinate game updates and rendering each frame.
 * - Dispatch input events to UI or gameplay layers.
 * - Respect GameState for controlling pause, resume, and end logic.
 *
 * Note: This is a pure controller and should not contain logic
 * related to rendering, entity state, or business rules directly.
 * 
 * TODO: The entire file needs to be written
 *
 * @author Jardina Gomez
 */
