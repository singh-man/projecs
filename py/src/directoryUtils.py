import os

def toRecurse():
    recurse = raw_input('Include Subdirectories Y/N : ')
    if recurse == "Y" or recurse == "y":
        return True
    return False
    
def removeFile(f):
    os.remove(f)
    
def removeFilesFromDir(dirPath):
    ext = raw_input('File extension(s) (space seperated) to remove or press enter if you want to remove all files. : ')
    exts = ext.split(" ")
    fileList = recursivelyFindFiles([], dirPath, exts, toRecurse())
        
    for f in fileList:
        removeFile(f)
        
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

def findFiles():
    ext = raw_input('File extension(s) (space seperated) to find or hit enter for all files : ')
    exts = ext.split(" ")
    return recursivelyFindFiles([], dirPath, exts, toRecurse())

def printList(fileList):
    for f in fileList:
        print f
        
if __name__ == "__main__":
    dirPath = raw_input('Please provide directory path - must end with / : ')
    print " 1) To List all files in a directory or any specifc files in a directory \n 2) Remove files from directory/subdirectory"
    choice = input("Choose your option: ")
    if choice == 1:
        printList(findFiles())
    elif choice == 2:
        removeFilesFromDir(dirPath)
