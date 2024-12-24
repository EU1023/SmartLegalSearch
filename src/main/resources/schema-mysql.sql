CREATE TABLE IF NOT EXIST `criminal_case` (
  `group_id` varchar(100) NOT NULL,
  `id` varchar(100) NOT NULL,
  `court` varchar(25) NOT NULL,
  `date` date DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `charge` varchar(25) DEFAULT NULL,
  `judge_name` varchar(50) DEFAULT NULL,
  `defendant_name` varchar(50) DEFAULT NULL,
  `text` text,
  PRIMARY KEY (`group_id`,`id`,`court`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
