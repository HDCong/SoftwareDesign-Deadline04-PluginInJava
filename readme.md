# Software Design

## Deadline 3: Use user-written plugins by 2 ways

1. Create MyPlugin abstract class have 2 methods

2. Build jar file. And add this file to project which use this class.

3. Create Plugin External project to define External Class. Create Using Plugin project to use plugin in 2 ways.

4. Build Plugin External. Copy file .class after build to project Using Plugin

5. To load class external at runtime. Define a ClassLoader , then get all file .class in folder contain (In this case, the class is not belong any package)

## References: 

https://viblo.asia/p/load-class-at-runtime-gGJ5928GKX2

https://stackoverflow.com/questions/32222151/how-to-load-all-compiled-class-from-a-folder

**IDE : InteliJ IDEA**
