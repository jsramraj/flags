[![](https://jitpack.io/v/jsramraj/flags.svg)](https://jitpack.io/#jsramraj/flags)

*Android counter part of the [RSFlags](https://github.com/jsramraj/RSFlags) iOS library*

# Lightweight android library for getting flag image for any country
## This entire library is only **175 kb**. Can you believe that?
Simple, no nonsense, lightweight library for getting flag icon for any country from the two character country code


# How this differs from other libraries?
There are plenty of open source libraries available for getting the flag icon. 
But they all include separate image or vector files for each country (there are more than 250 flags in the world) hence become **bulky**.

Unlike all the other libraries, this one includes only **ONE** 
[sprite image](https://github.com/jsramraj/flags/raw/master/flags/src/main/res/drawable/all_flags.png)
which consists of all the icons of
all the flags. And the icons are sliced from the sprite image when requested.

### Demo Screenshot
<img src="./demo/flags.png" width="40%">

## Usage
```java
BitmapDrawable usFlag = Flags.forCountry("US")
```
That is all

## How to include
### As a dependency
1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
2. Add the dependency
```groovy
dependencies {
  implementation 'com.github.jsramraj:flags:$version'
}
```
Just replace the *$version* with the appropriate version number, for e.g **v1.0**

### Direct include
If, for some reason, you are not okay with adding a entire library, you can still use this by just adding two files into your project.

* Give this library a star
* Drag the [sprite image](https://github.com/jsramraj/flags/raw/master/flags/src/main/res/drawable/all_flags.png) that consists of all the flag icons into your project's asset folder
* Add the [Flags class](https://github.com/jsramraj/flags/blob/master/flags/src/main/java/com/jsramraj/flags/Flags.java) to your project

That is all!!! you are good to go. 

## License
```
MIT License

Copyright (c) 2020 Ramaraj Thangapandi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
