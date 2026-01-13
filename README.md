# I-Taaruf
A simple JavaFX matchmaking app built using OOP concepts.

Features:
User registration and login with role-based access
Profile management with validation (age ≥ 18, guardian contact required)
Match request system with guardian approval (inheritance)
Basic messaging between users
Audit logging of system activities
File handling using text files for data persistence

Technologies Used:
Java
JavaFX
Object-Oriented Programming (OOP)
File I/O (Text Files)

Project Structure:
The system is divided into several components:
Authentication: Login, Register, User, UserManager
Profile Management: Profile, ProfileView
Matching System: MatchRequest, Guardian
Messaging: Message, MessageView
Utilities: FileHandler, AuditLogger

UI Navigation: DashboardView, Main

Team Contributions:
Each team member was responsible for specific modules based on the UML diagram to ensure clear separation of concerns and smooth integration.

License:
This project is licensed under the MIT License.

HOW TO USE THE APPLICATION:

Launch the application
Run the Main class to start the application. The login screen will be displayed first.

Register a new account
Click on the Register option and enter a username, password, and select a role (user or guardian). After successful registration, you can proceed to log in.

Log in to the system
Enter your registered username and password to access the application dashboard.

Navigate using the Dashboard
The dashboard allows you to access different features of the system, including profile management, messaging, match requests, and audit logs (if applicable).

Create or update your profile
Go to the Profile section and fill in your personal details. Make sure your age is 18 or above and a guardian contact is provided. Click Save Profile to store your information.

Send match requests
Users can send match requests to other users. Match requests will remain pending until reviewed by a guardian.

Guardian approval
Guardians can view pending match requests and choose to approve or reject them.

Send messages
Use the messaging feature to send and receive simple text messages between users.

View audit logs
Important system actions such as login, registration, and approvals are recorded and can be viewed in the audit log section.

Exit the application
Close the application window to exit the system.
