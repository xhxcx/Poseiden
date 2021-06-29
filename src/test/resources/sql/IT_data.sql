use `poseiden_test`;
TRUNCATE bidlist ;
TRUNCATE curvepoint;
TRUNCATE rating;
TRUNCATE rulename;
TRUNCATE trade;

-- bidList
INSERT INTO `bidlist` (`account`, `type`, `bidQuantity`)
VALUES ( 'Account bidList 1',    'Type bidList 1',    1);

INSERT INTO `bidlist` (`account`, `type`, `bidQuantity`)
VALUES ( 'Account bidList 2',    'Type bidList 2',    10);