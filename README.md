# TCP Hangman Game
TCP Hangman Game is a text-based multiplayer Hangman game that uses TCP connections and multithreading to offer an interactive and engaging experience. This project demonstrates the principles of network programming by allowing two clients to compete in a classic game of Hangman, with the server managing communication and game logic.

## About
In the TCP Hangman Game, two players connect to a server and alternate between selecting and guessing words. The server manages multiple concurrent games, ensuring smooth gameplay and role-switching for each round. Key features include:
- **Multithreading Support**: Multiple games can run concurrently without overlap.
- **TCP Protocol**: Reliable communication between clients and the server.
- **Dynamic Gameplay**: Players alternate roles, and rounds are tracked independently.

## Installation
To set up and run the TCP Hangman Game, follow these steps:
### Prerequisites
- Ensure JDK 21 and JRE are installed.
### Setup and Execution
1. Clone the repository:
```sh
git clone https://github.com/udvale/Hangman-Game.git
```
2. Navigate to the project directory:
```sh
cd src
```
4. Compile and run the server:
```sh
javac *.java
java Server.java
```
5. Open Two Terminals to Run Clients: <br>
In each terminal execute:
```sh
java Client.java <SERVER_IP_ADDRESS> <PORT>
```
## Usage
This project was developed as part of the Comp352 - Computer Networks course at Dickinson College. It serves as an educational tool to demonstrate the practical application of network programming concepts such as TCP communication, concurrency through multithreading, and the design of distributed systems.
