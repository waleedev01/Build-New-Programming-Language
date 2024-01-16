# University Coursework: LPse Language Compiler

## Overview

This project encompasses the development of a compiler for the LPse language, a simple programming language designed for educational purposes. The compiler translates LPse code into assembly code for a hypothetical Simple Stack Machine.

---

## Project Structure

- `Grammar Definition`: Developed an LL(1) grammar for the LPse language.
- `Parser Implementation`: Implemented a recursive-descent parser in Java to analyze LPse syntax.
- `Compiler Construction`: Extended the parser to generate assembly code, adhering to the semantics of the LPse language.

---

## Contributions

### Grammar and Syntax Analysis

- Designed a context-free grammar that captures the essence of LPse language constructs.
- Ensured that the grammar adheres to LL(1) properties to facilitate straightforward parsing.

### Tokenization

- Created token definitions that map directly to the terminal symbols in the grammar.
- Ensured accurate and efficient lexical analysis by defining clear patterns for each token.

### Parsing Technique

- Utilized recursive-descent parsing methods to process LPse program inputs.
- Implemented a handcrafted parser without the aid of parser generation tools to gain a deeper understanding of parsing mechanics.

### Code Generation

- Developed code generation logic that converts parsed structures into Simple Stack Machine assembly instructions.
- Utilized an `emit` method for assembly code output to streamline the generation process.

### Testing and Validation

- Conducted rigorous testing using a variety of LPse source programs to validate the compiler's functionality.
- Utilized the LL1Check tool to confirm the LL(1) property of the language grammar.

---

## Technologies Used

- Java: The primary language used for developing the compiler.
- IntelliJ IDEA: The integrated development environment for project development and management.

---

## Building and Running

To build and run the compiler:

```sh
javac LPseCompiler.java
java LPseCompiler input.lpse output.ssma 
```

Replace input.lpse with your LPse source file and output.ssma with the desired output file for the assembly code.

## Reflections
This project provided invaluable insights into the workings of compilers and the importance of a well-defined grammar in language design. It was a challenging yet rewarding experience that honed my programming and problem-solving skills.


