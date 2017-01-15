#!/bin/bash

if (($# < 3))
then
	echo "   Needed 3 parametres. Usage: <db_address> <db_username> <db_pwd>"
	exit
fi

echo "
<-----DOWNLOADING FILE----->
"
wget -O rozklad.zip www.wroclaw.pl/open-data/opendata/rozklady/OtwartyWroclaw_rozklad_jazdy_GTFS.zip
if [ ! -e rozklad.zip ]; then
	echo "   Problem with extracting archive. Maybe not exists"
	exit
fi
echo "   File has been downloaded"

echo "
<-----EXTRACTING FILES----->
"
unzip -o rozklad.zip
echo "   
   Files have been extracted"

# special character to print
S1="\""


ARRAYS=(agency.txt calendar.txt calendar_dates.txt control_stops.txt feed_info.txt routes.txt route_types.txt stops.txt stop_times.txt variants.txt vehicle_types.txt trips.txt)

touch update.sql
touch removeQuotes.l

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
#compile flex file to remove quotes
flex removeQuotes.l
gcc -o a lex.yy.c
echo "   Flex file has been compiled"


echo "
<-----IMPORTING FILES TO DATABASE----->
"
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
	echo "   File $FILE has been imported successfully to database"
done
     
echo "      
   Database update complete
"

echo "
<-----REMOVING USELESS FILES----->
"
rm removeQuotes.l
rm a
rm lex.yy.c
rm update.sql
rm *.txt
rm rozklad.zip
echo "  Useless files deleted
"
