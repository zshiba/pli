# PLI
PLI is a Pure Lisp interpreter written in Java. (work in progress)

## Getting Started
1. Download the zip of this repository, and then unzip it. Or, clone the repository with the git command.
```bash
$ git clone https://github.com/zshiba/pli.git
```
2. Move to the **pli** directory.
3. Compile the source.
```bash
$ javac -d . -cp ./src ./src/PLI.java
```
4. Run the interpreter. (Start REPL.)
```bash
$ java PLI
>> 
```
5. The source can go across multiple lines. To indicate the end of the source, send a EOF (End Of File) control (Ctrl_D). See "Limitation" below.
```bash
$ java PLI
>> (quote a)
(quote b)
# Note: After writing code, hit the enter key and then send Ctrl+D.
=> a

=> b

>> 
```



## Limitation
To indicate the end of the source with stdin, it is necessary to enter a newline character (hit the enter key) at the end of the source and then send a EOF control (Ctrl+D).

## Development Environment
Mac, JDK 1.8

## License
MIT
