CREATE DATABASE IF NOT EXISTS meanmachine;

USE meanmachine;

CREATE TABLE IF NOT EXISTS identity (
    person_id BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (person_id)
) ENGINE=INNODB;
    
CREATE TABLE IF NOT EXISTS photos (
    photo_id BIGINT NOT NULL AUTO_INCREMENT,
    filename VARCHAR(255) NOT NULL,
    width INT NOT NULL,
    height INT NOT NULL,
    person_loc_x INT NOT NULL,
    person_loc_y INT NOT NULL,
    person_id_fk BIGINT NOT NULL,
    
    PRIMARY KEY (photo_id),
    INDEX (person_id_fk),
    
    CONSTRAINT fk_photos_identity_person_id FOREIGN KEY (person_id_fk)
      REFERENCES identity(person_id)
      ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS posts (
    post_id BIGINT NOT NULL AUTO_INCREMENT,
    content TEXT NOT NULL,
    timestamp DATETIME,
    person_id_fk BIGINT NOT NULL,
    
    PRIMARY KEY (post_id),
    INDEX (person_id_fk),
    
    CONSTRAINT fk_posts_identity_person_id FOREIGN KEY (person_id_fk)
      REFERENCES identity(person_id)
      ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB;