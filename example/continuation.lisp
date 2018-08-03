(define c (lambda (l pred succ fail) (cond ((eq l ()) (fail))
                                           ((pred (car l)) (succ (car l)))
                                           (t (c (cdr l) pred succ fail)))))

(define find (lambda (l e) (c l
                              (lambda (x) (eq (car x) e))
                              (lambda (x) (cdr x))
                              (lambda () (quote not_found)))))

(find (quote ((a . 1) (b . 2) (c . 3))) (quote a))

(find (quote ((a . 1) (b . 2) (c . 3))) (quote b))

(find (quote ((a . 1) (b . 2) (c . 3))) (quote c))

(find (quote ((a . 1) (b . 2) (c . 3))) (quote something))
