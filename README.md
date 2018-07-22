# PLI
PLI is a **Pure Lisp** interpreter written in Java. (This project is still a work in progress.)

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
```
$ java PLI
>> (quote a)
(quote b)
# Note: After writing code, hit the enter key and then hit Ctrl+D.
=> a

=> b
```

## Constant Literals and the Empty List
### t , nil, ()
```
>> t
=> t

>> nil
=> nil

>> ()
=> nil
```
## Special Forms
### quote
```
>> (quote a)
=> a

>> (quote (a b c))
=> (a b c)
```
### define
```
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
```
>> (lambda (x) x) 
=> (procedure)

>> ((lambda (x) x) (quote a))
=> a
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
## Primitive functions
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

>> (eq (quote a) (quote a))
=> t
```
### atom
```
>> (atom t)
=> t

>> (atom nil)
=> t

>> (atom ())
=> t
```
### cons
```
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
```
>> (car (cons (quote a) (quote b)))
=> a

>> (car (cons (quote a) (cons (quote b) (quote c))))
=> a

>> (car (cons (cons (quote a) (quote b)) (quote c)))
=> (a . b)
```
### cdr
```
>> (cdr (cons (quote a) (quote b)))
=> b

>> (cdr (cons (quote a) (cons (quote b) (quote c))))
=> (b . c)

>> (cdr (cons (cons (quote a) (quote b)) (quote c)))
=> c
```

## Exmaples
### caar, cadr, cdar, cddr
```
>> (define numbers (cons (cons (quote 1) (quote 2)) (cons (quote 3) (quote 4))))
=> numbers

>> numbers
=> ((1 . 2) 3 . 4)

>> (define caar (lambda (l) (car (car l))))
=> caar

>> (define cadr (lambda (l) (car (cdr l))))
=> cadr

>> (define cdar (lambda (l) (cdr (car l))))    
=> cdar

>> (define cddr (lambda (l) (cdr (cdr l))))
=> cddr

>> (car numbers)
=> (1 . 2)

>> (cdr numbers)
=> (3 . 4)

>> (caar numbers)
=> 1

>> (cadr numbers)
=> 3

>> (cdar numbers)
=> 2

>> (cddr numbers)
=> 4
```
### reverse (a list by using an accumulator)
```
>> (define reverse (lambda (l a) (cond ((eq l nil) a) (t (reverse (cdr l) (cons (car l) a))))))
=> reverse

>> (define numbers (cons (quote 1) (cons (quote 2) (cons (quote 3) (cons (quote 4) (cons (quote 5) ()))))))
=> numbers

>> numbers
=> (1 2 3 4 5)

>> (reverse numbers ())
=> (5 4 3 2 1)

>> (reverse (quote (a b c d e f g)) ())
=> (g f e d c b a)
```
### append (one list to another)
```
>> (define append (lambda (xs ys) (cond ((eq xs ()) ys)
                                        (t (cons (car xs) (append (cdr xs) ys))))))
=> append

>> (append (quote (a b c)) (quote (d e f)))
=> (a b c d e f)

>> (append (cons (quote a) (cons (quote b) nil)) (cons (quote c) (cons (quote d) (cons (quote e) nil))))
=> (a b c d e)
```
### contain (if the list contatins the element)
```
>> (define contain (lambda (l e) (cond ((eq l ()) nil)
                                       ((eq (car l) e) t)
                                       (t (contain (cdr l) e)))))
=> contain

>> (define l (quote (a 2 c 4 e 6 g)))
=> l

>> l
=> (a 2 c 4 e 6 g)

>> (contain l (quote a))
=> t

>> (contain l (quote 1))
=> nil

>> (contain l (quote 6))
=> t

>> (contain l (quote 7))
=> nil
```
### filter (elements in a list with a predicate)
```
>> (define filter (lambda (p l) (cond ((eq l ()) ())
                                      ((p (car l)) (cons (car l) (filter p (cdr l))))
                                      (t (filter p (cdr l))))))
=> filter

>> (filter (lambda (x) (eq x (quote 1))) (quote (1 2 1 2 1)))
=> (1 1 1)

>> (filter (lambda (x) (eq x (quote 2))) (quote (1 2 1 2 1)))
=> (2 2)

>> (filter (lambda (x) (eq x (quote 3))) (quote (1 2 1 2 1)))
=> nil

>> (define pred (lambda (x) (cond ((eq x (quote 2)) t)
                                  ((eq x (quote 4)) t)
                                  ((eq x (quote 6)) t)
                                  ((eq x (quote 8)) t)
                                  (t nil))))
=> pred

>> (filter pred (quote (1 2 3 4 5 6 7 8 9)))
=> (2 4 6 8)
```
### drop (elements in a list with a predicate)
```
>> (define drop (lambda (p l) (cond ((eq l ()) ())
                                    ((p (car l)) (drop p (cdr l)))
                                    (t (cons (car l) (drop p (cdr l)))))))
=> drop

>> (drop (lambda (x) (eq x (quote 1))) (quote (1 2 1 2 1)))
=> (2 2)

>> (drop (lambda (x) (eq x (quote 2))) (quote (1 2 1 2 1)))
=> (1 1 1)

>> (drop (lambda (x) (eq x (quote 3))) (quote (1 2 1 2 1)))  
=> (1 2 1 2 1)

>> (define pred (lambda (x) (cond ((eq x (quote 2)) t)
                                  ((eq x (quote 4)) t)
                                  ((eq x (quote 6)) t)
                                  ((eq x (quote 8)) t)
                                  (t nil))))
=> pred

>> (drop pred (quote (1 2 3 4 5 6 7 8 9)))
=> (1 3 5 7 9)
```
### zip (two lists)
```
>> (define zip (lambda (xs ys) (cond ((eq xs ()) ())
                                     ((eq ys ()) ())
                                     (t (cons (cons (car xs) (cons (car ys) nil)) (zip (cdr xs) (cdr ys)))))))
=> zip

>> (zip (quote (a b c)) (quote (1 2 3)))
=> ((a 1) (b 2) (c 3))

>> (zip (quote (a b c)) ())
=> nil

>> (zip () (quote (1 2 3)))
=> nil

>> (zip (quote (a b c)) (quote (1 2)))
=> ((a 1) (b 2))

>> (zip (quote (a b)) (quote (1 2 3)))
=> ((a 1) (b 2))
```
## ToDos
- [ ] Update Parser.java to accept dot notation form as input
- [ ] Update Procedure.java (Impliment ToDos)
- [ ] (Reconsider the abstraction. Is procedure s-expression?)
- [ ] Find the definition of atom()
- [ ] Add examples
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

## References
- [(How to Write a (Lisp) Interpreter (in Python))](http://norvig.com/lispy.html)
- [The Roots of Lisp](http://www.paulgraham.com/rootsoflisp.html)
- [RECURSIVE FUNCTIONS OF SYMBOLIC EXPRESSIONS AND THEIR COMPUTATION BY MACHINE (Part I) (12-May-1998)](http://www-formal.stanford.edu/jmc/recursive.html)
