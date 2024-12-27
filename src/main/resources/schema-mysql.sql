CREATE TABLE IF NOT EXISTS`legal_case` (
  `group_id` varchar(100) NOT NULL,
  `id` varchar(100) NOT NULL,
  `court` varchar(25) NOT NULL,
  `date` date DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `charge` varchar(25) DEFAULT NULL,
  `judge_name` varchar(50) DEFAULT NULL,
  `defendant_name` varchar(50) DEFAULT NULL,
  `text` longtext,
  `law` text,
  `case_type` varchar(25) DEFAULT NULL,
  `doc_type` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`group_id`,`id`,`court`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `account_system` (
  `email` varchar(100) NOT NULL,
  `name` varchar(25) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email_verified` tinyint(1) NOT NULL DEFAULT '0',
  `role` varchar(25) NOT NULL DEFAULT 'user',
  `identity` varchar(25) DEFAULT NULL,
  `phone` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `account_system` (
  `email` varchar(100) NOT NULL,
  `name` varchar(25) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email_verified` tinyint(1) NOT NULL DEFAULT '0',
  `role` varchar(25) NOT NULL DEFAULT 'user',
  `identity` varchar(25) DEFAULT NULL,
  `phone` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


