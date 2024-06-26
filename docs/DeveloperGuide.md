---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# NetConnect Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/netconnect-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/netconnect-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete i/1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/netconnect-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/netconnect-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/netconnect-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/netconnect-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete i/1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `NetConnectParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `NetConnectParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `NetConnectParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/se-edu/netconnect-level3/tree/master/src/main/java/seedu/address/model/Model.java)

Here's a (partial) class diagram of the `Model` component:

<puml src="diagrams/ModelClassDiagram.puml" width="450" alt="ModelClassDiagram"/>

The `Model` component,

* stores the netconnect data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change. This filtered list is further filtered by the Filter classes.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `NetConnect`, which `Person` references. This allows `NetConnect` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" alt="BetterModelClassDiagram"/>

</box>

Here are the other classes in `Model` (omitted from the class diagram above) that are used for filtering the list that is displayed to users:

<puml src="diagrams/FilterClasses.puml" width="300" alt="FilterClasses"/>

How the filtering works:
* `ModelManager` stores only one instance of `Filter` at any one time. The stored `Filter` instance in turn stores all the `XYZPredicate` objects currently applied to the filtered view.
* `FilteredList#setPredicate(...)` is called with the `Filter` instance, and only shows all `Person` objects that satisfy **all** predicates in `Filter`.

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/netconnect-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" alt="StorageClassDiagram"/>

The `Storage` component,
* can save both netconnect data and user preference data in JSON format, and read them back into corresponding objects.
* it can also save the state of the command box in a file called `state.txt` in the data folder.
* inherits from both `NetConnectStorage`, `UserPrefStorage` as well as `StateStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.netconnect.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Export Feature

#### Expected Behaviour

The `export` command allows users to export contact data from NetConnect into a CSV file. Users have the option to specify a file name. If no file path is provided, the CSV file is saved as a default name. The command exhibits the following exceptional behaviors:

* If the file name provided is invalid or inaccessible, an error message is displayed to the user.

The successful execution of the `export` command results in the creation of a CSV file as the specified or default name at default location, containing all the contact data available in NetConnect.

#### Current Implementation

A `ExportCommand` instance is created in the `ExportCommandParser#parse(...)` method when the user issues the `export` command. The process involves the following steps:

1. Parsing the command input to extract the file nqm3 if provided.
2. If no filename is provided, setting the filename to a default value.
3. Utilizing the `CsvExporter` component to write the data to the CSV file.

The sequence diagram below illustrates the execution of a `ExportCommand`:

<puml src="diagrams/ExportCommandSequenceDiagram.puml" alt="ExportCommandSequenceDiagram" />

The sequence diagram below illustrates the creation and execution of a `CsvExporter`:

<puml src="diagrams/ExportCommandSequenceDiagram.puml" alt="CsvExporterSequenceDiagram" />

#### Design Considerations

**Aspect: Handling of file paths in the `export` command:**

* **Alternative 1 (Current Choice):** Always save the CSV file to a fixed, pre-defined location without user input.
  * Pros: Simplifies the command's implementation by removing the need to parse and validate user-provided file paths.
  * Cons: Reduces user flexibility in determining where the CSV file should be saved.
* **Alternative 2:** Allow users to specify a file path, defaulting to a pre-defined location if not specified.
  * Pros: Provides flexibility for users to save the CSV file wherever they prefer.
  * Cons: Additional error handling is required to manage invalid or inaccessible file paths.

### Person Roles Feature

#### Overview

The person can be categorized into three roles: `Client`, `Supplier`, and `Employee`. These classes extend the base `Person` class and encapsulate various role-specific functionalities and attributes, improving the application's ability to cater to a diverse range of user interactions.

* **Client**: Represents a customer, associated with products and preferences.
* **Supplier**: Represents a vendor, associated with products and terms of service.
* **Employee**: Represents an employee, associated with a department, job title, and skills.

#### Expected Behaviour

The `Person` class is extended by three other classes, each with their own additional attributes:

* `Client` Subclass contains `products` attribute of type `Products`, representing the products associated with the client, and contains `preferences` as a `String`, detailing client-specific preferences.

* `Supplier` Subclass contains `products` attribute of type `Products`, which lists the items supplied, and holds `termsOfService` of type `TermsOfService`, outlining the agreement with the supplier.

* `Employee` Subclass includes a `department` attribute of type `Department`, signifying the department the employee belongs to, has a `jobTitle` attribute of type `JobTitle`, representing the employee's official title, and features `skills` of type `Skills`, indicating the competencies of the employee.

#### Implementation

##### Class Additions

* `Client`, `Supplier`, and `Employee` classes have been added, extending the `Person` class to include role-specific fields.
* New classes `Department`, `JobTitle`, `Products`, `Skills`, and `TermsOfService` are introduced to encapsulate relevant attributes.

##### Data Storage

* The `JsonAdaptedPerson` class has been implemented to support the conversion to and from the new role-based classes, ensuring compatibility with the enhanced JSON schema.

##### User Interface Enhancements

* The UI has been enhanced to dynamically display optional fields based on the person's role, offering a tailored user experience.

##### Command and Parser Modifications

* Commands and parsers have been updated to recognize and process additional arguments related to the new person types.
* Modified commands handle logic specific to each role, ensuring correct operation based on person type.

#### Usage Scenario

1. An administrator decides to add a new `Employee` to the system.
2. The administrator inputs the employee's details, including department, job title, and skills.
3. The system processes the input, updates the data storage, and the UI reflects the new employee's information, displaying department and job title.

#### Design Considerations

**Aspect: How to distinguish the person role:**

* **Alternative 1 (current choice)**: Creation of subclasses `Supplier`, `Employee`, and `Client` that inherit from the `Person` class.
  * **Pros**:
    1. Specialization: Allows for clear role-specific methods and properties, ensuring that objects have only the attributes and behaviors pertinent to their role.
    1. Extensibility: Easier to add new roles by creating additional subclasses.
    1. Polymorphism: Enables the use of a single interface to represent any person type, which simplifies code that interacts with these objects.
  * **Cons**:
    1. Complexity: More complex class hierarchy which can become difficult to manage with a large number of roles.
    1. Redundancy: Potential for redundant code in subclasses if there is significant overlap in behavior or data.
    1. Rigid Hierarchy: Changing the class hierarchy can be challenging if the differentiation between roles changes over time.

* **Alternative 2**: Introducing a `type` field within the `Person` class to indicate the person's role and including all possible fields for all types.
  * **Pros**:
    1. Simplicity: A flat structure with a single `Person` class could simplify the system.
    1. Flexibility: Easy to change a person’s role by updating the type field without the need to instantiate a new object.
    1. Single Table Storage: All person records can be stored in a single database table, which can simplify CRUD operations.
  * **Cons**:
    1. Data Sparseness: The `Person` object will have redundant fields that are not applicable to all types, leading to wasted storage space and potential confusion.
    1. Increased Conditionals: The code will require conditional logic to handle behavior specific to each role, which can make the code more complex and harder to maintain.
    1. Loss of Type Safety: Without distinct classes, it's easier to mistakenly assign the wrong attributes or behaviors to a person object.

<puml src="diagrams/PersonClassDiagram.puml" width="650" alt="PersonClass"/>

### Save state feature

The save state feature is implemented using the `TextStateStorage` which implements `StateStorage` interface. It is responsible for saving the state of the command box. The state is saved in a file called `state.txt` in the data folder. The state is updated at each change in the input. Additionally, it implements the following operations:

* `TextStateStorage#saveState(String state)` — Saves the current state of the command box into file.
* `TextStateStorage#readState()` — Reads the saved state of the command box from the file and returns the string.
* `TextStateStorage#clearState()` — Clears the file storing the states.
* `TextStateStorage#isStateStorageExists()` — Checks if the file storing the states exists.
* `TextStateStorage#deleteStateStorage()` — Deletes the file storing the states.
* `TextStateStorage#getStateStorageFilePath()` — Returns the file path of the file storing the states.
* `TextStateStorage#getFilePathString()` — Returns the file path of the file storing the states as a string.
* `TextStateStorage#getDirectoryPath()` — Returns the directory path of the file storing the states.

Given below is an example usage scenario and how the save state feature behaves at each step.

Step 1. The user launches the application. During set up, the presence of the state storage file is checked. If absent, a new storage file is created. When the command box is instantiated, it calls the `TextStateStorage#readState()` method to get the last command that was present in the command box before it was last closed. The text in the file is retrieved and loaded into the command box using `TextField#setText`.

**Note:** If the storage file is not found a new empty file is created.

Step 2. The user changes the input in the command box. The `TextStateStorage#saveState(String state)` method is called to save the current state of the command box into the file.

<puml src="diagrams/SaveStateActivityDiagram.puml" alt="SaveStateActivityDiagram" />


#### Design considerations:

**Aspect: How save state executes:**

* **Alternative 1 (current choice):** Update the storage file at every change in input.
  * Pros: Lower risk of data loss.
  * Cons: Constantly updating the storage file with every change in input may introduce performance overhead.

* **Alternative 2:** Update the storage file only when the application is closed.
  * Pros: Reduces the number of writes to the storage file, reducing performance overhead.
  * Cons: Does not save the state of the command box in case of a crash.

* **Alternative 3:** Update the storage file when there is a pause in typing.
  * Pros: Reduces the number of writes to the storage file, reducing performance overhead.
  * Cons: May not save the state of the command box in case of a crash.

### Relate feature

#### Expected behaviour

The `relate` command allows users to relate two persons via their unique `ID`. Exceptional behavior:
* If the `ID` provided by user does not exist, an error message is displayed.
* If the `ID` provided is not an integer value that is more than 0, an error message is displayed.

<puml src="diagrams/RelateActivityDiagram.puml" alt="RelateActivityDiagram" />

#### Current implementation
Given a command `relate i/1 i/2`, the `NetConnectParser` recognises the `relate` command and first instantiates a `RelateCommandParser` object. It then passes the command string into `RelateCommandParser#parse(...)`, where the input `i/1` and `i/2` is validated for its format. Following which, `RelateCommandParser` instantiates a `RelateCommand` object.

The`RelateCommand` object extends the `Command` interface, and hence contains a method called `execute(...)`, which takes in a `model`. A model can be thought of as a container for the application's data, and it also controls the exact contact list that the user will see. In the `execute(...)` command, we validate that NetConnect has both IDs `1` and `2` and, does not already have a relation. We add it to our RelatedList storage if both are true. To change our view and to ratify the successful command, we will have to change the view the user sees by instantiating a predicate called `IdContainsDigitsPredicate`. We will then pass it into the `model#stackFilters(predicate)` method in the `model` object to update the filtered list of persons to only include the two people with ID `1` and `2`.

Recalling that we also have a message box to inform the result of the actions taken (in prose form), the `RelateCommand#execute(...)` method will also return a `CommandResult` object, which contains the summary of the number of people listed.

#### Design considerations

**Aspect: How to store relations between contacts**

* **Alternative 1 (current choice)**: Store the Related List within the NetConnect model as a JSON file. Each time a relate command is done, we will just have to save the NetConnect model.
    * Pros: This approach requires a single command for saving and loading and does not violate encapsulation of classes. Saving and loading does not have to be exposed to wider classes and done within the NetConnect model interface. 
    * Cons: JSON files can be difficult to amend and maintain.

* **Alternative 2 (previously implemented)**: Store the Related List as a .txt file.
    * Pros: Easier to edit, and implement.
    * Cons: Harder to maintain as there will be multiple files to be used by our application. Save and load implementation is also exposed outside the NetConnect model.

* **Alternative 3**: Store Relations as another field in every person. A relate command would add the opposing contact to both persons provided. 
    * Pros: Easy to understand as a user. Querying of contacts will also be fast as the relations are stored within the same contact. 
    * Cons: Unnecessary to user, and complicates UI. Also has a higher potential for bugs given that the entire contact list has to be searched and updated each time a relation is added and subsequently removed. 

### ShowRelated feature

#### Expected behaviour

The `showrelated` command allows users to view all persons related to a specific person via their unique `ID`. Exceptional behavior:
* If there are multiple `ID` provided by user, an error message is displayed.
* If the `ID` provided is not an integer value that is more than 0, an error message is displayed.

<puml src="diagrams/ShowRelatedActivityDiagram.puml" alt="ShowRelatedActivityDiagram" />

#### Current implementation
Given a command `showrelated i/1`, the `NetConnectParser` recognises the `showrelated` command and first instantiates a `ShowRelatedCommandParser` object. It then passes the command string into `ShowRelatedCommandParser#parse(...)`, where the input `i/1` is validated for its format. Following which, `ShowRelatedCommandParser` instantiates a `ShowRelatedCommand` object.

The`ShowRelatedCommand` object extends the `Command` interface, and hence contains a method called `execute(...)`, which takes in a `model`. A model can be thought of as a container for the application's data, and it also controls the exact contact list that the user will see. In the `execute(...)` command, we extract all the tuples that contain ID `1` and use it to instantiate a predicate called `IdContainsDigitsPredicate` which extracts all the `ID` of the related profiles (excluding itself of ID `1`). We will then pass it into the `model#stackFilters(predicate)` method in the `model` object to update the filtered list of persons to only include persons related to the person with ID of `1`.

Recalling that we also have a message box to inform the result of the actions taken (in prose form), the `ShowRelatedCommand#execute(...)` method will also return a `CommandResult` object, which contains the summary of the number of people listed.

#### Design considerations

**Aspect: How to extract the IDs of the related profiles**

* **Alternative 1 (current choice)**: Extract all the tuples from storage and use regular expressions to check if either of the `ID` in the tuple is the specified `ID`, then extract the `ID` of the other related profile.
    * Pros: This approach is straightforward and uses existing methods to extract the related profiles.
    * Cons: Regular expressions can be difficult to understand and maintain.

* **Alternative 2**: Extract only tuples that contain the specified `ID`, then extract the `ID` of the other related profile.
    * Pros: Smaller chunk of data is extracted, reducing the amount of data to be processed as data is filtered out during read from storage.
    * Cons: More complex implementation as the data is filtered out during read from storage, requiring filtering with String data instead of as IdTuple.

### Unrelate feature

#### Expected behaviour

The `unrelate` command allows users to unrelate two persons via their unique `ID`. Exceptional behavior:
* If the `ID` provided by user does not exist, an error message is displayed.
* If the `ID` provided is not an integer value that is more than 0, an error message is displayed.

<puml src="diagrams/UnrelateActivityDiagram.puml" alt="UnrelateActivityDiagram" />

#### Current implementation
Given a command `unrelate i/1 i/2`, the `NetConnectParser` recognises the `unrelate` command and first instantiates an `UnrelateCommandParser` object. It then passes the command string into `UnrelateCommandParser#parse(...)`, where the input `i/1` and `i/2` is validated for its format. Following which, `UnrelateCommandParser` instantiates an `UnrelateCommand` object.

The`UnrelateCommand` object extends the `Command` interface, and hence contains a method called `execute(...)`, which takes in a `model`. A model can be thought of as a container for the application's data, and it also controls the exact contact list that the user will see. In the `execute(...)` command, we validate that NetConnect has both IDs `1` and `2` and already has a relation. We remove it from our RelatedList stored in NetConnect if both are true. To change our view and to ratify the successful user command, we will have to change the view the user sees by instantiating a predicate called `IdContainsDigitsPredicate`. We will then pass it into the `model#stackFilters(predicate)` method in the `model` object to update the filtered list of persons to only show the two people who have just been unrelated with the ID `1` and `2`.

Recalling that we also have a message box to inform the result of the actions taken (in prose form), the `UnrelateCommand#execute(...)` method will also return a `CommandResult` object, which contains the id of the two people, in a 1relates2 format..

#### Design considerations

**Aspect: What kind of unrelation will be done using the two IDs**

* **Alternative 1 (current choice)**: The two IDs given will be used to remove that specific relation.
    * Pros: This approach would allow the user the greatest flexibility in removing relations between contacts.
    * Cons: User may think that it would delete all relations from each contact.

* **Alternative 2**: The two IDs given will be used to remove all relations for the two contacts. 
    * Pros: Can help with resetting of all relations for the contact quickly.
    * Cons: Since the showrelated feature can show the user which relations for the contact already exists, manual and more precise unrelation has more value. This multi-field input for unrelate may confuse users based on the existing relate command usage.

### Delete feature

#### Expected behaviour

The `delete` command allows users to delete `Person` from NetConnect using either the target `Person`'s `Id` or `Name`. `Id` allows users to directly and accurately delete the `Person` if the `Id` is known, while `Name` provides the flexibility to delete using name if `Id` is not known. Exceptional behaviour:
* If both `Id` and `Name` is provided, an error message is shown.
* If there are no `Person`s with the given `Name` or `Id`, the display is updated to show all `Person`s, and an error message is shown.

<puml src="diagrams/DeleteActivityDiagram.puml" alt="DeleteActivityDiagram" />

* If `Name` is provided but there are more than one `Person` with the specified `Name` in NetConnect, all `Person`s with the matching `Name` will be displayed, and user will be prompted to re-input the `delete` command using `Id` instead.

<puml src="diagrams/DeleteNameActivityDiagram.puml" alt="DeleteNameActivityDiagram" />

`delete` can be done regardless of whether the target `Person` is in the current displayed list. If the `Person` is in the current displayed list, the display view does not change upon successful delete. If the `Person` is in not in the current displayed list, the display view is changed to display all `Person`s upon successful delete.

<puml src="diagrams/DeleteDisplayActivityDiagram.puml" alt="DeleteDisplayActivityDiagram" />

#### Current implementation

A `DeleteCommand` instance is instantiated in `DeleteCommandParser#parse(...)` by the factory methods `DeleteCommand#byId(Id)` or `DeleteComamnd#byName(Name)`. The sequence diagram below shows the creation of a `DeleteCommand` with `Id`. The process is similar for `DeleteCommand` with `Name`.

<puml src="diagrams/DeleteParseSequenceDiagram.puml" alt="DeleteParseSequenceDiagram" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

Deletion of `Person` from NetConnect is facilitated by `Model#getPersonById(Id)` or `Model#getPersonByName(Name)`, and `Model#deletePerson(Person))`. The sequence diagram below shows the execution of a `DeleteCommand` with `Id`. The process is similar for `DeleteCommand` with `Name`.

<puml src="diagrams/DeleteExecuteSequenceDiagram.puml" alt="DeleteExecuteSequenceDiagram" />

#### Design considerations

**Aspect: How delete command executes:**

* **Alternative 1 (current choice):** A single `Model#deletePerson(Person)` method.
  * Pros:
    1. Implementation of `Model#getPersonById(Id)` and `Model#getPersonByName(Name)` can be reused for other purposes.
    1. Simple implementation of `Model#deletePerson(Person)`.
  * Cons: Need to ensure `Person` with matching `Id` or `Name` exists in the list before `Model#getPersonById(Id)`, `Model#getPersonByName(Name)` and `Model#deletePerson(Person)` are called.

* **Alternative 2:** Separate methods of `Model#deletePersonById(Id)` and `Model#deletePersonByName(Name)`.
  * Pros: Simple implementation of `DeleteCommand#execute(...)`.
  * Cons:
    1. Presence checks required in `Model#deletePersonById(Id)` and `Model#deletePersonByName(Name)`.
    1. More boilerplate code since `Model#deletePersonById(Id)` and `Model#deletePersonByName(Name)` are very similar.

### Unique `Id` of `Person`

The unique id of `Person` is stored as a private field `Id` instance in `Person`. `Id` value is enforced to be unique between each `Person` by keeping the constructors of the `Id` class private, and by using a private static field `nextId`. The `Id` class provides 3 factory methods to instantiate `Id`:

* `Id#generateNextId()` — instantiates an `Id` object with the next available value given by `nextId`.
* `Id#generateId(int)` — instantiates an `Id` object with the given value, and updates nextId to be the given value + 1. This method is necessary to update `nextId` while keeping the `Id` value of each `Person` the same between different runs of the application.
* `Id#generateTempId(int)` — instantiates an `Id` object with the given value, without changing the value of `nextId`. This method is used for non-`add` NetConnect commands that accepts id as an argument. This method is necessary as using `Id#generateId(int)` in non-`add` commands will cause `nextId` to be updated, even if there are no persons in the NetConnect using the previous `Id`. Example:
  1. NetConnect has persons with `Id` 1 to 5.
  1. User inputs `delete i/1000` command, where `Id#generateId(int)` is used.
  1. `nextId` is updated to be 1001.
  1. On the next `Id#generateNextId()`, `Id` value 1001 will be used although values 6 to 1000 are not used.

<puml src="diagrams/IdClassDiagram.puml" alt="IdClassDiagram" />

Operations with `Id` on `Person` in NetConnect is facilitated through `Model#hasId(Id)` and `Model#getPersonById(Id)`.

### `Find` feature

#### Expected behaviour
The `find` command allows users to filter the display to show `Person`s from NetConnect with fields matching certain values. The command allows finding by name, phone number, tag, role, remark. Parameters provided are subjected to its respective validity checks, and mentions of these checks will be omitted in this section.

#### Current implementation

The execution of `find` is facilitated by `Model#clearFilter()` and `Model#stackFilters(NetConnectPredicate)`. `Model` uses the `Filter` classes in the `Model` component to facilitate the implementation. `find` by each field uses its respective `XYZPredicate`:
* `find` by name uses `NameContainsKeywordsPredicate`
* `find` by tag uses `TagsContainsKeywordsPredicate`
* `find` by phone number uses `PhoneMatchesDigitsPredicate`
* `find` by role uses `RoleMatchesKeywordsPredicate`
* `find` by remark uses `RemarkContainsKeywordsPredicate`

The sequence diagram below shows the parsing of a `find n/John` command. The process is similar for `find` command with other parameters.

<puml src="diagrams/FindParseSequenceDiagram.puml" alt="FindParseSequenceDiagram" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

The sequence diagram below shows the execution of the `find` command created from a `find n/John` command.

<puml src="diagrams/FindExecuteSequenceDiagram.puml" alt="FindExecuteSequenceDiagram" />

The `list` command, or any other command that alters the displayed view can be used to clear all existing filters. In these cases, the stored `Filter` object in `ModelManager` will be set to a `Filter` containing no predicates.

#### Design considerations

**Aspect: How the view of the displayed list is filtered**

* **Alternative 1 (current choice)**: Create a new `Filter` class to store all the predicates.
  * Pros: Allows retrieval of current predicates, and can be displayed to user.
  * Cons: Require an additional private field in `ModelManager`.

* **Alternative 2**: Stack the predicates using `FilteredList#setPredicate(...)`, `FilteredList#getPredicate()` and `Predicate#and(...), without explicitly storing the predicates.
  * Pros: Simple implementation without additional classes or fields.
  * Cons: Unable to retrieve the current predicates applied and hence unable to display to users the current filters applied.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedNetConnect`. It extends `NetConnect` with an undo/redo history, stored internally as an `netConnectStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedNetConnect#commit()` — Saves the current netconnect state in its history.
* `VersionedNetConnect#undo()` — Restores the previous netconnect state from its history.
* `VersionedNetConnect#redo()` — Restores a previously undone netconnect state from its history.

These operations are exposed in the `Model` interface as `Model#commitNetConnect()`, `Model#undoNetConnect()` and `Model#redoNetConnect()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedNetConnect` will be initialized with the initial netconnect state, and the `currentStatePointer` pointing to that single netconnect state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the netconnect. The `delete` command calls `Model#commitNetConnect()`, causing the modified state of the netconnect after the `delete 5` command executes to be saved in the `netConnectStateList`, and the `currentStatePointer` is shifted to the newly inserted netconnect state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitNetConnect()`, causing another modified netconnect state to be saved into the `netConnectStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitNetConnect()`, so the netconnect state will not be saved into the `netConnectStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoNetConnect()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous netconnect state, and restores the netconnect to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial NetConnect state, then there are no previous NetConnect states to restore. The `undo` command uses `Model#canUndoNetConnect()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoNetConnect()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the netconnect to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `netConnectStateList.size() - 1`, pointing to the latest netconnect state, then there are no undone NetConnect states to restore. The `redo` command uses `Model#canRedoNetConnect()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the netconnect, such as `list`, will usually not call `Model#commitNetConnect()`, `Model#undoNetConnect()` or `Model#redoNetConnect()`. Thus, the `netConnectStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitNetConnect()`. Since the `currentStatePointer` is not pointing at the end of the `netConnectStateList`, all netconnect states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire netconnect.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
    * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

Experienced managers who:
* oversees contact relations with suppliers, customers and employees
* has to manage a significant number of contacts
* works independently in a supervisory role
* has average IT skills
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: provides managers a platform to manage employee, client and partner contact information
easily, and to keep track of past interactions.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​          | I want to …​                                 | So that I can…​                                                      |
|----------|------------------|----------------------------------------------|----------------------------------------------------------------------|
| `* * *`  | occasional user  | add a new person                             |                                                                      |
| `* * *`  | occasional user  | delete a person                              | remove entries that I no longer need                                 |
| `* * *`  | occasional user  | find a person by name                        | quickly access their contact details                                 |
| `* * *`  | occasional user  | find a person by contact number              | see who contacted me                                                 |
| `* * *`  | occasional user  | find a person by remark                      | view all people with a particular remark                             |
| `* * *`  | occasional user  | find a person by role                        | view all roles separately                                            |
| `* * *`  | occasional user  | tag roles to members                         | see distinctions and manage using roles                              |
| `* *`    | occasional user  | go back to the state from where i left off   | avoid going back to the same page/state when I close the application |
| `* *`    | occasional user  | edit person information                      | refer to accurate personal information in the future                 |
| `* *`    | experienced user | export contact lists to a CSV file           | create backups or use the data in other applications                 |
| `* *`    | experienced user | relate two profiles together                 | connect two contacts together                                        |
| `* *`    | experienced user | view which contacts are related to a profile | assign tasks to my employees                                         |
| `* *`    | experienced user | unrelate two profiles                        | disconnect two contacts                                              |
| `* *`    | new user         | see usage instructions                       | refer to instructions when I forget how to use the App               |
| `* *`    | new user         | clear all existing contacts                  | populate with my actual contacts                                     |

### Use cases

(For all use cases below, the **System** is the `NetConnect` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - List All Persons**

**MSS**

1. User requests to list all persons.
2. NetConnect shows the list of all persons.

    Use case ends.

**Extensions**

* 2a. The list of all persons is empty.

  Use case ends.

**Use case: UC02 - Find a List of Persons by Name**

**MSS**

1. User requests for the list of persons matching a name.
2. NetConnect shows a list of persons with matching name.

   Use case ends.

**Extensions**

* 2a. There are no persons with a matching name.

  Use case ends.

**Use case: UC03 - Find a Specific Person by Contact Number**

**MSS**

1. User requests for the person with the matching contact number.
2. NetConnect shows the person with matching contact number.

   Use case ends.

**Extensions**

* 2a. There is no person with a matching number.

  Use case ends.

**Use case: UC04 - Find a Specific Person by Role**

**MSS**

1. User requests for the person with the matching role.
2. NetConnect shows the person with matching role.

   Use case ends.

**Extensions**

* 2a. There is no person with a matching role.

  Use case ends.

**Use case: UC05 - Find a Specific Person by Remark**

**MSS**

1. User requests for the person with the matching remark.
2. NetConnect shows the person with matching remark.

   Use case ends.

**Extensions**

* 2a. There is no person with a matching number.

  Use case ends.

**Use case: UC06 - Add a New Person**

**MSS**

1.  User requests to add a new person with given information.
2.  NetConnect adds a new person to the list.

    Use case ends.

**Extensions**

* 1a. Some given arguments are invalid.

    * 1a1. NetConnect shows an error message.

      Use case ends.

**Use case: UC07 - Delete a Person by UID**

**MSS**

1. User requests to delete a specific person by UID.
2. NetConnect deletes the person.

    Use case ends.

**Extensions**

* 1a. There is no person with the provided UID.

    * 1a1. NetConnect shows an error message.

      Use case ends.

**Use case: UC08 - Delete a Person by Name**

**MSS**

1. User requests to delete a specific person by name.
2. NetConnect deletes the person.

   Use case ends.

**Extensions**

* 1a. There are no persons with the provided name.

    * 1a1. NetConnect shows an error message.

      Use case ends.

* 1b. There are more than one person with the provided name.

    * 1b1. NetConnect !!list the persons with matching name (UC2)!!.
    * 1b2. User selects an UID from the list.

1. User requests for the list of persons matching a name.
2. NetConnect shows a list of persons with matching name.

**Use case: UC09 - Tag a Person by UID with Custom Tag**

**MSS**

1. User requests to tag a specific person by UID with a custom tag.
2. NetConnect tags the person with given custom tag.

   Use case ends.

**Extensions**

* 1a. There is no person with the provided UID.

    * 1a1. NetConnect shows an error message.

      Use case ends.

**Use case: UC10 - Tag a Person by Name with Custom Tag**

**MSS**

1. User requests to tag a specific person by name with a custom tag.
2. NetConnect tags the person with given custom tag.

   Use case ends.

**Extensions**

* 1a. There are no persons with the provided name.

    * 1a1. NetConnect shows an error message.

      Use case ends.

* 1b. There are more than one person with the provided name.

    * 1b1. NetConnect !!list the persons with matching name (UC2)!!.
    * 1b2. User selects an UID from the list.

      Use case resumes at step 2.

**Use case: UC11 - Edit Person Information by UID**

**MSS**

1. User requests to edit the information of a specific person by UID.
2. NetConnect edit the person information.

   Use case ends.

**Extensions**

* 1a. There is no person with the provided UID.

    * 1a1. NetConnect shows an error message.

      Use case ends.

* 1b. Some given arguments are invalid.

    * 1b1. NetConnect shows an error message.

      Use case ends.

**Use case: UC12 - Export Contact List to CSV File**

**MSS**

1. User requests to export contact list to CSV file with a given filename.
2. NetConnect creates a CSV file with the contact list data.

   Use case ends.

**Extensions**

* 1a. The given filename is invalid.

    * 1a1. NetConnect shows an error message.

      Use case ends.

* 1b. The contact list is empty.

    * 1b1. NetConnect shows an error message.

      Use case ends.

**Use case: UC13 - Relate two contacts together**

**MSS**

1. User requests to relate Contact A with Contact B.
2. NetConnect adds a relation between the two contacts.

**Extensions**

* 1a. The given ID does not exist.

    * 1a1. NetConnect shows an error message.

      Use case ends.

* 1b. The contact list is empty.

    * 1b1. NetConnect shows an error message.

      Use case ends.

* 1c. There is an ambiguous command.

    * 1c1. NetConnect shows an error message requesting to fix ambiguity.

      Use case ends.

* 1c. User relates contact to the same contact.

    * 1c1. NetConnect shows an error message.

      Use case ends.

  Use case ends.

**Use case: UC14 - View all contacts related to a single contact**

**MSS**

1. User requests to see all contacts related to Contact A.
2. NetConnect shows the list of related contacts.

**Extensions**

* 1a. The given ID does not exist.

    * 1a1. NetConnect shows an error message.

      Use case ends.

* 1b. The contact list is empty.

    * 1b1. NetConnect shows an error message.

      Use case ends.

  Use case ends.

**Use case: UC15 - Unrelate two contacts**

**MSS**

1. User requests to unrelate Contact A with Contact B.
2. NetConnect removes the relation between the two contacts.

**Extensions**

* 1a. The given ID does not exist.

    * 1a1. NetConnect shows an error message.

      Use case ends.

* 1b. The contact list is empty.

    * 1b1. NetConnect shows an error message.

      Use case ends.

* 1c. There is an ambiguous command.

    * 1c1. NetConnect shows an error message requesting to fix ambiguity.

      Use case ends.

* 1c. User unrelates contact to the same contact.

    * 1c1. NetConnect shows an error message.

      Use case ends.

  Use case ends.

**Use case: UC16 - Clear all contact list**

**MSS**

1. User requests to clear all contacts.
2. NetConnect requests confirmation from user.
3. User confirms the request.
4. NetConnect deletes the entire contact list.

   Use case ends.

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2. Should be able to hold up to 500 _persons_ without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. All user operations (excluding export to CSV) should complete within 200 milliseconds.
5. The codebase should follow a [given set of coding style](https://se-education.org/guides/conventions/java/intermediate.html) and should be well documented.
6. Should provide friendlier syntax and command _aliases_ for advanced users to complete tasks quicker.
7. Should display clear error messages for invalid inputs and failed operations, stating the correct command format or inputs required.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Persons**: Any person in the contact list. Includes all employees, clients and partners.
* **State**: The page that displays the results from the last command given.
* **UID**: A unique numerical identifier for any person added in NetConnect.
* **CSV file**: A plain text file format that uses commas to separate values, and newlines to separate records.
* **Alias**: A shortcut name/format for commands.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete i/1`<br>
      Expected: Contact with id 1 is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 1`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete i/x`, `...` (where no persons have id x)<br>
      Expected: Similar to previous.

   1. Guarantees: Person with the specified id will be deleted from NetConnect and removed from the displayed list.

1. Deleting a person while displayed list is filtered

   1. Prerequisites: Filter displayed list using `find` command or one of its variants. Partial contact list displayed.

   1. Test case: `delete i/x` (where x is the id of person displayed in the list)<br>
      Expected: Contact with id x is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete i/y` (where y is the id of a person **not** displayed in the list)<br>
      Expected: Contact with id x is deleted from NetConnect, and displayed list displays the full list of persons in NetConnect (without the person with id y. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 1`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete i/z`, `...` (where no persons have id z)<br>
      Expected: Similar to previous.

   1. Guarantees: Person with the specified id will be deleted from NetConnect and removed from the displayed list. Full unfiltered list of persons will be displayed (similar to when `list` command is entered).

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_
