# Optical Character Recognizer

Optical Character Recognizer is a JAVA executable (JAR). It converts handwritten characters into machine-encoded characters using a multilayer artificial neural network system.

![Optical Character Recognizer preview](img/preview.png)

## Requirements

Building the Sudoku Solver executable requires the following tools:
- X.org graphic system (see [www.x.org](https://www.x.org/wiki/))
```bash
sudo apt-get install xorg
```
- OpenJDK (see [openjdk.java.net](https://openjdk.java.net))
```bash
sudo apt-get install openjdk-8-jre
```
- Fast Artificial Neural Network library (see [FANN website](http://leenissen.dk/fann/wp/))
```bash
sudo apt-get install libfann-dev
```

## Compilation

To build the executable, use:

```bash
make all
```

## Usage

To launch the Java executable, use:
```bash
make run
```
Then, select an BMP image (available in [`img/`](img/)) and click on the "Start" button :

<img src="img/browse_preview.png" width="300" title="Optical Character Recognizer browse preview">

## License

Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses/). See [`LICENSE`](LICENSE) for more information.
