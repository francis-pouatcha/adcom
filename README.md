adcom
=====

Adorsys Commerce Framework. This is a framwork for a modular building of applications that include:

- A rest backend
- Angularjs micro client
- Terminal management

Module Concept
==============


module.lib
==========

This stands for a shared library module. This is a jar that can be linked and used by other war modules. 

A module.lid can contain:
	- JPA Mapped super classes
    - JPA Entities. In order for a JPA to be loaded in a referencing application, it must be explicitly listed in the persistence.xml of the referenced module.
	- Corresponding Repository Classes: These a delta spike repositories used to manage JPA-Entities.
	- Corresponding EJB Classes: These are ejbs using repository classes to access to entities.

We use these modules to allow for data sharing among war module:
	- Sharing login and session state among war modules.
	- Persistent event mechanism among independent modules. Sort of event queue. A module can expose an event through the definition of an EventJPA-Klass and an EventListener-Klass.
		- Each Module interested in the Event can register a persistent listener.
		- Before sending an event, the producing module loads the list of listeners and create one instance of the event for each registered listener.
		- Worker threads in the consuming module regularly check the event table, load event destinated to them, process theses events and delete them.

adcore.lib
----------
general core module with utility classes.

adbase.lib
----------
general adbase shared library. Contains all database classes for the adbase server module. 

module.client
=============

This is an angularjs browser based client module. No server side logic.

adres.client
------------
Is used to stored files shared among all client modules. Because they all run on the save domain, client browser will never need to reload files when they switch among clients.

adlogin.client
--------------
This is the login client module. It provides the following functionality:
	- Login
	- Display a list of workspaces
	- Change password
	- See connection history
	- Logout
	
adbase.client
-------------
This is the client module used to manage organisations, orgUnits, logins and terminals.

module.server
=============

REST modules. Contains all rest interfaces used to service client modules.

adterm.server
-------------
Special module used to register terminals. Uses basic authentication help register terminals.

adbase.server
-------------
Server module for the management of organisations, orgUnits, logins and terminals.



