# Optimising Parameters Used by Reinforcement Learning Algorithms

This repository contains all the code written for my 3rd year project, which was supervised by [Dr. Irina Voiculescu](https://www.cs.ox.ac.uk/irina.voiculescu/).

## How-to guide

The project uses Bazel, an open-source build tool created by Google. Instructions on how to download and use Bazel can be found on its [website](https://bazel.build/) and [GitHub repository](https://github.com/bazelbuild/bazel).

### Building the project

After installing Bazel, you can build the entire project by running the following command in your terminal:
```
bazel build ...:all
```

41 targets should be found and you should see something like this:
```
$ bazel build ...:all
Loading:
Loading: 0 packages loaded
INFO: Analysed 41 targets (0 packages loaded).
INFO: Found 41 targets...
// Information about building/compiling
INFO: Elapsed time: 24.962s, Critical Path: 6.75s
INFO: Build completed successfully, 54 total actions
```

### Running tests

Before running any experiments, it would be a good idea to make sure that all tests pass. You can do this by running:
```
bazel test ...:all
```

19 test targets should be found and you should see something like this:
```
$ bazel test ...:all
Loading:
Loading: 0 packages loaded
INFO: Analysed 41 targets (0 packages loaded).
INFO: Found 22 targets and 19 test targets...
# Information about building (or "Building: no action" if you just built the
# whole project) followed by information about testing
//src/test/java/com/games/agents:montecarlo_compute_cdf                  PASSED in 6.5s
# ...
//src/test/java/com/games/nim:state                                      PASSED in 5.3s

Executed 19 out of 19 tests: 19 tests pass.
```

If any of the tests fail, one or more files called `test.log` will be created locally and their file paths will be printed in your terminal. You can copy/paste these file paths into a browser to see debugging information, including which tests failed and which specific assertion statements caused the failure(s).

If all you've done is clone this repository, all the tests should pass. If they don't, you're welcome to send me a pull request with a fix or open an issue [here](https://github.com/cvlasov/rl-games/issues) :)

### Running experiments

The interesting, interactive parts of this project are the experiments that live in the `src/main/java/com/games/experiments` directory. Everything is explained in detail in my project report (in the `report` directory).

In order to run a particular experiment on a particular game implementation, all you need to do is run `blaze run` with the relevant `java_binary` target from `src/main/java/com/games/experiments/BUILD`. Section 4.1 of the report describes the two types of experiments (epsilon experiments and convergence experiments) and Chapter 3 describes all the implementations of the games (Tic-Tac-Toe, Nim, and Chung Toi).

Play around with the experiments and their parameters and see what results you get! The example below gives more details about how to do so.

*Keep in mind that if you use very large values for the number of training and/or testing games, the experiments can take a very long time to run. For instance, running experiments where Chung Toi is played millions of times takes several hours.*

#### Example

Suppose you want to run the epsilon experiment for Nim. You need to follow these steps:
1. Open the file `src/main/java/com/games/experiments/NimEpsilonExperiment.java`.
2. Adjust the parameters passed to the `saveEpsilonTestResultsInCSV` method called from `main` to try out whatever you are interested in.
3. Run the experiment by executing:
    ```
    bazel run src/main/java/com/games/experiments:epsilon_nim
    ```
4. View the results by finding the newly created CSV file in your current directory. The file's name will contain some information about the experiment setup, for instance `Nim_EpsilonResults_1000TrainingGames_10000TestGames.csv`.
5. **[Optional]** Open the CSV file using Microsoft Excel (for example), select all the data, and use it to create a graph in order to visualise the results.

A few notes about Step 3:    
   - The first thing this command does is build the target called `epsilon_nim` declared in `src/main/java/com/games/experiments/BUILD`. Since this is a `java_binary` target (more details [here](https://docs.bazel.build/versions/master/be/java.html#java_binary)), building it means locally generating a file called `epsilon_nim.jar`. On Windows, a file called `epsilon_nim.exe` is also generated. You will see the paths to these files in the output from the command you just ran.
      - On Windows, these paths will look something like this:
          ```
          C:/users/{YOUR USERNAME}/appdata/local/temp/_bazel_{YOUR USERNAME}/8fghcxhr/execroot/__main__/bazel-out/x64_windows-fastbuild/bin/src/main/java/com/games/experiments/epsilon_nim.jar
          C:/users/{YOUR USERNAME}/appdata/local/temp/_bazel_{YOUR USERNAME}/8fghcxhr/execroot/__main__/bazel-out/x64_windows-fastbuild/bin/src/main/java/com/games/experiments/epsilon_nim.exe
          ```
   - The `epsilon_nim.jar` file that is generated can be run by you since it contains the class files and the direct dependencies of the experiment. However, you cannot send it to anyone else or run it elsewhere since it does not have access to indirect dependencies like OpenCSV. If you want to run the JAR elsewhere, then you can instead run:
      ```
      bazel run src/main/java/com/games/experiments:epsilon_nim_deploy.jar
      ```
   to produce a self-contained JAR file that can be run anywhere using `java -jar`.
   - **[Ignore this if you're not using Windows]** On Windows, `bazel run` will run `epsilon_nim.exe` after successfully building the target. You may get an error that looks like this:
       ```
       INFO: Running command line: 'C:/users/Bob Smith/appdata/local/temp/_bazel_Bob Smith}/8fghcxhr/execroot/__main__/bazel-out/x64_windows-fastbuild/bin/src/main/java/com/games/experiments/epsilon_nim.exe'
       'C:\users\Bob' is not recognized as an internal or external command,
       operable program or batch file.
       ERROR: Non-zero return code '1' from command: Process exited with status 1
       ```
   This is because Windows has issues handling file paths that contain whitespace. In this case, it will most likely be because your username (i.e. directory under `C:/Users/` on your computer) contains a space - for example "Bob Smith" like above. You can fix this by copy/pasting the file path of `epsilon_nim.exe`, surrounding the whole thing with double quotes (`"`), and running it.
