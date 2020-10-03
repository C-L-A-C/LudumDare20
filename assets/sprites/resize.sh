#!/bin/bash

for f in *.png
do
	convert "$f" -trim +repage "$f"

	w=$(identify -format "%[fx:w]" "$f")
	h=$(identify -format "%[fx:h]" "$f")
	if [ "$w" -gt "$h" ]
	then
		max=$w
	else
		max=$h
	fi
	echo "Convert $f to ${max}x${max}"
	convert "$f" -gravity center -background transparent -extent "${max}x${max}" "$f"
 	
	convert "$f" -interpolate Integer -filter point -resize 32x32 "$f"
done
