#!/usr/bin/env python
import serial
import rospy
import rospkg
import rosnode
import math
import time

from mrs_msgs.msg import UavManagerDiagnostics as UavManagerDiagnostics
from mrs_msgs.srv import String as mrsString
from mavros_msgs.srv import CommandBool as CommandBool
from std_srvs.srv import Trigger as Trigger
from std_srvs.srv import SetBool as SetBool
from gazebo_msgs.srv import DeleteModel, DeleteModelRequest, ApplyBodyWrench
from geometry_msgs.msg import Point, Wrench, Vector3

from nav_msgs.msg import Odometry
from std_msgs.msg import Header, Float64, Int8,String,Bool
from gazebo_msgs.srv import GetLinkState, GetLinkStateRequest


class motorFailureSerial:
    def __init__(self):
        # Initialize node
        rospy.init_node('motor_failure_serial')

        #critical things
        self.reaction_times = []
        self.perception_times = []

        # Create publisher
        #self.percept_pub=rospy.Publisher ('/failure_uav1', Int8, queue_size=1)
        self.motor1 = rospy.ServiceProxy('/uav1/control_manager/motors', SetBool)
        self.tracker = rospy.ServiceProxy('/uav1/control_manager/switch_tracker', mrsString)
        self.arm1 = rospy.ServiceProxy('/uav1/mavros/cmd/arming', CommandBool)
        self.ctd = 0
        self.isFinished = False
        self.file = open("ariacReactionTimes.log", "w")
        self.startTime = time.time()

        # Create subscriber
        #rospy.Subscriber('/agent_detected_failure_uav1', String, self.reaction)
        #rospy.Subscriber('finish', Bool, self.callback2)
        
    def run(self):
        count = 1;
        serW = serial.Serial()  #9600
        serW.baudrate = 9600
        serW.port = '/dev/pts/18'
        #serR = serial.Serial('/dev/pts/9', 9600, rtscts=True,dsrdtr=True)
        s1 = '1'
        s1 = s1.encode()
        while(count<=20):
            s=0
            serW.open()
            serW.write("fail1".encode())
            self.motor1(0)
            serW.close()
            self.perception_times.append(time.perf_counter())
            ser = serial.Serial('/dev/pts/20', 9600, rtscts=True,dsrdtr=True)
            while (s!=s1):
                s = ser.read(1)
            self.reaction_times.append(time.perf_counter()) 
            self.motor1(1)
            self.arm1(1)
            self.tracker('MpcTracker')
            time.sleep(10)
            count=count+1
        self.recordTimes()
        rospy.signal_shutdown('Node is shutting down.')

    def recordTimes(self):
        f = open("ariacReactionTimes.log", "w")
        if len(self.perception_times) == len(self.reaction_times):
            for pTime, rTime in zip(self.perception_times, self.reaction_times):
                elapsed_time = (rTime - pTime) * 1000 
                f.write(f"{pTime}\t{rTime}\t{elapsed_time}\n")
        else:
            pIter = iter(self.perception_times)
            next(pIter)
            for rTime in self.reaction_times:
                pTime = next(pIter)
                elapsed_time = (rTime - pTime) * 1000 
                f.write(f"{pTime}, {rTime}, {elapsed_time}\n")
        total_time = time.time()-self.startTime        
        if not f.closed:
            f.write(f"Sent msgs: {len(self.perception_times)}\n")
            f.write(f"Received msgs: {len(self.reaction_times)}\n")
            f.write(f"Elapsed_time(s): {total_time}\n")
            f.close()

if __name__ == '__main__':
    node = motorFailureSerial()
    time.sleep(1)
    node.run()
    
        
