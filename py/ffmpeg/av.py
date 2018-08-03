#pytest -s av.py -k test_ffmpeg_incrementVolume

import sys
sys.path.insert(0, 'utils')
print(*sys.path, sep = "\nPYTHONPATH:- ")

from utils import directoryUtils

def getPath():
    if "linux" in sys.platform: return "/mnt/d/mani/video/compressed/"
    else: return "D:/mani/video/compressed/"

def getFFmpeg():
    if "linux" in sys.platform: return "ffmpeg"
    else: return "D:/mani/dev/opt/ffmpeg-20160614-git-cb46b78-win32-static/bin/ffmpeg.exe"

def replaceFileExt(inputFile, tExt):
    import re
    name, ext = re.split("\\.(?=[^\\.]+$)", inputFile)
    outputFile = name+tExt
    #print(outputFile, inputFile)
    return outputFile

def ffmpeg_toM4a_libfdk_aac(inFile, outFile):
    return getFFmpeg() + " -i %s -c:a libfdk_aac -profile:a aac_he_v2 -b:a 48k %s" % (inFile, outFile)

def ffmpeg_toM4aWith_builtInAAC(inFile, outFile):
    return getFFmpeg() + " -i %s -c:a aac -b:a 48k %s" % (inFile, outFile)

def ffmpeg_toWav(inFile, outFile):
    return getFFmpeg() + " -i %s %s" % (inFile, outFile)

def ffmpeg_incVolume(inFile, outFile):
    return getFFmpeg() + " -i %s -map 0 -c copy -c:a aac -af \"volume=7dB\" %s" % (inFile, outFile)

def ackermann(m, n):
    #I assume that you check that n and m are non-negative before you run this
      if m == 0:
           return n + 1
      elif n == 0:
          return ackermann(m - 1, 1)
      else:
          return ackermann(m - 1, ackermann(m, n - 1))

def test_ffmpeg_toM4a_libfdk_aac():
    filesList = directoryUtils.findFiles(getPath(), "mp3")
    fileMap = {f:replaceFileExt(f, "m4a") for f in filesList}
    # for f in filesList:
    #     fileMap[f] = getOutputFileName(f, "mp3", "m4a") # assigning value to a key

    cmdList = [ffmpeg_toM4a_libfdk_aac(f1, f2) for f1, f2 in fileMap.items()]
    directoryUtils.printList(cmdList)
    map(lambda cmd:directoryUtils.execCmd(cmd), cmdList)

def test_ffmpeg_incrementVolume():
    filesList = directoryUtils.findFiles(getPath(), "")
    fileMap = {f:replaceFileExt(f, "mkv") for f in filesList}
    cmdList = [ffmpeg_incVolume(f1, f2) for f1, f2 in fileMap.items()]
    directoryUtils.printList(cmdList)
    map(lambda cmd:directoryUtils.execCmd(cmd), cmdList)

if __name__ == "__main__":
    filesList = directoryUtils.findFiles(getPath(), "mp3")
    