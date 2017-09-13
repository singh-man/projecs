-- create tables

DROP TABLE IF EXISTS `emp`.`login_details`;
DROP TABLE IF EXISTS `emp`.`employee`;

CREATE TABLE `emp`.`employee` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `emp_name` varchar(255) NOT NULL,
  `eid` varchar(45) NOT NULL,
  `pwd` varchar(45) NOT NULL,
  `role` varchar(45) DEFAULT 'user',
  `date_of_join` date,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

ALTER TABLE `emp`.`employee` ADD UNIQUE (`eid`);


CREATE TABLE `emp`.`login_details` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `the_date` date NOT NULL,
  `in_time` time NOT NULL,
  `out_time` time,
  `emp_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `emp_idx` (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

ALTER TABLE `emp`.`login_details` ADD CONSTRAINT `FK_login_details_1` FOREIGN KEY `FK_login_details_1` (`emp_id`)
    REFERENCES `emp`.`employee` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT
, ROW_FORMAT = DYNAMIC;

--delete from employee;
INSERT INTO `emp`.`employee` (emp_name, eid, pwd, role, date_of_join) VALUES ('admin','admin','admin','admin','2011-11-11'),('user1','user1','user1','user','2011-11-11'),('user2','user2','user2','user','2011-11-11');








select * from login_details where the_date in ('2011-11-11','2011-11-12') order by the_date
select emp_id from login_details where the_date in ('2011-11-11','2011-11-12')
select * from employee where id in (select emp_id from login_details where the_date in ('2011-11-11','2011-11-12'));


select * from employee where role = 'user'
select e.id, l.id from employee e, login_details l
select e.emp_name, e.eid, l.the_date, l.in_time, l.out_time from employee e, login_details l where e.id = l.emp_id and l.the_date >= '2011-11-10' and the_date <= '2011-11-14' order by e.emp_name;

select * from employee order by emp_name










-- H2 or Derby tables

DROP TABLE login_details;
DROP TABLE employee;

CREATE TABLE employee (
  id int NOT NULL generated always as identity,
  emp_name varchar(255) NOT NULL,
  eid varchar(45) NOT NULL,
  pwd varchar(45) NOT NULL,
  role varchar(45) DEFAULT 'user',
  date_of_join date,
  PRIMARY KEY (id)
);

ALTER TABLE employee ADD CONSTRAINT unq_eid UNIQUE (eid);

CREATE TABLE login_details (
  id int NOT NULL generated always as identity,
  the_date date NOT NULL,
  in_time time NOT NULL,
  out_time time,
  emp_id int NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE login_details ADD CONSTRAINT "emp_FK" FOREIGN KEY (emp_id)
    REFERENCES employee(id) 
    ON DELETE CASCADE 
    ON UPDATE RESTRICT;

--delete from employee;
INSERT INTO employee (emp_name, eid, pwd, role, date_of_join) VALUES ('admin','admin','<give password here>','admin','2011-11-11'),('user1','user1','user1','user','2011-11-11'),('user2','user2','user2','user','2011-11-11');
