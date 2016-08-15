# SLogo Design:

> Austin Liu (abl17)

> Emanuele Macchi (em186)


> Steven Mazzari (sam98)


> Michael Ogez (mjo14)

# Introduction

**Problem:** 

Interpret logo commands and display them on the screen.

**Design goals:** 

We want to be able to easily add new commands, add more turtles/environment elements and create functions/features without changing the original design. We also want to be able to dynamically change the contents of the model and have the view update without making major changes to the contents of the view. Having modularity between the model, the view, and the controller is the primary design principle that we want to follow.

**Architecture:** 

MVC. View is front end, M is back end. The interface between the view and the model should be closed. The command interface will be open to extension,  but the command factory should be closed to modification. 

**Program at a High Level:**

We want to be able to take in an input from the user in multiple media (command, drop-down menus, text files) and be able to parse the input into a formatted data structure(s). Using the parsed data, we will be able to modify the model based on specifications from the user proxied through the controller. Based on updates to the model, the view will display information specified by the model. In terms of design, we are going for a read-evaluation-print loop, where the controller reads in an input, evaluates it into a data structure/passes to model, and prints the output to the view.

# Overview

Map of design: The layout of our design consists of 3 main parts: a view, a controller, and a model.  The view is handled by the front end and the controller and model fall under the back end.  The view passes user input to the controller for parsing, and implements an observer to look for changes to the model.  Once the command has been properly parsed, it will be passed to a command factory which will call the correct action, eg the move method in the turtle class.  Once the action has been completed, one or more of the objects in the backend will have been updated with new information which will then be visible in the model, which the observer can pass to the view.  

Four APIs: 
​
## API 1: Front End Internal API ##
	
## View ##

**-void createTopMenu()**
// Creates a TopMenu

**-void createCommandsHistory()**
// Creates a CommandsHistory

**-void createEnvironmentMenu()**
// Creates an EnvironmentMenu

**-void updateResourceFile()**
// Observes the TopMenu class to see if the user selects any resource file. If he does, it passes it to the model. 

**-void updateEnvironmentMenu()**
//Observes the Model to see if it has to update any environmental variables 


## API 2: Front End External API ##

## View ##

**-void update(Observable o, Object arg)**
// When observable object notifies, it updates according to the different object

**-void clearWindow()**
// Removes all the elements from the turtle window 

**-void drawTurtle(int x, int y)**
// Draws a turtle on screen at coordinates (x,y)

**-void drawBackground()**
// Draws a background

**-void updateCommandHistory()**
// Updates the current command history 

## Window ##

**-void drawBackground()**
// method to draw the background

**-void drawLines()**
// if a turtle’s boolean draw value is true, follow movements with line

## Console ##

> This class will be responsible to display the console view 

**-void displayText(Text text)**
// show text in console

**-void displayBackground(Background backGround)**
// show background of console

## SideMenu ##

> This class will be responsible for displaying the side menu, which will be able to contain a variety of information (such as variables, functions,..)

**-void displayCommandHistory()**
// Show command history on window

**-void updateCommandHistory(Command newCommand)**
// Add recent command(s) to history displayed on window

**-voidAddVariables(Variable variables)**
// give user the option to add variable

**-voidAddFunctions(Function functions)**
// give user option to define new function

**-void displayVariables()**
// Show variables already defined on window

**-void displayFunctions()**
// give names of functions defined in window

**-void displayCurrentLibraryFiles()**
// give names of files in window

## TopMenuBar ##

> This class will be responsible for the top menu

**-void loadLibraryFile()**
// this loads a file that contains functions and/or variables that the user can now utilize

**-void loadNewImage()**
//loads a new image file that can be utilized as a turtle

**-void loadNewImageBackground()**
//loads a new image that can be used to set a new background image chosen by the user

**-void getHelp()**
//displays a help menu in which you will be able to search for the different functions and get a description

## HelpMenu ##

> This class will provide the user specific information about each function

## API 3: Back End Internal API

## Turtle ##

img, (x,y), color,

**-void move()**
// changes x and/or y location on window

**-void setVisibility()**
// leaves turtle on window but is no longer visible

**-boolean wrap(Location iPosition, int pixels):**
// Checks for the top and bottom bounds of the WindowPanel

**-Location getOppositePoint (Location position, Vector direction ):** 
// Obtains the direction of opposite

**-boolean checkBounds ():**
// Checks the bounds and compare the turtle's location with respect to the bounds.

**-int turn (int x):**
// Turns turtle by x degrees. Returns the amount of rotation in degrees

**- Rectangle getBounds ():**
//Returns a rectangle representing the turtle bounds

**-int setDirection (int x):**
// Turns the turtle to the direction specified by the input degrees. This is with respect to the user.

**-int faceToPoint (int x, int y):** 
//Turns the turtle to face a specific point

**- int setPosition (int x, int y):** 
//Sets the turtles positions to a partiuclar point on the screen. Returns a (rounded) number of pixels moved

**-int goHome():**
// Returns the turtle home and documents the number of pixels it has moved.

**-void paint():**
// Paints the turtle.
-int clear () 
// Returns turtle home and remove history of traces. Returns pixels moved

## ColorBundle ##

**-void setColor (): **
// Sets the color

**-Color getColor (int index): **
// Gets the color at a specific index

**-Map<Integer, Color> getColorMap ():**
// Returns the color palette

## Background ##

> adds non turtle-related objects and barriers to the window

**-void addImage()**
// adds an image to window given a certain location

**-void addBarrier()**
// gives visible constraint on turtles movement within the window

## TurtleBundle ##

**-void createTurtle()**
// takes in certain parameters such as location, image, color, etc and makes a turtle image at that location to be added to the window

**-void removeTurtle()**
// removes one turtle from window

**-void clear()**
// removes all turtles from the collection

## VariableBundle ##

**-void recieveVariable()**
// stores variable defined by user

**-Variable getVariable()**
// returns variable defined by user

## FunctionBundle ##

**-void receiveFunction()**
// stores function defined

**-Function getFunction()**
// get a previously defined function from the stored functions given a name or index

## Pen ##

**-void addLine()**
// adds line to window

## API 4: Back End External API ##

## ControllerMain ##

> This class is responsible for taking in a string, file, or other type of input parameter from the View and passing it to the parser.  It contains a list of all current projects and works in between the view and model.

**-void process(File or string)**
// sends user input to parser

**-void load(File)**
// loads saved user input to parser

## Project (extends Observable) ##

> Contains a set of objects, such as TurtleBundle, VariableBundle, FunctionBundle, etc. which the Controller receives any changes from.  

**-void update()**
// makes any changes to the objects it contains.

**-void execute(single command)**
// completes a single command

**-void executeList(List of commands)**
// completes a group of commands

**-void save()**
// saves current status of all objects

![](http://i.imgur.com/VT142Mj.png)

# User Interface

![](http://i.imgur.com/5Rtl1qQ.png)

The user interface will be divided primarily in four parts. The first area will be responsible to display the current movement of the turtle. The user will not be able to interact with this part of the program. There is then an area that serves as a console, allowing the user to type in commands. The user will be required to press ENTER when he/she finished typing in the commands. This will also display the error messages (similar to a java console), providing information about the type of error. There is another area that displays the current variables as well as the functions that the user defined. The user will also be able to change the values of the variables from this screen. The last box will contain a least of the most recent commands executed by the user. There is also a menu bar on top, which allows the user to save the current configuration (variables and functions). It also allows the user to load packages that allow the user to utilize previously defined functions/variables.  There is also a help menu, which will create a new dialog that will allow the user to search for the definition of a specific method. 

# Design Details

Let's describe the **Controller First:**

# Controller: #

## public class ControllerMain ##

**Basic Idea:** The controller holds the list of all projects and/or workspaces and is able to create a new one. It also works as the medium to receive input from the view and pass the input to the parser. The controller is finally responsible for receiving parsed input and sending it to an "Executor" class as well as the corresponding current project/workspace.

**@Params:** Language, Resource package/bundle, ...

**@Private Objects:**

- Parser
- WorkspaceManager
- Text Field (holding the inputs)

**@Methods:**

### [Constructor] public ControllerMain (): ###

Initialize parser and view. Sets up the input field for the user, and adds an action listener to the input field such that the input string is sent to the controller where the command "process" is completed whenever the "Compile" button is pressed.

### public void process (String command): ###

Parses (with parser), executes each and every command, and updates the command list in the view.

**Throws:** IllegalSyntaxException - If parsing gives a syntax error

### public void process (File file): ###

Parses (with parser), executes each and every command in a file, and updates the command list in the view based on the commands in a file.

**Throws:** IllegalSyntaxException - If parsing gives a syntax error, or the file is not formatted correctly

### public void load (File fileName): ###

Reads in a file with variables, commands, and states pre-written in a certain format. We then update the current project/workspace based on this file.

**Throws:** UnknownFileException - If file does not exist or is in an incorrect format

----------

## public class Project extends Observable ##

**Basic Idea:** This class contains history, contains the TurtleBundle, and provides additional functionalities (Overwrite, save, etc.)

**@Params:** Resource Bundles

**@Private Objects:**

- userDefinedCommandMap
- VariableBundle
- TurtleBundle
- WindowPane

**@Methods:**

### [Constructor] public Project (): ###

Sets up the following components:

- Custom command map
- TurtleBundle (i.e. TurtleManager)
- Any resource bundles/getting any resource errors
- History List
- VariableBundle (i.e. VariableManager)
- Color stuff
- WindowPane (from View)

### public void update (): ###

Updates the contents of the project. Essentially, what this means for the first sprint is update the TurtleBundle

### public void executeList (List<ObjectCommand> commandList): ###

Executes a list of commands displayed in the project

### public void execute (ObjectCommand command): ### 

Execute a single command ObjectCommand.evaluate(Active Project)

### public void update (): ###

Notifies the visual representation observers of all the variables, commands, and expressions relating to this project.

Does the following two steps:

- setChanged() -- Observable method
- notifyObservers() -- Observable method

### public void save (File inputFile): ###

Save commands to file

**Throws:** UnknownFileException -- If invalid file format

### Misc. Getters and Setters and Makers: ###

- setStroke (Stroke s) -- Sets stroke on turtle pens
- getPalette () -- Get color stuff
- addTurtleObserver () -- Add an observer to the TurtleBundle
- getPane () -- Get WindowPane of the project
- setBackground (int rgbColor) -- Sets background color
- getHistory () -- Returns project history
- getCommand (String command) -- returns the command associated  
- getVariables () -- gets VariableBundle


----------
----------

Let's describe the **Model:**

# Model: #

## public class ColorBundle ##

**Basic Idea:** This class contains Color management. Allows the user to make colors and to store them. All allows for retrieval.

**@Params:** DefaultColors, and Map<Integer, Color> colorPalette

**@Private Objects:**

**@Methods:**

### [Constructor] public ColorBundle (): ###

Sets palette and default colors

### public void setColor (): ###

Sets the color

### public Color getColor (int index): ###

Gets the color at a specific index

### public Map<Integer, Color> getColorMap (): ###

Returns the color palette

----------

## public class Pen ##

**Basic Idea:** This class contains the code necessary to hold the lines that should be drawn on the WindowPane.

**@Params:** Default Stroke

**@Private Objects:**

**@Methods:**

### [Constructor] public Pen (): ###

Creates a new list of PenLines. Sets the default stroke

### public void addLine (Location Li, Location Lf): ###

Adds a new PenLine to list of lines that are written. 

----------

## public class Turtle ##

**Basic Idea:** This class contains the code necessary to hold the Turtle that resides on the WindowPane

**@Params:** CenterLocation, TurtleGeometricProperties...

**@Private Objects:**

**@Methods:**

### [Constructor] public Turtle (): ###

Sets the pen of the turtle. Sets the center of the turtle. Set initial angle direction of the turtle.

### public int move (int pixels): ### 

Moves the turtle in (pixels) amount in the direction it heads.

### public boolean wrap(Location iPosition, int pixels): ###

Checks for the top and bottom bounds of the WindowPanel

### public Location getOppositePoint (Location position, Vector direction ): ###

Obtains the direction of opposite

### public boolean checkBounds (): ###

Checks the bounds and compare the turtle's location with respect to the bounds.

### public int turn (int x): ###

Turns turtle by x degrees. Returns the amount of rotation in degrees

### public Rectangle getBounds (): ###

Returns a rectangle representing the turtle bounds

### public int setDirection (int x): ###

Turns the turtle to the direction specified by the input degrees. This is with respect to the user.

### public int faceToPoint (int x, int y): ###

Turns the turtle to face a specific point

### public int setPosition (int x, int y): ###

Sets the turtles positions to a partiuclar point on the screen. Returns a (rounded) number of pixels moved

### public int goHome(): ###

Returns the turtle home and documents the number of pixels it has moved.

### public void paint(): ###

Paints the turtle.

### public void setVisibility (boolean bool): ###

Sets the visibility of the turtle.

### public int clear () ###

Returns turtle home and remove history of traces. Returns pixels moved

----------

## public class TurtleBundle extends Observable ##

**Basic Idea:** Contains multiple turtles and states corresponding to the turtle.

**@Params:** Map<Integer, Turtle> turtles, Map<Integer, Image> turtleImages

**@Private Objects:**

**@Methods:**

### [Constructor] public TurtleManager (): ###

Sets the map of turtles, active turtles, and images of turtles.

### public void setOn(int index): ###

Sets the turtle to be activated at a certain position.

### public void update (): ###

Updates this object and notifies observers and paints the turtles

### public void setOff(int index): ###

Sets the turtle to be deactivated at a certain position.

### pulic void paint (Color myColor): ###

Paints all the turtles based on a specified color.

----------
----------

## public class View implements Observer:  ##

**Basic Idea:** Creates a user interface and allows for updates from the Model. It will  be easy to add new features since it is mostly independent from the model. If there are new parameters that we need to keep track of, we will include the necessary methods and modify the update class (that will catch whatever new information the Model notifies it) 

**@Params:** Model myModel, Controller myController, GridPane myGridPane, EnvironmentMenu myEnvironment, TopMenu myTop. 

**@Methods:**

###private void createTopMenu()###

Creates a TopMenu

###private void createCommandsHistory()###

Creates a CommandsHistory

###private void createEnvironmentMenu()###

Creates an EnvironmentMenu

###public void update(Observable o, Object arg)###

When observable object notifies, it updates according to the different object

### public void clearWindow()###

Removes all the elements from the turtle window 

### public void drawTurtle(int x, int y) ###

Draws a turtle on screen at coordinates (x,y)

### public void drawBackground() ###

Draws a background

###private void updateResourceFile()###


Observes the TopMenu class to see if the user selects any resource file. If he does, it passes it to the model. 

###private void updateEnvironmentMenu()###

Observes the Model to see if it has to update any environmental variables 

###public void updateCommandHistory()###

Updates the current command history 

## public class TopMenu implements Observable: ##

**Basic Idea:** Creates a menu bar at the top of the screen with a file and help option

**@Params:**  HelpMenu myHelpMenu

**@Private Objects:**

**@Methods:**

### public void getResource(String s)###

Returns the resource file containing functions and variables and passes it to the Model

###public void saveConfiguration()###

Saves the current configuration of the model in a new file 

###public void loadHelp()###

Creates a dialog box where the user can search for the definitions of the Logo commands 

##public class EnvironmentMenu ##

**Basic Idea:** Creates a box that contains the information about the variables/functions defined by user

**@Params:**  VBox myVariables 

**@Methods:**

##public class update(List<> params)##

Displays the new parameters for the current Logo environment

##public class CommandHistoryBox##

**Basic Idea:** Creates a box that contains a list with the commands executed by the user

**@Params:**  TextField mycCommands

**@Methods:**

###public void updateCommands(List<> commands) ###

Updates the list of commands


# API Example Code #

- commandWindow.setEventListener( e -> {passUserInfo()})
- (In Main:) myController.parse(inputString)
- myController.parse calls for creation of CommandFactory inside of the Controller
- CommandFactory creates buildTree (AST syntax tree) and calls updates to appropriate parameters in the Model, specifically the turtles y-position (+50).
- Data structure/string created by CommandFactory is sent to view to update the environment’s history block.
- Turtle class in model updates and calls method notify()
- TurtleBundleObserver sees the updates in the states (only the turtle in this case), and passes to the view the important information needed to send, i.e. Collections<TurtleImages> to display. In this case, there is only one turtle to display.


# Design Considerations

How to transmit the information between the model and the view. We examined a variety of solutions. One was to create a class that contained all the current variables/functions/objects in one class, and have the View access that to check for changes or updates. The main problems we encountered is that we did not want duplicate data, and we did not want the model and view to both interact with the same data source. Another consideration we had was about the possibility of passing a Turtle object to the View. We thought this would allow us to easily access the position and graphic info about the turtle. However, this option was discarded because it required to pass too much unnecessary information to the View. We also discussed the implementation of the drawing method. We decided to pass only the Image of the turtle to the View. This will allow the View to display the image, but it will empower the model to make any necessary changes to the turtle image (such as moving it, changing color, etc). 

# Team Responsibilities

**Austin:** work on parser and interfacing the Model hierarchy

**Steven:** work on implementation of Model and Controller parts

**Emanuele:** work primarily on the layout, displaying functions/variables, environmental changes

**Michael:** work on displaying the turtle and receiving user input