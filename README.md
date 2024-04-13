[![Java CI](https://github.com/AY2324S2-CS2103T-F12-1/tp/actions/workflows/gradle.yml/badge.svg)](https://github.com/AY2324S2-CS2103T-F12-1/tp/actions/workflows/gradle.yml/badge.svg)
[![codecov](https://codecov.io/gh/AY2324S2-CS2103T-F12-1/tp/branch/master/graph/badge.svg)](https://codecov.io/gh/AY2324S2-CS2103T-F12-1/tp)

# NetConnect

## Introduction

NetConnect is a desktop app for food business managers managing contacts in SMEs, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). It enables managers to efficiently manage their employees, clients, as well as suppliers, **all in one place** ☝🏻.

![Ui](docs/images/Ui.png)

## Features

1. Profile Management
    * Add a new profile: Easily create a new profile for an employee, client, or supplier. You can choose to tag them with a custom tag.
    * Delete or Edit a profile: Remove a profile from the list or Edit their information via their ID.


2. Category Management
    * Roles: Categorize your profiles into *Employees, Clients, or Suppliers*.
    * Tailored profile information: Each of the roles have their own set of information to be filled in, e.g. Employees have the field `Department`, while Supplier have `Product`.

3. Search

The searches can be stacked and allows you to find a profile based on the following criteria:
    * Search by name: Find a profile by their name.
    * Search by contact: Find a profile by their contact number.
    * Search by tag: Find a profile by their tag.
    * Search by role: Find a profile by their role.
    * Search by remark: Find a profile by their remark.

4. Related Profiles
    * Relate profiles: Link profiles together to show their relationship.
    * Unrelate profiles: Remove the relationship between two profiles.
    * View related profiles: View all profiles related to a specific profile.

5. Export
    * Export profiles: Export all profiles to a CSV file.

## User-Friendly Commands

NetConnect uses simple CLI commands to make it easy for you to manage your contacts.

## Warning System

NetConnect will warn you if you are about to carry out a destructive action, and it requires your confirmation before execution. This ensures that you do not accidentally delete important information.

## Error Handling

With a robust error handling system, clear error and resolution messages will guide you through the process of managing your contacts seamlessly.

## Getting Started
To begin your journey with NetConnect, simply download the latest release from [here](https://github.com/AY2324S2-CS2103T-F12-1/tp/releases) and refer to the [User Guide](docs/UserGuide.md)
for detailed guidance in using the application.

For the detailed documentation of this project, see the **[NetConnect Website](https://ay2324s2-cs2103t-f12-1.github.io/tp/)**.
* This project is a **part of the se-education.org** initiative. If you would like to contribute code to this project, see [se-education.org](https://se-education.org#https://se-education.org/#contributing) for more info.
