# pytest -s av.py -k test_ffmpeg_incrementVolume

import sys
# sys.path.insert(0, 'utils')
# print(*sys.path, sep = "\nPYTHONPATH:- ")

import pytest

from utils import directoryUtils
from utils.utils import execCmd, isLinux, replaceFileExt, dealWithSpacesInFilePathNames


def getPath():
    if isLinux():
        return "/d/mani/video/"
    else:
        return "D:/mani/video/"


def getFFmpeg():
    if isLinux():
        return "ffmpeg"
    else:
        return "C:/mani/dev/opt/ffmpeg-20190219-ff03418-win64-static/bin/ffmpeg.exe"


def ffmpeg_toM4a_libfdk_aac(inFile, outFile):
    return getFFmpeg() + " -i %s -c:a libfdk_aac -profile:a aac_he_v2 -b:a 48k %s" % (dealWithSpacesInFilePathNames(inFile), dealWithSpacesInFilePathNames(outFile))


def ffmpeg_toM4aWith_builtInAAC(inFile, outFile):
    return getFFmpeg() + " -i %s -c:a aac -b:a 48k %s" % (dealWithSpacesInFilePathNames(inFile), dealWithSpacesInFilePathNames(outFile))


def ffmpeg_toWav(inFile, outFile):
    return getFFmpeg() + " -i %s %s" % (dealWithSpacesInFilePathNames(inFile), dealWithSpacesInFilePathNames(outFile))


def ffmpeg_incVolume(inFile, outFile, db):
    return getFFmpeg() + " -i %s -map 0 -c copy -c:a aac -af \"volume=%sdB\" %s" % (dealWithSpacesInFilePathNames(inFile), db, dealWithSpacesInFilePathNames(outFile))


def ffmpeg_encode(inFile, outFile, encoder, crf, resolution):
    return getFFmpeg() + \
           " -i %s -vf scale=%s -map 0 -c copy -c:v %s -preset medium -crf %s -c:a aac -strict experimental -b:a 96k %s" \
           % (dealWithSpacesInFilePathNames(inFile), resolution, encoder, crf, dealWithSpacesInFilePathNames(outFile))


def ffmpeg_concat(inFile, outFile):
    return getFFmpeg() + " -f concat -i %s -c copy %s" % (inFile, outFile)

def ffmpeg_import(inFile, outFile, srtFile):
    inFile = dealWithSpacesInFilePathNames(inFile)
    outFile = dealWithSpacesInFilePathNames(outFile)
    options = {1: getFFmpeg() + " -i %s -sub_charenc UTF-8 -i %s -map 0:v -map 0:a -c copy -map 1 -c:s:0 srt -metadata:s:s:0 language=en %s",
               2: getFFmpeg() + " -i %s -c:a aac -vf subtitles=%s %s"}
    if inFile == srtFile or srtFile == "":
        cmd = options[2] % (inFile, inFile, outFile)
    else:
        cmd = options[1] % (inFile, dealWithSpacesInFilePathNames(srtFile), outFile)
    return cmd


def ffmpeg_cut(inFile, outFile, startTime, endTime):
    import datetime, time, re
    sh, sm, ss = re.split(":", startTime)
    sSeconds = int(datetime.timedelta(hours=int(sh), minutes=int(sm), seconds=int(ss)).total_seconds())
    eh, em, es = re.split(":", endTime)
    eSeconds = int(datetime.timedelta(hours=int(eh), minutes=int(em), seconds=int(es)).total_seconds())
    duration = time.strftime('%H:%M:%S', time.gmtime(eSeconds - sSeconds))
    return getFFmpeg() + " -ss %s -i %s -ss 00:00:01 -t %s -c copy %s" % (startTime, inFile, duration, outFile)


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


def encode_ffmpeg():
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


def cut_ffmpeg():
    inFile = input("Enter file <" + getPath() + "compressed/" + "> : ")
    outFile, ext = inFile.rsplit('.', 1)
    sTime = input("Enter start time: ")
    eTime = input("Enter end time: ")
    cmd = ffmpeg_cut(inFile, outFile + "_cut." + ext, sTime, eTime);
    directoryUtils.printList([cmd])
    directoryUtils.dumpCmdToScript([cmd], "../../")


def concat_ffmpeg():
    inFile = input("Enter txt file <" + getPath() + "compressed/" + "> : ")
    outFile = input("Enter ouput file <" + getPath() + "compressed/" + "> : ")
    cmd = ffmpeg_concat(inFile, outFile);
    directoryUtils.printList([cmd])
    directoryUtils.dumpCmdToScript([cmd], "../../")


def concat_ffmpeg_2():
    """ffmpeg concat used here doesn't take absolute path in the file so run in the same directory.
    also it removes the generated file.txt i.e. it prints the command to run but run the command as well
    Note: execCmd doesn't like cmds array in ".
    """
    temp_file = "file.txt"
    inFiles = input("Enter space seperated input files name only! <" + getPath() + "compressed/" + "> : ").split(" ")
    inFiles = ["file \'" + e + "\'" for e in inFiles]
    directoryUtils.writeToFile(inFiles, temp_file, "w")
    outFile = input("Enter ouput file <" + getPath() + "compressed/" + "> : ")
    cmd = ffmpeg_concat(temp_file, outFile)
    directoryUtils.printList([cmd])
    execCmd(cmd)
    directoryUtils.removeFile(temp_file)


def import_ffmpeg():
    inFile = input("Enter file <" + getPath() + "compressed/" + "> : ")
    srtFile = input("Provide srt file or leave blank if same as input file: ")
    outFile = replaceFileExt(inFile, "_en.mkv")
    cmd = ffmpeg_import(inFile, outFile, srtFile)
    directoryUtils.printList([cmd])
    directoryUtils.dumpCmdToScript([cmd], "../../")


def test_mp3TpM4a():
    mp3ToM4a_ffmpeg()


if __name__ == "__main__":
    filesList = directoryUtils.findFiles(getPath(), ".mp3")
