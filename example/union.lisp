(define not
        (lambda (p) (cond (p nil)
                          (t t))))

(define contain
        (lambda (l e) (cond ((eq l ()) nil)
                            ((eq (car l) e) t)
                            (t (contain (cdr l) e)))))

(define union
        (lambda (s1 s2) (cond ((eq s1 ()) s2)
                              ((eq s2 ()) s1)
                              ((not (contain s2 (car s1))) (cons (car s1) (union (cdr s1) s2)))
                              (t (union (cdr s1) s2)))))


(union (quote (1 2 3)) (quote (2 3 4)))

(union (quote (1 3 5 7 9)) (quote (0 2 4 6 8)))

(union (quote ((a . 1) (b . 1) (c . 1))) (quote ((a . 1) (b . 2) (c . 1) (d . 1))))
