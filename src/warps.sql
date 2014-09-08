CREATE TABLE IF NOT EXISTS `UltraWarp` (
	`id` INTEGER NOT NULL PRIMARY KEY,
	`warpName` VARCHAR(255) NOT NULL,
	`playerUUID` VARCHAR(255) NOT NULL,
	`X` FLOAT NOT NULL,
	`Y` FLOAT NOT NULL,
	`Z` FLOAT NOT NULL,
	`YAW` FLOAT NOT NULL,
	`PITCH` FLOAT NOT NULL,
	`world` VARCHAR(255) NOT NULL,
	`public` BOOLEAN DEFAULT 0
)