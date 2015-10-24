#!/bin/bash
cd ~/github/Your-Worst-Nightmare-Segfault/Experiments
python passengers.py > data.txt

gcc passenger.c
./a.out < data.txt > extracted.txt

gcc parse.c
./a.out < extracted.txt > parsed.txt

open parsed.txt

