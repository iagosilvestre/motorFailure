# motorFailure

## Description

An environment for simulating a motor failure in an UAV using the MRS UAV system,

## Getting Started

### Dependencies

* Ubuntu 20.04
* Gazebo 11.10
* ROS Noetic
* MRS System 
* NOTE: MRS documentation https://ctu-mrs.github.io/

### Installing

* Since this package gives you just the environment, you can use it however you like, as long as you already have Ubuntu, ROS Noetic and Gazebo 11.10. 
  We strongly recommend that you use MRS System, which installs everything you need.
* Installing MRS:
* At the link https://github.com/ctu-mrs/mrs_uav_system , go to the Installation section and choose one of the installation options,
  any of them will work, we recommend that you use the local option, that you just run the script and everything is automatically downloaded.
* NOTE: Please only install MRS on a newly installed Ubuntu, it will be an easy way
* With all that done, simply clone this package into a Catkin workspace (if you don't know what that is, http://wiki.ros.org/catkin/Tutorials/create_a_workspace)
  and build. (If you have MRS, we recommend that you clone into the "mrs_workspace" folder that is in "home")

### Executing simulation



*Go to the folder start and run start.sh, using in the terminal
```
./start.sh
```

*Go to the folder uav_agents and run gradlew, using in the terminal

```
./gradlew
```

*Go to the folder scripts and run motorFailure.py, using in the terminal
```
roslaunch sarc_environment motorFailure.py
```
