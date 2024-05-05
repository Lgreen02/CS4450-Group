# CS 4450 - Computer Graphics
Prof. Tony Diaz

## Project Outline:
Your program will therefore create an original scene in Minecraft fashion. It is simple but can be used to
demonstrate different aspects of graphics like texture mapping, lighting, visible surface detection, surface
rendering etc. We will be unable to cover some of the more advanced concepts in this course but learning to
implement them using OpenGL through our LWJGLibrary is very simple. Hence, this project forces you to
explore features of the LWJGL without really worrying much about the underlying theoretical principles,
especially if you are keen to learn and do a good job in this project.

## Project Tasks:

### Checkpoint 1:
As always the Java style sheet should be followed with code separated into separate classes as needed.
You should have a window created that is 640x480 and centered on the screen. Your program should be
able to display a cube (which is at least 2 in width) in 3D space with each face colored differently. You
should be able to manipulate the camera with the mouse to give a first person appearance and be able to
navigate the environment using the input.Keyboard class with either the w,a,s,d keys or the arrow keys
to move around as well as the space bar to move up and the left shift button to go down. Finally, your
program should also use the escape key quit your application.

### Checkpoint 2:
Your program should still be able to do all from the above check point. In addition to the above
requirements your program should now be able to draw multiple cubes using our chunks method
(creating a world at least 30 cubes x 30 cubes large), with each cube textured and then randomly placed
using the simplex noise classes provided (Your terrain should be randomly placed each time you run the
program but still appear to smoothly rise and fall as opposed to sudden mountains and valleys
appearing). Finally, your program should have a minimum of 6 cube types defined with a different
texture for each one as follows: Grass, sand, water, dirt, stone, and bedrock.

### Checkpoint 3:
Your program should still be able to do all from the above checkpoints. In addition to the above
requirements your program should now be able to correctly place only grass, sand, or water at the
topmost level of terrain, dirt, or stone at levels below the top, and bedrock at the very bottom of the
generated terrain. A light source should be created that will leave half the world brightly lit and the other
half dimly illuminated.

## Project Members:
Gerardo Solis \
Carson Green \
Nick Hortua
