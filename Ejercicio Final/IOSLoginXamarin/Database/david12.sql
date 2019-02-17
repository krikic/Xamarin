-- Table: users
CREATE TABLE users (
    id int  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username varchar(100)  NOT NULL,
    password text  NOT NULL,
    email varchar(100)  NOT NULL,
    name varchar(100)  NOT NULL,
    phone varchar(10)  NOT NULL
);
 
-- End of file.