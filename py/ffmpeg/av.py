# pytest -s av.py -k test_ffmpeg_incrementVolume

import sys
# sys.path.insert(0, 'utils')
# print(*sys.path, sep = "\nPYTHONPATH:- ")

import pytest

from utils import directoryUtils
from utils.utils import execCmd, isLinux, replaceFileExt


def getPath():
    if isLinux():
        return "/mnt/d/mani/video/"
    else:
        return "D:/mani/video/"


def getFFmpeg():
    if isLinux():
        return "ffmpeg"
    else:
        return "D:/mani/dev/opt/ffmpeg-20180912-b69ea74-win64-static/bin/ffmpeg.exe"


def ffmpeg_toM4a_libfdk_aac(inFile, outFile):
    return getFFmpeg() + " -i %s -c:a libfdk_aac -profile:a aac_he_v2 -b:a 48k %s" % (inFile, outFile)


def ffmpeg_toM4aWith_builtInAAC(inFile, outFile):
    return getFFmpeg() + " -i %s -c:a aac -b:a 48k %s" % (inFile, outFile)


def ffmpeg_toWav(inFile, outFile):
    return getFFmpeg() + " -i %s %s" % (inFile, outFile)


def ffmpeg_incVolume(inFile, outFile, db):
    return getFFmpeg() + " -i %s -map 0 -c copy -c:a aac -af \"volume=%sdB\" %s" % (inFile, db, outFile)


def ffmpeg_encode(inFile, outFile, encoder, crf, resolution):
    return getFFmpeg() + \
           " -i %s -vf scale=%s -map 0 -c copy -c:v %s -preset medium -crf %s -c:a aac -strict experimental -b:a 96k %s" \
           % (inFile, resolution, encoder, crf, outFile)


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
    print([execCmd(cmd) for cmd in cmdList])
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


def ffmpegEncode():
    fileOrFolder = input("Enter file or folder path <" + getPath() + "compressed/" + "> : ")
    if directoryUtils.isDir(fileOrFolder):
        filesList = directoryUtils.findFiles(fileOrFolder, "")
    else:
        filesList = [fileOrFolder]

    encoders = {1: "libx264", 2: "libx265", 3: "libaom-av1", 4: "libvpx-vp9"}
    [print(k, v) for k, v in encoders.items()]
    encoder = encoders[int(input("Provide encoder: "))]
    crf = input("Provide CRF : [0-23-51 least]:")
    ratios = ["-1:-1", "426:240", "-1:360", "852:480", "-1:720", "-1:1080"]
    [print(idx, " : ", v) for idx, v in enumerate(ratios)]
    resolution = ratios[int(input("Provide vertical resolution : "))]

    fileMap = {f: replaceFileExt(f, "_" + encoder + ".mkv") for f in filesList}
    cmdList = [ffmpeg_encode(f1, f2, encoder, crf, resolution) for f1, f2 in fileMap.items()]
    directoryUtils.printList(cmdList)
    # map(lambda cmd:directoryUtils.execCmd(cmd), cmdList)
    directoryUtils.dumpCmdToScript(cmdList, "../../")

def test_mp3TpM4a():
    mp3ToM4a_ffmpeg()


if __name__ == "__main__":
    filesList = directoryUtils.findFiles(getPath(), ".mp3")
