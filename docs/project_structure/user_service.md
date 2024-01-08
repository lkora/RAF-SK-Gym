https://docs.spring.io/spring-boot/docs/current/reference/html/messaging.html
Messaging protocol is used for inter service data flow

User service:
3 tipa korisnika 
enum
- admin - rucni unos
- manager
- client

# --- Registration ---
BaseProfileInfo: {
  "username": "string",
  "password": "string",
  "email": "string",
  "dob": "date",
  "firstName": "string",
  "lastName": "string",  
}

# Manager
POST /api/register/manager
Request: {
    "baseInfo": BaseProfileInfo
    "gymName": "string",
    "employmentDate": "date"
}
Response: {
    "message": "Activation email sent. Please check your email."
}

# Client
POST /api/register/client
Request: {
    "baseInfo": BaseProfileInfo
}
Response: {
    "message": "Activation email sent. Please check your email."
}

# --- LOGIN ---
POST /api/login
Request: {
    "email": "string",
    "password": "string"
}
Response: {
    "jwt": "string"
}

# --- ACTIONS ---
# Ban user route
POST /api/admin/ban/{username}
Response: {
    "message": "User has been banned."
}

# Unban user route
PUT /api/admin/unban/{username}
Response: {
    "message": "User has been unbanned."
}

# Edit profile route
PUT /api/profile/edit
Request: {
    "username": "string",
    "password": "string",
    "email": "string",
    "dob": "date",
    "firstName": "string",
    "lastName": "string"
}
Response: {
    "message": "Profile updated successfully."
}



NOTES
- The ticket number is generated for the user in the database as well as the appointment number, or is the appointment number selected at registration?
- Reponse also sends a notification via the messaging service, above all the data of the registered user should be sent in this message
- the password is sent in plaintext but is hashed with a secret that is stored in the customer service

Example table:

# Base User Info Table
CREATE TABLE base_user_info (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    dob DATE NOT NULL,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    userType ENUM('admin', 'client', 'manager') NOT NULL
);

# Admin Table
CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    base_user_info_id INT,
    FOREIGN KEY (base_user_info_id) REFERENCES base_user_info(id)
);

# Client Table
CREATE TABLE client (
    id INT AUTO_INCREMENT PRIMARY KEY,
    base_user_info_id INT,
    cardNumber VARCHAR(255),
    numOfScheduledTrainings INT,
    FOREIGN KEY (base_user_info_id) REFERENCES base_user_info(id)
);

# Manager Table
CREATE TABLE manager (
    id INT AUTO_INCREMENT PRIMARY KEY,
    base_user_info_id INT,
    gymName VARCHAR(255),
    employmentDate DATE,
    FOREIGN KEY (base_user_info_id) REFERENCES base_user_info(id)
);
