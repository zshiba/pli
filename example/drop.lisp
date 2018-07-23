(define drop (lambda (p l) (cond ((eq l ()) ())
                                 ((p (car l)) (drop p (cdr l)))
                                 (t (cons (car l) (drop p (cdr l)))))))

(drop (lambda (x) (eq x (quote 1))) (quote (1 2 1 2 1)))

(drop (lambda (x) (eq x (quote 2))) (quote (1 2 1 2 1)))

(drop (lambda (x) (eq x (quote 3))) (quote (1 2 1 2 1)))


(define pred (lambda (x) (cond ((eq x (quote 2)) t)
                               ((eq x (quote 4)) t)
                               ((eq x (quote 6)) t)
                               ((eq x (quote 8)) t)
                               (t nil))))

(drop pred (quote (1 2 3 4 5 6 7 8 9)))
