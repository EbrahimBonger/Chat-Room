## README

The project has been implemented to allow multiple users to chat in one platform. The application has a multi-threading system on both client and server-side. The multi-threading system is critical for the entire system. It allows the user to communicate and update their status dynamically without any delay.

![](demo/chat%20room%20demo.gif)

### Chat Room Requirements
The project has server and client programs to let users exchange messages. Each user will run the ChatClient program and connect to a shared ChatServer.  The server will be responsible for forwarding incoming messages to all clients that are connected.

Typing a message and hitting send should transmit the desired message to the server, which will in turn forward the message to any other client that is connected. The server should also print something to its screen such as: "Joe: Hi I just joined" indicating that a message was sent by the user Joe.

**Extra feature:** add functionality to support multiple chatrooms and allow users to join. 
