# TrashTalk_Dapp powered by NEM

TrashTalk are smart, solar-powered, sensor-equipped waste &amp; recycling stations that communicate real-time status to on-demand collection crews.

The Raspberry Pi runs the program of the Object Detector file to see if the objects matches with the Training Data to see if it is recyclable or not. So when object is matched and confirmed as recycable it then confirms to firebase with real-time data. Right after this confirmation, NEM blockchain api will send specific amount of NEM for that recyclable object to a NEM test address. Ta-Da! 

## Getting Started

These instructions will get a copy of the project up and running on local machine for development and testing purposes. 

### Project Components
The project consists of the following software and hardware components:-

* NEM Test Account - a test Address to prove the proof of concept. 
* Android Mobile - In order to monitor this hardware via a mobile app, we will need an Android mobile phone. 
* Arduino 101 and Grove Base - Connected with UltraSonic Distance Sensor
* TrashTalk PiCam: This is the complete hardware setup of a Raspberry Pi connected with a USB logitech camera. 
* Android Mobile: In order to monitor this virtual hardware via a mobile app, we will need an Android mobile phone.
* Firebase Integration in the Raspberry Pi (Optional): Connect Firebase services from Raspberry Pi to Android Json File when a recycable match is proven.


###Prerequisites

What things you need to install the software in the Raspberry Pi and how to install them

```
Python 2.7
```

```
OpenCV 3.2
```

### Installing

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Contributing

TBD

## Versions

We use [OpenCV 3.2](https://www.pyimagesearch.com/2017/09/04/raspbian-stretch-install-opencv-3-python-on-your-raspberry-pi/) that includes the extra modules not orginally included from previous versionings.

We use [Python 2.7](https://www.pyimagesearch.com/2017/09/04/raspbian-stretch-install-opencv-3-python-on-your-raspberry-pi/) using Noobs Rasberian Stretch. 


## Authors

* **America Lopez** 
* **Penelope Lopez** 
* **Victor Godales** 
* **Justin Zemlyansky**


## License

This project is licensed under the MIT License 

## Acknowledgments

* Inspiration - Siraj Raval and Codacus
* How to do Object Detection with OpenCV - Siraj Raval 
* OpenCV RealTime Object Recognition in any background -Codacus
