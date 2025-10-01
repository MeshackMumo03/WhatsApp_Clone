🧠 WhatsApp Clone

📄 About

WhatsApp Clone is a desktop messaging application built using JavaFX. It mimics core features of WhatsApp: you can send and receive messages, manage conversations, and create a chat experience in a desktop environment. This project is intended as a learning exercise/demonstrator to explore GUI development, networking, and real-time messaging in Java.

🧠 Features

✅ One-on-One Chat: Send and receive direct (peer-to-peer or server-mediated) text messages.

⚡ User Interface: Rich JavaFX UI with message bubbles, input fields, and conversation view.

🌐 Networking: Underlying communication layer to handle sending/receiving messages between users.

📤 Message Delivery: Messages delivered (ideally in near real-time) between clients.

🔒 (Optional/Future) Security: Provision to add encryption or secure transport for messages.

🌐 Tech Stack
Layer	Technologies Used
Frontend / UI	JavaFX (FXML, CSS for styling)
Backend / Logic	Java (core networking, message handling)
Database / Storage	(Currently) file-based local storage or in-memory structures
Communication / Networking	Java Sockets / TCP, possibly a simple server relay
Auth / User Management	(Optional / future) simple login/registration system
Deployment / Execution	Run as a Java desktop application (JAR)
⚙️ Local Setup
# 1. Clone the repository
git clone https://github.com/MeshackMumo03/WhatsApp_Clone.git
cd WhatsApp_Clone

# 2. Make sure you have Java & JavaFX installed
#    (Java 11+ recommended, and JavaFX SDK if not bundled)

# 3. Build / compile
#    If using an IDE (e.g. IntelliJ, Eclipse), import project and run.
#    Or via command line with your build tool (Maven, Gradle, or javac).

# 4. Run the application
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -jar WhatsAppClone.jar


⚠️ Adjust the --module-path and --add-modules flags based on how JavaFX is set up in your environment.

🚀 Deployment / Distribution

You can distribute the app as a runnable JAR or package it with JavaFX runtime for easier setup on other machines. Some options:

Java Packager / jlink: Create a custom runtime image bundling your code + JavaFX.

Native installers: Use tools like jpackage to build native installers (Windows .exe, macOS .dmg, Linux .deb etc.).

Server component (optional): If your architecture uses a centralized server to route messages, deploy that backend to a server or cloud environment.

🛠️ Future Enhancements / To-Do

✅ Persistent storage (database) for message history

✅ User authentication and registration

✅ Group chats

✅ File/image sharing

✅ Message encryption / end-to-end security

✅ Status / presence / online indicators

✅ Notifications, desktop alerts

✅ UI polish, theming / dark mode

🧪 Testing

Run multiple instances of the client to simulate chatting between users

Test message ordering, delivery, and error handling (e.g. connection drops)

Validate UI responsiveness (resizing windows, long messages)

🧾 License

This project is licensed under the MIT License — see the LICENSE
 file for details.

📫 Contact / Contributors
Contributors: Meshack Mumo.
              Melvin Ngugi. 
              Chantal Pande. 
              Steve Kamau. 

GitHub: https://github.com/MeshackMumo03/WhatsApp_Clone
 
GitHub

Feel free to fork, submit issues, or contribute enhancements.
