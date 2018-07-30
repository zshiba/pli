(define contain
        (lambda (l e) (cond ((eq l ()) nil)
                            ((eq (car l) e) t)
                            (t (contain (cdr l) e)))))

(define symmetric-difference_r
        (lambda (s1 s2 h1 h2 a) (cond ((eq s1 ()) (cond ((eq s2 ()) a)
                                                        (t (cond ((contain h1 (car s2)) (symmetric-difference_r s1 (cdr s2) h1 h2 a))
                                                                 (t (symmetric-difference_r s1 (cdr s2) h1 h2 (cons (car s2) a)))))))
                                      (t (cond ((contain h2 (car s1)) (symmetric-difference_r (cdr s1) s2 h1 h2 a))
                                               (t (symmetric-difference_r (cdr s1) s2 h1 h2 (cons (car s1) a))))))))

(define symmetric-difference
        (lambda (s1 s2) (symmetric-difference_r s1 s2 s1 s2 ())))

(symmetric-difference (quote ()) (quote ()))

(symmetric-difference (quote (1 2 3)) (quote (2 3 4)))

(symmetric-difference (quote (1 3 5 7 9)) (quote (0 2 4 6 8)))

(symmetric-difference (quote ((a . 1) (b . 1) (c . 1))) (quote ((a . 1) (b . 2) (c . 1) (d . 1))))

(symmetric-difference (quote (a b)) (quote ()))

(symmetric-difference (quote ()) (quote (a b)))
