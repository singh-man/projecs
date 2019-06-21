import sys
# print(*sys.path, sep = "\nPYTHONPATH:- ")

# from ffmpeg import av or from ffmpeg.av import * or import ffmpeg.av as av
# the below can be used insted
import ffmpeg.av as av

# [print(k, v.__name__) for k, v in options.items()]
# options[int(selection)]()

funcs = av.listAllUsefullFunctions()

[print(str(k) + ": ", v[0]) for k, v in funcs.items()]

selection = input("What u want! : ")

# options.get(selection) doesn't work
funcs[int(selection)][1]()
