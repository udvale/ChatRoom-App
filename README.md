# Computer Networks Project
A text based multiplayer Hangman game utalizing a TCP connection with multithreading. Allows two clients to connect with a server and play hangman together by alternating who is picking the word and who is guessing what the word is. 

## Installation
1. Clone the repository
```rb
git clone remote <REMOTE_URL>
```
2. Navigate to the project directory
```rb
cd src
```
4. Compile and run the game
```rb
javac *.java
java Server.java
java Client.java <IP_ADDRESS>
```
## About
This Hangman game supports two clients who will alternate picking and guessing the word. The program facilitates the game by keeping track of the rounds and number of guesses and switches user roles when one of the players has one the round. 
