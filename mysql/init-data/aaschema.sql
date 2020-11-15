# Converted with pg2mysql-1.9
# Converted on Fri, 23 Oct 2020 18:44:20 +0000
# Lightbox Technologies Inc. http://www.lightbox.ca

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone="+00:00";

create database tua2020;

CREATE TABLE tua2020.access_level (
    id bigint auto_increment NOT NULL,
    version bigint DEFAULT 1 NOT NULL,
    business_key varchar(36) NOT NULL,
    name varchar(32) NOT NULL, 
	PRIMARY KEY(`id`)
) ENGINE=InnoDB;

CREATE TABLE tua2020.`user` (
    id bigint auto_increment NOT NULL,
    version bigint DEFAULT 1 NOT NULL,
    business_key varchar(36) NOT NULL,
    login varchar(32) NOT NULL,
    password varchar(64) NOT NULL,
    email varchar(255) NOT NULL,
    locked bool NOT NULL,
    activated bool NOT NULL,
    created timestamp DEFAULT 0 NOT NULL,
    last_valid_login timestamp DEFAULT 0,
    last_invalid_login timestamp DEFAULT 0,
    invalid_login_attempts int(11) DEFAULT 0 NOT NULL,
    last_login_ip varchar(64),
    activation_code varchar(64) NOT NULL,
    reset_password_code varchar(64),
    reset_password_code_add_date timestamp DEFAULT 0, 
	PRIMARY KEY(`id`)
) ENGINE=InnoDB;

CREATE TABLE tua2020.user_access_level (
    id bigint auto_increment NOT NULL,
    version bigint DEFAULT 1 NOT NULL,
    business_key varchar(36) NOT NULL,
    user_id bigint NOT NULL,
    access_level_id bigint NOT NULL
, PRIMARY KEY(`id`)
) ENGINE=InnoDB;

CREATE TABLE tua2020.image (
    id bigint auto_increment NOT NULL,
    version bigint DEFAULT 1 NOT NULL,
    business_key varchar(36) NOT NULL,
    yacht_model_id bigint NOT NULL,
    lob BLOB NOT NULL
, PRIMARY KEY(`id`)
) ENGINE=InnoDB;

CREATE TABLE tua2020.opinion (
    id bigint auto_increment NOT NULL,
    version bigint DEFAULT 1 NOT NULL,
    business_key varchar(36) NOT NULL,
    rental_id bigint NOT NULL,
    rating int(11) NOT NULL,
    comment text,
    date timestamp DEFAULT 0 NOT NULL,
    edited bool DEFAULT 0 NOT NULL
, PRIMARY KEY(`id`)
) ENGINE=InnoDB;

CREATE TABLE tua2020.port (
    id bigint auto_increment NOT NULL,
    version bigint DEFAULT 1 NOT NULL,
    business_key varchar(36) NOT NULL,
    name varchar(64) NOT NULL,
    lake varchar(32) NOT NULL,
    nearest_city varchar(32) NOT NULL,
    longitude decimal(9,6) DEFAULT 0 NOT NULL,
    latitude decimal(9,6) DEFAULT 0 NOT NULL,
    active bool DEFAULT 1 NOT NULL
, PRIMARY KEY(`id`)
) ENGINE=InnoDB;

CREATE TABLE tua2020.rental (
    id bigint auto_increment NOT NULL,
    version bigint DEFAULT 1 NOT NULL,
    business_key varchar(36) NOT NULL,
    user_id bigint NOT NULL,
    yacht_id bigint NOT NULL,
    begin_date timestamp DEFAULT 0 NOT NULL,
    end_date timestamp DEFAULT 0 NOT NULL,
    rental_status_id bigint NOT NULL,
    price decimal(20,2) DEFAULT 0 NOT NULL
, PRIMARY KEY(`id`)
) ENGINE=InnoDB;

CREATE TABLE tua2020.rental_status (
    id bigint auto_increment NOT NULL,
    version bigint DEFAULT 1 NOT NULL,
    business_key varchar(36) NOT NULL,
    name varchar(32) NOT NULL
, PRIMARY KEY(`id`)
) ENGINE=InnoDB;

CREATE TABLE tua2020.user_details (
    id bigint NOT NULL,
    first_name varchar(32) NOT NULL,
    last_name varchar(32) NOT NULL,
    phone_number varchar(10) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE tua2020.yacht (
    id bigint auto_increment NOT NULL,
    version bigint DEFAULT 1 NOT NULL,
    business_key varchar(36) NOT NULL,
    name varchar(32) NOT NULL,
    production_year int(11) NOT NULL,
    yacht_model_id bigint NOT NULL,
    price_multiplier decimal(3,2) NOT NULL,
    equipment text NOT NULL,
    current_port_id bigint,
    avg_rating decimal(3,2) DEFAULT 0,
    active bool DEFAULT 1 NOT NULL
, PRIMARY KEY(`id`)
) ENGINE=InnoDB;

CREATE TABLE tua2020.yacht_model (
    id bigint auto_increment NOT NULL,
    version bigint NOT NULL,
    business_key varchar(36) NOT NULL,
    manufacturer varchar(32) NOT NULL,
    model varchar(32) NOT NULL,
    capacity int(11) NOT NULL,
    general_description text NOT NULL,
    basic_price decimal(10,2) NOT NULL,
    active bool DEFAULT 1 NOT NULL
, PRIMARY KEY(`id`)
) ENGINE=InnoDB;

CREATE VIEW tua2020.auth_view AS
 SELECT `user`.login,
    `user`.password,
    access_level.name AS access_level
   FROM tua2020.`user`
     JOIN tua2020.user_access_level ON `user`.id = user_access_level.user_id
     JOIN tua2020.access_level ON user_access_level.access_level_id = access_level.id
  WHERE `user`.locked IS FALSE AND `user`.activated IS TRUE;

CREATE USER 'ssbd02admin'@'%' IDENTIFIED BY 'h5LJwK5t';
CREATE USER 'ssbd02auth'@'%' IDENTIFIED BY 'kcH3FBde';
CREATE USER 'ssbd02mok'@'%' IDENTIFIED BY 'wSnBC87Y';
CREATE USER 'ssbd02moj'@'%' IDENTIFIED BY '9vWfuLnP';


GRANT ALL PRIVILEGES ON * . * TO 'ssbd02admin'@'%';


GRANT SELECT ON tua2020.auth_view TO 'ssbd02auth'@'%';


GRANT SELECT ON tua2020.access_level TO 'ssbd02mok'@'%';
GRANT ALL PRIVILEGES ON tua2020.`user` TO 'ssbd02mok'@'%';
GRANT INSERT, SELECT, DELETE ON tua2020.user_access_level TO 'ssbd02mok'@'%';
GRANT ALL PRIVILEGES ON tua2020.user_details TO 'ssbd02mok'@'%';


GRANT SELECT ON tua2020.access_level TO 'ssbd02moj'@'%';
GRANT INSERT, SELECT, DELETE ON tua2020.image TO 'ssbd02moj'@'%';
GRANT INSERT, SELECT, UPDATE ON tua2020.opinion TO 'ssbd02moj'@'%';
GRANT INSERT, SELECT, UPDATE ON tua2020.port TO 'ssbd02moj'@'%';
GRANT INSERT, SELECT, UPDATE ON tua2020.rental TO 'ssbd02moj'@'%';
GRANT SELECT ON tua2020.rental_status TO 'ssbd02moj'@'%';
GRANT SELECT ON tua2020.`user` TO 'ssbd02moj'@'%';
GRANT SELECT ON tua2020.user_access_level TO 'ssbd02moj'@'%';
GRANT SELECT ON tua2020.user_details TO 'ssbd02moj'@'%';
GRANT INSERT, SELECT, UPDATE ON tua2020.yacht TO 'ssbd02moj'@'%';
GRANT INSERT, SELECT, UPDATE ON tua2020.yacht_model TO 'ssbd02moj'@'%';

FLUSH PRIVILEGES;

--
-- Name: access_level_business_key_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX access_level_business_key_uindex USING btree ON tua2020.access_level (business_key);


--
-- Name: access_level_name_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX access_level_name_uindex USING btree ON tua2020.access_level (name);


--
-- Name: image_business_key_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX image_business_key_uindex USING btree ON tua2020.image (business_key);


--
-- Name: image_yacht_model_id_fkindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE INDEX image_yacht_model_id_fkindex USING btree ON tua2020.image (yacht_model_id);


--
-- Name: opinion_business_key_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX opinion_business_key_uindex USING btree ON tua2020.opinion (business_key);


--
-- Name: opinion_rental_id_fkindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE INDEX opinion_rental_id_fkindex USING btree ON tua2020.opinion (rental_id);


--
-- Name: opinion_rental_id_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX opinion_rental_id_uindex USING btree ON tua2020.opinion (rental_id);


--
-- Name: port_business_key_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX port_business_key_uindex USING btree ON tua2020.port (business_key);


--
-- Name: port_name_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX port_name_uindex USING btree ON tua2020.port (name);


--
-- Name: rental_business_key_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX rental_business_key_uindex USING btree ON tua2020.rental (business_key);


--
-- Name: rental_rental_status_id_fkindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE INDEX rental_rental_status_id_fkindex USING btree ON tua2020.rental (rental_status_id);


--
-- Name: rental_status_business_key_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX rental_status_business_key_uindex USING btree ON tua2020.rental_status (business_key);


--
-- Name: rental_status_name_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX rental_status_name_uindex USING btree ON tua2020.rental_status (name);


--
-- Name: rental_user_id_fkindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE INDEX rental_user_id_fkindex USING btree ON tua2020.rental (user_id);


--
-- Name: rental_yacht_id_fkindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE INDEX rental_yacht_id_fkindex USING btree ON tua2020.rental (yacht_id);


--
-- Name: user_access_level_access_level_id_fkindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE INDEX user_access_level_access_level_id_fkindex USING btree ON tua2020.user_access_level (access_level_id);


--
-- Name: user_access_level_business_key_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX user_access_level_business_key_uindex USING btree ON tua2020.user_access_level (business_key);


--
-- Name: user_access_level_user_id_access_level_id_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX user_access_level_user_id_access_level_id_uindex USING btree ON tua2020.user_access_level (user_id, access_level_id);


--
-- Name: user_access_level_user_id_fkindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE INDEX user_access_level_user_id_fkindex USING btree ON tua2020.user_access_level (user_id);


--
-- Name: user_activationcode_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX user_activationcode_uindex USING btree ON tua2020.`user` (activation_code);


--
-- Name: user_business_key_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX user_business_key_uindex USING btree ON tua2020.`user` (business_key);


--
-- Name: user_email_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX user_email_uindex USING btree ON tua2020.`user` (email);


--
-- Name: user_login_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX user_login_uindex USING btree ON tua2020.`user` (login);


--
-- Name: user_resetpasswordcode_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX user_resetpasswordcode_uindex USING btree ON tua2020.`user` (reset_password_code);


--
-- Name: yacht_business_key_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX yacht_business_key_uindex USING btree ON tua2020.yacht (business_key);


--
-- Name: yacht_current_port_id_fkindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE INDEX yacht_current_port_id_fkindex USING btree ON tua2020.yacht (current_port_id);


--
-- Name: yacht_model_business_key_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX yacht_model_business_key_uindex USING btree ON tua2020.yacht_model (business_key);


--
-- Name: yacht_model_model_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX yacht_model_model_uindex USING btree ON tua2020.yacht_model (model);


--
-- Name: yacht_name_uindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE UNIQUE INDEX yacht_name_uindex USING btree ON tua2020.yacht (name);


--
-- Name: yacht_yacht_model_id_fkindex; Type: INDEX; Schema: tua2020; Owner: ssbd02admin
--

CREATE INDEX yacht_yacht_model_id_fkindex USING btree ON tua2020.yacht (yacht_model_id);

--
-- Name: image image_yacht_model_id_fk; Type: FK CONSTRAINT; Schema:  tua2020; Owner: ssbd02admin
--

ALTER TABLE  tua2020.image
    ADD CONSTRAINT image_yacht_model_id_fk FOREIGN KEY (yacht_model_id) REFERENCES  tua2020.yacht_model(id);


--
-- Name: opinion opinion_rental_id_fk; Type: FK CONSTRAINT; Schema:  tua2020; Owner: ssbd02admin
--

ALTER TABLE  tua2020.opinion
    ADD CONSTRAINT opinion_rental_id_fk FOREIGN KEY (rental_id) REFERENCES  tua2020.rental(id);


--
-- Name: rental rental_rental_status_id_fk; Type: FK CONSTRAINT; Schema:  tua2020; Owner: ssbd02admin
--

ALTER TABLE  tua2020.rental
    ADD CONSTRAINT rental_rental_status_id_fk FOREIGN KEY (rental_status_id) REFERENCES  tua2020.rental_status(id);


--
-- Name: rental rental_user_id_fk; Type: FK CONSTRAINT; Schema:  tua2020; Owner: ssbd02admin
--

ALTER TABLE  tua2020.rental
    ADD CONSTRAINT rental_user_id_fk FOREIGN KEY (user_id) REFERENCES  tua2020.`user`(id);


--
-- Name: rental rental_yacht_id_fk; Type: FK CONSTRAINT; Schema:  tua2020; Owner: ssbd02admin
--

ALTER TABLE  tua2020.rental
    ADD CONSTRAINT rental_yacht_id_fk FOREIGN KEY (yacht_id) REFERENCES  tua2020.yacht(id);


--
-- Name: user_access_level user_access_level_access_level_id_fk; Type: FK CONSTRAINT; Schema:  tua2020; Owner: ssbd02admin
--

ALTER TABLE  tua2020.user_access_level
    ADD CONSTRAINT user_access_level_access_level_id_fk FOREIGN KEY (access_level_id) REFERENCES  tua2020.access_level(id);


--
-- Name: user_access_level user_access_level_user_id_fk; Type: FK CONSTRAINT; Schema:  tua2020; Owner: ssbd02admin
--

ALTER TABLE  tua2020.user_access_level
    ADD CONSTRAINT user_access_level_user_id_fk FOREIGN KEY (user_id) REFERENCES  tua2020.`user`(id);


--
-- Name: yacht yacht_port_id_fk; Type: FK CONSTRAINT; Schema:  tua2020; Owner: ssbd02admin
--

ALTER TABLE  tua2020.yacht
    ADD CONSTRAINT yacht_port_id_fk FOREIGN KEY (current_port_id) REFERENCES  tua2020.port(id);


--
-- Name: yacht yacht_yacht_model_id_fk; Type: FK CONSTRAINT; Schema:  tua2020; Owner: ssbd02admin
--

ALTER TABLE  tua2020.yacht
    ADD CONSTRAINT yacht_yacht_model_id_fk FOREIGN KEY (yacht_model_id) REFERENCES  tua2020.yacht_model(id);



/*ALTER TABLE tua2020.access_level
    ADD CONSTRAINT access_level_pk PRIMARY KEY (id);
ALTER TABLE tua2020.image
    ADD CONSTRAINT image_pk PRIMARY KEY (id);
ALTER TABLE tua2020.opinion
    ADD CONSTRAINT opinion_pk PRIMARY KEY (id);
ALTER TABLE tua2020.port
    ADD CONSTRAINT port_pk PRIMARY KEY (id);
ALTER TABLE tua2020.rental
    ADD CONSTRAINT rental_pk PRIMARY KEY (id);
ALTER TABLE tua2020.rental_status
    ADD CONSTRAINT rental_status_pk PRIMARY KEY (id);
ALTER TABLE tua2020.user_access_level
    ADD CONSTRAINT user_access_level_pk PRIMARY KEY (id);
ALTER TABLE tua2020.user_details
    ADD CONSTRAINT user_details_pk PRIMARY KEY (id);
ALTER TABLE tua2020.`user`
    ADD CONSTRAINT user_pk PRIMARY KEY (id);
ALTER TABLE tua2020.yacht_model
    ADD CONSTRAINT yacht_model_pk PRIMARY KEY (id);
ALTER TABLE tua2020.yacht
    ADD CONSTRAINT yacht_pk PRIMARY KEY (id);*/
