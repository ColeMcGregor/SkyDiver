# Skydiver

**Skydiver** is a modular 2D arcade game focused on reactive movement, scalable difficulty, and clean system architecture. Players guide a falling avatar through procedurally populated vertical spaces, avoiding hazards while managing speed and momentum under increasing pressure.

The codebase emphasizes **separation of concerns**, **declarative UI overlays**, and **domain-driven structure**, making it suitable for extensible gameplay and maintainable architecture. Core logic is decoupled from rendering and input, enabling platform-agnostic loop control and modular UI layering.

---

## 🎮 Gameplay Systems

- **Decoupled Input & Motion**  
  Player steering is handled through directional drag events.
  
- **Dynamic Pacing Engine**  
  Speed naturally accelerates with decay penalties triggered by obstacle collisions.

- **Collision-Driven Events**  
  - *Non-lethal* slowdowns: Balloons, Kites  
  - *Lethal* collisions: Hang Gliders

- **Modular Level Design**  
  Themed zones are plug-and-play, with their own visuals, difficulty curves, and obstacle pools.

- **Overlay-Driven UI**  
  Menus and state transitions (Pause, Options, Game Over, Start) are handled via isolated overlays.

- **Local Stat Persistence**  
  High scores and player metrics are saved across sessions using injected key-value storage.

---

## 🧱 System Architecture

| Layer                     | Description                                               |
|--------------------------|-----------------------------------------------------------|
| `GameLoop`               | Abstract loop interface with platform-specific hooks      |
| `GameManager`            | Central coordinator of update and draw calls              |
| `Spawner`                | Controls timed obstacle and collectible generation        |
| `Player`                 | Handles movement, velocity, and input logic               |
| `UIManager`              | Coordinates active UI overlay stack                       |
| `StatsManager` / `ScoreManager` | Tracks session and persistent stats               |
| `SoundManager` / `KeyValueStorage` | Abstracted platform services (injected)      |

---

## 💡 Design Principles

- **Modular GameObjects** – Self-managed update/render behavior  
- **Functional Separation** – Input, logic, and rendering layers are isolated  
- **Backend Agnosticism** – Core logic depends only on interfaces  
- **State-Driven Flow** – Controlled via a shared `GameState` singleton  

---

## 🛠️ Built With

- **Kotlin** + Jetpack Compose (Android)
- **Custom Game Loop** with platform callback control
- **Minimal Geometry Engine** using `Vector2` and `Rect`
- **JUnit** for collision/math testing

---

## 🧑‍🤝‍🧑 Authors

**Cole McGregor** – Architecture · Systems · Gameplay  
**Jardina “Jar” Gomez** – Design · Animation · Worldbuilding

> A handcrafted game framework focused on extensibility, readability, and player flow.

---
