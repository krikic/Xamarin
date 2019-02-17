CREATE TABLE IF NOT EXISTS 'users' (
'id' int(20) NOT NULL AUTO_INCREMENT,
'username' varchar(70) NOT NULL,
'password' varchar(40) NOT NULL,
'email' varchar(50) NOT NULL,
'created_at' datetime NOT NULL,
'updated_at' datetime DEFAULT NULL,
PRIMARY KEY ('id'),
UNIQUE KEY 'email' ('email')
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;