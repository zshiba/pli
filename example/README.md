# (PLI) Pure Lisp Interpreter Examples

### caar, cadr, cdar, cddr
```lisp
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
```lisp
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
```lisp
>> (define append (lambda (xs ys) (cond ((eq xs ()) ys)
                                        (t (cons (car xs) (append (cdr xs) ys))))))
=> append

>> (append (quote (a b c)) (quote (d e f)))
=> (a b c d e f)

>> (append (cons (quote a) (cons (quote b) nil)) (cons (quote c) (cons (quote d) (cons (quote e) nil))))
=> (a b c d e)
```

### contain (if the list contains the element)
```lisp
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
```lisp
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
```lisp
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

### zip (two lists into one)
```lisp
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

### list (crests a list in which the send argument can be an atom)
```lisp
>> (define list (lambda (x y) (cons x (cons y ()))))
=> list

>> (list (quote a) (quote b))
=> (a b)

>> (list (quote a) ())
=> (a nil)

>> (list (quote a) (quote (b c)))
=> (a (b c))

>> (cons (quote a) (quote b))
=> (a . b)

>> (cons (quote a) ())
=> (a)

>> (cons (quote a) (quote (b c)))
=> (a b c)
```
