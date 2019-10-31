CREATE TABLE `token` (
  `user_id` int(11) NOT NULL,
  `token` varchar(255) NOT NULL,
  `expires` timestamp  NULL,
  PRIMARY KEY (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
