(define filter (lambda (p l) (cond ((eq l ()) ())
                                   ((p (car l)) (cons (car l) (filter p (cdr l))))
                                   (t (filter p (cdr l))))))

(filter (lambda (x) (eq x (quote 1))) (quote (1 2 1 2 1)))

(filter (lambda (x) (eq x (quote 2))) (quote (1 2 1 2 1)))

(filter (lambda (x) (eq x (quote 3))) (quote (1 2 1 2 1)))


(define pred (lambda (x) (cond ((eq x (quote 2)) t)
                               ((eq x (quote 4)) t)
                               ((eq x (quote 6)) t)
                               ((eq x (quote 8)) t)
                               (t nil))))

(filter pred (quote (1 2 3 4 5 6 7 8 9)))
