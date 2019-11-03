#!/usr/bin/env bash

for filename in ./stickers/COUVS/*512*.png; do
	cwebp $filename -o "${filename/png/webp}"
done

for filename in ./stickers/PERSOS/*512*.png; do
	cwebp $filename -o "${filename/png/webp}"
done

for filename in ./stickers/TEXT/*512*.png; do
	cwebp $filename -o "${filename/png/webp}"
done
