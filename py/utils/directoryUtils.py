#pytest -s directoryUtils.py -k test_printFiles

import sys
print(*sys.path, sep = "\nPYTHONPATH:- ")

import os
#import pytest

def execCmd(cmd):
    import subprocess
    # subprocess.call(cmd.split(" "))
    p = subprocess.Popen(cmd.split(" "), stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    out, err = p.communicate()
    print(err)

def toRecurse():
    recurse = input('Include Subdirectories Y/N : ')
    choice = {"Y" : True, "y" : True}
    return choice.__contains__(recurse)

def dumpCmdToScript(cmdList):
    script = "../../ffmpeg" + ".sh" if "linux" in sys.platform else ".bat"
    fo = open(script, "w")
    for c in cmdList: fo.write("%s\n" % c)
    print("Run this in shell: " + "../../ffmpeg.sh")

def removeFile(f): os.remove(f)
    
def removeFilesFromDir(dirPath):
    ext = input('File extension(s) (space seperated) to remove or press enter if you want to remove all files. : ')
    exts = ext.split(" ")
    fileList = recursivelyFindFiles([], dirPath, exts, toRecurse())
    
    map(lambda f:removeFile(f), fileList)
        
def recursivelyFindFiles(fileList, dirPath, exts, toRecurse=False):
    dirlist = os.listdir(dirPath)
    for f in dirlist:
        inputFilePath = dirPath+f
        if toRecurse and os.path.isdir(inputFilePath):
            recursivelyFindFiles(fileList, inputFilePath+"/", exts, toRecurse)
        elif os.path.isfile(inputFilePath):
            fName, fExt = os.path.splitext(inputFilePath) # fExt is like .wav, .mp3; hence removing .
            if fExt[1:] in exts or '' in exts:
                fileList.append(inputFilePath)
    return fileList

def findFiles(dirPath, ext=""):
    return recursivelyFindFiles([], dirPath, ext.split(" "), True)

def printList(fileList):
    print('\n --> '.join(fileList))
        
def test_findFiles():
    print("test_printList")
    files = findFiles("/mnt/d/mani/video/", "mkv")
    printList(files)

def test_printMe():
    print("test_printMe")

if __name__ == "__main__":
    dirPath = input('Please provide directory path - must end with / : ')
    '''
    print (" 1) To List all files in a directory or any specifc files in a directory \n 2) Remove files from directory/subdirectory")
    choice = input("Choose your option: ")
    if choice == 1:
        printList(findFiles())
    elif choice == 2:
        removeFilesFromDir(dirPath)
    '''
