# Optical Character Recognizer [![Build Status](https://travis-ci.org/kortescode/One-Time-Password-Generator.svg?branch=master)](https://travis-ci.org/kortescode/One-Time-Password-Generator)

Optical Character Recognizer is a Java executable (Jar). It converts handwritten characters into machine-encoded characters using a multilayer artificial neural network system.

<img src="img/previews/text_preview.png" width="500" title="Optical Character Recognizer text preview">

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

Browse a BMP image or draw characters:

<img src="img/previews/browse_preview.png" width="250" title="Optical Character Recognizer browse preview"> <img src="img/previews/draw_preview.png" width="250" title="Optical Character Recognizer draw preview">

Some BMP example are availbale in:
- [`img/letters/`](img/letters/)
- [`img/letters_small/`](img/letters_small/)
- [`img/numbers/`](img/numbers/)
- [`img/symbols/`](img/symbols/)
- [`img/texts/`](img/texts/)

<img src="img/previews/letter_preview.png" width="250" title="Optical Character Recognizer letter preview"> <img src="img/previews/symbol_preview.png" width="250" title="Optical Character Recognizer symbol preview">

### Constants

- Drawing pen stroke: `DRAWINGSTROKE` in [`Ocr.java`](src/main/Ocr.java)
- Drawing image width: `DRAWINGIMGWIDTH` in [`Ocr.java`](src/main/Ocr.java)
- Drawing image height: `DRAWINGIMGHEIGHT` in [`Ocr.java`](src/main/Ocr.java)
- Vertical space recognition sensibility: `MATRIX_SPACE_SENSI_Y` in [`ICharactersConstants.java`](src/bmp/ICharactersConstants.java)
- Horizontal space recognition sensibility: `MIN_SPACE_CHARS` in [`INeuralNetworkConstants.java`](src/fann/INeuralNetworkConstants.java)

## License

Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses/). See [`LICENSE`](LICENSE) for more information.
