Datasketches memory multirelease test
=====================================

Tests a multirelease version of the Apache datasketches-memory project, using JPMS features.

How it works:
-------------

The application is a simple JPMS modular application, that depends on three other modules:

- mrjar
- org.slf4j
- datasketches-memory-multirelease

The application uses the mods directory as the module-path.

Setting up your IDE
-------------------

Add the following VM flags to your launch configuration:

```
--add-opens
java.base/jdk.internal.ref=org.apache.datasketches.memory
```

Use Main as the entrypoint to the application.

Compiling using JAVAC
---------------------

From the root directory, in the shell execute `compile.sh`:

```
> compile.sh

--- CLEAN & COMPILE ---
--- JAR ---
```

Running the JAR
---------------

From the root directory, in the shell execute `run.sh`:

```
./run.sh
--- RUN ---
method A
Passed on-heap array test
Passed simple BB test
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
Passed simple allocate direct
```
