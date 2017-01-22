#!/bin/bash


#	Following script is used to update database jakDojade with actual 
#	GTFS version from official site.
#	Last script update: 22 January 2017

#Start time to measure script execution time
START=$(date +%s.%N)

#Check properly entered arguments
if (($# < 3))
then
	echo "   Needed 3 parametres. Usage: <db_address> <db_username> <db_pwd>"
	exit
fi

#Create dir for temporary files
if [ ! -d "temp" ]; then
	mkdir temp
fi
cd temp

#Download site content and check for changes
wget http://www.wroclaw.pl/open-data/index.php/zbiory-danych/17-transport/106-rozklad-jazdy-transportu-publicznego -q -O - >content_new
update=0
if [  -e content_old ]; then
	if ["$(diff content_new content_old)" == ""]; then
		rm content_new
		echo "	Database is up to date !"
	else
		update=1
	fi
else
	update=1
fi

#Perform actions to update database if outdated
if [ $update == 1 ]; then

	echo "
	<-----DOWNLOADING FILE----->
	"
	#Download GTFS file
	wget -O rozklad.zip www.wroclaw.pl/open-data/opendata/rozklady/OtwartyWroclaw_rozklad_jazdy_GTFS.zip
	if [ ! -e rozklad.zip ]; then
		echo "   Problem with extracting archive. Maybe not exists"
		exit
	fi
	echo "   File has been downloaded"

	echo "
	<-----EXTRACTING FILES----->
	"
	#Exctract GTFS file
	unzip -o rozklad.zip
	echo "   
	   Files have been extracted"
	
	S1="\"" # special character to print

	#Array with table names
	ARRAYS=(agency.txt calendar.txt calendar_dates.txt control_stops.txt feed_info.txt routes.txt route_types.txt stops.txt stop_times.txt variants.txt vehicle_types.txt trips.txt)

	#Create needed files
	#	1. sql script
	#	2. flex file to remove quotes
	touch update.sql
	touch removeQuotes.l

	#Flex file content
echo "%{
	#include <stdio.h>
	int yywrap();
	int yylex();
%}
%%
[$S1] 	{};
%%
int yywrap() {  
	return 1; 
}
int main() { 
	return yylex(); 
}" > removeQuotes.l
	echo "
	<-----COMPILING FLEX FILE----->
	"
	#Make flex program
	flex removeQuotes.l
	gcc -o a lex.yy.c
	echo "   Flex file has been compiled"

	echo "
	<-----IMPORTING FILES TO DATABASE----->
	"
	#For every file(table) run flex and import to database with sql file
	for FILE in ${ARRAYS[@]}
	do	
		./a < $FILE > $FILE.tmp && mv $FILE.tmp $FILE
		echo "TRUNCATE TABLE $(echo $FILE | cut -f 1 -d '.');
		LOAD DATA LOCAL INFILE '$FILE'
		INTO TABLE $(echo $FILE | cut -f 1 -d '.')
		FIELDS TERMINATED BY ','
		LINES TERMINATED BY '\n'
		IGNORE 1 ROWS;" > update.sql
		mysql --host=$1 --user=$2 --password=$3 --default-character-set=utf8 jakDojade < update.sql
		echo "   File $FILE has been imported successfully to database
	Table $(echo $FILE | cut -f 1 -d '.') is now up to date"
	done

	echo "DROP TABLE IF EXISTS trip_type; CREATE TABLE trip_type AS SELECT routes.route_short_name, route_types.route_type2_name, trips.trip_id FROM route_types
	INNER JOIN routes ON route_types.route_type2_id=routes.route_type2_id
	INNER JOIN trips ON routes.route_id=trips.route_id
	GROUP BY trips.trip_id" > update.sql
	mysql --host=$1 --user=$2 --password=$3 --default-character-set=utf8 jakDojade < update.sql
	echo "   Table trip_type has been successfully updated"
	     
	echo "      
	   Database update complete
	"
	mv content_new content_old

	echo "
	<-----REMOVING USELESS FILES----->
	"
	#Remove useless files
	rm removeQuotes.l
	rm a
	rm lex.yy.c
	rm update.sql
	rm *.txt
	rm rozklad.zip
	cd ..
	echo "  Useless files deleted
"
	#End time of script
	END=$(date +%s)
	echo "#################################################

Script execution time: $(echo "$END - $START" | bc) seconds
"
fi
