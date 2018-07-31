(define compose (lambda (f g) (lambda (x) (f (g x)))))

(define reverse_a (lambda (l a) (cond ((eq l nil) a)
                                      (t (reverse_a (cdr l) (cons (car l) a))))))

(define reverse (lambda (l) (reverse_a l ())))

(define contain (lambda (l e) (cond ((eq l ()) nil)
                                    ((eq (car l) e) t)
                                    (t (contain (cdr l) e)))))

(define unique (lambda (l) (cond ((eq l ()) ())
                                 ((contain (cdr l) (car l)) (unique (cdr l)))
                                 (t (cons (car l) (unique (cdr l)))))))

(define duplicate (lambda (l) (cond ((eq l ()) ())
                                    (t (cons (car l) (cons (car l) (duplicate (cdr l))))))))

(compose reverse unique)

(compose unique reverse)

(define l (quote (1 2 2 3 3 3 4 4 4 4 5 5 5 5 5)))

(unique l)

(reverse l)

(duplicate l)

((compose reverse unique) l)

((compose unique reverse) l)

((compose reverse duplicate) l)

((compose duplicate reverse) l)

(define complicated (compose duplicate (compose reverse unique)))

(complicated l)
