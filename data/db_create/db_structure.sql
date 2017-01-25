DROP DATABASE IF EXISTS `jakDojade`;
CREATE DATABASE `jakDojade` CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `jakDojade`;
DROP TABLE IF EXISTS `agency`, `calendar`, `calendar_dates`,`control_stops`, `feed_info`,`route_types`, `stop_times`,`routes`,`stops`,`trips`,`variants`,`vehicle_types`;
CREATE TABLE `agency` (
`agency_id` INT,
`agency_name` VARCHAR(50),
`agency_url` VARCHAR(50),
`agency_timezone` VARCHAR(20),
`agency_phone` VARCHAR(20),
`agency_lang` VARCHAR(2)
);

CREATE TABLE `calendar` (
`service_id` INT,
`monday` INT,
`tuesday` INT,
`wednesday` INT,
`thursday` INT,
`friday` INT,
`saturday` INT,
`sunday` INT,
`start_date` DATE,
`end_date` DATE
);

CREATE TABLE `calendar_dates` (
`service_id` INT,
`date` DATE,
`exception_type` VARCHAR(50)
);

CREATE TABLE `control_stops` (
`variant_id` INT,
`stop_id` INT
);

CREATE TABLE `feed_info` (
`feed_publisher_name` VARCHAR(50),
`feed_publisher_url` VARCHAR(50),
`feed_lang` VARCHAR(2),
`feed_start_date` DATE,
`feed_end_date` DATE
);

CREATE TABLE `route_types` (
`route_type2_id` INT,
`route_type2_name` VARCHAR(50)
);

CREATE TABLE `stop_times` (
`trip_id` VARCHAR(10),
`arrival_time` TIME,
`departure_time` TIME,
`stop_id` INT,
`stop_sequence` INT,
`pickup_type` INT,
`drop_off_type` INT
);

CREATE TABLE `routes` (
`route_id` INT,
`agency_id` INT,
`route_short_name` VARCHAR(5),
`route_long_name` VARCHAR(50),
`route_desc` VARCHAR(950),
`route_type` INT,
`route_type2_id` INT,
`valid_from` DATE,
`valid_until` DATE
);

CREATE TABLE `stops` (
`stop_id` INT,
`stop_code` INT,
`stop_name` VARCHAR(50),
`stop_lat` DOUBLE,
`stop_lon` DOUBLE
);

CREATE TABLE `trips` (
`route_id` INT,
`service_id` INT,
`trip_id` VARCHAR(10),
`trip_headsign` VARCHAR(36),
`direction_id` INT,
`brigade_id` INT,
`vehicle_id` INT,
`variant_id` INT
);

CREATE TABLE `variants` (
`variant_id` INT,
`is_main` INT,
`equiv_main_variant_id` INT,
`join_stop_id` INT,
`disjoin_stop_id` INT
);

CREATE TABLE `vehicle_types` (
`vehicle_type_id` INT, 
`vehicle_type_name` VARCHAR(32),
`vehicle_type_description` VARCHAR(80), 
`vehicle_type_symbol` VARCHAR(1)
);
