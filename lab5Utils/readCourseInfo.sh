#!/bin/bash
filename="$1"
count=0
while read -r line
do


   if [ -z "$line" ]
   then
       echo "line is empty"
       echo "\" $line \n \"+" >> finalCourseInfo
   else

       firstTwoChars=`echo $line | cut -c 1-2`
      
       if [ "$firstTwoChars" = "CS" ] 
       then
           echo "," >> finalCourseInfo
           echo "\" $line \n \"+" >> finalCourseInfo
           count=$(( $count + 1 ))
       else
           echo "\" $line \n \"+" >> finalCourseInfo
       fi      

   fi

done < "$filename"
