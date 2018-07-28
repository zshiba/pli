(define contain (lambda (l e) (cond ((eq l ()) nil)
                                    ((eq (car l) e) t)
                                    (t (contain (cdr l) e)))))

(define unique (lambda (l) (cond ((eq l ()) ())
                                 ((contain (cdr l) (car l)) (unique (cdr l)))
                                 (t (cons (car l) (unique (cdr l)))))))

(unique (quote (1 2 3 1 2 3)))

(unique (quote (a 1 b 2 c 3 a b c)))

(unique (quote ((a . 1) (b . 2) (c . 3) (b . 2))))

(unique (quote ()))

(unique (quote (a b c d e)))
