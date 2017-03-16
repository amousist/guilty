#!/bin/bash

pico2wave -w /tmp/voice.wav -l es-ES "$1" && play /tmp/voice.wav pitch -600 chorus 0.4 0.8 20 0.5 0.10 2 -t echo 0.9 0.8 33 0.9 echo 0.7 0.7 10 0.2 echo 0.9 0.2 55 0.5 gain 10


rm -f /tmp/voice.wav
