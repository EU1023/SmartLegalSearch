CREATE TABLE IF NOT EXISTS `legal_case` (
  `group_id` varchar(100) NOT NULL,
  `id` varchar(100) NOT NULL,
  `court` varchar(25) NOT NULL,
  `date` date DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `charge` varchar(25) DEFAULT NULL,
  `judge_name` varchar(50) DEFAULT NULL,
  `defendant_name` varchar(50) DEFAULT NULL,
  `text` text,
  `law` varchar(500) DEFAULT NULL,
  `case_type` varchar(25) DEFAULT NULL,
  `doc_type` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`group_id`,`id`,`court`)
);


