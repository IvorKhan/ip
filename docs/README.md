# YapPal User Guide

![product screenshot](Ui.png)

    __   __  _______  _______  _______  _______  ___     
    |  | |  ||   _   ||       ||       ||   _   ||   |    
    |  |_|  ||  |_|  ||    _  ||    _  ||  |_|  ||   |    
    |       ||       ||   |_| ||   |_| ||       ||   |    
    |_     _||       ||    ___||    ___||       ||   |___
      |   |  |   _   ||   |    |   |    |   _   ||       |
      |___|  |__| |__||___|    |___|    |__| |__||_______|

## Introducing YapPal!
Your best friend in:
- Staying organised 📚
- Chatting whenever you like 🗣️

## Adding Todo Tasks

Input `todo [name of task]` to add a ToDo task to your list!

Example: `todo Take a shower`

When you check your `list`:
```
1. [T][ ] Take a shower
```

## Adding Deadline Tasks

Input `deadline [name of task] /by [YYYY-MM-DD]` to add a Deadline task to your list!

Example: `deadline Submit my CS2103T project /by 2025-09-19`

When you check your `list`:
```
1. [D][ ] Submit my CS2103T project (by Sep 19 2025)
```

## Adding Event Tasks

Input `event [name of task] /from [YYYY-MM-DD] /to [YYYY-MM-DD]` to add an Event task to your list!

Example: `event O'Week /from 2025-07-24 /to 2025-07-28`

When you check your `list`:
```
1. [E][ ] O'Week (from Jul 24 2025 to Jul 28 2025)
```

## Adding Fixed Duration Tasks

Input `duration [name of task] /hr [number of hours] /min [number of minutes]` to add a Fixed Duration task to your list!

Example: `duration Run 15km /hr 1 /min 30`

When you check your `list`:
```
1. [F][ ] Run 15km (takes 1 hour 30 minutes)
```

## Listing your current tasks

Input `list` to look at your current list!

Example: `list`

Expected output:
```
1. [T][ ] Take a shower
2. [D][ ] Submit my CS2103T project (by Sep 19 2025)
3. [E][ ] O'Week (from Jul 24 2025 to Jul 28 2025)
4. [F][ ] Run 15km (takes 1 hour 30 minutes)
```

## Marking completed tasks

Input `mark [task index]` to mark a task!

Example: `mark 2`

Expected output:
```
1. [T][ ] Take a shower
2. [D][X] Submit my CS2103T project (by Sep 19 2025)
3. [E][ ] O'Week (from Jul 24 2025 to Jul 28 2025)
4. [F][ ] Run 15km (takes 1 hour 30 minutes)
```

## Unmarking tasks

Input `unmark [task index]` to unmark a task!

Example: `unmark 2`

Expected output:
```
1. [T][ ] Take a shower
2. [D][ ] Submit my CS2103T project (by Sep 19 2025)
3. [E][ ] O'Week (from Jul 24 2025 to Jul 28 2025)
4. [F][ ] Run 15km (takes 1 hour 30 minutes)
```

## Deleting tasks

Input `delete [task index]` to delete a task!

Example: `delete 1`

Expected output:
```
1. [D][X] Submit my CS2103T project (by Sep 19 2025)
2. [E][ ] O'Week (from Jul 24 2025 to Jul 28 2025)
3. [F][ ] Run 15km (takes 1 hour 30 minutes)
```