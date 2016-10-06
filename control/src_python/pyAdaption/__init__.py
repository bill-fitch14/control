from inspect import getargspec
# import java
# from java.lang import Thread, InterruptedException  
from telnetlib import theNULL
import time

from Adaption import EngineAdaption
# import jmri
from myscene import listenerObjects
from mytrack import U4_Constants
import numpy
from org.apache.log4j import Logger
from sm2 import Main





class pyAdaption(EngineAdaption):
  
    filename = 'srimMatrix'
    adaptors = None
    #   
    def __init__(self, adaptors):
        self.noBoxes = 10
        self.adaptors = adaptors
        EngineAdaption(self.noBoxes)
      
    def processAdaption(self, millis , distancetravelled, enginesetting, direction):
        self.adaptors1 = EngineAdaption.processEngineMeasurement(millis , distancetravelled, enginesetting, direction)
        if self.adaptors1 != None:
            print ("saving adaptors" , self.adaptors1)
            self.adaptors = self.adaptors1
            
        else:
            print "calculated adaptors = None"
        self.saveGlobalSrim()
      
    def saveGlobalSrim(self):
        self.array = self.getGlobalSrim()
        numpy.save(self.filename, self.array)
                
    def getGlobalSrim(self):
        srim = numpy.load(self.filename)
        return srim
      
    def getAdaptors(self):
        return self.adaptors
