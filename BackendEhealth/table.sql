CREATE TABLE users ( id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
              		 nama VARCHAR(100) NOT NULL ,
                     tgl_lahir DATE NOT NULL,
                     email VARCHAR(50) Not NULL,
                     password VARCHAR(30) NOT NULL,
                     confidience FLOAT(2) NOT NULL
             );