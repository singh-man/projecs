'''
Created on Dec 15, 2012

@author: emmhssh
'''

type('Hello, World!')

def countDown(n):
    if n > 0:
        print n
        countDown(n-1)
    if n <= 0:
        print 'BlastOff'
    
countDown(6)

class A:
    
    def __init__(self):
        self.firstName = 'man'
    def getName(self):
        return self.firstName
    
a = A()
print a.firstName
print a.getName(),
vars(a)
a.__dict__

fin = open("/home/emmhssh/dev/src/example/z_py/src/First.py")
for line in fin:
    print line.strip()


