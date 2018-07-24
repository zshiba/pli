# PLI
PLI is a **Pure Lisp** interpreter written in Java. (Take a look at [examples](https://github.com/zshiba/pli/tree/master/example/).)  
(This project is still a work in progress. Please find the "ToDos" and "Limitation" section at the bottom of this page for more details.)

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
```
$ java PLI
>> 
```
5. The source can go across multiple lines. To indicate the end of the source, send a EOF (End Of File) control (Ctrl+D). See the "Limitation" section below for more details.
```lisp
$ java PLI
>> (quote a)
(quote b)
# Note: After writing code, hit the enter key and then hit Ctrl+D.
=> a

=> b
```

## Constant Literals and the Empty List
### t , nil, ()
```lisp
>> t
=> t

>> nil
=> nil

>> ()
=> nil
```
## Special Forms
### quote
```lisp
>> (quote a)
=> a

>> (quote (a b c))
=> (a b c)
```
### define
```lisp
>> a       
** eval error: Undefined: a

>> (define a (quote a))
=> a

>> a       
=> a

>> (define b a)
=> b

>> b
=> a
```
### lambda
```lisp
>> (lambda (x) x) 
=> (procedure)

>> ((lambda (x) x) (quote a))
=> a
```
### cond
```lisp
>> (cond (nil (quote a)) (t (quote b)))
=> b

>> (cond (nil (quote a))                                                  
         (nil (quote b))
         (t   (quote c)))
=> c
```

## Primitive functions
### eq
```lisp
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

>> (eq (quote a) (quote a))
=> t
```
### atom
```lisp
>> (atom t)
=> t

>> (atom nil)
=> t

>> (atom ())
=> t

>> (atom a)
** eval error: Undefined: a

>> (atom (quote a))
=> t

>> (atom (quote ()))
=> t

>> (atom (cons (quote a) (quote b)))
=> nil

>> (atom (quote (a b)))
=> nil

>> (atom (cons (quote a) (cons (quote b) ())))
=> nil
```
### cons
```lisp
>> (cons (quote a) (quote b))
=> (a . b)

>> (cons (quote a) nil)
=> (a)

>> (cons (quote a) ())
=> (a)

>> (cons (quote a) (cons (quote b) nil))
=> (a b)

>> (cons (cons (quote a) nil) (quote b))
=> ((a) . b)

>> (cons (quote a) (cons (quote b) (quote c)))
=> (a b . c)

>> (cons (quote a) (cons (quote b) (cons (quote c) nil)))
=> (a b c)
```
### car
```lisp
>> (car (cons (quote a) (quote b)))
=> a

>> (car (cons (quote a) (cons (quote b) (quote c))))
=> a

>> (car (cons (cons (quote a) (quote b)) (quote c)))
=> (a . b)
```
### cdr
```lisp
>> (cdr (cons (quote a) (quote b)))
=> b

>> (cdr (cons (quote a) (cons (quote b) (quote c))))
=> (b . c)

>> (cdr (cons (cons (quote a) (quote b)) (quote c)))
=> c
```

## ToDos
- [ ] Update Parser.java to accept dot notation form as input
- [ ] (Reconsider the abstraction. Is procedure s-expression?)
- [ ] Make error handling robust
- [ ] Refactor Evaluator.java

## Limitation
- To indicate the end of the source with stdin, it is necessary to enter a newline character (hit the enter key) at the end of the source and then send a EOF control (Ctrl+D).
- Currently, input source must be written as the list notation form, (not the dot notation form).

## Development Environment
Mac, JDK 1.8

## License
MIT

## References
- [(How to Write a (Lisp) Interpreter (in Python))](http://norvig.com/lispy.html)
- [The Roots of Lisp](http://www.paulgraham.com/rootsoflisp.html)
- [RECURSIVE FUNCTIONS OF SYMBOLIC EXPRESSIONS AND THEIR COMPUTATION BY MACHINE (Part I) (12-May-1998)](http://www-formal.stanford.edu/jmc/recursive.html)
