
# 3d Engine

## Description

This is a 3d engine made completly in java without any other librarys. There could still be some bugs related to a face being on screen but non of the vertices being on screen beacuse the engine will not draw the face if all the points are off the screen to fix a issue of faces streching across the screen. There could also be some bugs when you are close to an object but I have tried to fix as many as possible.

### Notes

- Will not work if you dont give it permision to controll the mouse beacuse it cant lock the mouse
- The .jar might break on mac beacuse of permissions

## Code

### Classes

|Class         |Description                                                                                                                     |
|:------------:|:------------------------------------------------------------------------------------------------------------------------------:|
|Main          |Automatic class from my "Template Java App" app.                                                                                |
|App Manager   |Automatic class from my "Template Java App" app.                                                                                |
|Window Manager|Automatic class from my "Template Java App" app.                                                                                |
|App Window    |Automatic class from my "Template Java App" app.                                                                                |
|Window Object |Automatic class from my "Template Java App" app.                                                                                |
|Engine        |The main engine for the 3d engine. The "hub" for all the other classes.                                                         |
|Camera        |Stores all the data related for the camera such as a position, rotation, etc. Also includes the math for perspective projection.|
|Scene         |Stores all the objects in a scene. Also includes code to draw the scene.                                                        |
|Scene Object  |A object in a scene that includes position, rotation, size, model, color, etc.                                                  |
|Vertex        |A data type that includes x y z.                                                                                                |
|Face          |A data type for data of a face of an object.                                                                                    |
|Models        |Some basic 3d models that I created.                                                                                            |
