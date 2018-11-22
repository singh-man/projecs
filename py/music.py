import sys
# print(*sys.path, sep = "\nPYTHONPATH:- ")

# from ffmpeg import av or from ffmpeg.av import * or import ffmpeg.av as av
# the below can be used insted
import ffmpeg.av as av

options = {1: av.mp3ToM4a_ffmpeg,
           2: av.incrementVolume_ffmpeg,
           3: av.encode_ffmpeg}

[print(k, v.__name__) for k, v in options.items()]

selection = input("What u want!")

# options.get(selection) doesn't work
options[int(selection)]()
