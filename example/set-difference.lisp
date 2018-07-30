(define contain
        (lambda (l e) (cond ((eq l ()) nil)
                            ((eq (car l) e) t)
                            (t (contain (cdr l) e)))))

(define set-difference
        (lambda (s1 s2) (cond ((eq s1 ()) ())
                              ((contain s2 (car s1)) (set-difference (cdr s1) s2))
                              (t (cons (car s1) (set-difference (cdr s1) s2))))))

(set-difference (quote ()) (quote ()))

(set-difference (quote (1 2 3)) (quote (2 3 4)))

(set-difference (quote (1 3 5 7 9)) (quote (0 2 4 6 8)))

(set-difference (quote ((a . 1) (b . 1) (c . 1))) (quote ((a . 1) (b . 2) (c . 1) (d . 1))))

(set-difference (quote (a b)) (quote ()))

(set-difference (quote ()) (quote (a b)))
