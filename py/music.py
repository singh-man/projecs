import sys
# print(*sys.path, sep = "\nPYTHONPATH:- ")

# from ffmpeg import av or from ffmpeg.av import * or import ffmpeg.av as av
# the below can be used insted
import ffmpeg.av as av

selection = input("1. For music \n2. For Video")

options = {1: av.mp3ToM4a_ffmpeg,
           2: av.incrementVolume_ffmpeg}

# options.get(selection) doesn't work
options[int(selection)]()
