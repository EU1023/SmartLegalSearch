CREATE TABLE IF NOT EXIST `criminal_case` (      -- 刑事案件
    `group_id` VARCHAR(100),        -- 案件群組識別碼
    `id` VARCHAR(100),              -- 案件的唯一識別碼
    `court` VARCHAR(25),            -- 審理法院
    `verdict_date` DATE,            -- 判決日期
    `law` VARCHAR(500),             -- 相關法條
    `law_detail` TEXT,              -- 法條細節
    `url` VARCHAR(500),             -- 判決書連結
    `charge` VARCHAR(25),           -- 案由
    `facts` TEXT,                   -- 事實描述
    `motive` TEXT,                  -- 動機/理由描述

    PRIMARY KEY (`group_id`, `id`, `court`)  -- 主鍵由 group_id, id, court 三個欄位組成
);

CREATE TABLE IF NOT EXIST `criminal_related_parties` (     -- 關係人
    `party_id` INT AUTO_INCREMENT,            -- 關係人唯一識別碼，使用自增 (auto_increment)
    `group_id` VARCHAR(100),                  -- 案件群組識別碼
    `id` VARCHAR(100),                        -- 案件唯一識別碼
    `name` VARCHAR(25),                       -- 關係人姓名
    `role` VARCHAR(25),                       -- 關係人角色（法官/律師/檢察官/被告）

    PRIMARY KEY (`party_id`),                 -- 主鍵是 party_id
    FOREIGN KEY (`group_id`, `id`) REFERENCES criminal_case(`group_id`, `id`)
        ON DELETE CASCADE                   -- 當 criminal_case 表中的紀錄被刪除時，對應的關係人紀錄也會被刪除
        ON UPDATE CASCADE                   -- 當 criminal_case 表中的紀錄更新時，對應的關係人紀錄中的外鍵也會更新
);

CREATE TABLE IF NOT EXIST `criminal_penalties` (			 -- 刑罰
  `penalty_id` int NOT NULL AUTO_INCREMENT,  -- 刑罰的唯一識別碼
  `group_id` varchar(100) DEFAULT NULL,      -- 案件群組識別碼
  `id` varchar(100) DEFAULT NULL,            -- 案件唯一識別碼
  `type` varchar(25) DEFAULT NULL,           -- 刑罰類型（死刑、無期徒刑等）
  `years` int DEFAULT '0',                   -- 刑期年數
  `months` int DEFAULT '0',                  -- 刑期月數
  `days` int DEFAULT '0',                    -- 刑期日數
  `probation` tinyint DEFAULT '0',           -- 是否緩刑
  `probation_years` int DEFAULT '0',         -- 緩刑年數
  `fine_type` int DEFAULT '0',               -- 罰金類型
  `fine` int DEFAULT '0',                    -- 罰金金額

  PRIMARY KEY (`penalty_id`),                -- 主鍵
  KEY `group_id` (`group_id`,`id`),          -- 為 group_id 和 id 建立索引，改善查詢效能

  -- 外鍵約束：關聯 criminal_case 表格的 group_id 和 id
  CONSTRAINT `criminal_penalties_ibfk_1`
    FOREIGN KEY (`group_id`, `id`)
    REFERENCES `criminal_case` (`group_id`, `id`)
    ON DELETE CASCADE           -- 當刪除 criminal_case 表中的紀錄時，對應的刑罰紀錄也會刪除
    ON UPDATE CASCADE           -- 當 criminal_case 表中的紀錄更新時，對應的刑罰表中的外鍵欄位也會更新
);
