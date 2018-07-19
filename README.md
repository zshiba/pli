# PLI
PLI is a **Pure Lisp** interpreter written in Java.
(This project is still a work in progress.)

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
5. The source can go across multiple lines. To indicate the end of the source, send a EOF (End Of File) control (Ctrl+D). See the "Limitation" section below for more details.
```bash
$ java PLI
>> (quote a)
(quote b)
# Note: After writing code, hit the enter key and then send Ctrl+D.
=> a

=> b
```

## Note
### t , nil, ()
```
>> t
=> t

>> nil
=> nil

>> ()
=> nil
```
### quote
```
>> (quote a)
=> a

>> (quote (a b c))
=> (a b c)
```
### define
```
>> (define a (quote a))
=> a

>> a       
=> a
```
### lambda
```
>> (lambda (x) (quote x))
=> (procedure)

>> ((lambda (x) (quote x)) (quote a))
=> x
```
### cond
```
>> (cond (nil (quote a)) (t (quote b)))
=> b

>> (cond (nil (quote a))                                                  
         (nil (quote b))
         (t   (quote c)))
=> c
```
### eq
```
>> (eq t t)
=> t

>> (eq t nil)
=> nil

>> (eq nil ())
=> t

>> (eq () nil)
=> t

>> (eq t ())
=> nil

>> (eq (quote a) (quote b))
=> nil
```
### atom
```
>> 
```
### cons
```
>> 
```
### car
```
>> 
```
### cdr
```
>> 
```

## ToDos
- [ ] Update Parser.java to accept dot notation form as input
- [ ] Update Procedure.java (Impliment ToDos)
- [ ] (Reconsider the abstraction. Is procedure s-expression?)
- [ ] Add test cases
- [ ] Make error handlings robust
- [ ] Refactor Evaluator.java

## Limitation
- To indicate the end of the source with stdin, it is necessary to enter a newline character (hit the enter key) at the end of the source and then send a EOF control (Ctrl+D).
- Currently, input source must be written as the list notation form, (not the dot notation form).

## Development Environment
Mac, JDK 1.8

## License
MIT
