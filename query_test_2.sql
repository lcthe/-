CREATE TABLE  AccountBalance(
		id BIGINT AUTO_INCREMENT,
		accountId BIGINT,
		balance BIGINT,
		PRIMARY KEY (id)
);

INSERT INTO `AccountBalance` (accountId, balance) 
VALUES (1, 100);

INSERT INTO `AccountBalance` (accountId, balance) 
VALUES (2, 200);

ALTER TABLE RechargeLog AUTO_INCREMENT = 1;



CREATE TABLE RechargeLog(
		id BIGINT AUTO_INCREMENT,
		timestamp DATETIME,
		type VARCHAR(255),
		fromAccountId BIGINT,
		toAccountId BIGINT,
		balance DECIMAL,
		PRIMARY KEY(id)
);



select * FROM accountbalance;