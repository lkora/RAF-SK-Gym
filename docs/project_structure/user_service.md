https://docs.spring.io/spring-boot/docs/current/reference/html/messaging.html#messaging.kafka 
Messaging protocol is used for inter-service data flow

User service:
3 tipa korisnika 
enum
- admin - rucni unos
- manager
- client

# --- Registration ---
BaseProfileInfo
```json
{
  "username": "string",
  "password": "string",
  "email": "string",
  "dob": "date",
  "firstName": "string",
  "lastName": "string",  
}

```


# Manager
POST /api/register/manager
Request: 
```json
{
    "baseInfo": BaseProfileInfo,
    "gymName": "string",
    "employmentDate": "date"
}
```
Response: 
```json
{
    "message": "Activation email sent. Please check your email."
}
```



# Client
POST /api/register/client
Request: 
```json
{
    "baseInfo": BaseProfileInfo
}
```
Response: 
```json
{
    "message": "Activation email sent. Please check your email."
}
```

# --- LOGIN ---
POST /api/login
Request:
```json
{
    "email": "string",
    "password": "string"
}
```
Response: 
```json
{
    "jwt": "string"
}

```
# --- ACTIONS ---
# Ban user route
POST /api/admin/ban/{username}
Response: 
```json
{
    "message": "User has been banned."
}
```

# Unban user route
PUT /api/admin/unban/{username}
Response:
```json
 {
    "message": "User has been unbanned."
}
```

# Edit profile route
PUT /api/profile/edit
Request: 
```json
{
    "username": "string",
    "password": "string",
    "email": "string",
    "dob": "date",
    "firstName": "string",
    "lastName": "string"
}
```
Response: 
```json
{
    "message": "Profile updated successfully."
}
```



NOTES
- The ticket number is generated for the user in the database as well as the appointment number, or is the appointment number selected at registration?
- Reponse also sends a notification via the messaging service, above all the data of the registered user should be sent in this message
- the password is sent in plaintext but is hashed with a secret that is stored in the customer service

Example table:

# Base User Info Table
```sql
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

```
# Admin Table
```sql
CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    base_user_info_id INT,
    FOREIGN KEY (base_user_info_id) REFERENCES base_user_info(id)
);
```

# Client Table
```sql
CREATE TABLE client (
    id INT AUTO_INCREMENT PRIMARY KEY,
    base_user_info_id INT,
    cardNumber VARCHAR(255),
    numOfScheduledTrainings INT,
    FOREIGN KEY (base_user_info_id) REFERENCES base_user_info(id)
);
```

# Manager Table
```sql
CREATE TABLE manager (
    id INT AUTO_INCREMENT PRIMARY KEY,
    base_user_info_id INT,
    gymName VARCHAR(255),
    employmentDate DATE,
    FOREIGN KEY (base_user_info_id) REFERENCES base_user_info(id)
);

```