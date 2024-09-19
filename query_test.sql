

CREATE TABLE user (
		id INT AUTO_INCREMENT,
		name VARCHAR(255) NOT NULL,
		idcard VARCHAR(255),
		phone VARCHAR(255),
    password VARCHAR(255),
		photo VARCHAR(255),
		PRIMARY KEY (id)
);


INSERT INTO `user` (name, idcard, phone, password, photo) 
VALUES ('张', '440123123927881', '1124124', '1234', '0000');
INSERT INTO `user` (name, idcard, phone, password, photo) 
VALUES ('三', '412124123927881', '912724', '4321', '0000');
INSERT INTO `user` (name, idcard, phone, password, photo) 
VALUES ('李', '430124123927881', '1524224', '1324', '0000');
INSERT INTO `user` (name, idcard, phone, password, photo) 
VALUES ('四', '480124123927881', '1114124', '4123', '0000');


SELECT * FROM `user`

DELETE FROM user WHERE name = "王"

ALTER TABLE user AUTO_INCREMENT = 1;

SELECT phone FROM user WHERE phone = "1114124" LIMIT 1