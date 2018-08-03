import sys
print(*sys.path, sep = "\nPYTHONPATH:- ")

from ffmpeg import av
from utils import directoryUtils


# filesList = directoryUtils.findFiles(av.getPath(), "mp3")
# fileMap = {f:av.replaceFileExt(f, ".m4a") for f in filesList}
# cmdList = [av.ffmpeg_toM4a_libfdk_aac(f1, f2) for f1, f2 in fileMap.items()]
# directoryUtils.printList(cmdList)
# [directoryUtils.execCmd(cmd) for cmd in cmdList]



filesList = directoryUtils.findFiles(av.getPath(), "")
fileMap = {f:av.replaceFileExt(f, "_f_v9.mkv") for f in filesList}
cmdList = [av.ffmpeg_incVolume(f1, f2) for f1, f2 in fileMap.items()]
directoryUtils.printList(cmdList)
directoryUtils.dumpCmdToScript(cmdList)