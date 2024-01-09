CREATE TABLE IF NOT EXISTS user_info (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                username VARCHAR(255),
                                password VARCHAR(255),
                                email VARCHAR(255),
                                dob DATE,
                                firstName VARCHAR(255),
                                lastName VARCHAR(255),
                                userType ENUM('admin', 'client', 'manager')
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS admin (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       user_info_id INT,
                       FOREIGN KEY (user_info_id) REFERENCES user_info(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS client (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        user_info_id INT,
                        cardNumber VARCHAR(255),
                        numOfScheduledTrainings INT,
                        FOREIGN KEY (user_info_id) REFERENCES user_info(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS manager (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         user_info_id INT,
                         gymName VARCHAR(255),
                         employmentDate DATE,
                         FOREIGN KEY (user_info_id) REFERENCES user_info(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;