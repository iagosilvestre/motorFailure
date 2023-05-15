#!/usr/bin/env python
import serial
import math
import time

if __name__ == '__main__':
    count = 1;
    serW = serial.Serial()  #9600
    serW.baudrate = 9600
    serW.port = '/dev/pts/2'
    serR = serial.Serial()
    serR.baudrate = 9600
    serR.port = '/dev/pts/3'
    reaction_times = []
    perception_times = []
    while(count<10):
        serW.open()
        print(serW.name)         # check which port was really used
        serW.write("1".encode())
        serW.close()
        perception_times.append(time.perf_counter())
        serR.open()
        s = serR.read(10)
        while (s!='1'):
            s = serR.read(10)
        serR.close()
        reaction_times.append(time.perf_counter())
        time.sleep(10)
    
        
