CREATE TABLE IF NOT EXISTS `legal_case` (
  `group_id` varchar(100) NOT NULL,
  `id` varchar(100) NOT NULL,
  `court` varchar(25) NOT NULL,
  `verdict_date` date DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `charge` varchar(500) DEFAULT NULL,
  `judge_name` varchar(50) DEFAULT NULL,
  `defendant_name` varchar(50) DEFAULT NULL,
  `content` longtext,
  `content2` longtext,
  `law` text,
  `case_type` varchar(25) DEFAULT NULL,
  `doc_type` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`group_id`,`id`,`court`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE IF NOT EXISTS `account_system` (
  `email` varchar(100) NOT NULL,
  `name` varchar(25) NOT NULL DEFAULT 'guest',
  `password` varchar(100) NOT NULL,
  `email_verified` tinyint(1) NOT NULL DEFAULT '0',
  `role` varchar(25) NOT NULL DEFAULT 'guest',
  `phone` varchar(25) NOT NULL DEFAULT '0',
  `email_verification_token` varchar(255) DEFAULT NULL,
  `token_expiry` datetime DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



