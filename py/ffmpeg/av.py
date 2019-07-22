# pytest -s av.py -k test_ffmpeg_incrementVolume

import sys
# sys.path.insert(0, 'utils')
# print(*sys.path, sep = "\nPYTHONPATH:- ")

import pytest

from utils import directoryUtils
from utils.utils import execCmd, execCmd_2, isLinux, replaceFileExt, dealWithSpacesInFilePathNames


def getFFmpeg(): return "ffmpeg" if isLinux() else "C:/mani/dev/opt/ffmpeg-20190219-ff03418-win64-static/bin/ffmpeg.exe"


def toM4aWithLibfdkAAC(inFile, outFile):
    return getFFmpeg() + " -i {} -c:a libfdk_aac -profile:a aac_he_v2 -b:a 48k {}".format(dealWithSpacesInFilePathNames(inFile), dealWithSpacesInFilePathNames(outFile))


def toM4aWithBuildInAACFfmpeg(inFile, outFile):
    return getFFmpeg() + " -i {} -c:a aac -b:a 48k {}".format(dealWithSpacesInFilePathNames(inFile), dealWithSpacesInFilePathNames(outFile))


def toWavFfmpeg(inFile, outFile):
    return getFFmpeg() + " -i {} {}".format(dealWithSpacesInFilePathNames(inFile), dealWithSpacesInFilePathNames(outFile))


def incVolumeFfmpeg(inFile, outFile, db):
    return getFFmpeg() + " -i {} -map 0 -c copy -c:a aac -af \"volume={}dB\" {}".format(dealWithSpacesInFilePathNames(inFile), db, dealWithSpacesInFilePathNames(outFile))


def encodeFfmpeg(inFile, outFile, encoder, crf, resolution):
    return getFFmpeg() + \
           " -i {} -vf scale={} -map 0 -c copy -c:v {} -preset medium -crf {} -c:a aac -strict experimental -b:a 96k {}" \
          .format(dealWithSpacesInFilePathNames(inFile), resolution, encoder, crf, dealWithSpacesInFilePathNames(outFile))


def concatFfmpeg(inFile, outFile):
    return getFFmpeg() + " -f concat -i {} -c copy {}".format(inFile, outFile)


def importFfmpeg(inFile, outFile, srtFile):
    inFile = dealWithSpacesInFilePathNames(inFile)
    outFile = dealWithSpacesInFilePathNames(outFile)
    options = {1: getFFmpeg() + " -i {} -sub_charenc UTF-8 -i {} -map 0:v -map 0:a -c copy -map 1 -c:s:0 srt -metadata:s:s:0 language=en {}",
               2: getFFmpeg() + " -i {} -c:a aac -vf subtitles={} {}"}
    if inFile == srtFile or srtFile == "":
        cmd = options[2].format(inFile, inFile, outFile)
    else:
        cmd = options[1].format(inFile, dealWithSpacesInFilePathNames(srtFile), outFile)
    return cmd


def cutFfmpeg(inFile, outFile, startTime, endTime):
    import datetime, time, re
    sh, sm, ss = re.split(":", startTime)
    sSeconds = int(datetime.timedelta(hours=int(sh), minutes=int(sm), seconds=int(ss)).total_seconds())
    eh, em, es = re.split(":", endTime)
    eSeconds = int(datetime.timedelta(hours=int(eh), minutes=int(em), seconds=int(es)).total_seconds())
    duration = time.strftime('%H:%M:%S', time.gmtime(eSeconds - sSeconds))
    return getFFmpeg() + " -ss {} -i {} -ss 00:00:01 -t {} -c copy {}".format(startTime, inFile, duration, outFile)


def ffmpeg_mp3ToM4a_libfdk_aac():
    fileOrFolder = input("Enter file or folder path: ")
    filesList = directoryUtils.findFiles(fileOrFolder, "mp3") if directoryUtils.isDir(fileOrFolder) else [fileOrFolder]
    fileMap = {f: replaceFileExt(f, ".m4a") for f in filesList}
    # for f in filesList: fileMap[f] = getOutputFileName(f, "mp3", "m4a") # assigning value to a key

    cmdList = [toM4aWithLibfdkAAC(f1, f2) for f1, f2 in fileMap.items()]
    directoryUtils.printList(cmdList)
    # map(lambda cmd:directoryUtils.execCmd(cmd), cmdList)
    [execCmd_2(cmd) for cmd in cmdList]


def ffmpeg_mp3ToM4a():
    fileOrFolder = input("Enter file or folder path: ")
    filesList = directoryUtils.findFiles(fileOrFolder, "mp3") if directoryUtils.isDir(fileOrFolder) else [fileOrFolder]
    cmdList = []
    for f in filesList:
        wavFile = replaceFileExt(f, ".wav")
        m4aFile = replaceFileExt(f, ".m4a")
        cmdList.append(toWavFfmpeg(f, wavFile))
        cmdList.append(toM4aWithBuildInAACFfmpeg(wavFile, m4aFile))
    directoryUtils.printList(cmdList)
    print([execCmd(cmd) for cmd in cmdList])
    [directoryUtils.removeFile(replaceFileExt(f, ".wav")) for f in filesList]


def ffmpeg_incVolume():
    fileOrFolder = input("Enter file or folder path: ")
    if directoryUtils.isDir(fileOrFolder):
        filesList = directoryUtils.findFiles(fileOrFolder, "")
    else:
        filesList = [fileOrFolder]
    db = input("Provide db: ")
    fileMap = {f: replaceFileExt(f, "_f_v_" + db + ".mkv") for f in filesList}
    cmdList = [incVolumeFfmpeg(f1, f2, db) for f1, f2 in fileMap.items()]
    directoryUtils.printList(cmdList)
    # map(lambda cmd:directoryUtils.execCmd(cmd), cmdList)
    directoryUtils.dumpCmdToScript(cmdList, "./")


def ffmpeg_encode():
    fileOrFolder = input("Enter file or folder path: ")
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
    cmdList = [encodeFfmpeg(f1, f2, encoder, crf, resolution) for f1, f2 in fileMap.items()]
    directoryUtils.printList(cmdList)
    # map(lambda cmd:directoryUtils.execCmd(cmd), cmdList)
    directoryUtils.dumpCmdToScript(cmdList, "./")


def ffmpeg_cut():
    inFile = input("Enter file: ")
    outFile, ext = inFile.rsplit('.', 1)
    sTime = input("Enter start time: ")
    eTime = input("Enter end time: ")
    cmd = cutFfmpeg(inFile, outFile + "_cut." + ext, sTime, eTime);
    directoryUtils.printList([cmd])
    directoryUtils.dumpCmdToScript([cmd], "./")


def ffmpeg_concat():
    inFile = input("Enter txt file: ")
    outFile = input("Enter ouput file: ")
    cmd = concatFfmpeg(inFile, outFile);
    directoryUtils.printList([cmd])
    directoryUtils.dumpCmdToScript([cmd], "./")


def ffmpeg_concat2():
    """ffmpeg concat used here doesn't take absolute path in the file so run in the same directory.
    also it removes the generated file.txt i.e. it prints the command to run but run the command as well
    Note: execCmd doesn't like cmds array in ".
    """
    temp_file = "file.txt"
    inFiles = input("Enter space seperated input files name only!: ").split(" ")
    inFiles = ["file \'" + e + "\'" for e in inFiles]
    directoryUtils.writeToFile(inFiles, temp_file, "w")
    outFile = input("Enter ouput file: ")
    cmd = concatFfmpeg(temp_file, outFile)
    directoryUtils.printList([cmd])
    execCmd(cmd)
    directoryUtils.removeFile(temp_file)


def ffmpeg_import():
    inFile = input("Enter file: ")
    srtFile = input("Provide srt file or leave blank if same as input file: ")
    if srtFile == "" and directoryUtils.isFile(replaceFileExt(inFile, ".srt")):
        srtFile = replaceFileExt(inFile, ".srt")
    outFile = replaceFileExt(inFile, "_en.mkv")
    cmd = importFfmpeg(inFile, outFile, srtFile)
    directoryUtils.printList([cmd])
    directoryUtils.dumpCmdToScript([cmd], "./")


def test_mp3TpM4a():
    ffmpeg_mp3ToM4a()


def listAllUsefullFunctions():
    import inspect, sys
    all_functions = inspect.getmembers(sys.modules[__name__], inspect.isfunction)
    # print(all_functions)
    all_functions = [e for e in all_functions if e[0].find("ffmpeg_") >= 0 or e[0].find("handbrake_") >= 0]
    # print(all_functions)
    funcMap = {all_functions.index(e): e for e in all_functions}
    return funcMap


if __name__ == "__main__":
    filesList = directoryUtils.findFiles("./", ".mp3")