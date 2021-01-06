INSERT INTO `ims`.`customers` (`first_name`, `last_name`) VALUES ('sam', 'smith');
INSERT INTO `ims`.`item` (`name`, `version`, `price`, `description`) VALUES ('Books', '1.0', '15.50', 'Book is for Reading!');
INSERT INTO `ims`.`orders` (`OID`, `CID`) VALUES (1, 1);
INSERT INTO `ims`.`item_orders` (`OID`, `IID`) VALUES (1, 1);