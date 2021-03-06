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

### list (crests a list in which the 2nd argument can be an atom)
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

### find (the value associated with the given key in a list of key-value pairs)
```lisp
>> (define find (lambda (k l) (cond ((eq k ()) ())
                                    ((eq l ()) ())
                                    ((eq k (car (car l))) (car (cdr (car l))))
                                    (t (find k (cdr l))))))
=> find

>> (define pairs (quote ((k1 v1) (k2 v2) (k3 v3) (k4 v4) (k5 v5))))
=> pairs

>> pairs
=> ((k1 v1) (k2 v2) (k3 v3) (k4 v4) (k5 v5))

>> (find (quote k3) pairs)
=> v3

>> (find (quote k4) pairs)
=> v4

>> (find (quote somthing) pairs)
=> nil
```

### update (the pair that matches the given key with the new value)
```lisp
>> (define update (lambda (k v l) (cond ((eq k ()) l)
                                        ((eq l ()) l)
                                        ((eq k (car (car l))) (cons (cons k (cons v ())) (cdr l)))
                                        (t (cons (car l) (update k v (cdr l)))))))
=> update

>> (define pairs (quote ((k1 v1) (k2 v2) (k3 v3) (k4 v4) (k5 v5))))
=> pairs

>> pairs
=> ((k1 v1) (k2 v2) (k3 v3) (k4 v4) (k5 v5))

>> (update (quote k1) (quote vvv111) pairs)
=> ((k1 vvv111) (k2 v2) (k3 v3) (k4 v4) (k5 v5))

>> (update (quote k2) (quote vvv222) pairs)
=> ((k1 v1) (k2 vvv222) (k3 v3) (k4 v4) (k5 v5))

>> (update (quote k5) (quote vvv555) pairs)
=> ((k1 v1) (k2 v2) (k3 v3) (k4 v4) (k5 vvv555))

>> (update (quote something) (quote something) pairs)
=> ((k1 v1) (k2 v2) (k3 v3) (k4 v4) (k5 v5))

>> (define updated_pairs (update (quote k3) (quote vvv333) pairs))
=> updated_pairs

>> updated_pairs
=> ((k1 v1) (k2 v2) (k3 vvv333) (k4 v4) (k5 v5))
```

### null
```lisp
>> (define null (lambda (e) (eq e nil)))
=> null

>> (null nil)
=> t

>> (null ())
=> t

>> (null t)
=> nil

>> (null (quote a))
=> nil

>> (null ((lambda (x) x) ()))
=> t
```

### and
```lisp
>> (define and (lambda (p1 p2) (cond (p1 (cond (p2 t)
                                               (t nil)))
                                     (t nil))))
=> and

>> (and t t)
=> t

>> (and t nil)
=> nil

>> (and () t)
=> nil

>> (and ((lambda (x) (eq x (quote a))) (quote a))
        ((lambda (x) (eq x (quote b))) (quote b)))
=> t

>> (define pred1 (lambda (x) (atom x)))
=> pred1

>> (define pred2 (lambda (x) (cond ((atom x) nil)
                                   (t t))))
=> pred2

>> (and (pred1 (quote a)) (pred2 (quote (b c))))
=> t

>> (and (pred1 (quote a)) (pred2 (quote b)))
=> nil
```

### or
```lisp
>> (define or (lambda (p1 p2) (cond (p1 t)
                                    (p2 t)
                                    (t nil))))
=> or

>> (or t t)
=> t

>> (or t nil)
=> t

>> (or nil t)
=> t

>> (or nil nil)
=> nil

>> (or ((lambda (x) (eq x (quote a))) (quote a))
       ((lambda (x) (eq x (quote b))) (quote b)))
=> t

>> (define pred1 (lambda (x) (atom x)))
=> pred1

>> (define pred2 (lambda (x) (cond ((atom x) nil)
                                   (t t))))
=> pred2

>> (or (pred1 (quote a)) (pred2 (quote (b c))))
=> t

>> (or (pred1 (quote a)) (pred2 (quote b)))
=> t

>> (or (pred1 (quote (a b))) (pred2 (quote c)))
=> nil
```

### not
```lisp
>> (define not (lambda (p) (cond (p nil)
                                 (t t))))
=> not

>> (not t)
=> nil

>> (not nil)
=> t

>> (not ())
=> t

>> (not (quote a))
=> t

>> (not (eq (quote a) (quote a)))
=> nil

>> (not (atom (quote a)))
=> nil

>> (not (atom (quote (a b))))
=> t
```

### map (applies the lambda to each element of the list)
```lisp
>> (define map (lambda (f l) (cond ((eq l ()) ())
                             (t (cons (f (car l)) (map f (cdr l)))))))
=> map

>> (map (lambda (e) (atom e)) (quote (1 2 3)))
=> (t t t)

>> (map (lambda (e) (atom e)) (quote (1 (2) 3)))
=> (t nil t)

>> (map (lambda (e) (car e)) (quote ((a . 1) (b . 2) (c . 3))))
=> (a b c)

>> (define wrap (lambda (e) (cons e ())))
=> wrap

>> (map wrap (quote (a b c d e)))
=> ((a) (b) (c) (d) (e))

>> (map wrap (quote ((a . b) c (d . e) f)))
=> (((a . b)) (c) ((d . e)) (f))
```

### intersection (of two sets)
```lisp
>> (define contain
           (lambda (l e) (cond ((eq l ()) nil)
                               ((eq (car l) e) t)
                               (t (contain (cdr l) e)))))
=> contain

>> (define intersection
           (lambda (s1 s2) (cond ((eq s1 ()) ())
                                 ((eq s2 ()) ())
                                 ((contain s2 (car s1)) (cons (car s1) (intersection (cdr s1) s2)))
                                 (t (intersection (cdr s1) s2)))))
=> intersection

>> (intersection (quote (1 2 3)) (quote (2 3 4)))
=> (2 3)

>> (intersection (quote (1 3 5 7 9)) (quote (0 2 4 6 8)))
=> nil

>> (intersection (quote ((a . 1) (b . 1) (c . 1))) (quote ((a . 1) (b . 2) (c . 1) (d . 1))))
=> ((a . 1) (c . 1))
```

### union (of two sets)
```lisp
>> (define not
           (lambda (p) (cond (p nil)
                             (t t))))
=> not

>> (define contain
           (lambda (l e) (cond ((eq l ()) nil)
                               ((eq (car l) e) t)
                               (t (contain (cdr l) e)))))
=> contain

>> (define union
           (lambda (s1 s2) (cond ((eq s1 ()) s2)
                                 ((eq s2 ()) s1)
                                 ((not (contain s2 (car s1))) (cons (car s1) (union (cdr s1) s2)))
                                 (t (union (cdr s1) s2)))))
=> union

>> (union (quote (1 2 3)) (quote (2 3 4)))
=> (1 2 3 4)

>> (union (quote (1 3 5 7 9)) (quote (0 2 4 6 8)))
=> (1 3 5 7 9 0 2 4 6 8)

>> (union (quote ((a . 1) (b . 1) (c . 1))) (quote ((a . 1) (b . 2) (c . 1) (d . 1))))
=> ((b . 1) (a . 1) (b . 2) (c . 1) (d . 1))
```

### set-difference (of two sets returns a set that consists of elements in s1 but not in s2)
```lisp
>> (define contain
           (lambda (l e) (cond ((eq l ()) nil)
                               ((eq (car l) e) t)
                               (t (contain (cdr l) e)))))
=> contain

>> (define set-difference
           (lambda (s1 s2) (cond ((eq s1 ()) ())
                                 ((contain s2 (car s1)) (set-difference (cdr s1) s2))
                                 (t (cons (car s1) (set-difference (cdr s1) s2))))))
=> set-difference

>> (set-difference (quote ()) (quote ()))
=> nil

>> (set-difference (quote (1 2 3)) (quote (2 3 4)))
=> (1)

>> (set-difference (quote (1 3 5 7 9)) (quote (0 2 4 6 8)))
=> (1 3 5 7 9)

>> (set-difference (quote ((a . 1) (b . 1) (c . 1))) (quote ((a . 1) (b . 2) (c . 1) (d . 1))))
=> ((b . 1))

>> (set-difference (quote (a b)) (quote ()))
=> (a b)

>> (set-difference (quote ()) (quote (a b)))
=> nil
```

### symmetric-difference (of two sets returns a set that consists of elements each of which is only in s1 or is only in s2)
```lisp
>> (define contain
           (lambda (l e) (cond ((eq l ()) nil)
                               ((eq (car l) e) t)
                               (t (contain (cdr l) e)))))
=> contain

>> (define symmetric-difference_r
           (lambda (s1 s2 h1 h2 a) (cond ((eq s1 ()) (cond ((eq s2 ()) a)
                                                           (t (cond ((contain h1 (car s2)) (symmetric-difference_r s1 (cdr s2) h1 h2 a))
                                                                    (t (symmetric-difference_r s1 (cdr s2) h1 h2 (cons (car s2) a)))))))
                                         (t (cond ((contain h2 (car s1)) (symmetric-difference_r (cdr s1) s2 h1 h2 a))
                                                  (t (symmetric-difference_r (cdr s1) s2 h1 h2 (cons (car s1) a))))))))
=> symmetric-difference_r

>> (define symmetric-difference
           (lambda (s1 s2) (symmetric-difference_r s1 s2 s1 s2 ())))
=> symmetric-difference

>> (symmetric-difference (quote ()) (quote ()))
=> nil

>> (symmetric-difference (quote (1 2 3)) (quote (2 3 4)))
=> (4 1)

>> (symmetric-difference (quote (1 3 5 7 9)) (quote (0 2 4 6 8)))
=> (8 6 4 2 0 9 7 5 3 1)

>> (symmetric-difference (quote ((a . 1) (b . 1) (c . 1))) (quote ((a . 1) (b . 2) (c . 1) (d . 1))))
=> ((d . 1) (b . 2) (b . 1))

>> (symmetric-difference (quote (a b)) (quote ()))
=> (b a)

>> (symmetric-difference (quote ()) (quote (a b)))
=> (b a)
```

### unique
```lisp
>> (define contain (lambda (l e) (cond ((eq l ()) nil)
                                       ((eq (car l) e) t)
                                       (t (contain (cdr l) e)))))
=> contain

>> (define unique (lambda (l) (cond ((eq l ()) ())
                                    ((contain (cdr l) (car l)) (unique (cdr l)))
                                    (t (cons (car l) (unique (cdr l)))))))
=> unique

>> (unique (quote (1 2 3 1 2 3)))
=> (1 2 3)

>> (unique (quote (a 1 b 2 c 3 a b c)))
=> (1 2 3 a b c)

>> (unique (quote ((a . 1) (b . 2) (c . 3) (b . 2))))
=> ((a . 1) (c . 3) (b . 2))

>> (unique (quote ()))
=> nil

>> (unique (quote (a b c d e)))
=> (a b c d e)
```

### duplicate
```lisp
>> (define duplicate (lambda (l) (cond ((eq l ()) ())
                                       (t (cons (car l) (cons (car l) (duplicate (cdr l))))))))
=> duplicate

>> (duplicate (quote (a b c d e)))
=> (a a b b c c d d e e)

>> (duplicate (quote ((a) (b) (c))))
=> ((a) (a) (b) (b) (c) (c))

>> (duplicate (quote ()))
=> nil
```

### compose (two lambdas, f(g(x)))
```lisp
>> (define compose (lambda (f g) (lambda (x) (f (g x)))))
=> compose

>> (define reverse_a (lambda (l a) (cond ((eq l nil) a)
                                         (t (reverse_a (cdr l) (cons (car l) a))))))
=> reverse_a

>> (define reverse (lambda (l) (reverse_a l ())))
=> reverse

>> (define contain (lambda (l e) (cond ((eq l ()) nil)
                                       ((eq (car l) e) t)
                                       (t (contain (cdr l) e)))))
=> contain

>> (define unique (lambda (l) (cond ((eq l ()) ())
                                    ((contain (cdr l) (car l)) (unique (cdr l)))
                                    (t (cons (car l) (unique (cdr l)))))))
=> unique

>> (define duplicate (lambda (l) (cond ((eq l ()) ())
                                       (t (cons (car l) (cons (car l) (duplicate (cdr l))))))))
=> duplicate

>> (compose reverse unique)
=> (procedure)

>> (compose unique reverse)
=> (procedure)

>> (define l (quote (1 2 2 3 3 3 4 4 4 4 5 5 5 5 5)))
=> l

>> (unique l)
=> (1 2 3 4 5)

>> (reverse l)
=> (5 5 5 5 5 4 4 4 4 3 3 3 2 2 1)

>> (duplicate l)
=> (1 1 2 2 2 2 3 3 3 3 3 3 4 4 4 4 4 4 4 4 5 5 5 5 5 5 5 5 5 5)

>> ((compose reverse unique) l)
=> (5 4 3 2 1)

>> ((compose unique reverse) l)
=> (5 4 3 2 1)

>> ((compose reverse duplicate) l)
=> (5 5 5 5 5 5 5 5 5 5 4 4 4 4 4 4 4 4 3 3 3 3 3 3 2 2 2 2 1 1)

>> ((compose duplicate reverse) l)
=> (5 5 5 5 5 5 5 5 5 5 4 4 4 4 4 4 4 4 3 3 3 3 3 3 2 2 2 2 1 1)

>> (define complicated (compose duplicate (compose reverse unique)))
=> complicated

>> (complicated l)
=> (5 5 4 4 3 3 2 2 1 1)
```

### Accessing values bounded to the outermost environment in a lambda
```lisp
>> (define a (quote a))
=> a

>> (define b (quote b))
=> b

>> ((lambda (x) (cons x (cons a (cons b nil)))) (quote x))
=> (x a b)
```

### Continuation Passing Style (CPS)
```lisp
>> (define c (lambda (l pred succ fail) (cond ((eq l ()) (fail))
                                              ((pred (car l)) (succ (car l)))
                                              (t (c (cdr l) pred succ fail)))))
=> c

>> (define find (lambda (l e) (c l
                                 (lambda (x) (eq (car x) e))
                                 (lambda (x) (cdr x))
                                 (lambda () (quote not_found)))))
=> find

>> (find (quote ((a . 1) (b . 2) (c . 3))) (quote a))
=> 1

>> (find (quote ((a . 1) (b . 2) (c . 3))) (quote b))
=> 2

>> (find (quote ((a . 1) (b . 2) (c . 3))) (quote c))
=> 3

>> (find (quote ((a . 1) (b . 2) (c . 3))) (quote something))
=> not_found
```
