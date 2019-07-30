CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone_nbr` varchar(45) DEFAULT NULL,
  `open_id` varchar(45) DEFAULT NULL,
  `project_from` varchar(45) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=492485 DEFAULT CHARSET=latin1;

CREATE TABLE `user_temp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone_nbr` varchar(45) DEFAULT NULL,
  `open_id` varchar(45) DEFAULT NULL,
  `project_from` varchar(45) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=492485 DEFAULT CHARSET=latin1;

