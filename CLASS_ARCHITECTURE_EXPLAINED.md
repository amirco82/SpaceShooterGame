# Class Architecture Explained

## 1. High-Level Components

- **App**
  - Bootstraps the application by initializing `AppContent`, creating the UI, setting UI ports, registering the router, and starting the periodic scheduler.
  - Acts as the entry point that glues `Ui`, `AppContent`, and `SpaceShooterRouter` together for the running game.

- **AppContent**
  - Owns the main backend game logic instance (`SpaceShooterBackend`) and initializes it during startup.
  - Provides global access to the backend for the UI and router layers through `getBackend()`.

## 2. Logic & Routing

- **SpaceShooterBackend**
  - Implements the core game state machine, manages `GameWorld`, and delegates game-loop updates, movement, firing, and lifecycle transitions.
  - Interacts with `SpaceShooterUiPort` to update the UI, and uses `CollisionManager`, `SpawnManager`, and `DifficultyManager` to coordinate game logic.

- **SpaceShooterRouter**
  - Maps UI or input routes to backend actions such as move, start, pause, stop, world change, and fire.
  - Acts as a thin routing layer between `KeyboardInputHandler`/UI controllers and `SpaceShooterBackend`.

- **PeriodicLoop**
  - Encapsulates the timer that repeatedly calls `SpaceShooterBackend.gameTick()` approximately every 16ms.
  - Provides start and stop control for the main game update loop.

- **KeyboardInputHandler**
  - Captures keyboard events, tracks held arrow keys, and periodically routes movement and firing commands through `App.spaceRouter()`.
  - Bridges player input to the routing and backend layers for continuous movement and input-driven actions.

## 3. Game Entities & Managers

- **GameWorld**
  - Stores the game model state including `PlayerShip`, lists of `Enemy`, `Bullet`, `BonusItem`, and `GameStats`.
  - Provides helper operations for adding entities, clearing them, and removing expired objects.

- **PlayerShip**
  - Represents the player avatar with position, health, ammo, and boundary-limited movement.
  - Supports position updates, health and ammo management, and destruction checks used by backend logic.

- **Enemy**
  - Represents a hostile entity with position, vertical speed, health, and score value.
  - Moves downward each tick and provides hitbox and out-of-bounds checks for collision processing.

- **Bullet**
  - Represents a fired projectile with position, velocity, damage, and hitbox geometry.
  - Moves upward each frame and is removed once it leaves the play area.

- **BonusItem**
  - Represents a collectible power-up that can be either health or ammo.
  - Moves downward, applies an effect to `PlayerShip` on collision, and tracks consumption for cleanup.

- **CollisionManager**
  - Detects collisions between bullets and enemies, enemies and the player, and bonuses and the player.
  - Updates entity health, scoring, bonus effects, and player damage as part of the game tick.

- **SpawnManager**
  - Controls when new enemies and bonus items are created based on tick counters and current difficulty.
  - Adds new `Enemy` and `BonusItem` instances into `GameWorld` at runtime.

- **DifficultyManager**
  - Monitors `GameStats` and increases difficulty level when score thresholds are reached.
  - Influences spawn pacing indirectly by updating the game difficulty state.

- **GameStats**
  - Tracks score, game time, difficulty level, and enemies destroyed.
  - Provides reset and update operations used by the backend during restart and each game tick.

## 4. UI & Abstraction

- **SpaceShooterUiPort**
  - Defines the abstract UI contract for updating ship position, score, health, world background, bullets, rendering, and game-over presentation.
  - Allows the backend to remain decoupled from concrete Swing UI details.

- **SpaceShooterUiPortImpl**
  - Concrete implementation of `SpaceShooterUiPort` that updates `GameCanvasView` and `HUDView`.
  - Receives backend-driven updates and forwards render data to the visible UI components.

- **GameCanvasView**
  - A Swing panel responsible for drawing the player ship, enemies, bullets, bonuses, and background themes.
  - Receives object lists from the UI port implementation and repaints the game canvas each frame.

- **HUDView**
  - A Swing panel responsible for rendering score, health, ammo, difficulty, and the game-over overlay.
  - Exposes methods to show/hide game over state and refresh the displayed HUD data.
