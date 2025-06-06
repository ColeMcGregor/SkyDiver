Skydiver
Skydiver is a modular 2D arcade game centered on reactive motion, scaling difficulty, and flexible system design. Players guide a descending avatar through procedurally populated vertical environments, navigating risk and momentum under increasing pressure.

The project emphasizes separation of concerns, declarative UI overlays, and domain-driven architecture, making it an ideal foundation for extensible arcade gameplay. Core logic is fully decoupled from rendering and input subsystems, supporting platform-agnostic loop control and adaptable UI presentation.

Skydiver’s layered design includes configurable levels, dynamic spawning, reactive entity behaviors, and persistent local state — all maintained through clean interfaces and testable components.

🎮 Gameplay Systems
Decoupled Input & Motion: Player steering via directional drag events

Dynamic Pacing Engine: Speed increases over time with slowdown penalties

Collision-Driven Events:

Non-lethal slowdowns (e.g. kites, balloons)

Lethal collisions (e.g. hang gliders)

Thematic Level Modules: Drop-in zones with custom obstacle, background, and difficulty logic

Overlay-Driven UI: Paused, Game Over, Options, and Start as independent overlays

Stat Persistence: Local tracking with configurable storage adapters

🧱 System Architecture
Layer	Description
GameLoop	Abstract timing interface (platform-specific)
GameManager	Centralized update & draw coordinator
Spawner	Controls runtime entity generation
Player	Encapsulates position, state, and movement logic
UIManager	Handles active UI overlays and transitions
StatsManager / ScoreManager	Session vs persistent stat tracking
SoundManager / KeyValueStorage	Injected platform services

💡 Design Principles
Modular Entities: All game objects are self-updating and self-rendering, managed from above by a Game Loop via the Game Manager

Functional Separation: UI, gameplay, rendering, and input handled in isolation

Replaceable Backends: Sound, storage, and rendering are interface-driven

Resumable State: Game state tracks pause, resume, start, and end

🛠️ Built With
Kotlin, Jetpack Compose (Android-first)

Custom game loop with clean frame coordination

Lightweight collision engine based on Vector2 and Rect

JUnit for core unit testing and spatial validation

🧑‍🤝‍🧑 Authors
Cole McGregor – Architecture · Systems · Gameplay
Jardina “Jar” Gomez – Design · Animation · Worldbuilding

A handcrafted game framework focused on extensibility, readability, and player flow.
