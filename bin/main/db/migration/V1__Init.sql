CREATE TABLE message (
  id INT AUTO_INCREMENT,
  message_key VARCHAR(255) NOT NULL,
  sender VARCHAR(255) NOT NULL,
  saved_at DATETIME NOT NULL,
  CREATED_DATE DATETIME NOT NULL,
  PRIMARY KEY (id)
);