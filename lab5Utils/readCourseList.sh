#!/bin/bash
filename="$1"
while read -r line
do
   echo "\"$line\"," >> finalCoursesList
done < "$filename"
