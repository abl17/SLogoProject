# Description of the Interpreter (Parser) #

> Put any important information about the interpreter (parser) here

# Compilation Process: #

1. **Tokenization:** Compiler sees source program as a string of characters. Turn characters into symbols, so that later stages of compilation can deal with the program as a whole.
2. **Parser:** Recognizes certain patterns of symbols as representing meaningful units
3. **Code Generation:** Each unit that was recognized by the parser is actually translated into doable work (machine code, ...)

> **Important note:** Parsing and code generation do not happen separately. In other words, parsing and code generation do not happen one after the other in the compiler's algorithm. Each *meaningful* unit is translated as it is encountered.

> The translation of large units like procedures includes, **recursively**, the translation of smaller unit-like statements.

# Regular Expressions in Java (Regex): #

## Metacharacters: ##

> These are essentially characters with special meaning interpreted by the matcher.

**Metacharacters** supported: **<([{\^-=$!|]})?\*+.>**

# Design Decisions: #

For the tokenizer, we decided to separate on multiple spaces. Essentially, 3 spaces separating two tokens is equivalent to 1 space separating two tokens. Furthermore, we decided to infer that a new line is equivalent to a space, and essentially separates tokens. This is because in the 'examples' folder, the instructions are modeled this way.
