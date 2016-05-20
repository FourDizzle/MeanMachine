CREATE DATABASE IF NOT EXISTS mean_machine;

USE mean_machine;

CREATE TABLE IF NOT EXISTS friend (
    friend_id BIGINT NOT NULL AUTO_INCREMENT,
    facebook_id VARCHAR(128) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (friend_id, facebook_id)
) ENGINE=INNODB;
    
CREATE TABLE IF NOT EXISTS friend_photo (
    friend_photo_id BIGINT NOT NULL AUTO_INCREMENT,
    facebook_id VARCHAR(128) NOT NULL,
    image_width INT NOT NULL,
    image_height INT NOT NULL,
    image_name VARCHAR(255) NOT NULL,
    image_extension VARCHAR(20) NOT NULL,
    friend_id_fk BIGINT NOT NULL,
    friend_facebook_id_fk VARCHAR(128) NOT NULL,
    
    PRIMARY KEY (friend_photo_id, facebook_id),
    INDEX (friend_id_fk, friend_facebook_id_fk),
    
    FOREIGN KEY (friend_id_fk, friend_facebook_id_fk)
      REFERENCES friend(friend_id, facebook_id)
      ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB;