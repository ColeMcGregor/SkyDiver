# Skydiver

**Skydiver** is a fast-paced, modular arcade game where you control a skydiver navigating dangerous vertical environments. With increasing speed and limited visibility, players must avoid deadly obstacles like hang gliders and manage slowdown from balloons and kites — all while descending through diverse, immersive levels.

Crafted with an eye toward extensibility, *Skydiver* features a flexible level system allowing for unique, themed drop zones — from the classic open sky theme to spy-movie volcano shafts to icy crystal caverns.

---

## Gameplay Features

- **Touch-based Controls**: Swipe or tilt to maneuver mid-air
- **Scaling Game Speed**: Natural acceleration + strategic deceleration
- **Dynamic Obstacles**:
  - *Kites & Balloons*: Slow you down
  - *Hang Gliders*: Cause instant failure
- **Modular Multilevel System**: Easily add new themed environments
- **Clouds & Fog**: Aesthetic layers with potential gameplay effects
- **Local Achievements**: Track progress without backend dependency

---

## Multilevel Design

The architecture supports **drop-in themed levels** by:

- Decoupling obstacle sets, background art, and physics profiles
- Organizing environments into swappable "Level Modules"
- Supporting future zones like:
  - **Volcano Tube** – minecarts, magma jets, heat distortion
  - **Icy Cavern** – frozen gusts, stalactites, slick speed boosts
 
## Built With
Kotlin + Jetpack Compose
Custom game loop with decoupled render/update logic
Lightweight geometry engine for collision detection
JUnit for testing vector math and rectangle interaction

## Core Systems
Vector2 and Rect: Foundational math for movement and collision
GameManager: Central state machine for gameplay
LevelLoader: Abstracted loading for backgrounds & obstacle behavior
Player: Modular logic for drag, inertia, collision response

## Authors
Cole McGregor – Software Engineer · Designer · Artist
Jardina “Jar” Gomez – Software Engineer · Designer · Artist
Together we engineer the core, design the levels, and draw every image


