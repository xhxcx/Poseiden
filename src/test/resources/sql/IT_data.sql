use `poseiden_test`;
TRUNCATE bidlist ;
TRUNCATE curvepoint;
TRUNCATE rating;
TRUNCATE rulename;
TRUNCATE trade;
TRUNCATE users;

-- bidList
INSERT INTO `bidlist` (`account`, `type`, `bidQuantity`)
VALUES ( 'Account bidList 1',    'Type bidList 1',    1);
INSERT INTO `bidlist` (`account`, `type`, `bidQuantity`)
VALUES ( 'Account bidList 2',    'Type bidList 2',    10);

-- CurvePoint
INSERT INTO `curvepoint`( `CurveId`, `term`, `value`)
VALUES (1,50.1,123.9);
INSERT INTO `curvepoint`( `CurveId`, `term`, `value`)
VALUES (2,123,987);

-- Ratings
INSERT INTO `rating`( `moodysRating`, `sandPRating`, `fitchRating`, `orderNumber`)
VALUES ('moody S Rating 1','sand P Rating','fitch Rating 1',1);
INSERT INTO `rating`( `moodysRating`, `sandPRating`, `fitchRating`, `orderNumber`)
VALUES ('','sand Rating 2','fitch',2);

-- RuleName
INSERT INTO `rulename`( `name`, `description`, `json`, `template`, `sqlStr`, `sqlPart`)
VALUES ('name 1','description 1', 'json 1','template 1','sqlStr 1','sqlPart 1');
INSERT INTO `rulename`( `name`, `description`, `json`, `template`, `sqlStr`, `sqlPart`)
VALUES ('name 2','description 2', 'json 2',null,'sqlStr 2','sqlPart 2');

-- Trade
INSERT INTO `trade` (`account`, `type`, `buyQuantity`)
VALUES ( 'Account trade 1',    'Type trade 1',    1);
INSERT INTO `trade` (`account`, `type`, `buyQuantity`)
VALUES ( 'Account trade 2',    'Type trade 2',    10);

-- User
INSERT INTO Users(`fullname`, `username`, `password`, `role`)
VALUES('Fullname user 1', 'Username user 1', 'pwd user 1', 'ADMIN');
INSERT INTO Users(`fullname`, `username`, `password`, `role`)
VALUES ('Fullname user 2', 'Username user 2', 'pwd user 2', 'USER');