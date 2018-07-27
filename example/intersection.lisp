(define contain
        (lambda (l e) (cond ((eq l ()) nil)
                            ((eq (car l) e) t)
                            (t (contain (cdr l) e)))))

(define intersection
        (lambda (s1 s2) (cond ((eq s1 ()) ())
                              ((eq s2 ()) ())
                              ((contain s2 (car s1)) (cons (car s1) (intersection (cdr s1) s2)))
                              (t (intersection (cdr s1) s2)))))


(intersection (quote (1 2 3)) (quote (2 3 4)))

(intersection (quote (1 3 5 7 9)) (quote (0 2 4 6 8)))

(intersection (quote ((a . 1) (b . 1) (c . 1))) (quote ((a . 1) (b . 2) (c . 1) (d . 1))))
