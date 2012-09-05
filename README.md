#Timestamp Visualizer

##Overview

In the field of computer forensics, and in particular when working as a computer forensics expert in civil and criminal cases, it is useful to have strategies to detect suspicious behavior. One such strategies involves understanding the typical behavior of a user of a computer system. 

For example, if we track the times that Alice uses a particular system, we may find that she tends to work Monday-Friday, beginning around 8 am until maybe 6 pm. Knowing this is useful, because now if we notice that a few months ago, she was logged in at 3 am on Sunday morning, we will probably have better luck finding suspicious activity during this session.

The Timestamp Visualizer is a tool that facilitates finding possible suspicious digital activity. Given a listing of timestamps provided by the user, the Visualizer generates a graphical interface that is easily customized and manipulated by the user. Details are discussed below.

##Description

A GUI designed to ease visualizing timestamps. User is prompted for a text file containing timestamp data. Correct timestamp format is obtained by executing:

```ls -ls --time-style=+%m/%d/%Y\ %T``` or ```ls -Rls --time-style=+%m/%d/%Y\ %T```

Sample ls dumps are provided with the source to view or use in the GUI. Once a dump is provided by the user, the timestamps are displayed in a histogram:

![Simple graph](https://raw.github.com/lmichale/TimestampVisualizer/master/images/screenshot1.png)

##Features

###Multiple file selection

The user has the option of selecting one or more input files to graph. If more than one file is selected, the bars are distinguished by different colors.

![Multiple files](https://raw.github.com/lmichale/TimestampVisualizer/master/images/screenshot2.png)

###View as single or dual graphs

The user can view single graph (as seen above) or two graphs placed one below the other (see below). This can help compare the timestamp based on the various parameters like hours, day of the week, month, year, etc.

![Dual graph mode](https://raw.github.com/lmichale/TimestampVisualizer/master/images/screenshot3.png)

###Interactive graph

Clicking on the bars of the chart displays a zoomed-in chart: Year charts zoom into month, month zooms into day of the week, and so on.

The menu bar also allows users to render back to the previous graph after zooming in (aka, month back to year view)

###Data grouping

The user can group data by year, month, week, day of the week, hour, minute and second. These options can be selected on the side panel or by zooming in the chart. 

By using the side panel, users can also select what specific portions of the dataset to include. For instance, The user can opt to view data by month, but include only the years 2006 and 2009. The side panel selections for this particular example is below:

![Data grouping](https://raw.github.com/lmichale/TimestampVisualizer/master/images/screenshot4.png)

###Customize graph format and colors

Right clicking on the graph and selecting properties allows users to change colors, font, title, axis range, etc.

###Exporting

The export option in the menu bar gives the option of exporting the chart as either a PNG or PDF. PDF export has additional options - the user can format the PDF to include current date, expert witness’s name, source hard drive’s hash, and a filename listing. 

###Help menu

Aids the user in general ways, like getting started with file import. Also provides some regex help.

![Help menu](https://raw.github.com/lmichale/TimestampVisualizer/master/images/screenshot5.png)

##Contributors

lmichale, shilpar, swatigup, tgavanka
