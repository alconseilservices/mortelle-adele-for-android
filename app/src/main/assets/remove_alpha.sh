#!/usr/bin/env bash

for filename in ./1/*.png; do
    cp $filename ${filename/px/px_bkp}
    convert -flatten ${filename/px/px_bkp} $filename
    rm -f ${filename/px/px_bkp}
done

for filename in ./2/*.png; do
    cp $filename ${filename/px/px_bkp}
    convert -flatten ${filename/px/px_bkp} $filename
    rm -f ${filename/px/px_bkp}
done

for filename in ./3/*.png; do
    cp $filename ${filename/px/px_bkp}
    convert -flatten ${filename/px/px_bkp} $filename
    rm -f ${filename/px/px_bkp}
done

for filename in ./4/*.png; do
    cp $filename ${filename/px/px_bkp}
    convert -flatten ${filename/px/px_bkp} $filename
    rm -f ${filename/px/px_bkp}
done

for filename in ./5/*.png; do
    cp $filename ${filename/px/px_bkp}
    convert -flatten ${filename/px/px_bkp} $filename
    rm -f ${filename/px/px_bkp}
done
