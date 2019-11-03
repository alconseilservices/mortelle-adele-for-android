#!/usr/bin/env bash

for filename in ./1/*.webp; do
    echo '{'
    #echo  '"image_file": "${filename/./}",'
	echo '"image_file": "'"${filename/.\/1\//}"'",'
    echo  '"emojis": ["😄","😀"]'
    echo '},'
done

echo "###########################################"


for filename in ./2/*.webp; do
    echo '{'
    #echo  '"image_file": "${filename/./}",'
	echo '"image_file": "'"${filename/.\/2\//}"'",'
    echo  '"emojis": ["😄","😀"]'
    echo '},'
done

echo "###########################################"

for filename in ./3/*.webp; do
    echo '{'
    #echo  '"image_file": "${filename/./}",'
	echo '"image_file": "'"${filename/.\/3\//}"'",'
    echo  '"emojis": ["😄","😀"]'
    echo '},'
done

echo "###########################################"

for filename in ./4/*.webp; do
    echo '{'
    #echo  '"image_file": "${filename/./}",'
	echo '"image_file": "'"${filename/.\/4\//}"'",'
    echo  '"emojis": ["😄","😀"]'
    echo '},'
done
