# pytest -s av.py -k test_ffmpeg_incrementVolume

import sys
# sys.path.insert(0, 'utils')
# print(*sys.path, sep = "\nPYTHONPATH:- ")

import pytest

from utils import directoryUtils, utils
from utils.utils import *


def getPath():
    if isLinux():
        return "/mnt/d/mani/video/"
    else:
        return "D:/mani/video/"


def getFFmpeg():
    if isLinux():
        return "ffmpeg"
    else:
        return "D:/mani/dev/opt/ffmpeg-20160614-git-cb46b78-win32-static/bin/ffmpeg.exe"


def ffmpeg_toM4a_libfdk_aac(inFile, outFile):
    return getFFmpeg() + " -i %s -c:a libfdk_aac -profile:a aac_he_v2 -b:a 48k %s" % (inFile, outFile)


def ffmpeg_toM4aWith_builtInAAC(inFile, outFile):
    return getFFmpeg() + " -i %s -c:a aac -b:a 48k %s" % (inFile, outFile)


def ffmpeg_toWav(inFile, outFile):
    return getFFmpeg() + " -i %s %s" % (inFile, outFile)


def ffmpeg_incVolume(inFile, outFile, db):
    return getFFmpeg() + " -i %s -map 0 -c copy -c:a aac -af \"volume=%sdB\" %s" % (inFile, db, outFile)


def mp3ToM4a_ffmpeg_libfdk_aac():
    filesList = directoryUtils.findFiles(getPath(), "mp3")
    fileMap = {f: replaceFileExt(f, ".m4a") for f in filesList}
    # for f in filesList: fileMap[f] = getOutputFileName(f, "mp3", "m4a") # assigning value to a key

    cmdList = [ffmpeg_toM4a_libfdk_aac(f1, f2) for f1, f2 in fileMap.items()]
    directoryUtils.printList(cmdList)
    # map(lambda cmd:directoryUtils.execCmd(cmd), cmdList)
    [directoryUtils.execCmd(cmd) for cmd in cmdList]


def mp3ToM4a_ffmpeg():
    fileOrFolder = input("Enter file or folder path <" + getPath() + "> : ")
    if directoryUtils.isDir(fileOrFolder):
        filesList = directoryUtils.findFiles(fileOrFolder, "mp3")
    else:
        filesList = [fileOrFolder]
    cmdList = []
    for f in filesList:
        wavFile = replaceFileExt(f, ".wav")
        m4aFile = replaceFileExt(f, ".m4a")
        cmdList.append(ffmpeg_toWav(f, wavFile))
        cmdList.append(ffmpeg_toM4a_libfdk_aac(wavFile, m4aFile))
    directoryUtils.printList(cmdList)
    print([utils.execCmd(cmd) for cmd in cmdList])
    [directoryUtils.removeFile(replaceFileExt(f, ".wav")) for f in filesList]


def incrementVolume_ffmpeg():
    fileOrFolder = input("Enter file or folder path <" + getPath() + "compressed/" + "> : ")
    if directoryUtils.isDir(fileOrFolder):
        filesList = directoryUtils.findFiles(fileOrFolder, "")
    else:
        filesList = [fileOrFolder]
    db = input("Provide db: ")
    fileMap = {f: replaceFileExt(f, "_f_v_" + db + ".mkv") for f in filesList}
    cmdList = [ffmpeg_incVolume(f1, f2, db) for f1, f2 in fileMap.items()]
    directoryUtils.printList(cmdList)
    # map(lambda cmd:directoryUtils.execCmd(cmd), cmdList)
    directoryUtils.dumpCmdToScript(cmdList, "../../")


def test_mp3TpM4a():
    mp3ToM4a_ffmpeg()


if __name__ == "__main__":
    filesList = directoryUtils.findFiles(getPath(), ".mp3")
