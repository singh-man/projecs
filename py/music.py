import sys
# print(*sys.path, sep = "\nPYTHONPATH:- ")

from ffmpeg import av


selection = input("1. For music \n2. For Video")

options = {1 : av.mp3ToM4a_ffmpeg(), 
           2 : av.incrementVolume_ffmpeg()}

options.get(selection)